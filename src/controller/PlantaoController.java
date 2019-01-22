/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DAO.PlantaoDAO;
import model.DAO.PlantonistaDAO;
import model.DAO.PresidenteDAO;
import model.bean.Plantao;
import model.bean.Plantonista;
import model.bean.Presidente;
import util.Denominacao;
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
    
    private boolean cadastrar;
    
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm");
    private List<Plantao> plantoes;
    private ObservableList<Plantao> obPlantoes;
    private static Plantao plantao = new Plantao(1, Time.valueOf(LocalTime.now()), Date.valueOf(LocalDate.now()), new Plantonista("EMERSON", true), new Presidente("CARLOS", Denominacao.DIAC));;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTodos();
        tableViewPlantoes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaDizimistar(newValue));
    }
    
    public void carregarTodos() {
        tbData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tbHorario.setCellValueFactory(new PropertyValueFactory<>("hora"));
        tbLancador.setCellValueFactory(new PropertyValueFactory<>("lancador"));
        tbPresidente.setCellValueFactory(new PropertyValueFactory<>("presidente"));
        
        tbHorario.setCellFactory(coluna ->{
            return new TableCell<Plantao, Time>(){
                @Override
                protected void updateItem(Time time, boolean empty){
                    super.updateItem(time, empty);
                    
                    if(time==null|| empty){
                        setText(null);
                    }else{
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

        plantoes  = PlantaoDAO.recuperar();
    
    obPlantoes  = FXCollections.observableArrayList(plantoes);

    tableViewPlantoes.setItems (obPlantoes);
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
        }else{
            if(cadastrar){
                salvar();
            }else{
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
        if (btEditarCancelar.getText().equals("Editar") && Alertas.validarSelecaoEntidade(plantao, "Plantão")) {
            plantao = p;
            //abrir tela contribuições
        }else{
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
    }

    private void salvar() {
        if(!validarCampos()){
            return;
        }
        
        Time hora = Time.valueOf(tfHorario.getText().concat(":00"));
        Plantao p = new Plantao(hora, Date.valueOf(dtData.getValue()), cbLancador.getValue(), cbPresidente.getValue());
        PlantaoDAO.salvar(p);
        Alertas.cadastradoSucesso("Plantão");
        selectMode(1);
        plantao = p;
    }

    private void atualizar() {
        if(!validarCampos()){
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

    private boolean validarCampos() {
        if(!Alertas.validarData(dtData.getValue(), "Plantão")){
            return false;
        }
        
        if(!Alertas.validarHora(tfHorario.getText())){
            return false;
        }
        
        if(!Alertas.validarSelecaoEntidade(cbPresidente.getValue(), "Presidente")){
            return false;
        }
        
        if(!Alertas.validarSelecaoEntidade(cbLancador.getValue(), "Lançador")){
            return false;
        }
        
        return true;
    }

    public void procurar() {
        if (barraBusca.getValue()!=null) {
            plantoes = PlantaoDAO.recuperar(Date.valueOf(barraBusca.getValue()));
            obPlantoes = FXCollections.observableArrayList(plantoes);
            tableViewPlantoes.setItems(obPlantoes);
        } else {
            carregarTodos();
        }
    }

    public static Plantao getPlantao(){
        return plantao;
    }
}
