/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Date;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author Emers
 */
public class Aniversario implements Comparable<Aniversario>{
    private Date data;
    private String nome;

    public Aniversario() {
    }
    
    public Aniversario(Date data, String nome) {
        this.data = data;
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public static String getDataFormatada(Date data) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatoData.format(data.toLocalDate());
    }
    
    public static String getIdade(Date data) {
        return String.valueOf(Year.now().getValue() - data.toLocalDate().getYear());
    }
    
    @Override
    public String toString(){
        return getDataFormatada(data)+" - "+nome+" - "+getIdade(data)+" anos";
    }
    
    @Override
    public int compareTo(Aniversario a){
        return nome.compareTo(a.getNome());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.data);
        hash = 61 * hash + Objects.hashCode(this.nome);
        return hash;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Aniversario){
            return nome.equals(((Aniversario) o).getNome())&&data.equals(((Aniversario) o).getData());
        }
        return false;
    }
}
