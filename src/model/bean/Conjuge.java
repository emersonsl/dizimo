package model.bean;

import java.sql.Date;

public class Conjuge {

    private int id;
    private String nome;
    private Date dataNascimento;
    private Date dataCasamento;

    public Conjuge(String nome, Date dataNascimento, Date dataCasamento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataCasamento = dataCasamento;
    }

    public Conjuge(int id, String nome, Date dataNascimento, Date dataCasamento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.dataCasamento = dataCasamento;
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
     * @return the dataNascimento
     */
    public Date getDataNascimento() {
        return dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the dataCasamento
     */
    public Date getDataCasamento() {
        return dataCasamento;
    }

    /**
     * @param dataCasamento the dataCasamento to set
     */
    public void setDataCasamento(Date dataCasamento) {
        this.dataCasamento = dataCasamento;
    }
    
    public String toString(){
        return "Nome: "+nome+"Data de casamento"+dataCasamento;
    }
}
