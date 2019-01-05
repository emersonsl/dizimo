package model.bean;


public class Plantonista {

    private int id;
    private String nome;
    private String senha;
    private boolean isCoordenador;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public boolean isIsCoordenador() {
        return isCoordenador;
    }

    public void setIsCoordenador(boolean isCoordenador) {
        this.isCoordenador = isCoordenador;
    }

}
