/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Date;
import java.util.List;
import model.DAO.ConjugeDAO;
import model.DAO.ContribuicaoDAO;
import model.DAO.DizimistaDAO;
import model.bean.Contribuicao;
import model.bean.Dizimista;
import tools.ExportarPDF;

/**
 *
 * @author Emerson
 */
public class Test {

    public static void main(String[] args) {
        contribuicoesData();
    }
    
    private static void contribuicoesData(){
        Date d1 = new Date(119, 0, 1);
        Date d2 = new Date(119, 5, 3);
        System.out.println("DATAS Entre: " + d1 + " e " + d2);
        
        ExportarPDF.ContribuicoesDosDizimistas(d2, d1);
    }
    
    private static void contribuicoes(){
        
        Dizimista d = DizimistaDAO.recuperar(6011);
        System.out.println("id: "+d.getId()+" nome: "+d.getNome());
        ExportarPDF.ContribuicoesDoDizimista(d);
    }
    
    private static void aniversarios(){
        Date d1 = new Date(119, 4, 26);
        Date d2 = new Date(119, 5, 2);
        System.out.println("DATAS Entre" + d1 + " e " + d2);
        System.out.println("Aniversario dos dizimistas");
        System.out.println(DizimistaDAO.recuperar(d1, d2));
        System.out.println("Aniversario de casamento");
        System.out.println(ConjugeDAO.recuperar(d1, d2));

        ExportarPDF.aniversarioDosDizimistas(d1, d2);
    }

}
