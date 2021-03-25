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
import model.bean.Dizimista;

/**
 *
 * @author Emerson
 */
public class DizimistaDAO {

    public static void salvar(Dizimista dizimista) {

        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = c.prepareStatement("INSERT INTO dizimista (id_dizimista,nome,data_nascimento,telefone,email,rua,numero,bairro,"
                    + "complemento,grupo_movimento_pastoral,data_inscricao, ativo) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
            if (dizimista.getId() != null) {
                stmt.setInt(1, dizimista.getId());
            } else {
                stmt.setInt(1, 0);
            }
            stmt.setString(2, dizimista.getNome());
            stmt.setDate(3, dizimista.getDataNascimento());
            stmt.setString(4, dizimista.getTelefone());
            stmt.setString(5, dizimista.getEmail());
            stmt.setString(6, dizimista.getRua());
            stmt.setString(7, dizimista.getNumero());
            stmt.setString(8, dizimista.getBairro());
            stmt.setString(9, dizimista.getComplemento());
            stmt.setString(10, dizimista.getGrupoMovimentoPastoral());
            stmt.setDate(11, dizimista.getDataInscricao());
            stmt.setBoolean(12, dizimista.isAtivo());

            stmt.executeUpdate();

            Conjuge conjuge = dizimista.getConjuge();
            if (conjuge != null) {
                if (dizimista.getId() == null) {
                    rs = stmt.executeQuery("SELECT LAST_INSERT_ID()");
                    if (rs.next()) {
                        conjuge.setId(rs.getInt(1));
                    }
                }else{
                    conjuge.setId(dizimista.getId());
                }
                ConjugeDAO.salvar(conjuge);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Dizimista> recuperar() {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From dizimista");
            rs = stmt.executeQuery();

            List<Dizimista> dizimistas = new ArrayList<>();
            while (rs.next()) {
                Conjuge con = ConjugeDAO.recuperar(rs.getInt("id_dizimista"));
                Dizimista d = new Dizimista(rs.getInt("id_dizimista"), rs.getString("nome"),
                        rs.getString("email"), rs.getString("telefone"), rs.getDate("data_nascimento"),
                        rs.getString("grupo_movimento_pastoral"), rs.getDate("data_inscricao"), con, rs.getString("rua"),
                        rs.getString("bairro"), rs.getString("numero"), rs.getString("complemento"), rs.getBoolean("ativo"));

                dizimistas.add(d);
            }
            return dizimistas;
        } catch (SQLException ex) {
            return null;
        }
    }

    public static List<Dizimista> recuperar(String nomeSobrenome) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("SELECT * FROM dizimista LEFT JOIN conjuge on dizimista.id_dizimista=conjuge.dizimista_id_dizimista where dizimista.nome like ? or conjuge.nome like ?");
            
            stmt.setString(1, "%" + nomeSobrenome + "%");
            stmt.setString(2, "%" + nomeSobrenome + "%");
            rs = stmt.executeQuery();

            List<Dizimista> dizimistas = new ArrayList<>();
            while (rs.next()) {
                Conjuge con = ConjugeDAO.recuperar(rs.getInt("id_dizimista"));

                Dizimista d = new Dizimista(rs.getInt("id_dizimista"), rs.getString("nome"),
                        rs.getString("email"), rs.getString("telefone"), rs.getDate("data_nascimento"),
                        rs.getString("grupo_movimento_pastoral"), rs.getDate("data_inscricao"), con, rs.getString("rua"),
                        rs.getString("bairro"), rs.getString("numero"), rs.getString("complemento"), rs.getBoolean("ativo"));

                dizimistas.add(d);
            }
            return dizimistas;
        } catch (SQLException ex) {
            return null;
        }
    }

        public static List<Dizimista> recuperar(Date dataInicio, Date dataFinal) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("SELECT * FROM dizimista WHERE ativo=1 and Dayofyear(data_nascimento) BETWEEN ? and ? ORDER BY(nome)" );
            stmt.setInt(1, dataInicio.toLocalDate().getDayOfYear());
            stmt.setInt(2, dataFinal.toLocalDate().getDayOfYear());
            
            rs = stmt.executeQuery();

            List<Dizimista> dizimistas = new ArrayList<>();
            while (rs.next()) {
                Conjuge con = ConjugeDAO.recuperar(rs.getInt("id_dizimista"));

                Dizimista d = new Dizimista(rs.getInt("id_dizimista"), rs.getString("nome"),
                        rs.getString("email"), rs.getString("telefone"), rs.getDate("data_nascimento"),
                        rs.getString("grupo_movimento_pastoral"), rs.getDate("data_inscricao"), con, rs.getString("rua"),
                        rs.getString("bairro"), rs.getString("numero"), rs.getString("complemento"), rs.getBoolean("ativo"));

                dizimistas.add(d);
            }
            return dizimistas;
        } catch (SQLException ex) {
            System.err.println("Ex.:"+ex);
            return null;
        }
    }
    
    public static Dizimista recuperar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = c.prepareStatement("Select * From dizimista where id_dizimista like ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            rs.next();
            Conjuge con = ConjugeDAO.recuperar(rs.getInt("id_dizimista"));
            Dizimista d = new Dizimista(rs.getInt("id_dizimista"), rs.getString("nome"),
                    rs.getString("email"), rs.getString("telefone"), rs.getDate("data_nascimento"),
                    rs.getString("grupo_movimento_pastoral"), rs.getDate("data_inscricao"), con, rs.getString("rua"),
                    rs.getString("bairro"), rs.getString("numero"), rs.getString("complemento"), rs.getBoolean("ativo"));
            return d;
        } catch (SQLException ex) {
            return null;
        } 
    }

    public static void atualizar(Dizimista dizimista) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("UPDATE dizimista SET nome = ?, data_nascimento = ?, telefone = ?, email = ?, rua = ?, numero = ?, bairro = ?"
                    + ",complemento = ?, grupo_movimento_pastoral = ?, data_inscricao = ?, ativo = ? WHERE id_dizimista = ?");

            stmt.setString(1, dizimista.getNome());
            stmt.setDate(2, dizimista.getDataNascimento());
            stmt.setString(3, dizimista.getTelefone());
            stmt.setString(4, dizimista.getEmail());
            stmt.setString(5, dizimista.getRua());
            stmt.setString(6, dizimista.getNumero());
            stmt.setString(7, dizimista.getBairro());
            stmt.setString(8, dizimista.getComplemento());
            stmt.setString(9, dizimista.getGrupoMovimentoPastoral());
            stmt.setDate(10, dizimista.getDataInscricao());
            stmt.setBoolean(11, dizimista.isAtivo());
            stmt.setInt(12, dizimista.getId());

            Conjuge conjuge = dizimista.getConjuge();
            if (conjuge != null) {
                ConjugeDAO.atualizar(conjuge);
            }

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void apagar(int id) {
        Connection c = Conexao.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = c.prepareStatement("DELETE FROM dizimista WHERE id_dizimista = ?");

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DizimistaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
