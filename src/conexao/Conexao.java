/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Emerson
 */
public abstract class Conexao {
    
    private static final String DRIVER = "com.mysql.jdbc.Driver"; 
    private static final String URL = "jdbc:mysql://localhost:3306/dizimo_sao_joao_calabria"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "";
    
    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException ex1) {
            throw new RuntimeException("Erro na conexão com o banco de dados!");
        }
    }
    
    public static void closeConnection(Connection c){
        try {
            if(c!=null){
                c.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao Fechar conexão");
        }
    }
    
    
    public static void closeConnection(Connection c, PreparedStatement stmt){
        try {
            closeConnection(c);
            if(stmt!=null){
                stmt.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao Fechar conexão");
        }
    }
    
    public static void closeConnection(Connection c, PreparedStatement stmt, ResultSet rs){
        try {
            closeConnection(c, stmt);
            if(rs!=null){
                rs.close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao Fechar conexão");
        }
    }  
}
