/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DAO.PresidenteDAO;
import model.bean.Presidente;
import util.Denominacao;
import view.Alertas;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class PresidenteController implements Initializable {

    @FXML
    private TableView<Presidente> tableViewPresidentes;
    @FXML
    private TableColumn<Presidente, String> tbNome;
    @FXML
    private TableColumn<Presidente, Denominacao> tbDenominacao;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField barraBusca;
    @FXML
    private ComboBox<Denominacao> cbDenominacao;
    @FXML
    private Button btCadastrarSalvar;
    @FXML
    private Button btEditarCancelar;
    @FXML
    private Button btApagar;
    @FXML
    private AnchorPane apEsquerdo;

    private List<Presidente> presidentes;
    private ObservableList<Presidente> obPresidentes;
    private boolean cadastrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTodos();
        tableViewPresidentes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaPresidente(newValue));
    }

    public void carregarTodos() {
        tbNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbDenominacao.setCellValueFactory(new PropertyValueFactory<>("denominacao"));

        presidentes = PresidenteDAO.recuperar();

        obPresidentes = FXCollections.observableArrayList(presidentes);
        tableViewPresidentes.setItems(obPresidentes);
    }

    private void selecionarItemTabelaPresidente(Presidente p) {
        if (p != null) {
            tfNome.setText(p.getNome());
            cbDenominacao.setValue(p.getDenominacao());
        } else {
            tfNome.setText("");
            cbDenominacao.getItems().clear();
        }
    }

    public void cadastrarSalvar() {
        if (btCadastrarSalvar.getText().equals("Cadastrar")) {
            cbDenominacao.getItems().clear();
            tfNome.setText("");
            cbDenominacao.getItems().addAll(Denominacao.PE, Denominacao.DIAC, Denominacao.MINIS);
            cbDenominacao.setValue(Denominacao.PE);
            cadastrar = true;
            selectMode(2);
        } else {
            if (cadastrar) {
                Presidente presidente;
                String nome = tfNome.getText();
                if (!Alertas.validarNome(nome)) {
                    return;
                }

                Denominacao denominacao = cbDenominacao.getValue();

                presidente = new Presidente(nome.toUpperCase(), denominacao);
                PresidenteDAO.salvar(presidente);
                Alertas.cadastradoSucesso("Presidente");

                cbDenominacao.getItems().clear();
                cadastrar = false;
            } else {
                Presidente presidente = tableViewPresidentes.getSelectionModel().getSelectedItem();
                String nome = tfNome.getText();
                if (!Alertas.validarNome(nome)) {
                    return;
                }
                presidente.setDenominacao(cbDenominacao.getValue());
                presidente.setNome(nome.toUpperCase());
                PresidenteDAO.atualizar(presidente);
                Alertas.atualizadoSucesso("Presidente");
            }
            selectMode(1);
        }

    }

    public void editarCancelar() {
        Presidente presidente = tableViewPresidentes.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar") && Alertas.validarSelecaoEntidade(presidente, "Presidente")) {
            cbDenominacao.getItems().addAll(Denominacao.PE, Denominacao.DIAC, Denominacao.MINIS);
            selectMode(2);
        } else {
            selectMode(1);
        }
    }

    public void apagar() {
        Presidente presidente = tableViewPresidentes.getSelectionModel().getSelectedItem();
        if (Alertas.validarSelecaoEntidade(presidente, "Presidente")) {
            if (Alertas.confirmarApagar("Presidente")) {
                PresidenteDAO.apagar(presidente.getId());
                if(Alertas.verificarRestricao(PresidenteDAO.recuperar(presidente.getId()), "Presidente")){
                    Alertas.apagadoSucesso("Presidente");
                }
                carregarTodos();
            }
        }
    }

    public void procurar() {
        if (!barraBusca.getText().equals("")) {
            presidentes = PresidenteDAO.recuperar(barraBusca.getText());
            obPresidentes = FXCollections.observableArrayList(presidentes);
            tableViewPresidentes.setItems(obPresidentes);
        } else {
            carregarTodos();
        }
    }

    public void editarMode() {
        Presidente presidente = tableViewPresidentes.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar")) {
            if (presidente == null) {
                cadastrar = true;
            }
            cbDenominacao.getItems().addAll(Denominacao.PE, Denominacao.DIAC, Denominacao.MINIS);
            selectMode(2);
        }
    }

    private void selectMode(int mode) {
        if (mode == 1) {
            apEsquerdo.setDisable(false);
            btCadastrarSalvar.setText("Cadastrar");
            btEditarCancelar.setText("Editar");
            btApagar.setVisible(true);
            tfNome.setEditable(false);
            tfNome.setText("");
            cbDenominacao.getItems().clear();
            cbDenominacao.setValue(null);
            carregarTodos();
        } else if (mode == 2) {
            apEsquerdo.setDisable(true);
            tfNome.setEditable(true);
            btCadastrarSalvar.setText("Salvar");
            btEditarCancelar.setText("Cancelar");
            btApagar.setVisible(false);
            tfNome.setEditable(true);
        }
    }

}
