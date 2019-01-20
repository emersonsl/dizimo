package model.bean;

import java.sql.Date;
import java.sql.Time;

public class Plantao {

    private Integer id;
    private Time hora;
    private Date data;
    private Plantonista lancador;
    private Presidente presidente;

    /**
     * Construct of class
     */
    public Plantao() {
    }

    public Plantao(Time hora, Date data, Plantonista lancador, Presidente presidente) {
        this.hora = hora;
        this.data = data;
        this.lancador = lancador;
        this.presidente = presidente;
    }

    public Plantao(Integer id, Time hora, Date data, Plantonista lancador, Presidente presidente) {
        this.id = id;
        this.hora = hora;
        this.data = data;
        this.lancador = lancador;
        this.presidente = presidente;
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
    public void setId(Integer id) {
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
        
    public Plantonista getLancador() {
        return lancador;
    }

    public void setLancador(Plantonista lancador) {
        this.lancador = lancador;
    }

    public Presidente getPresidente() {
        return presidente;
    }

    public void setPresidente(Presidente presidente) {
        this.presidente = presidente;
    }
}
