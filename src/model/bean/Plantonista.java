package model.bean;


public class Plantonista {

    private int id;
    private String nome;
    private String senha;
    private boolean coordenador;

    public Plantonista(String nome, boolean coordenador) {
        this.nome = nome;
        this.coordenador = coordenador;
    }

    public Plantonista(int id, String nome, String senha, boolean isCoordenador) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.coordenador = isCoordenador;
    }
    
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

    public boolean isCoordenador() {
        return coordenador;
    }

    public void setCoordenador(boolean isCoordenador) {
        this.coordenador = isCoordenador;
    }
    
    public String getCoordenador(){
        return coordenador? "Sim": "Não";
    }
    
    @Override
    public String toString(){
        return nome;
    }

}
