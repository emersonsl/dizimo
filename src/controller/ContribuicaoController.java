/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.time.Year;
import java.time.format.DateTimeFormatter;
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
import model.DAO.ContribuicaoDAO;
import model.bean.Contribuicao;
import model.bean.Dizimista;
import model.bean.Plantao;
import model.bean.Plantonista;
import util.Mes;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class ContribuicaoController implements Initializable {
    
    //botões
    @FXML
    private Button buttonCadastrarSalvar;
    @FXML
    private Button buttonEditarCancelar;
    @FXML
    private Button buttonApagar;
    @FXML
    private TextField tfDtPlantao, tfHrPlantao, tfLancadorPlantao, tfPresidentePlantao;
    //dados da contribuição
    @FXML
    private TextField tfNomeDizimista, tfValor;
    @FXML
    private ComboBox<Dizimista> cbIdDizimista;
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
    private AnchorPane apEsquerdo;
    
    private List <Contribuicao> contribuicoes;
    private ObservableList<Contribuicao> obContribuicoes;
    private boolean cadastrar;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregarDadosPlantao();
        carregarTodos();
        tableViewContribuicoes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaContribuicao(newValue));
    }
    
    private void carregarDadosPlantao(){
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("hh:mm");
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        Plantao p = PlantaoController.getPlantao();
        tfDtPlantao.setText(formatoData.format(p.getData().toLocalDate()));
        tfHrPlantao.setText(formatoHora.format(p.getHora().toLocalTime()));
        tfLancadorPlantao.setText(p.getLancador().getNome());
        tfPresidentePlantao.setText(p.getPresidente().toString());
    }
    
   public void carregarTodos() {
       tbDizimista.setCellValueFactory(new PropertyValueFactory<>("dizimista"));
       tbValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
       tbMes.setCellValueFactory(new PropertyValueFactory<>("mes"));
       tbAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
       tbPlantonista.setCellValueFactory(new PropertyValueFactory<>("plantonista"));

        contribuicoes = ContribuicaoDAO.recuperar();

        obContribuicoes = FXCollections.observableArrayList(contribuicoes);
        tableViewContribuicoes.setItems(obContribuicoes);
    }

    private void selecionarItemTabelaContribuicao(Contribuicao c) {
        if (c != null) {
            tfNomeDizimista.setText(c.getDizimista().getNome());
            tfValor.setText(c.getValor().toString());
            cbIdDizimista.setValue(c.getDizimista());
            cbMes.setValue(c.getMes());
            cbAno.setValue(c.getAno());
            cbPlantonista.setValue(c.getPlantonista());
        } else {
            
        }
    }

    
    
    public void cadastrarSalvar(){
    
    }
    
    public void editarCancelar(){
        
    }
    
    public void apagar(){
        
    }
    
    public void encerrarPlantao(){
        
    }

    public void buscarDizimista(){
        
    }
    
    
    
}
