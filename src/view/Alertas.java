/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
        alert.setContentText(tipo + " foi apagado com sucesso");
        alert.show();
    }

    public static void atualizadoSucesso(String tipo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tipo + " atualizado");
        alert.setContentText(tipo + " foi atualizado com sucesso");
        alert.show();
    }
    
    public static void cadastradoSucesso(String tipo) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tipo + " cadastrado");
        alert.setContentText(tipo + " foi cadastrado com sucesso");
        alert.show();
    }
}
