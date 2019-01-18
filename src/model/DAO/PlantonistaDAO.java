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
import model.bean.Plantonista;

/**
 *
 * @author Emerson
 */
public class PlantonistaDAO {

    public static void salvar(Plantonista plantonista) {

        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("INSERT INTO plantonista(nome, coordenador, senha) VALUES (?,?, ?)");

            stmt.setString(1, plantonista.getNome());
            stmt.setBoolean(2, plantonista.isCoordenador());
            stmt.setString(3, plantonista.getSenha());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static List<Plantonista> recuperar() {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From plantonista order by nome");
            rs = stmt.executeQuery();

            List<Plantonista> plantonistas = new ArrayList<>();
            while (rs.next()) {
                Plantonista p = new Plantonista(rs.getInt("id_plantonista"), rs.getString("nome"), rs.getString("senha"), rs.getBoolean("coordenador"));
                plantonistas.add(p);
            }
            return plantonistas;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }
    
    public static Plantonista recuperar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From plantonista where id_plantonista = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if(rs.next()) {
                Plantonista p = new Plantonista(rs.getInt("id_plantonista"), rs.getString("nome"), rs.getString("senha"), rs.getBoolean("coordenador"));
                return p;
            }
            return null;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static List<Plantonista> recuperar(String nomeSobrenome) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From plantonista where nome like ? order by nome");
            stmt.setString(1, "%" + nomeSobrenome + "%");
            rs = stmt.executeQuery();

            List<Plantonista> plantonistas = new ArrayList<>();
            while (rs.next()) {
                Plantonista p = new Plantonista(rs.getInt("id_plantonista"), rs.getString("nome"), rs.getString("senha"), rs.getBoolean("coordenador"));
                plantonistas.add(p);
            }
            return plantonistas;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static void atualizar(Plantonista plantonista) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("UPDATE plantonista SET nome = ?, coordenador = ?, senha = ? WHERE id_plantonista = ?");

            stmt.setString(1, plantonista.getNome());
            stmt.setBoolean(2, plantonista.isCoordenador());
            stmt.setString(3, plantonista.getSenha());

            stmt.setInt(4, plantonista.getId());

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
            stmt = c.prepareStatement("DELETE FROM plantonista WHERE id_plantonista = ?");

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

}
