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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DAO.ConjugeDAO;
import model.DAO.ContribuicaoDAO;
import model.DAO.DizimistaDAO;
import model.bean.Conjuge;
import model.bean.Dizimista;
import tools.ExportarPDF;
import view.Alertas;

/**
 * FXML Controller class
 *
 * @author Emerson
 */
public class DizimistaController implements Initializable {

    @FXML
    private CheckBox ckId, ckDataInsc, ckCasaReli;
    @FXML
    private Group gpConj;
    @FXML
    private TextField id, nome, telefone, email, grupos, rua, numero, bairro, complemento, nomeConj;
    @FXML
    private DatePicker dtNasc, dtInsc, dtNascConj, dtCasa;
    @FXML
    private Button btCadastrarSalvar, btEditarCancelar, btVerCont, btApagar, btAtivarDesativar;
    @FXML
    private TableView<Dizimista> tableViewDizimistas;
    @FXML
    private TableColumn<Dizimista, Integer> tbNum;
    @FXML
    private TableColumn<Dizimista, String> tbNome;
    @FXML
    private TextField totalDizimistas;
    @FXML
    private TextField barraBusca;
    @FXML
    private AnchorPane apEsquerdo;
    @FXML
    private AnchorPane apDados;
    @FXML
    private Button verTodos;

    private List<Dizimista> dizimistas;
    private ObservableList<Dizimista> obDizimistas;
    List<TextField> textFields;
    List<DatePicker> datePickers;
    boolean cadastrar = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addEditables();
        carregarTodos();
        tableViewDizimistas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> selecionarItemTabelaDizimistar(newValue));
    }

    public void carregarTodos() {
        tbNum.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        dizimistas = DizimistaDAO.recuperar();

        obDizimistas = FXCollections.observableArrayList(dizimistas);
        tableViewDizimistas.setItems(obDizimistas);
        totalDizimistas.setText(dizimistas.size() + " dizimista(s)");
    }

    private void selecionarItemTabelaDizimistar(Dizimista d) {
        clear();
        if (d != null) {
            btAtivarDesativar.setVisible(true);
            apDados.setDisable(false);
            id.setText(d.getId().toString());
            nome.setText(d.getNome());
            dtNasc.setValue(d.getDataNascimento().toLocalDate());
            telefone.setText(d.getTelefone());
            email.setText(d.getEmail());
            dtInsc.setValue(d.getDataInscricao().toLocalDate());
            grupos.setText(d.getGrupoMovimentoPastoral());
            rua.setText(d.getRua());
            numero.setText(d.getNumero());
            bairro.setText(d.getBairro());
            complemento.setText(d.getComplemento());
            Conjuge con = d.getConjuge();
            if (con != null) {
                gpConj.setVisible(true);
                ckCasaReli.setSelected(true);
                nomeConj.setText(con.getNome());
                dtNascConj.setValue(con.getDataNascimento().toLocalDate());
                dtCasa.setValue(con.getDataCasamento().toLocalDate());
            } else {
                ckCasaReli.setSelected(false);
                gpConj.setVisible(false);
            }
            if (!d.isAtivo()) {
                apDados.setDisable(true);
                btAtivarDesativar.setText("Ativar");
            } else {
                btAtivarDesativar.setText("Desativar");
            }
        } else {
            selectMode(1);
            btAtivarDesativar.setVisible(false);
        }
    }

    public void editarMode() {
        Dizimista d = tableViewDizimistas.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar")) {
            if (d != null) {
                selectMode(3);
            } else {
                selectMode(2);
            }
        }
    }

    private void addEditables() {
        textFields = new ArrayList<>();
        textFields.add(id);
        textFields.add(nome);
        textFields.add(telefone);
        textFields.add(email);
        textFields.add(grupos);
        textFields.add(rua);
        textFields.add(numero);
        textFields.add(bairro);
        textFields.add(complemento);
        textFields.add(nomeConj);

        datePickers = new ArrayList<>();
        datePickers.add(dtNasc);
        datePickers.add(dtInsc);
        datePickers.add(dtNascConj);
        datePickers.add(dtCasa);
    }

    private void clear() {
        apDados.setDisable(false);
        textFields.forEach((t) -> {
            t.setText("");
        });
        datePickers.forEach((d) -> {
            d.setValue(null);
        });
        ckCasaReli.setSelected(false);
        ckDataInsc.setSelected(false);
        ckId.setSelected(false);
        dtInsc.setDisable(false);
    }

    private void setEditables(boolean b) {
        textFields.forEach((t) -> {
            t.setEditable(b);
        });
        datePickers.forEach((d) -> {
            d.setEditable(b);
        });
    }

    private void selectMode(int i) {
        switch (i) {
            case 1:
                //busca
                carregarTodos();
                id.setDisable(false);
                ckId.setVisible(false);
                ckDataInsc.setVisible(false);
                ckCasaReli.setVisible(false);
                setEditables(false);
                gpConj.setVisible(false);
                btCadastrarSalvar.setText("Cadastrar");
                btEditarCancelar.setText("Editar");
                btVerCont.setVisible(true);
                btApagar.setVisible(true);
                apEsquerdo.setDisable(false);
                clear();
                break;
            case 2:
                //cadastrar
                clear();
                cadastrar = true;
                ckId.setVisible(true);
                ckDataInsc.setVisible(true);
                ckCasaReli.setVisible(true);
                ckCasaReli.setSelected(false);
                setEditables(true);
                gpConj.setVisible(false);
                btCadastrarSalvar.setText("Salvar");
                btEditarCancelar.setText("Cancelar");
                btVerCont.setVisible(false);
                btApagar.setVisible(false);
                btAtivarDesativar.setVisible(false);
                apEsquerdo.setDisable(true);
                apDados.setDisable(false);
                break;
            case 3:
                //editar
                cadastrar = false;
                id.setDisable(true);
                ckDataInsc.setVisible(true);
                ckCasaReli.setVisible(true);
                setEditables(true);
                btCadastrarSalvar.setText("Salvar");
                btEditarCancelar.setText("Cancelar");
                btVerCont.setVisible(false);
                btApagar.setVisible(false);
                btAtivarDesativar.setVisible(false);
                apEsquerdo.setDisable(true);
                apDados.setDisable(false);
                break;
            default:
                break;
        }
    }

    public void grupoConjVisivel() {
        gpConj.setVisible(ckCasaReli.isSelected());
    }

    public void setIdAuto() {
        id.setDisable(ckId.isSelected());
    }

    public void setDataInscAuto() {
        dtInsc.setDisable(ckDataInsc.isSelected());
        if (ckDataInsc.isSelected()) {
            dtInsc.setValue(LocalDate.now());
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

    public void cadastrarSalvar() {
        if (btCadastrarSalvar.getText().equals("Cadastrar")) {
            cadastrar = true;
            selectMode(2);
        } else {
            if (cadastrar) {
                salvar();
            } else {
                atualizar();
            }
        }
    }

    public void editarCancelar() {
        Dizimista d = tableViewDizimistas.getSelectionModel().getSelectedItem();
        if (btEditarCancelar.getText().equals("Editar") && Alertas.validarSelecaoEntidade(d, "Dizimista")) {
            selectMode(3);
        } else {
            selectMode(1);
        }
    }

    public void verContribuicoes() {
        Dizimista d = tableViewDizimistas.getSelectionModel().getSelectedItem();
        if (Alertas.validarSelecaoEntidade(d, "Dizimista")) {
            try {
                ExportarPDF.ContribuicoesDoDizimista(d);
            } catch (DocumentException | IOException ex) {
                Alertas.erroAberturaAquivo();
            }
        }
    }

    public void apagar() {
        Dizimista d = tableViewDizimistas.getSelectionModel().getSelectedItem();
        if (Alertas.validarSelecaoEntidade(d, "Dizimista")) {
            if (Alertas.confirmarApagar("Dizimista") && Alertas.verificarRestricao(ContribuicaoDAO.recuperar(d), "Dizimista")) {
                DizimistaDAO.apagar(d.getId());
                Alertas.apagadoSucesso("Dizimista");
                carregarTodos();
            }
        }
    }

    public void procurar() {
        if (!barraBusca.getText().equals("")) {
            dizimistas = DizimistaDAO.recuperar(barraBusca.getText());

            obDizimistas = FXCollections.observableArrayList(dizimistas);
            tableViewDizimistas.setItems(obDizimistas);
            totalDizimistas.setText(dizimistas.size() + " dizimistas encontrado(s)");
        } else {
            carregarTodos();
        }

    }

    private void salvar() {
        if (!validarCampos()) {
            return;
        }
        Conjuge c = null;
        if (ckCasaReli.isSelected()) {
            c = new Conjuge(nomeConj.getText().toUpperCase(), Date.valueOf(dtNascConj.getValue()), Date.valueOf(dtCasa.getValue()));
        }
        Dizimista d;
        if (ckId.isSelected()) {
            d = new Dizimista(nome.getText().toUpperCase(), email.getText().toUpperCase(), telefone.getText(), Date.valueOf(dtNasc.getValue()), grupos.getText().toUpperCase(), Date.valueOf(dtInsc.getValue()), c, rua.getText().toUpperCase(), bairro.getText().toUpperCase(), numero.getText().toUpperCase(), complemento.getText().toUpperCase(), true);
        } else {
            d = new Dizimista(Integer.parseInt(id.getText()), nome.getText().toUpperCase(), email.getText().toUpperCase(), telefone.getText(), Date.valueOf(dtNasc.getValue()), grupos.getText().toUpperCase(), Date.valueOf(dtInsc.getValue()), c, rua.getText().toUpperCase(), bairro.getText().toUpperCase(), numero.getText().toUpperCase(), complemento.getText().toUpperCase(), true);
        }
        DizimistaDAO.salvar(d);
        Alertas.cadastradoSucesso("Dizimista");
        selectMode(1);
    }

    private void atualizar() {
        if (!validarCampos()) {
            return;
        }
        Dizimista d = tableViewDizimistas.getSelectionModel().getSelectedItem();
        d.setNome(nome.getText().toUpperCase());
        d.setDataNascimento(Date.valueOf(dtNasc.getValue()));
        d.setTelefone(telefone.getText());
        d.setEmail(email.getText().toUpperCase());
        d.setDataInscricao(Date.valueOf(dtInsc.getValue()));
        d.setGrupoMovimentoPastoral(grupos.getText().toUpperCase());
        d.setRua(rua.getText().toUpperCase());
        d.setNumero(numero.getText().toUpperCase());
        d.setBairro(bairro.getText().toUpperCase());
        d.setComplemento(complemento.getText().toUpperCase());

        if (d.getConjuge() == null && ckCasaReli.isSelected()) {
            Conjuge c = new Conjuge(nomeConj.getText().toUpperCase(), Date.valueOf(dtNascConj.getValue()), Date.valueOf(dtCasa.getValue()));
            c.setId(d.getId());
            ConjugeDAO.salvar(c);
            d.setConjuge(c);
        } else if (d.getConjuge() != null) {
            Conjuge c = d.getConjuge();
            if (ckCasaReli.isSelected()) {
                c.setNome(nomeConj.getText().toUpperCase());
                c.setDataNascimento(Date.valueOf(dtNascConj.getValue()));
                c.setDataCasamento(Date.valueOf(dtCasa.getValue()));
            } else {
                ConjugeDAO.apagar(c.getId());
                d.setConjuge(null);
            }
        }
        DizimistaDAO.atualizar(d);
        Alertas.atualizadoSucesso("Dizimista");
        selectMode(1);
    }

    public void ativarDesativar() {
        Dizimista d = tableViewDizimistas.getSelectionModel().getSelectedItem();
        if (Alertas.validarSelecaoEntidade(d, "Dizimista")) {
            if (Alertas.confirmarSetStatus(d.isAtivo(), "Dizimista")) {
                d.setAtivo(!d.isAtivo());
                DizimistaDAO.atualizar(d);
                Alertas.ativadoDesativadorSucesso(d.isAtivo(), "Dizimista");
                selectMode(1);
            }
        }
    }

    private boolean validarCampos() {
        if (cadastrar && !ckId.isSelected() && !Alertas.validarCadastroIdDizimista(id.getText())) {
            return false;
        }

        if (!Alertas.validarNome(nome.getText()) || !Alertas.validarData(dtNasc.getValue(), "nascimento") || !Alertas.validarTelefone(telefone.getText()) || !Alertas.validarData(dtInsc.getValue(), "inscrição")) {
            return false;
        }

        if (!Alertas.validarTexto(rua.getText(), "Rua") || !Alertas.validarNumeroEndereco(numero.getText()) || !Alertas.validarTexto(bairro.getText(), "Bairro")) {
            return false;
        }

        if (ckCasaReli.isSelected() && (!Alertas.validarTexto(nomeConj.getText(), "Nome do esposo(a)") || !Alertas.validarData(dtNascConj.getValue(), "nascimento do esposo(a)") || !Alertas.validarData(dtCasa.getValue(), "casamento"))) {
            return false;
        }
        return true;
    }
    
    public void verTodos(){
        try {
            ExportarPDF.listarDizimistas();
        } catch (DocumentException | IOException ex) {
            Alertas.erroAberturaAquivo();
        }
    }
}
