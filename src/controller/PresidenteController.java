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

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class PresidenteController implements Initializable {

    @FXML
    private TableView <Presidente> tableViewPresidentes;
    @FXML
    private TableColumn<Presidente, String> tbNome;
    @FXML
    private TableColumn<Presidente, Denominacao> tbDenominacao;
    @FXML
    private TextField tfNome;
    @FXML
    private ComboBox<Denominacao> cbDenominacao;
    @FXML
    private AnchorPane apDireito;
    @FXML
    private Button btCadastrarSalvar;
    
    
    private List<Presidente> presidentes;
    private ObservableList <Presidente> obPresidentes;
    boolean cadastrar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTodos();
        tableViewPresidentes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaDizimistar(newValue));
    }    
    
    public void carregarTodos() {
        tbNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tbDenominacao.setCellValueFactory(new PropertyValueFactory<>("denominacao"));
        
        presidentes = PresidenteDAO.recuperar();

        
        obPresidentes = FXCollections.observableArrayList(presidentes);
        tableViewPresidentes.setItems(obPresidentes);
        apDireito.setVisible(false);
    }

    private void selecionarItemTabelaDizimistar(Presidente p) {
        if (p != null) {
            apDireito.setVisible(true);
            tfNome.setText(p.getNome());
            cbDenominacao.setValue(p.getDenominacao());
            
        }else{
            apDireito.setVisible(false);
        }
    }
    
    public void cadastrarSalvar(){
        if(btCadastrarSalvar.getText().equals("Cadastrar")){
            tfNome.setEditable(true);
            cbDenominacao.setEditable(true);
            tfNome.setText("");
            cbDenominacao.setValue(Denominacao.PE);
            cbDenominacao.getItems().clear();
            apDireito.setVisible(true);

            cbDenominacao.getItems().addAll(Denominacao.PE, Denominacao.DIAC, Denominacao.MINIS);
            btCadastrarSalvar.setText("Salvar");
            cadastrar = true;
        }else{
            Presidente presidente;

            String nome = tfNome.getText();
            if(nome.equals("")){
                System.out.print("erro");
                return;
            }

            Denominacao denominacao = cbDenominacao.getValue();
            if(denominacao==null){
                System.out.print("erro");
                return;
            }

            presidente = new Presidente(nome, denominacao);
            btCadastrarSalvar.setText("Cadastrar");
            tfNome.setVisible(false);
            cbDenominacao.setVisible(false);
            cadastrar = false;
        }
        
    }

}
