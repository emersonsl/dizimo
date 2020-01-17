/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import model.bean.Contribuicao;
import util.Mes;

/**
 *
 * @author Emerson
 */
public class Test {

    public static void main(String[] args) {
        Contribuicao c1 =  new Contribuicao();
        Contribuicao c2 =  new Contribuicao();
        c1.setMes(Mes.FEV);
        c1.setAno(Year.now());
        c2.setAno(Year.now());
        c2.setMes(Mes.FEV);
        c2.setAno(Year.now());
        List <Contribuicao> con = new ArrayList<>();
        con.add(c1);
        if(con.contains(c2)){
            System.out.println("Funfou");
        }else{
            System.out.println("Deu ruim");
        }
        
    }
}
