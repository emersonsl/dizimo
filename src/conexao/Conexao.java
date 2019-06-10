/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Emerson
 */
public abstract class Conexao {
    
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    private static final String URL = "jdbc:mysql://localhost:3306/dizimo_sao_joao_calabria"; 
    private static final String USER = "emerson"; 
    private static final String PASSWORD = "@senha";
    private static Connection connection;
    

    public static Connection getConnection(){
        if(connection == null){
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException | SQLException ex1) {
                throw new RuntimeException("Erro na conexão com o banco de dados!");
            }
        }
        return connection;
    }
}
