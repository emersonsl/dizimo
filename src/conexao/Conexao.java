/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.Configuracao;
import view.Alertas;

/**
 *
 * @author Emerson
 */
public abstract class Conexao {

    
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost:3306/dizimo_nossa_senhora_das_gracas";
    private static String USER = "root";
    private static String PASSWORD = "@senha";
    private static Connection connection;

    private static void carregarConfiguracao() throws IOException {
        String local = Configuracao.getParametro("db.local");
        String nome = Configuracao.getParametro("db.nome");
        URL = "jdbc:mysql://"+local+":3306/"+nome;
        USER = Configuracao.getParametro("db.usuario");
        PASSWORD = Configuracao.getParametro("db.senha");
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                carregarConfiguracao();
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException ex1) {
                Alertas.erroConexaoBD();
                throw new RuntimeException("Erro na conexão com o banco de dados!" + "\n" + ex1.getLocalizedMessage());
            } catch (IOException ex) {
                Alertas.erroArquivoConfiguracao();
            }
        }
        return connection;
    }
}
