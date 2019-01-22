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
        } finally {
            Conexao.closeConnection(c, stmt);
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
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }
}
