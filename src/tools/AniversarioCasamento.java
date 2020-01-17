/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Emers
 */
public class AniversarioCasamento extends Aniversario{
    
    private String nomeConjuge;
    
    public AniversarioCasamento(){}
    
    public AniversarioCasamento(Date data, String nome, String nomeConjuge){
        super(data, nome);
        this.nomeConjuge = nomeConjuge;
    }
    
    @Override
    public String toString(){
        return getDataFormatada(super.getData())+" - "+super.getNome()+" e "+nomeConjuge+" - "+getIdade(super.getData())+" anos";
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof AniversarioCasamento){
            return super.getNome().equals(((AniversarioCasamento) o).nomeConjuge);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.nomeConjuge);
        return hash;
    }
    
}
