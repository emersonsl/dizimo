package model.bean;

import java.time.Year;
import java.util.Objects;
import util.Mes;

public class Contribuicao {

    private int id;
    private Double valor;
    private Mes mes;
    private Year ano;
    private Dizimista dizimista;
    private Plantonista plantonista;
    private Plantao plantao;
    
    public Contribuicao(){
        
    }

    public Contribuicao(int id, Double valor, Mes mes, Year ano, Dizimista dizimista, Plantonista plantonista, Plantao plantao) {
        this.id = id;
        this.valor = valor;
        this.mes = mes;
        this.ano = ano;
        this.dizimista = dizimista;
        this.plantonista = plantonista;
        this.plantao = plantao;
    }

    public Contribuicao(Double valor, Mes mes, Year ano, Dizimista dizimista, Plantonista plantonista, Plantao plantao) {
        this.valor = valor;
        this.mes = mes;
        this.ano = ano;
        this.dizimista = dizimista;
        this.plantonista = plantonista;
        this.plantao = plantao;
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
     * @return the valor
     */
    public Double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Double valor) {
        this.valor = valor;
    }

    /**
     * @return the mes
     */
    public Mes getMes() {
        return mes;
    }

    /**
     * @param mes the mes to set
     */
    public void setMes(Mes mes) {
        this.mes = mes;
    }

    /**
     * @return the ano
     */
    public Year getAno() {
        return ano;
    }

    /**
     * @param ano the ano to set
     */
    public void setAno(Year ano) {
        this.ano = ano;
    }

    /**
     * @return the dizimista
     */
    public Dizimista getDizimista() {
        return dizimista;
    }

    /**
     * @param dizimista the dizimista to set
     */
    public void setDizimista(Dizimista dizimista) {
        this.dizimista = dizimista;
    }

    /**
     * @return the plantonista
     */
    public Plantonista getPlantonista() {
        return plantonista;
    }

    /**
     * @param plantonista the plantonista to set
     */
    public void setPlantonista(Plantonista plantonista) {
        this.plantonista = plantonista;
    }

    /**
     * @return the plantao
     */
    public Plantao getPlantao() {
        return plantao;
    }

    /**
     * @param plantao the plantao to set
     */
    public void setPlantao(Plantao plantao) {
        this.plantao = plantao;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Contribuicao){
            return ((Contribuicao) o).mes.getMes()==mes.getMes()&&((Contribuicao) o).ano.equals(ano);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.mes);
        hash = 29 * hash + Objects.hashCode(this.ano);
        return hash;
    }

}
