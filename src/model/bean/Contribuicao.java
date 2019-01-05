package model.bean;

import java.time.Year;

public class Contribuicao {
	private int id;
	private Double valor;
	private int mes;
	private Year ano;
	private Dizimista dizimista;
	private Plantonista plantonista;
	private Plantao plantao;

        public Contribuicao(Double valor, int mes, Year ano, Dizimista dizimista, Plantonista plantonista, Plantao plantao) {
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
	public int getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
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
	
}
