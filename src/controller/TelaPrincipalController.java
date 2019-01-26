/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class TelaPrincipalController implements Initializable {

    @FXML
    private Tab tabPlantao, tabDizimista, tabPresidente, tabPlantonista;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane aPlantao = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Plantao.FXML"));
            tabPlantao.setContent(aPlantao);
            AnchorPane aDizimista = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Dizimista.FXML"));
            tabDizimista.setContent(aDizimista);
            AnchorPane aPresidente = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Presidente.FXML"));
            tabPresidente.setContent(aPresidente);
            AnchorPane aPlantonista = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/Plantonista.FXML"));
            tabPlantonista.setContent(aPlantonista);
        } catch (IOException ex) {
            Logger.getLogger(TelaPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
