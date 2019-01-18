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
import model.bean.Conjuge;
import model.bean.Dizimista;
import model.bean.Plantao;
import model.bean.Plantonista;
import model.bean.Presidente;

/**
 *
 * @author Emerson
 */
public class PlantaoDAO {
    
    public static List<Plantao> recuperar() {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From plantao");
            rs = stmt.executeQuery();

            List<Plantao> plantoes = new ArrayList<>();
            while (rs.next()) {
                Plantonista plantonista = PlantonistaDAO.recuperar(rs.getInt("plantonista_id_plantonista"));
                Presidente presidente = PresidenteDAO.recuperar(rs.getInt("presidente_id_presidente"));
                Plantao p = new Plantao(rs.getInt("id_plantao"), rs.getTime("hora"), rs.getDate("Data"),plantonista , presidente);
                
                plantoes.add(p);
            }
            return plantoes;
        } catch (SQLException ex) {
            return null;
        } finally {
            Conexao.closeConnection(c, stmt);
        }
    }
    
}
