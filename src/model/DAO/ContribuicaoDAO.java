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
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.Contribuicao;
import model.bean.Dizimista;
import model.bean.Plantao;
import model.bean.Plantonista;
import util.Mes;

/**
 *
 * @author Emerson
 */
public class ContribuicaoDAO {

    public static void salvar(Contribuicao contribuicao) {

        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("INSERT INTO contribuicao(dizimista_id_dizimista, plantonista_id_plantonista, "
                    + "plantao_id_plantao, valor, mes, ano)VALUES(?,?,?,?,?,'" + contribuicao.getAno() + "')");

            stmt.setInt(1, contribuicao.getDizimista().getId());
            stmt.setInt(2, contribuicao.getPlantonista().getId());
            stmt.setInt(3, contribuicao.getPlantao().getId());
            stmt.setDouble(4, contribuicao.getValor());
            stmt.setString(5, contribuicao.getMes().name());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Contribuicao> recuperar() {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From contribuicao");
            rs = stmt.executeQuery();

            List<Contribuicao> contribuicoes = new ArrayList<>();
            while (rs.next()) {
                Dizimista d = DizimistaDAO.recuperar(rs.getInt("dizimista_id_dizimista"));
                Plantonista p = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Plantao plantao = PlantaoDAO.recuperar(rs.getInt("plantao_id_plantao"));
                Year ano = Year.parse(rs.getString("ano").substring(0, 4));

                Contribuicao contribuicao = new Contribuicao(rs.getInt("id_contribuicao"), rs.getDouble("valor"), Mes.valueOf(rs.getString("mes")), ano, d, p, plantao);
                contribuicoes.add(contribuicao);
            }
            return contribuicoes;
        } catch (SQLException ex) {
            return null;
        } 
    }
    
    public static List<Contribuicao> recuperar(Plantao plantao) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From contribuicao WHERE plantao_id_plantao=?");
            stmt.setInt(1, plantao.getId());
            rs = stmt.executeQuery();

            List<Contribuicao> contribuicoes = new ArrayList<>();
            while (rs.next()) {
                Dizimista d = DizimistaDAO.recuperar(rs.getInt("dizimista_id_dizimista"));
                Plantonista plantonista = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Plantao p = PlantaoDAO.recuperar(rs.getInt("plantao_id_plantao"));
                Year ano = Year.parse(rs.getString("ano").substring(0, 4));

                Contribuicao contribuicao = new Contribuicao(rs.getInt("id_contribuicao"), rs.getDouble("valor"), Mes.valueOf(rs.getString("mes")), ano, d, plantonista, p);
                contribuicoes.add(contribuicao);
            }
            return contribuicoes;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    public static List<Contribuicao> recuperar(Dizimista dizimista) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From contribuicao WHERE dizimista_id_dizimista = ?");
            stmt.setInt(1, dizimista.getId());
            rs = stmt.executeQuery();

            List<Contribuicao> contribuicoes = new ArrayList<>();
            while (rs.next()) {
                Dizimista d = DizimistaDAO.recuperar(rs.getInt("dizimista_id_dizimista"));
                Plantonista plantonista = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Plantao p = PlantaoDAO.recuperar(rs.getInt("plantao_id_plantao"));
                Year ano = Year.parse(rs.getString("ano").substring(0, 4));

                Contribuicao contribuicao = new Contribuicao(rs.getInt("id_contribuicao"), rs.getDouble("valor"), Mes.valueOf(rs.getString("mes")), ano, d, plantonista, p);
                contribuicoes.add(contribuicao);
            }
            return contribuicoes;
        } catch (SQLException ex) {
            return null;
        } 
    }
    
    public static List<Contribuicao> recuperar(Date dataInicial, Date dataFinal) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("SELECT contribuicao.* FROM contribuicao JOIN plantao ON plantao.id_plantao = contribuicao.plantao_id_plantao WHERE plantao.data BETWEEN ? AND ?");
            stmt.setDate(1, dataInicial);
            stmt.setDate(2, dataFinal);
            rs = stmt.executeQuery();

            List<Contribuicao> contribuicoes = new ArrayList<>();
            while (rs.next()) {
                Dizimista d = DizimistaDAO.recuperar(rs.getInt("dizimista_id_dizimista"));
                Plantonista plantonista = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Plantao p = PlantaoDAO.recuperar(rs.getInt("plantao_id_plantao"));
                Year ano = Year.parse(rs.getString("ano").substring(0, 4));

                Contribuicao contribuicao = new Contribuicao(rs.getInt("id_contribuicao"), rs.getDouble("valor"), Mes.valueOf(rs.getString("mes")), ano, d, plantonista, p);
                contribuicoes.add(contribuicao);
                System.out.println("tá aqui");
            }
            return contribuicoes;
        } catch (SQLException ex) {
            System.err.println("Excessão: "+ex);
            return null;
        } 
    }
    
    public static void atualizar(Contribuicao contribuicao) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("UPDATE contribuicao SET dizimista_id_dizimista = ?, plantonista_id_plantonista = ?, plantao_id_plantao = ?, valor = ?, mes = ?, ano = '" + contribuicao.getAno() + "' WHERE id_contribuicao = ?");

            stmt.setInt(1, contribuicao.getDizimista().getId());
            stmt.setInt(2, contribuicao.getPlantonista().getId());
            stmt.setInt(3, contribuicao.getPlantao().getId());
            stmt.setDouble(4, contribuicao.getValor());
            stmt.setString(5, contribuicao.getMes().name());
            stmt.setInt(6, contribuicao.getId());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void apagar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("DELETE FROM contribuicao WHERE id_contribuicao = ?");

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
