/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DAO.ContribuicaoDAO;
import model.DAO.DizimistaDAO;
import model.DAO.PlantonistaDAO;
import model.bean.Contribuicao;
import model.bean.Dizimista;
import model.bean.Plantao;
import model.bean.Plantonista;
import util.Mes;
import view.Alertas;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class ContribuicaoController implements Initializable {

    //botões
    @FXML
    private Button btCadastrarSalvar;
    @FXML
    private Button btEditarCancelar;
    @FXML
    private Button btApagar;
    @FXML
    private Label lbDtPlantao, lbHrPlantao, lbLancadorPlantao, lbPresidentePlantao;
    //dados da contribuição
    @FXML
    private TextField tfValor, tfIdDizimista;
    @FXML
    private Label lbNomeDizimista, lb1NomeDizimista;
    @FXML
    private ComboBox<Mes> cbMes;
    @FXML
    private ComboBox<Year> cbAno;
    @FXML
    private ComboBox<Plantonista> cbPlantonista;
    //dados da table View
    @FXML
    private TableView<Contribuicao> tableViewContribuicoes;
    @FXML
    private TableColumn<Contribuicao, Dizimista> tbDizimista;
    @FXML
    private TableColumn<Contribuicao, Integer> tbValor;
    @FXML
    private TableColumn<Contribuicao, Mes> tbMes;
    @FXML
    private TableColumn<Contribuicao, Year> tbAno;
    @FXML
    private TableColumn<Contribuicao, Plantonista> tbPlantonista;

    @FXML
    private TextField tfValorTotal;

    @FXML
    private AnchorPane apEsquerdo;

    private Plantao plantao;
    private List<Contribuicao> contribuicoes;
    private ObservableList<Contribuicao> obContribuicoes;
    private boolean cadastrar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        plantao = PlantaoController.getPlantao();
        carregarDadosPlantao();
        carregarTodos();
        tableViewContribuicoes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaContribuicao(newValue));
    }

    private void carregarDadosPlantao() {
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        lbDtPlantao.setText(formatoData.format(plantao.getData().toLocalDate()));
        lbHrPlantao.setText(formatoHora.format(plantao.getHora().toLocalTime()));
        lbLancadorPlantao.setText(plantao.getLancador().getNome());
        lbPresidentePlantao.setText(plantao.getPresidente().toString());
    }

    public void carregarTodos() {
        tbDizimista.setCellValueFactory(new PropertyValueFactory<>("dizimista"));
        tbValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        tbMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
        tbAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tbPlantonista.setCellValueFactory(new PropertyValueFactory<>("plantonista"));

        contribuicoes = ContribuicaoDAO.recuperar(plantao);

        obContribuicoes = FXCollections.observableArrayList(contribuicoes);
        tableViewContribuicoes.setItems(obContribuicoes);
        tfValorTotal.setText(String.valueOf(calcularValorTotal()));
    }

    private Double calcularValorTotal() {
        Double total = 0.0;
        total = contribuicoes.stream().map((c) -> c.getValor()).reduce(total, (accumulator, _item) -> accumulator + _item);
        return total;
    }

    private void selecionarItemTabelaContribuicao(Contribuicao c) {
        if (c != null) {
            lb1NomeDizimista.setVisible(true);
            lbNomeDizimista.setVisible(true);
            Dizimista d = c.getDizimista();
            if (d != null) {
                lbNomeDizimista.setText(d.getNome());
                tfIdDizimista.setText(c.getDizimista().getId().toString());
            } else {
                lbNomeDizimista.setText("Dizimista Removido");
                tfIdDizimista.setText("nulo");
            }
            tfValor.setText(c.getValor().toString());
            cbMes.setValue(c.getMes());
            cbAno.setValue(c.getAno());
            cbPlantonista.setValue(c.getPlantonista());
        } else {
            clear();
        }
    }

    private void selectMode(int i) {
        switch (i) {
            case 1:
                //busca
                carregarTodos();
                tfIdDizimista.setEditable(false);
                tfValor.setEditable(false);
                btCadastrarSalvar.setText("Cadastrar");
                btEditarCancelar.setText("Editar");
                btApagar.setVisible(true);
                apEsquerdo.setDisable(false);
                clear();
                break;
            case 2:
                //cadastrar
                clear();
                cadastrar=true;
                carregarComboBox();
                tfIdDizimista.setEditable(true);
                tfValor.setEditable(true);
                btCadastrarSalvar.setText("Salvar");
                btEditarCancelar.setText("Cancelar");
                btApagar.setVisible(false);
                apEsquerdo.setDisable(true);
                break;
            case 3:
                //editar
                carregarComboBox();
                cadastrar = false;
                tfIdDizimista.setEditable(true);
                tfValor.setEditable(true);
                btCadastrarSalvar.setText("Salvar");
                btEditarCancelar.setText("Cancelar");
                btApagar.setVisible(false);
                apEsquerdo.setDisable(true);
                break;
            default:
                break;
        }
    }

    public void editarMode() {
        Contribuicao c = tableViewContribuicoes.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar")) {
            if (c != null) {
                selectMode(3);
            } else {
                selectMode(2);
            }
        }

    }

    private void carregarComboBox() {
        cbMes.getItems().addAll(Arrays.asList(Mes.values()));
        Year a = Year.parse(String.valueOf(Year.now().getValue() - 1));
        Year b = Year.parse(String.valueOf(Year.now().getValue() + 1));
        cbAno.getItems().addAll(a, Year.now(), b);
        cbPlantonista.getItems().addAll(PlantonistaDAO.recuperar());
    }

    private void clear() {
        lbNomeDizimista.setVisible(false);
        lb1NomeDizimista.setVisible(false);
        lbNomeDizimista.setText("");
        tfIdDizimista.setText("");
        cbMes.setValue(null);
        cbMes.getItems().clear();
        cbAno.setValue(null);
        cbAno.getItems().clear();
        tfValor.setText("");
        cbPlantonista.setValue(null);
        cbPlantonista.getItems().clear();
    }

    public void cadastrarSalvar() {
        if (btCadastrarSalvar.getText().equals("Cadastrar")) {
            cadastrar = true;
            selectMode(2);
        } else {
            if (cadastrar) {
                salvar();
            } else {
                atualizar();
            }
        }

    }

    public void salvar() {
        if (validarCampos()) {
            Dizimista d = DizimistaDAO.recuperar(Integer.parseInt(tfIdDizimista.getText()));
            Plantonista p = cbPlantonista.getValue();
            Contribuicao c = new Contribuicao(Double.parseDouble(tfValor.getText()), cbMes.getValue(), cbAno.getValue(), d, p, plantao);
            ContribuicaoDAO.salvar(c);
            Alertas.cadastradoSucesso("Contribuição");
            selectMode(1);
        }
    }

    public void atualizar() {
        if (validarCampos()) {
            Dizimista d = DizimistaDAO.recuperar(Integer.parseInt(tfIdDizimista.getText()));
            Contribuicao c = tableViewContribuicoes.getSelectionModel().getSelectedItem();
            c.setDizimista(d);
            c.setPlantonista(cbPlantonista.getValue());
            c.setMes(cbMes.getValue());
            c.setAno(cbAno.getValue());
            c.setValor(Double.parseDouble(tfValor.getText()));
            ContribuicaoDAO.atualizar(c);
            Alertas.atualizadoSucesso("Contribuição");
            selectMode(1);
        }
    }

    public void editarCancelar() {
        Contribuicao c = tableViewContribuicoes.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar") && Alertas.validarSelecaoEntidade(c, "Contribuição")) {
            selectMode(3);
        } else {
            selectMode(1);
        }
    }

    public void apagar() {
        Contribuicao c = tableViewContribuicoes.getSelectionModel().getSelectedItem();
        if (Alertas.validarSelecaoEntidade(c, "Contribuição")) {
            if (Alertas.confirmarApagar("Contribuição")) {
                ContribuicaoDAO.apagar(c.getId());
                Alertas.apagadoSucesso("Contribuição");
                carregarTodos();
            }
        }
    }

    public void encerrarPlantao() {

    }

    private boolean validarCampos() {
        if (!Alertas.validarBuscaIdDizimista(tfIdDizimista.getText())) {
            tfIdDizimista.setText("");
            return false;
        }
        //mes ano valor plantonista
        if (!Alertas.validarSelecaoComboBox(cbMes.getValue(), "Mes")) {
            return false;
        }
        if (!Alertas.validarSelecaoComboBox(cbAno.getValue(), "Ano")) {
            return false;
        }
        if (!Alertas.validarValor(tfValor.getText())) {
            return false;
        } else {
            tfValor.setText(tfValor.getText().replace(',', '.'));
        }
        if (!Alertas.validarSelecaoComboBox(cbPlantonista.getValue(), "Plantonista")) {
            return false;
        }
        return true;
    }

    public void buscarDizimista() {
        if (tfIdDizimista.getText().length() == 4) {
            if (Alertas.validarBuscaIdDizimista(tfIdDizimista.getText())) {
                Dizimista d = DizimistaDAO.recuperar(Integer.parseInt(tfIdDizimista.getText()));
                if (d != null) {
                    lb1NomeDizimista.setVisible(true);
                    lbNomeDizimista.setVisible(true);
                    lbNomeDizimista.setText(d.getNome());
                }
            } else {
                lbNomeDizimista.setText("");
                lb1NomeDizimista.setVisible(false);
                lbNomeDizimista.setVisible(false);
            }
        }
    }

}
