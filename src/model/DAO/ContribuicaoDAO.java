/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Contribuicao;

/**
 *
 * @author Emerson
 */
public class ContribuicaoDAO {
    public static void salvar(Contribuicao contribuicao){
        
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = c.prepareStatement("INSERT INTO contribuicao(dizimista_id_dizimista, plantonista_id_plantonista, "
                    + "plantao_id_plantao, valor, mes, ano)VALUES(?,?,?,?,?,'"+contribuicao.getAno()+"')");
            
            stmt.setInt(1, contribuicao.getDizimista().getId());
            stmt.setInt(2, contribuicao.getPlantonista().getId());
            stmt.setInt(3, contribuicao.getPlantao().getId());
            stmt.setDouble(4, contribuicao.getValor());
            stmt.setInt(5, contribuicao.getMes());
      
            
            stmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            Conexao.closeConnection(c, stmt);
        }
    }
}
