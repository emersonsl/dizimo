package model.bean;

import java.sql.Date;

public class Dizimista {

    private Integer id;
    private String nome;
    private String email;
    private String telefone;
    private Date dataNascimento;
    private String grupoMovimentoPastoral;
    private Date dataInscricao;
    private Conjuge conjuge;
    private String rua;
    private String bairro;
    private String numero;
    private String complemento;

    public Dizimista(String nome, String email, String telefone, Date dataNascimento, String grupoMovimentoPastoral, Date dataInscricao, Conjuge conjuge, String rua, String bairro, String numero, String complemento) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.grupoMovimentoPastoral = grupoMovimentoPastoral;
        this.dataInscricao = dataInscricao;
        this.conjuge = conjuge;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Dizimista(Integer id, String nome, String email, String telefone, Date dataNascimento, String grupoMovimentoPastoral, Date dataInscricao, Conjuge conjuge, String rua, String bairro, String numero, String complemento) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.grupoMovimentoPastoral = grupoMovimentoPastoral;
        this.dataInscricao = dataInscricao;
        this.conjuge = conjuge;
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.complemento = complemento;
    }

    public Dizimista() {
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGrupoMovimentoPastoral() {
        return grupoMovimentoPastoral;
    }

    public void setGrupoMovimentoPastoral(String grupoMovimentoPastoral) {
        this.grupoMovimentoPastoral = grupoMovimentoPastoral;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Conjuge getConjuge() {
        return conjuge;
    }

    public void setConjuge(Conjuge conjuge) {
        this.conjuge = conjuge;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    @Override
    public String toString(){
        return id+" - "+nome;
    }

}
