/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Emerson
 */
public enum Denominacao {
    
    PE(1), DIAC(2), MINIS(3);
    
    private final int denominacao;
    
    Denominacao (int denominacaoV){
       denominacao = denominacaoV; 
    }
    
    public int denominacao(){
        return denominacao;
    }
}
