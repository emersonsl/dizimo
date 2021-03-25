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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import tools.ExportarPDF;
import view.Alertas;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class RelatoriosController implements Initializable {

    @FXML
    private ToggleGroup groupButtonRelatorios;
    @FXML
    private RadioButton aniversarios;
    @FXML
    private RadioButton contribuicoes;
    @FXML
    private DatePicker dtInicial;
    @FXML
    private DatePicker dtFinal;
    @FXML
    private Button btGerar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        groupButtonRelatorios = new ToggleGroup();
        aniversarios.setToggleGroup(groupButtonRelatorios);
        contribuicoes.setToggleGroup(groupButtonRelatorios);
    }

    public void gerar() {

        if (validarCampos()) {
            Toggle selectedToggle = groupButtonRelatorios.getSelectedToggle();
            Date dataInicial = Date.valueOf(dtInicial.getValue());
            Date dataFinal = Date.valueOf(dtFinal.getValue());
            if (selectedToggle.equals(aniversarios)) {
                try {
                    ExportarPDF.aniversarioDosDizimistas(dataInicial, dataFinal);
                } catch (DocumentException | IOException ex) {
                    Alertas.erroAberturaAquivo();
                }
            } else {
                try {
                    ExportarPDF.contribuicoesDosDizimistas(dataInicial, dataFinal);
                } catch (DocumentException | IOException ex) {
                    Alertas.erroAberturaAquivo();
                }
            }
        }
    }

    private boolean validarCampos() {
        if (!Alertas.validarSelecaoComboBox(groupButtonRelatorios.getSelectedToggle(), "Tipo de Relatorio")) {
            return false;
        }
        if (!Alertas.validarData(dtInicial.getValue(), "início")) {
            return false;
        }
        if (!Alertas.validarData(dtFinal.getValue(), "fim")) {
            return false;
        }
        if (!Alertas.validarIntervalo(dtInicial.getValue(), dtFinal.getValue())) {
            return false;
        }
        return true;
    }
    
    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(btGerar.isFocused()){
                gerar();
            }
        }
    }

}
