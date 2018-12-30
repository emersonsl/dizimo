package model.bean;

import java.sql.Date;

public class Plantonista extends Pessoa{
	private String senha;
	private boolean isCoordenador;

        public Plantonista(String senha, boolean isCoordenador, Integer id, String nome, String email, String telefone, Endereco endereco, Date dataNascimento) {
            super(id, nome, email, telefone, endereco, dataNascimento);
            this.senha = senha;
            this.isCoordenador = isCoordenador;
        }
	
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	/**
	 * @return the isCoordenador
	 */
	public boolean isCoordenador() {
		return isCoordenador;
	}
	/**
	 * @param isCoordenador the isCoordenador to set
	 */
	public void setCoordenador(boolean isCoordenador) {
		this.isCoordenador = isCoordenador;
	}

}
