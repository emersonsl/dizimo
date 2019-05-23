/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Conjuge;

/**
 *
 * @author Emerson
 */
public class ConjugeDAO {

    public static void salvar(Conjuge conjuge) {

        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("INSERT INTO conjuge(dizimista_id_dizimista,nome,data_nascimento,data_casamento)VALUES(?,?,?,?)");

            stmt.setInt(1, conjuge.getId());
            stmt.setString(2, conjuge.getNome());
            stmt.setDate(3, conjuge.getDataNascimento());
            stmt.setDate(4, conjuge.getDataCasamento());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static Conjuge recuperar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = c.prepareStatement("SELECT * FROM conjuge WHERE dizimista_id_dizimista=?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            rs.next();
            return new Conjuge(rs.getInt("dizimista_id_dizimista"), rs.getString("nome"), rs.getDate("data_nascimento"), rs.getDate("data_casamento"));
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static List <Conjuge> recuperar(Date dataInicio, Date dataFinal) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = c.prepareStatement("SELECT * FROM conjuge WHERE dayofyear(data_casamento) BETWEEN ? and ? ORDER BY(nome)");
            stmt.setInt(1, dataInicio.toLocalDate().getDayOfYear());
            stmt.setInt(2, dataFinal.toLocalDate().getDayOfYear());
       
            rs = stmt.executeQuery();
            
            Conjuge con;
            List <Conjuge> conjuges = new ArrayList<>();
            while (rs.next()) {
                con = new Conjuge(rs.getInt("dizimista_id_dizimista"), rs.getString("nome"), rs.getDate("data_nascimento"), rs.getDate("data_casamento"));
                conjuges.add(con);
            }
            
            return conjuges;
            
        } catch (SQLException ex) {
            System.out.println("ex:" + ex);
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    static void atualizar(Conjuge conjuge) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("UPDATE conjuge SET nome = ?, data_nascimento = ?, data_casamento = ? WHERE dizimista_id_dizimista = ?");

            stmt.setString(1, conjuge.getNome());
            stmt.setDate(2, conjuge.getDataNascimento());
            stmt.setDate(3, conjuge.getDataCasamento());
            stmt.setInt(4, conjuge.getId());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static void apagar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("DELETE FROM conjuge WHERE dizimista_id_dizimista = ?");

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }
}
