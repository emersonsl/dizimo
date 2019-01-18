package model.bean;

import util.Denominacao;

public class Presidente {

    private int id;
    private String nome;
    private Denominacao denominacao;

    public Presidente() {
    }

    public Presidente(String nome, Denominacao denominacao) {
        this.nome = nome;
        this.denominacao = denominacao;
    }

    public Presidente(int id, String nome, Denominacao denominacao) {
        this.id = id;
        this.nome = nome;
        this.denominacao = denominacao;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the denominacao
     */
    public Denominacao getDenominacao() {
        return denominacao;
    }

    /**
     * @param denominacao the denominacao to set
     */
    public void setDenominacao(Denominacao denominacao) {
        this.denominacao = denominacao;
    }
    
    public String toString(){
        return getDenominacao()+". "+nome;
    }
}
