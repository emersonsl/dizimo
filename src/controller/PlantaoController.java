/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.DAO.PlantaoDAO;
import model.DAO.PlantonistaDAO;
import model.DAO.PresidenteDAO;
import model.bean.Plantao;
import model.bean.Plantonista;
import model.bean.Presidente;
import view.Alertas;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class PlantaoController implements Initializable {

    @FXML
    private DatePicker barraBusca;
    @FXML
    private DatePicker dtData;
    @FXML
    private TextField tfHorario;
    @FXML
    private ComboBox<Plantonista> cbLancador;
    @FXML
    private ComboBox<Presidente> cbPresidente;
    @FXML
    private Button btCadastrarSalvar;
    @FXML
    private Button btEditarCancelar;
    @FXML
    private Button btVerCont;
    @FXML
    private Button btApagar;
    @FXML
    private AnchorPane apEsquerdo;
    @FXML
    private TableView<Plantao> tableViewPlantoes;
    @FXML
    private TableColumn<Plantao, Date> tbData;
    @FXML
    private TableColumn<Plantao, Time> tbHorario;
    @FXML
    private TableColumn<Plantao, Plantonista> tbLancador;
    @FXML
    private TableColumn<Plantao, Presidente> tbPresidente;
    @FXML
    private AnchorPane apPrincipal;

    private boolean cadastrar;

    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
    private List<Plantao> plantoes;
    private ObservableList<Plantao> obPlantoes;
    private static Plantao plantao;

    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTodos();
        tableViewPlantoes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaDizimistar(newValue));
        tableViewPlantoes.setOnMouseClicked(new EventHandler<MouseEvent>(){
            
            @Override
            public void handle(MouseEvent click){
                if(click.getClickCount()==2){
                    verContribuicoes();
                }
            }
        });
    }

    public void carregarTodos() {
        tbData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tbHorario.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tbLancador.setCellValueFactory(new PropertyValueFactory<>("lancador"));
        tbPresidente.setCellValueFactory(new PropertyValueFactory<>("presidente"));

        tbHorario.setCellFactory(coluna -> {
            return new TableCell<Plantao, Time>() {
                @Override
                protected void updateItem(Time time, boolean empty) {
                    super.updateItem(time, empty);

                    if (time == null || empty) {
                        setText(null);
                    } else {
                        setText(formatoHora.format(time.toLocalTime()));
                    }
                }
            };
        });
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tbData.setCellFactory(coluna -> {
            return new TableCell<Plantao, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(formatoData.format(item.toLocalDate()));
                    }
                }
            };
        });

        plantoes = PlantaoDAO.recuperar();

        obPlantoes = FXCollections.observableArrayList(plantoes);

        tableViewPlantoes.setItems(obPlantoes);
    }

    private void selecionarItemTabelaDizimistar(Plantao p) {
        clear();
        if (p != null) {
            dtData.setValue(p.getData().toLocalDate());
            tfHorario.setText(formatoHora.format(p.getHora().toLocalTime()));
            cbLancador.setValue(p.getLancador());
            cbPresidente.setValue(p.getPresidente());

        } else {
            selectMode(1);
        }
    }

    private void carregarComboBox() {
        List<Plantonista> plantonistas = PlantonistaDAO.recuperar();
        List<Presidente> presidentes = PresidenteDAO.recuperar();

        cbLancador.getItems().addAll(plantonistas);
        cbPresidente.getItems().addAll(presidentes);

    }

    public void editarMode() {
        Plantao p = tableViewPlantoes.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar")) {
            if (p != null) {
                selectMode(3);
            } else {
                selectMode(2);
            }
        }
    }

    private void selectMode(int i) {
        switch (i) {
            case 1:
                //busca
                tfHorario.setEditable(false);
                dtData.setEditable(false);
                cbLancador.setEditable(false);
                cbPresidente.setEditable(false);
                btCadastrarSalvar.setText("Cadastrar");
                btEditarCancelar.setText("Editar");
                btVerCont.setVisible(true);
                btApagar.setVisible(true);
                apEsquerdo.setDisable(false);
                clear();
                carregarTodos();
                break;
            case 2:
                //cadastrar
                cadastrar = true;
                clear();
                carregarComboBox();
                btCadastrarSalvar.setText("Salvar");
                btEditarCancelar.setText("Cancelar");
                btVerCont.setVisible(false);
                btApagar.setVisible(false);
                apEsquerdo.setDisable(true);
                tfHorario.setEditable(true);
                dtData.setEditable(true);
                break;
            case 3:
                //editar
                cadastrar = false;
                clear();
                carregarComboBox();
                btCadastrarSalvar.setText("Salvar");
                btEditarCancelar.setText("Cancelar");
                btVerCont.setVisible(false);
                btApagar.setVisible(false);
                apEsquerdo.setDisable(true);
                tfHorario.setEditable(true);
                dtData.setEditable(true);
                break;
            default:
                break;
        }
    }

    public void cadastrarSalvar() {
        if (btCadastrarSalvar.getText().equals("Cadastrar")) {
            selectMode(2);
            cadastrar = true;
        } else {
            if (cadastrar) {
                salvar();
            } else {
                atualizar();
            }
        }

    }

    public void editarCancelar() {
        Plantao p = tableViewPlantoes.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar") && Alertas.validarSelecaoEntidade(p, "Plantão")) {
            selectMode(3);
            cadastrar = false;
        } else {
            selectMode(1);
        }

    }

    public void verContribuicoes() {
        Plantao p = tableViewPlantoes.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar") && Alertas.validarSelecaoEntidade(p, "Plantão")) {
            plantao = p;
            //abrir tela contribuições
            chamarTelaContribuicoes();
        } else {
            selectMode(1);
        }
    }

    public void apagar() {
        Plantao p = tableViewPlantoes.getSelectionModel().getSelectedItem();
        if (Alertas.validarSelecaoEntidade(p, "Plantão")) {
            if (Alertas.confirmarApagar("Plantão")) {
                PlantaoDAO.apagar(p.getId());
                Alertas.apagadoSucesso("Plantão");
                carregarTodos();
            }
        }

    }

    private void clear() {
        tfHorario.setText("");
        dtData.setValue(null);
        cbLancador.getItems().clear();
        cbPresidente.getItems().clear();
        cbLancador.setValue(null);
        cbPresidente.setValue(null);
    }

    private void salvar() {
        if (!validarCampos()) {
            return;
        }

        Time hora = Time.valueOf(tfHorario.getText().concat(":00"));
        Plantao p = new Plantao(hora, Date.valueOf(dtData.getValue()), cbLancador.getValue(), cbPresidente.getValue());
        PlantaoDAO.salvar(p);
        Alertas.cadastradoSucesso("Plantão");
        selectMode(1);
        plantao = p;
        chamarTelaContribuicoes();
    }

    private void atualizar() {
        if (!validarCampos()) {
            return;
        }

        Time hora = Time.valueOf(tfHorario.getText().concat(":00"));
        Plantao p = tableViewPlantoes.getSelectionModel().getSelectedItem();
        p.setData(Date.valueOf(dtData.getValue()));
        p.setHora(hora);
        p.setLancador(cbLancador.getValue());
        p.setPresidente(cbPresidente.getValue());

        PlantaoDAO.atualizar(p);
        Alertas.atualizadoSucesso("Plantão");
        selectMode(1);
    }

    private void chamarTelaContribuicoes() {
        try {
            AnchorPane aContribuicoes = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Contribuicao.fxml"));
            apPrincipal.getChildren().setAll(aContribuicoes);
        } catch (IOException ex) {
            Logger.getLogger(PlantaoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean validarCampos() {
        corrigirHora();
        if (!Alertas.validarData(dtData.getValue(), "Plantão")) {
            return false;
        }

        if (!Alertas.validarHora(tfHorario.getText())) {
            return false;
        }

        if (!Alertas.validarSelecaoEntidade(cbPresidente.getValue(), "Presidente")) {
            return false;
        }

        if (!Alertas.validarSelecaoEntidade(cbLancador.getValue(), "Lançador")) {
            return false;
        }

        return true;
    }

    public void procurar() {
        if (barraBusca.getValue() != null) {
            plantoes = PlantaoDAO.recuperar(Date.valueOf(barraBusca.getValue()));
            obPlantoes = FXCollections.observableArrayList(plantoes);
            tableViewPlantoes.setItems(obPlantoes);
        } else {
            carregarTodos();
        }
    }

    public static Plantao getPlantao() {
        return plantao;
    }
    
    public void autoCompletarHora(){
        switch(tfHorario.getText().length()){
            case 1:
                tfHorario.setText(tfHorario.getText().concat(":"));
                tfHorario.positionCaret(tfHorario.getText().length());
                break;
            case 5:
                StringBuilder text = new StringBuilder(tfHorario.getText());
                char a = text.charAt(2);
                if(a != ':'){
                    text.setCharAt(2, ':');
                    text.setCharAt(1, a);
                    tfHorario.setText(text.toString());
                    tfHorario.positionCaret(tfHorario.getText().length());
                }
                break;
            default:
                break;
        }
    }
    
    public void corrigirHora(){
        if(tfHorario.getText().length()==4){
            tfHorario.setText("0"+tfHorario.getText());
        }
    }
    
    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(btApagar.isFocused()){
                apagar();
            }else if(btCadastrarSalvar.isFocused()){
                cadastrarSalvar();
            }else if(btEditarCancelar.isFocused()){
                editarCancelar();
            }else if(btVerCont.isFocused()){
                verContribuicoes();
            }
        }
    }
    
    
}
