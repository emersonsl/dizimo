/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.FXML;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.bean.Conjuge;
import model.bean.Endereco;
import util.Data;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class CadastroDizimistaController implements Initializable {

    @FXML
    private Group casamentoGrupo;
    @FXML
    private CheckBox checkBoxId;
    @FXML
    private CheckBox checkBoxCasamento;
    @FXML
    private CheckBox checkBoxDataInsc;
    //informações basicas
    @FXML
    private TextField id;
    @FXML
    private TextField nome;
    @FXML
    private TextField email;
    @FXML
    private TextField dataNas;
    @FXML
    private TextField dataInsc;
    @FXML
    private TextField telefone;
    @FXML
    private TextField grupos;
    //endereco
    @FXML
    private TextField rua;
    @FXML
    private TextField numero;
    @FXML
    private TextField bairro;
    @FXML
    private TextField complemento;
    //dados do esposo(a)
    @FXML
    private TextField nomeCon;
    @FXML
    private TextField dataNasCon;
    @FXML
    private TextField dataCas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        casamentoGrupo.setVisible(false);
    }

    public void grupoVisivel() {
        if (checkBoxCasamento.isSelected()) {
            casamentoGrupo.setVisible(true);
        } else {
            casamentoGrupo.setVisible(false);
        }
    }

    public void setDataInscAuto() {
        if (checkBoxDataInsc.isSelected()) {
            dataInsc.setText(Data.getDataAtualStr());
            dataInsc.setDisable(true);
        } else {
            dataInsc.setDisable(false);
        }
    }

    public void setNumAuto() {
        if (checkBoxId.isSelected()) {
            id.setDisable(true);
        } else {
            id.setDisable(false);
        }
    }

    public void autoCompletarDataNas() {
        if (dataNas.getText().length() == 2 || dataNas.getText().length() == 5) {
            dataNas.setText(dataNas.getText().concat("/"));
            dataNas.positionCaret(dataNas.getText().length());
        } else if (dataNas.getText().length() > 10) {
            dataNas.setText(dataNas.getText().substring(0, 10));
            dataNas.positionCaret(dataNas.getText().length());
        }
    }

    public void autoCompletarDataInsc() {
        if (dataInsc.getText().length() == 2 || dataInsc.getText().length() == 5) {
            dataInsc.setText(dataInsc.getText().concat("/"));
            dataInsc.positionCaret(dataInsc.getText().length());
        } else if (dataInsc.getText().length() > 10) {
            dataInsc.setText(dataInsc.getText().substring(0, 10));
            dataInsc.positionCaret(dataInsc.getText().length());
        }
    }

    public void autoCompletarDataNasCon() {
        if (dataNasCon.getText().length() == 2 || dataNasCon.getText().length() == 5) {
            dataNasCon.setText(dataNasCon.getText().concat("/"));
            dataNasCon.positionCaret(dataNasCon.getText().length());
        } else if (dataNasCon.getText().length() > 10) {
            dataNasCon.setText(dataNasCon.getText().substring(0, 10));
            dataNasCon.positionCaret(dataNasCon.getText().length());
        }
    }

    public void autoCompletarTelefone() {
        switch (telefone.getText().length()) {
            case 1:
                if (!telefone.getText().equals("(")) {
                    telefone.setText("(".concat(telefone.getText()));
                    telefone.positionCaret(2);
                }
                break;
            case 3:
                telefone.setText(telefone.getText().concat(") "));
                telefone.positionCaret(telefone.getText().length());
                break;
            case 9:
                telefone.setText(telefone.getText().concat("-"));
                telefone.positionCaret(telefone.getText().length());
                break;
            case 15:
                StringBuilder texto = new StringBuilder(telefone.getText());
                char a = texto.charAt(10);
                if (a != '-') {
                    texto.setCharAt(9, a);
                    texto.setCharAt(10, '-');
                    telefone.setText(texto.toString());
                    telefone.positionCaret(telefone.getText().length());
                }
            default:
                break;
        }
        if (telefone.getText().length() > 15) {
            telefone.setText(telefone.getText().substring(0, 15));
            telefone.positionCaret(telefone.getText().length());
        }
    }

    public void autoCompletarDataCas() {
        if (dataCas.getText().length() == 2 || dataCas.getText().length() == 5) {
            dataCas.setText(dataCas.getText().concat("/"));
            dataCas.positionCaret(dataCas.getText().length());
        } else if (dataCas.getText().length() > 10) {
            dataCas.setText(dataCas.getText().substring(0, 10));
            dataCas.positionCaret(dataCas.getText().length());
        }
    }

    private boolean verificarDatas() {
        if (!Data.isValid(dataNas.getText())) {
            dataNas.setText("");
            return false;
        }
        if (!Data.isValid(dataInsc.getText())) {
            dataInsc.setText("");
            return false;
        }
        if (checkBoxCasamento.isSelected()) {
            if (!Data.isValid(dataNasCon.getText())) {
                dataNasCon.setText("");
                return false;
            }
            if (!Data.isValid(dataCas.getText())) {
                dataCas.setText("");
                return false;
            }
        }
        return true;
    }
    private boolean verificarTelefone(){
        String regex = "\\(\\d{2}\\) (\\d{4}|\\d{5})\\-\\d{4}";
        return telefone.getText().matches(regex);
    }

    public void salvar() {

        if (!verificarDatas()) {
            JOptionPane.showMessageDialog(null, "Data(s) incorretas");
            return;
        }
        if(!telefone.getText().equals("") && !verificarTelefone()){
            JOptionPane.showMessageDialog(null, "Telefone incorreto");
            return;
        }
        if(nome.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Nome não preenchido");
            return;
        }
        
        
        Integer idC = null;
        String nomeC, emailC, telefoneC, grupoMovimentoPastoralC;
        Endereco enderecoC;
        Date dataNascimentoC, dataNasConC, dataCasC, dataInscC;
        Conjuge conjugeC = null;

        if (checkBoxId.isSelected()||id.getText().equals("")) {
            idC = null;
        }else{
            idC = Integer.parseInt(id.getText());
        }

        if (checkBoxCasamento.isSelected()) {
            dataNasConC = Data.criar(dataNasCon.getText());
            dataCasC = Data.criar(dataCas.getText());
            conjugeC = new Conjuge(nomeCon.getText().toUpperCase(), dataNasConC, dataCasC);
        }

        nomeC = nome.getText().toUpperCase();
        emailC = email.getText().toUpperCase();
        telefoneC = telefone.getText().toUpperCase();
        grupoMovimentoPastoralC = grupos.getText().toUpperCase();

        enderecoC = new Endereco(rua.getText().toUpperCase(), bairro.getText().toUpperCase(), numero.getText(), complemento.getText().toUpperCase());

        dataNascimentoC = Data.criar(dataNas.getText());
        dataInscC = Data.criar(this.dataInsc.getText());

        Controller.ControllerDizimista.getInstance().criar(idC, nomeC, emailC, telefoneC, enderecoC, dataNascimentoC, grupoMovimentoPastoralC, conjugeC, dataInscC);
        JOptionPane.showMessageDialog(null, "Dizimista cadastrado(a) com sucesso");

    }

}
