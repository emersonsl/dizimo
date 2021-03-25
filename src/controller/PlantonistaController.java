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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.DAO.PlantonistaDAO;
import model.bean.Plantonista;
import view.Alertas;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class PlantonistaController implements Initializable {

    @FXML
    private TableView<Plantonista> tableViewPlantonistas;
    @FXML
    private TableColumn<Plantonista, String> tbNome;
    @FXML
    private TableColumn<Plantonista, String> tbCoordenador;
    @FXML
    private TextField tfNome;
    @FXML
    private CheckBox ckCoordenador;
    @FXML
    private TextField barraBusca;
    @FXML
    private Button btCadastrarSalvar;
    @FXML
    private Button btEditarCancelar;
    @FXML
    private Button btApagar;
    @FXML
    private AnchorPane apEsquerdo;

    private List<Plantonista> plantonistas;
    private ObservableList<Plantonista> obPlantonistas;
    boolean cadastrar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTodos();
        tableViewPlantonistas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaPlantonista(newValue));
    }

    public void carregarTodos() {
        tbNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbCoordenador.setCellValueFactory(new PropertyValueFactory<>("coordenador"));

        plantonistas = PlantonistaDAO.recuperar();

        obPlantonistas = FXCollections.observableArrayList(plantonistas);
        tableViewPlantonistas.setItems(obPlantonistas);
    }

    private void selecionarItemTabelaPlantonista(Plantonista p) {
        if (p != null) {
            tfNome.setText(p.getNome());
            ckCoordenador.setSelected(p.isCoordenador());
        } else {
            tfNome.setText("");
            ckCoordenador.setSelected(false);
        }
    }

    public void cadastrarSalvar() {
        if (btCadastrarSalvar.getText().equals("Cadastrar")) {
            tfNome.setText("");
            ckCoordenador.setSelected(false);
            cadastrar = true;
            selectMode(2);
        } else {
            if (cadastrar) {
                Plantonista plantonista;
                String nome = tfNome.getText();
                if (!Alertas.validarNome(nome)) {
                    return;
                }

                plantonista = new Plantonista(nome.toUpperCase(), ckCoordenador.isSelected());
                PlantonistaDAO.salvar(plantonista);
                Alertas.cadastradoSucesso("Plantonista");

                ckCoordenador.setSelected(false);
                cadastrar = false;
            } else {
                Plantonista plantonista = tableViewPlantonistas.getSelectionModel().getSelectedItem();
                String nome = tfNome.getText();
                if (!Alertas.validarNome(nome)) {
                    return;
                }
                plantonista.setCoordenador(ckCoordenador.isSelected());
                plantonista.setNome(nome.toUpperCase());
                PlantonistaDAO.atualizar(plantonista);
                Alertas.atualizadoSucesso("Plantonista");
            }
            selectMode(1);
        }

    }

    public void editarCancelar() {
        Plantonista plantonista = tableViewPlantonistas.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar") && Alertas.validarSelecaoEntidade(plantonista, "Plantonista")) {
            selectMode(2);
        } else {
            selectMode(1);
        }
    }

    public void apagar() {
        Plantonista plantonista = tableViewPlantonistas.getSelectionModel().getSelectedItem();
        if (Alertas.validarSelecaoEntidade(plantonista, "Plantonista")) {
            if (Alertas.confirmarApagar("Plantonista")) {
                PlantonistaDAO.apagar(plantonista.getId());
                if (Alertas.verificarRestricao(PlantonistaDAO.recuperar(plantonista.getId()), "Plantonista")) {
                    Alertas.apagadoSucesso("Plantonista");
                }
                carregarTodos();
            }
        }
    }

    public void procurar() {
        if (!barraBusca.getText().equals("")) {
            plantonistas = PlantonistaDAO.recuperar(barraBusca.getText());
            obPlantonistas = FXCollections.observableArrayList(plantonistas);
            tableViewPlantonistas.setItems(obPlantonistas);
        } else {
            carregarTodos();
        }
    }

    public void editarMode() {
        Plantonista plantonista = tableViewPlantonistas.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar")) {
            if (plantonista != null) {
                cadastrar = false;
            } else {
                cadastrar = true;
            }
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

    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(btApagar.isFocused()){
                apagar();
            }else if(btCadastrarSalvar.isFocused()){
                cadastrarSalvar();
            }else if(btEditarCancelar.isFocused()){
                editarCancelar();
            }
        }
    }
}
