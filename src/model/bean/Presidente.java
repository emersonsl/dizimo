package model.bean;

public class Presidente {
	private int id;
	private String nome;
	private int denominacao;
	
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
	public int getDenominacao() {
		return denominacao;
	}
	/**
	 * @param denominacao the denominacao to set
	 */
	public void setDenominacao(int denominacao) {
		this.denominacao = denominacao;
	}
}
