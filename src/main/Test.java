/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tools.Configuracao;

/**
 *
 * @author Emerson
 */
public class Test {

    public static void main(String[] args) {
        try { 
            String idInicial = Configuracao.getParametro("db.idInicial");
            System.out.println("ID inicial: "+idInicial+"PN: "+idInicial.substring(0, 1));
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
