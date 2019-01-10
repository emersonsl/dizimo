/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class ContribuicaoController implements Initializable {
    
    @FXML
    private Button buttonCadastrarSalvar;
    @FXML
    private Button buttonEditarCancelar;
    @FXML
    private Button buttonApagar;
    
    @FXML
    private AnchorPane anchorPaneDireito;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void alterarModo(boolean cadastrar){
        if(cadastrar){
            buttonCadastrarSalvar.setText("Salvar");
            buttonEditarCancelar.setText("Cancelar");
            buttonApagar.setVisible(false);
            anchorPaneDireito.setDisable(true);
        }else{
            buttonCadastrarSalvar.setText("Cadastrar");
            buttonEditarCancelar.setText("Editar");
            buttonApagar.setVisible(true);
            anchorPaneDireito.setDisable(false);
        }
    }
    
    public void cadastrarSalvar(){
        if(buttonCadastrarSalvar.getText().equals("Salvar") && salvar()){
            alterarModo(false);
        }else{
            alterarModo(true);
        }
    }

    private boolean salvar() {
        return true;
    }
    
}
