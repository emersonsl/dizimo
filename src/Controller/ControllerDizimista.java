/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.sql.Date;
import java.util.List;
import model.DAO.DizimistaDAO;
import model.bean.Conjuge;
import model.bean.Dizimista;
import model.bean.Endereco;
import util.Data;

/**
 *
 * @author Emerson
 */
public class ControllerDizimista {
    private static ControllerDizimista instance;
    
    private ControllerDizimista(){
        
    }
    
    public static ControllerDizimista getInstance(){
        
        if(instance==null)
            instance = new ControllerDizimista();
        return instance;
    }
    
    public void criar(Integer id, String nome, String email, String telefone, Endereco endereco, Date dataNascimento, String grupoMovimentoPastoral, Conjuge conjuge, Date dataInscricao){
        Dizimista d = new Dizimista(id, nome, email, telefone, endereco, dataNascimento, grupoMovimentoPastoral, dataInscricao, conjuge);
        DizimistaDAO.salvar(d);
    }
    
    public List<Dizimista> recuperar(){
        return DizimistaDAO.recuperar();
    }
    
    public List<Dizimista> recuperar(String nomeSobrenome){
        return DizimistaDAO.recuperar(nomeSobrenome);
    }
    
    public Dizimista recuperar(int id){
        return DizimistaDAO.recuperar(id);
    }

    public void atualizar(Integer id, String nome, String email, String telefone, Endereco endereco, Date dataNascimento, String grupoMovimentoPastoral, Conjuge conjuge, Date dataInscricao) {
        Dizimista d = new Dizimista(id, nome, email, telefone, endereco, dataNascimento, grupoMovimentoPastoral, dataInscricao, conjuge);
        DizimistaDAO.atualizar(d);
    }
    
    public void apagar(int id){
        DizimistaDAO.apagar(id);
    }
}
