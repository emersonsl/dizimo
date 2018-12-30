/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.FXML;

import Controller.ControllerDizimista;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import model.bean.Conjuge;
import model.bean.Dizimista;
import model.bean.Endereco;
import util.Data;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class BuscaDizimistaController implements Initializable {

    @FXML
    private TableView<Dizimista> tableViewDizimistas;
    @FXML
    private TableColumn<Dizimista, Integer> tbNum;
    @FXML
    private TableColumn<Dizimista, String> tbNome;
    @FXML
    private TextField totalDizimistas;
    @FXML
    private Button editarSalvar;
    @FXML
    private Group grupoTextField;
    @FXML
    private AnchorPane painelTabela;
    @FXML
    private TextField barraBusca;
    @FXML
    private Button buttonApagar;

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

    List<TextField> textFieldsEditables;

    ControllerDizimista controller = Controller.ControllerDizimista.getInstance();
    private List<Dizimista> dizimistas;
    private ObservableList<Dizimista> obDizimistas;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarTodos();
        addTextFieldsEditables();
        tableViewDizimistas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaDizimistar(newValue));
    }

    private void addTextFieldsEditables() {
        textFieldsEditables = new ArrayList<>();
        textFieldsEditables.add(nome);
        textFieldsEditables.add(email);
        textFieldsEditables.add(grupos);
        textFieldsEditables.add(telefone);
        textFieldsEditables.add(dataNas);
        textFieldsEditables.add(dataInsc);
        textFieldsEditables.add(dataCas);
        textFieldsEditables.add(dataNasCon);
        textFieldsEditables.add(nomeCon);
        textFieldsEditables.add(rua);
        textFieldsEditables.add(numero);
        textFieldsEditables.add(bairro);
        textFieldsEditables.add(complemento);
    }

    private void setTextFieldsEditables(boolean b) {
        textFieldsEditables.forEach((t) -> {
            t.setEditable(b);
        });
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

    private boolean verificarTelefone() {
        String regex = "\\(\\d{2}\\) (\\d{4}|\\d{5})\\-\\d{4}";
        return telefone.getText().matches(regex);
    }

    public void carregarTodos() {
        tbNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        dizimistas = controller.recuperar();

        obDizimistas = FXCollections.observableArrayList(dizimistas);
        tableViewDizimistas.setItems(obDizimistas);
        totalDizimistas.setText(dizimistas.size() + " dizimistas cadastrados");
    }

    private void selecionarItemTabelaDizimistar(Dizimista d) {
        if (d != null) {
            id.setText(d.getId().toString());
            nome.setText(d.getNome());
            dataNas.setText(Data.getDataStr(d.getDataNascimento()));
            telefone.setText(d.getTelefone());
            email.setText(d.getEmail());
            dataInsc.setText(Data.getDataStr(d.getDataInscricao()));
            grupos.setText(d.getGrupoMovimentoPastoral());
            rua.setText(d.getEndereco().getRua());
            numero.setText(d.getEndereco().getNumero());
            bairro.setText(d.getEndereco().getBairro());
            complemento.setText(d.getEndereco().getComplemento());
            Conjuge con = d.getConjuge();
            if (con != null) {
                casamentoGrupo.setVisible(true);
                checkBoxCasamento.setSelected(true);
                nomeCon.setText(con.getNome());
                dataNasCon.setText(Data.getDataStr(con.getDataNascimento()));
                dataCas.setText(Data.getDataStr(con.getDataCasamento()));
            } else {
                checkBoxCasamento.setSelected(false);
                casamentoGrupo.setVisible(false);
            }
        }else{
            id.setText("");
            textFieldsEditables.forEach((t) -> {
                t.setText("");
            });
        }
    }

    public void editarSalvar() {
        if (tableViewDizimistas.getSelectionModel().getSelectedItem() != null) {
            if (editarSalvar.getText().equals("Editar")) {
                editarSalvar.setText("Salvar");
                checkBoxCasamento.setVisible(true);
                setTextFieldsEditables(true);
                painelTabela.setDisable(true);
                id.setDisable(true);
                buttonApagar.setVisible(false);
            } else {
                if (salvar()) {
                    voltar();
                }
            }
        }
    }

    public boolean salvar() {

        if (!verificarDatas()) {
            JOptionPane.showMessageDialog(null, "Data(s) incorretas");
            return false;
        }
        if (!telefone.getText().equals("") && !verificarTelefone()) {
            JOptionPane.showMessageDialog(null, "Telefone incorreto");
            return false;
        }
        if (nome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nome não preenchido");
            return false;
        }

        Integer idC = Integer.parseInt(id.getText());
        String nomeC, emailC, telefoneC, grupoMovimentoPastoralC;
        Endereco enderecoC;
        Date dataNascimentoC, dataNasConC, dataCasC, dataInscC;
        Conjuge conjugeC = null;

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
        dataInscC = Data.criar(dataInsc.getText());

        controller.atualizar(idC, nomeC, emailC, telefoneC, enderecoC, dataNascimentoC, grupoMovimentoPastoralC, conjugeC, dataInscC);
        JOptionPane.showMessageDialog(null, "Dizimista atualizado(a) com sucesso");
        return true;
    }

    public void voltar() {
        if (editarSalvar.getText().equals("Salvar")) {
            editarSalvar.setText("Editar");
            checkBoxCasamento.setVisible(false);
            setTextFieldsEditables(false);
            painelTabela.setDisable(false);
            id.setDisable(false);
            buttonApagar.setVisible(true);
        }
    }

    public void procurar() {
        if (!barraBusca.getText().equals("")) {
            dizimistas = controller.recuperar(barraBusca.getText());

            obDizimistas = FXCollections.observableArrayList(dizimistas);
            tableViewDizimistas.setItems(obDizimistas);
            totalDizimistas.setText(dizimistas.size() + " dizimistas encontrados");
        } else {
            carregarTodos();
        }
    }

    public void apagar() {
        if (tableViewDizimistas.getSelectionModel().getSelectedItem() != null && JOptionPane.showConfirmDialog(null, "tem certeza que deseja apagar") == 0) {
            controller.apagar(Integer.parseInt(id.getText()));
            JOptionPane.showMessageDialog(null, "Dizimista apagado com sucesso");
            carregarTodos();
        }
    }
}
