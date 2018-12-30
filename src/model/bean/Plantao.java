package model.bean;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Plantao {
	private int id;
	private Time hora;
	private Date data;
	private List <Contribuicao> contribuicoes;
	
	/**
	 * Construct of class
	 */
	public Plantao() {
		setContribuicoes(new ArrayList<Contribuicao>());
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
	 * @return the hora
	 */
	public Time getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(Time hora) {
		this.hora = hora;
	}
	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
	/**
	 * @return the contribuicoes
	 */
	public List <Contribuicao> getContribuicoes() {
		return contribuicoes;
	}
	/**
	 * @param contribuicoes the contribuicoes to set
	 */
	public void setContribuicoes(List <Contribuicao> contribuicoes) {
		this.contribuicoes = contribuicoes;
	}	
}
