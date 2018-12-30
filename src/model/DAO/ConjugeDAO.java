/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Conjuge;

/**
 *
 * @author Emerson
 */
public class ConjugeDAO {
    public static void salvar(Conjuge conjuge){
        
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            stmt = c.prepareStatement("INSERT INTO conjuge(nome,data_nascimento,data_casamento)VALUES(?,?,?)");
            
            stmt.setString(1, conjuge.getNome());
            stmt.setDate(2, conjuge.getDataNascimento());
            stmt.setDate(3, conjuge.getDataCasamento());
            
            stmt.executeUpdate();
            
            //recuperando ID
            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            if(rs.next()){
                conjuge.setId(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Conexao.closeConnection(c, stmt);
        }
    }
    
    public static Conjuge recuperar(int id){
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

         try {
             stmt = c.prepareStatement("SELECT * FROM conjuge WHERE id_conjuge=?");
             stmt.setInt(1, id);
             rs = stmt.executeQuery();
             rs.next();
             return new Conjuge(rs.getInt("id_conjuge"), rs.getString("nome"), rs.getDate("data_nascimento"), rs.getDate("data_casamento"));
         } catch (SQLException ex) {
             return null;
         }finally{
             Conexao.closeConnection(c, stmt);
         }
    }
}
