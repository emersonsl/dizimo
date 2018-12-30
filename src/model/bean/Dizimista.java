package model.bean;

import java.sql.Date;

public class Dizimista extends Pessoa{
	private String grupoMovimentoPastoral;
	private Date dataInscricao;
	private Conjuge conjuge;
        
        public Dizimista(Integer id, String nome, String email, String telefone, Endereco endereco, Date dataNascimento, String grupoMovimentoPastoral, Date dataInscricao, Conjuge conjuge) {
            super(id, nome, email, telefone, endereco, dataNascimento);
            this.grupoMovimentoPastoral = grupoMovimentoPastoral;
            this.dataInscricao = dataInscricao;
            this.conjuge = conjuge;
        }
	/**
	 * @return the grupoMovimentoPastoral
	 */
	public String getGrupoMovimentoPastoral() {
		return grupoMovimentoPastoral;
	}
	/**
	 * @param grupoMovimentoPastoral the grupoMovimentoPastoral to set
	 */
	public void setGrupoMovimentoPastoral(String grupoMovimentoPastoral) {
		this.grupoMovimentoPastoral = grupoMovimentoPastoral;
	}
	/**
	 * @return the dataInscricao
	 */
	public Date getDataInscricao() {
		return dataInscricao;
	}
	/**
	 * @param dataInscricao the dataInscricao to set
	 */
	public void setDataInscricao(Date dataInscricao) {
		this.dataInscricao = dataInscricao;
	}
	/**
	 * @return the conjuge
	 */
	public Conjuge getConjuge() {
		return conjuge;
	}
	/**
	 * @param conjuge the conjuge to set
	 */
	public void setConjuge(Conjuge conjuge) {
		this.conjuge = conjuge;
	}

}
