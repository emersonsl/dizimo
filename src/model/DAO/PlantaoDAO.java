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
import model.bean.Plantao;
import model.bean.Plantonista;
import model.bean.Presidente;

/**
 *
 * @author Emerson
 */
public class PlantaoDAO {

    public static void salvar(Plantao plantao) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = c.prepareStatement("INSERT INTO plantao(hora, data, plantonista_id_plantonista, presidente_id_presidente) VALUES (?,?,?,?)");

            stmt.setTime(1, plantao.getHora());
            stmt.setDate(2, plantao.getData());
            stmt.setInt(3, plantao.getLancador().getId());
            stmt.setInt(4, plantao.getPresidente().getId());
            stmt.executeUpdate();

            rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
            if(rs.next())
                plantao.setId(rs.getInt(1));
        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static List<Plantao> recuperar() {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("SELECT * FROM plantao ORDER BY data DESC");
            rs = stmt.executeQuery();

            List<Plantao> plantoes = new ArrayList<>();
            while (rs.next()) {
                Plantonista plantonista = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Presidente presidente = PresidenteDAO.recuperar(rs.getInt("presidente_id_presidente"));
                Plantao p = new Plantao(rs.getInt("id_plantao"), rs.getTime("hora"), rs.getDate("Data"), plantonista, presidente);

                plantoes.add(p);
            }
            return plantoes;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static Plantao recuperar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From plantao where id_plantao = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Plantonista plantonista = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Presidente presidente = PresidenteDAO.recuperar(rs.getInt("presidente_id_presidente"));
                Plantao p = new Plantao(rs.getInt("id_plantao"), rs.getTime("hora"), rs.getDate("Data"), plantonista, presidente);
                return p;
            }
            return null;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static List<Plantao> recuperar(Date data) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("SELECT * FROM plantao where data = ? ORDER BY data DESC");
            stmt.setDate(1, data);
            rs = stmt.executeQuery();

            List<Plantao> plantoes = new ArrayList<>();
            while (rs.next()) {
                Plantonista plantonista = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Presidente presidente = PresidenteDAO.recuperar(rs.getInt("presidente_id_presidente"));
                Plantao p = new Plantao(rs.getInt("id_plantao"), rs.getTime("hora"), rs.getDate("Data"), plantonista, presidente);

                plantoes.add(p);
            }
            return plantoes;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

    public static void atualizar(Plantao plantao) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("UPDATE plantao SET hora = ?, data =  ?, plantonista_id_plantonista = ? , presidente_id_presidente = ? WHERE id_plantao = ?");

            stmt.setTime(1, plantao.getHora());
            stmt.setDate(2, plantao.getData());
            stmt.setInt(3, plantao.getLancador().getId());
            stmt.setInt(4, plantao.getPresidente().getId());

            stmt.setInt(5, plantao.getId());

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
            stmt = c.prepareStatement("DELETE FROM plantao WHERE id_plantao = ?");

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }

}
