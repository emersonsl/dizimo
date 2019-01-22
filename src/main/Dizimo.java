/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import model.DAO.ContribuicaoDAO;
import model.bean.Contribuicao;

/**
 *
 * @author Emerson
 */
public class Dizimo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = "";
        System.out.println(s.matches("6\\d"));
        Contribuicao c = ContribuicaoDAO.recuperar().get(0);
        System.out.println("Ano: "+c.getAno());
    }
    
}
