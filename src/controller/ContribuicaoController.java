/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.DAO.ContribuicaoDAO;
import model.DAO.DizimistaDAO;
import model.DAO.PlantonistaDAO;
import model.bean.Contribuicao;
import model.bean.Dizimista;
import model.bean.Plantao;
import model.bean.Plantonista;
import tools.Backup;
import tools.Configuracao;
import tools.ExportarPDF;
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
    private Button btEncerrarPlantao;
    @FXML
    private Label lbDtPlantao, lbHrPlantao, lbLancadorPlantao, lbPresidentePlantao;
    //dados da contribuição
    @FXML
    private TextField tfValor, tfIdDizimista;
    @FXML
    private Label lbNomeDizimista, lb1NomeDizimista;
    @FXML
    private ComboBox<Mes> cbMesInicial;
    @FXML
    private CheckBox ckMaisMeses;
    @FXML
    private ComboBox<Mes> cbMesFinal;
    @FXML
    private ComboBox<Year> cbAnoInicial;
    @FXML
    private ComboBox<Year> cbAnoFinal;
    @FXML
    private ComboBox<Plantonista> cbPlantonista;
    //dados da table View
    @FXML
    private TableView<Contribuicao> tableViewContribuicoes;
    @FXML
    private TableColumn<Contribuicao, Dizimista> tbDizimista;
    @FXML
    private TableColumn<Contribuicao, Double> tbValor;
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

    @FXML
    private AnchorPane apPrincipal;

    private Plantao plantao;
    private List<Contribuicao> contribuicoes;
    private ObservableList<Contribuicao> obContribuicoes;
    private boolean cadastrar;
    DecimalFormat df = new DecimalFormat("#.00");
    Integer idInicial;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            idInicial = Integer.valueOf(Configuracao.getParametro("db.idInicial"));
        } catch (IOException ex) {
            Alertas.erroArquivoConfiguracao();
        }
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

        tbValor.setCellFactory(coluna -> {
            return new TableCell<Contribuicao, Double>() {
                @Override
                protected void updateItem(Double valor, boolean empty) {
                    super.updateItem(valor, empty);

                    if (valor == null || empty) {
                        setText(null);
                    } else {
                        setText(df.format(valor));
                    }
                }
            };
        });

        contribuicoes = ContribuicaoDAO.recuperar(plantao);

        obContribuicoes = FXCollections.observableArrayList(contribuicoes);
        tableViewContribuicoes.setItems(obContribuicoes);
        tfValorTotal.setText(df.format(calcularValorTotal()));
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

            tfValor.setText(df.format(c.getValor()));

            cbMesInicial.setValue(c.getMes());
            cbAnoInicial.setValue(c.getAno());
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
                cadastrar = false;
                tfIdDizimista.setEditable(false);
                tfValor.setEditable(false);
                btCadastrarSalvar.setText("Cadastrar");
                btEditarCancelar.setText("Editar");
                btApagar.setVisible(true);
                apEsquerdo.setDisable(false);
                ckMaisMeses.setVisible(false);
                cbMesFinal.setVisible(false);
                cbAnoFinal.setVisible(false);
                clear();
                break;
            case 2:
                //cadastrar
                clear();
                cbMesFinal.setVisible(false);
                cbAnoFinal.setVisible(false);
                cadastrar = true;
                carregarComboBox();
                tfIdDizimista.setEditable(true);
                tfIdDizimista.requestFocus();
                tfValor.setEditable(true);
                btCadastrarSalvar.setText("Salvar+");
                btEditarCancelar.setText("Concluir");
                btApagar.setVisible(false);
                apEsquerdo.setDisable(true);
                ckMaisMeses.setVisible(true);
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
                ckMaisMeses.setVisible(false);
                cbMesFinal.setVisible(false);
                cbAnoFinal.setVisible(false);
                break;
            default:
                break;
        }
    }

    public void editarMode() {
        if (btEditarCancelar.getText().equals("Editar")) {
            Contribuicao c = tableViewContribuicoes.getSelectionModel().getSelectedItem();
            if (c != null) {
                selectMode(3);
            } else {
                selectMode(2);
            }
        }

    }

    private void clearComboBox(){
        cbMesInicial.getItems().clear();
        cbMesFinal.getItems().clear();
        cbAnoInicial.getItems().clear();
        cbPlantonista.getItems().clear();
    }
    
    private void carregarComboBox() {
        clearComboBox();
        cbMesInicial.getItems().addAll(Arrays.asList(Mes.values()));
        cbMesFinal.getItems().addAll(Arrays.asList(Mes.values()));
        Year a = Year.parse(String.valueOf(Year.now().getValue() - 1));
        Year b = Year.parse(String.valueOf(Year.now().getValue() + 1));
        cbAnoInicial.getItems().addAll(a, Year.now(), b);
        cbAnoFinal.getItems().addAll(a, Year.now(), b);
        cbPlantonista.getItems().addAll(PlantonistaDAO.recuperar());
    }

    private void clear() {
        clearComboBox();
        lbNomeDizimista.setVisible(false);
        lb1NomeDizimista.setVisible(false);
        lbNomeDizimista.setText("");
        tfIdDizimista.setText("");
        cbMesInicial.setValue(null);
        cbMesFinal.setValue(null);
        ckMaisMeses.setSelected(false);
        cbMesInicial.getItems().clear();
        cbAnoInicial.setValue(null);
        cbAnoInicial.getItems().clear();
        cbAnoFinal.setValue(null);
        cbAnoFinal.getItems().clear();
        tfValor.setText("");
        cbPlantonista.setValue(null);
        cbPlantonista.getItems().clear();
        tableViewContribuicoes.getSelectionModel().clearSelection();
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
            Contribuicao c;

            if (ckMaisMeses.isSelected()) {
                LocalDate dtInicial = LocalDate.of(cbAnoInicial.getValue().getValue(), cbMesInicial.getValue().getMes(), 1);
                LocalDate dtFinal = LocalDate.of(cbAnoFinal.getValue().getValue(), cbMesFinal.getValue().getMes(), 1);
                List <Contribuicao> contribuicoes = new ArrayList<>();
                
                int qtdMes = 0;
                
                for(int i = dtInicial.getYear(); i <=dtFinal.getYear(); i++){ //Anos
                    Year year = Year.parse(""+i);
                    int j = 1;
                    int k = 12;
                    if(i==dtInicial.getYear()){ //primeiro mês do primeiro ano
                        j=dtInicial.getMonthValue();
                    }
                    if(i==dtFinal.getYear()){ //ultimo mês do ultimo ano
                        k=dtFinal.getMonthValue();
                    }
                    for(; j<=k; j++){ //meses
                        c = new Contribuicao(0.0, Mes.JAN.setMes(j), year, d, p, plantao);
                        contribuicoes.add(c);
                        qtdMes++;
                    }
                    
                }
                
                Double valor = Double.parseDouble(tfValor.getText()) / qtdMes;
                
                for(Contribuicao con: contribuicoes){
                    con.setValor(valor);
                    ContribuicaoDAO.salvar(con);
                }
            } else {
                c = new Contribuicao(Double.parseDouble(tfValor.getText()), cbMesInicial.getValue(), cbAnoInicial.getValue(), d, p, plantao);
                ContribuicaoDAO.salvar(c);
            }
            //Alertas.cadastradoSucesso("Contribuição");
            selectMode(2);
            carregarTodos();
        }
    }

    public void atualizar() {
        if (validarCampos()) {
            Dizimista d = DizimistaDAO.recuperar(Integer.parseInt(tfIdDizimista.getText()));
            Contribuicao c = tableViewContribuicoes.getSelectionModel().getSelectedItem();
            c.setDizimista(d);
            c.setPlantonista(cbPlantonista.getValue());
            c.setMes(cbMesInicial.getValue());
            c.setAno(cbAnoInicial.getValue());
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
        try {
            AnchorPane aPlantao = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Plantao.fxml"));
            apPrincipal.getChildren().setAll(aPlantao);
            Backup.executar();
        } catch (IOException ex) {
            Logger.getLogger(ContribuicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validarCampos() {
        Contribuicao c = tableViewContribuicoes.getSelectionModel().getSelectedItem();
        if (tfIdDizimista.getText() != null && tfIdDizimista.getText().matches("\\d{1,3}")) { //tratamento para número entre 0 e 999 com três digitos
            StringBuilder text = new StringBuilder();
            for (int i = tfIdDizimista.getText().length(); i < 4; i++) {
                text.append("0");
            }
            text.append(tfIdDizimista.getText());
            tfIdDizimista.setText(text.toString());
        }
        
        if (!Alertas.validarBuscaIdDizimista(tfIdDizimista.getText())) {
            tfIdDizimista.setText("");
            return false;
        }
        //mes ano valor plantonista
        if (!Alertas.validarSelecaoComboBox(cbMesInicial.getValue(), "Mês")) {
            return false;
        }
        if (!Alertas.validarSelecaoComboBox(cbAnoInicial.getValue(), "Ano")) {
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
        if (cadastrar && ckMaisMeses.isSelected() && !Alertas.validarSelecaoComboBox(cbMesFinal.getValue(), "Mês final")) {
            return false;
        }
        
        LocalDate dtInicial = LocalDate.of(cbAnoInicial.getValue().getValue(), cbMesInicial.getValue().getMes(), 1);
        LocalDate dtFinal;
        if(ckMaisMeses.isSelected()){
             dtFinal = LocalDate.of(cbAnoFinal.getValue().getValue(), cbMesFinal.getValue().getMes(), 1);
        }else{
            dtFinal = dtInicial;
        }
        
        if (cadastrar && ckMaisMeses.isSelected() && !Alertas.validarIntervalo(dtInicial, dtFinal)) {
            return false;
        }
        if (cadastrar && !Alertas.validarContribuicoes(dtInicial, dtFinal, tfIdDizimista.getText())) {
            return false;
        }
        if (!cadastrar && cbMesInicial.getValue()!=c.getMes() && !Alertas.validarContribuicoes(dtInicial, dtFinal, tfIdDizimista.getText())){
            return false;
        }
        return true;
    }

    public void buscarDizimista() {
        if (tfIdDizimista.getText().length() == 4 || (idInicial >= 0 && idInicial < 1000)) {
            if (!tfIdDizimista.getText().matches("\\d{1,4}")) {
                return;
            }
            Dizimista d = DizimistaDAO.recuperar(Integer.parseInt(tfIdDizimista.getText()));
            if (d != null) {
                lb1NomeDizimista.setVisible(true);
                lbNomeDizimista.setVisible(true);
                lbNomeDizimista.setText(d.getNome());
                List<Contribuicao> contribuicoes = ContribuicaoDAO.recuperar(d);
                if (cadastrar) {
                    if (!contribuicoes.isEmpty()) {
                        Contribuicao contribuicao = contribuicoes.get(contribuicoes.size() - 1);
                        if (contribuicao != null) {
                            Mes mes = contribuicao.getMes();
                            Year year = contribuicao.getAno();
                            if (mes.getMes() == 12) {
                                cbMesInicial.setValue(Mes.JAN);
                                cbAnoInicial.setValue(year.plusYears(1));
                            } else {
                                boolean teste = false;
                                Mes proxMes = mes.setMes(mes.getMes() + 1);
                                cbMesInicial.setValue(proxMes);
                                cbAnoInicial.setValue(year);
                            }
                        }
                    } else {
                        cbMesInicial.setValue(Mes.JAN.setMes(LocalDate.now().getMonthValue()));
                        cbAnoInicial.setValue(Year.of(LocalDate.now().getYear()));
                    }
                }
            } else {
                cbMesInicial.setValue(null);
                cbAnoInicial.setValue(null);
                lbNomeDizimista.setText("");
                lb1NomeDizimista.setVisible(false);
                lbNomeDizimista.setVisible(false);
                cbMesInicial.setValue(null);
                cbAnoInicial.setValue(null);
            }
        }
    }

    public void maisMeses() {
        cbMesFinal.setVisible(ckMaisMeses.isSelected());
        cbAnoFinal.setVisible(ckMaisMeses.isSelected());
    }
    
    public void imprimir(){
        try {
            ExportarPDF.contribuicoesDosDizimistas(plantao.getData(), plantao.getData());
        } catch (DocumentException | IOException ex) {
            Alertas.erroAberturaAquivo();
        }
    }

    @FXML  // <== perhaps you had this missing??
    void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (btCadastrarSalvar.isFocused()) {
                cadastrarSalvar();
            }else if(btEditarCancelar.isFocused()){
                editarCancelar();
            }else if(btApagar.isFocused()){
                apagar();
            }else if(btEncerrarPlantao.isFocused()){
                encerrarPlantao();
            }
        }
    }
}
