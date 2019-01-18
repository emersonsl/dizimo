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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Presidente;
import util.Denominacao;

/**
 *
 * @author Emerson
 */
public class PresidenteDAO {

    public static void salvar(Presidente presidente) {

        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("INSERT INTO presidente (nome, denominacao) VALUES (?,?)");

            stmt.setString(1, presidente.getNome());
            stmt.setString(2, presidente.getDenominacao().name());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static List<Presidente> recuperar() {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From presidente order by nome");
            rs = stmt.executeQuery();

            List<Presidente> presidentes = new ArrayList<>();
            while (rs.next()) {
                Presidente p = new Presidente(rs.getInt("id_presidente"), rs.getString("nome"), Denominacao.valueOf(rs.getString("denominacao")));
                presidentes.add(p);
            }
            return presidentes;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }
    
    public static Presidente recuperar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From presidente where id_presidente = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                Presidente p = new Presidente(rs.getInt("id_presidente"), rs.getString("nome"), Denominacao.valueOf(rs.getString("denominacao")));
                return p;
            }
            return null;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static List<Presidente> recuperar(String nomeSobrenome) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From presidente where nome like ? order by nome");
            stmt.setString(1, "%" + nomeSobrenome + "%");
            rs = stmt.executeQuery();

            List<Presidente> presidentes = new ArrayList<>();
            while (rs.next()) {
                Presidente p = new Presidente(rs.getInt("id_presidente"), rs.getString("nome"), Denominacao.valueOf(rs.getString("denominacao")));
                presidentes.add(p);
            }
            return presidentes;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static void atualizar(Presidente presidente) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("UPDATE presidente SET nome = ?, denominacao = ? WHERE id_presidente = ?");

            stmt.setString(1, presidente.getNome());
            stmt.setString(2, presidente.getDenominacao().name());
            stmt.setInt(3, presidente.getId());

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
            stmt = c.prepareStatement("DELETE FROM presidente  WHERE id_presidente = ?");

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }
}
