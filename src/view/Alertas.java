/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.DAO.DizimistaDAO;
import model.bean.Dizimista;

/**
 *
 * @author Emerson
 */
public abstract class Alertas {

    public static boolean validarNome(String nome) {
        if (nome == null || nome.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nome invalido");
            alert.setContentText("O nome digitado não corresponde ou está em branco, utilize apenas letras e espaços");
            alert.show();
            return false;
        }
        return true;
    }

    public static void erroConexaoBD() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro na conexao com banco de dados");
        alert.setContentText("Erro na conexao com o banco de dados, tente reiniciar o sistema");
        alert.show();
    }

    public static boolean validarSelecaoEntidade(Object o, String tipo) {
        if (o == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(tipo + " não selecionado (a)");
            alert.setContentText(tipo + " não foi selecionado(a), clique na tabela para selecionar");
            alert.show();
            return false;
        }
        return true;
    }

    public static boolean validarTexto(String valor, String tipo) {
        if (valor == null || valor.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(tipo + "invalido(a)");
            alert.setContentText(tipo + " digitado(a) não corresponde ou está em branco");
            alert.show();
            return false;
        }
        return true;

    }

    public static boolean confirmarApagar(String tipo) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Apagar " + tipo + "?");
        alert.setContentText("Tem certeza que deseja apagar " + tipo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    public static void apagadoSucesso(String tipo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tipo + " apagado");
        alert.setContentText(tipo + " foi apagado(a) com sucesso");
        alert.show();
    }

    public static void atualizadoSucesso(String tipo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tipo + " atualizado");
        alert.setContentText(tipo + " foi atualizado(a) com sucesso");
        alert.show();
    }

    public static void cadastradoSucesso(String tipo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tipo + " cadastrado");
        alert.setContentText(tipo + " foi cadastrado(a) com sucesso");
        alert.show();
    }

    public static boolean validarData(LocalDate valor, String tipo) {
        if (valor == null || !valor.toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Data invalida");
            alert.setContentText("A data de " + tipo + " foi preenchida incorretamente");
            alert.show();
            return false;
        }
        return true;
    }

    public static boolean validarIdDizimista(String id) {
        if (id == null || !id.matches("6\\d{3}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Número do carnê invalido");
            alert.setContentText("Número do dizimista invalido, verifique se o valor está entre 6001 e 6999");
            alert.show();
            return false;
        }
        if (DizimistaDAO.recuperar(Integer.parseInt(id)) != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Número do carnê invalido");
            alert.setContentText("Número do dizimista já está sendo usado, tente outro número");
            alert.show();
            return false;
        }
        return true;
    }

    public static boolean validarNumero(String numero) {
        if (numero == null || !numero.matches("\\d{1,6}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Número do endereço invalido");
            alert.setContentText("Número do endereço invalido");
            alert.show();
            return false;
        }
        return true;
    }

    public static boolean validarTelefone(String telefone) {
        if (telefone != null && !telefone.equals("") && !telefone.matches("\\(\\d{2}\\) \\d{4,5}\\-\\d{4}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Telefone invalino");
            alert.setContentText("Número do telefone invalido, verifique se está preenchido corretamente");
            alert.show();
            return false;
        }
        return true;
    }

    public static boolean validarHora(String hora) {
        if (hora == null || !hora.matches("\\d{2}:\\d{2}")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hora invalida");
            alert.setContentText("Horário invalido, verifique se está preenchido corretamente Ex.: 00:00");
            alert.show();
            return false;
        }
        return true;
    }
}
