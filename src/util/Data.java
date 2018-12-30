/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Emerson
 */
public abstract class Data {
    public static Date criar(int dia, int mes, int ano){
        return new Date(new GregorianCalendar(ano,mes-1,dia).getTimeInMillis());
    }
    public static Date criar(String data){
        String[] dataFormat = data.split("/");
        int dia = Integer.parseInt(dataFormat[0]);
        int mes = Integer.parseInt(dataFormat[1]);
        int ano = Integer.parseInt(dataFormat[2]);
        return new Date(new GregorianCalendar(ano,mes-1,dia).getTimeInMillis());
    }
    public static Date getDataAtual(){
        return new Date(System.currentTimeMillis());
    }
    public static String getDataAtualStr(){
        StringBuilder dataStr = new StringBuilder();
        Date data = new Date(System.currentTimeMillis());
        String [] dataFormat = data.toString().split("-");
        dataStr.append(dataFormat[2]).append("/").append(dataFormat[1]).append("/").append(dataFormat[0]);
        return dataStr.toString();
    }
    public static boolean isValid(String data){
        String regex = "((3[0-1])|([1-2]?[0-9]|(0?[1-9])))/((1[0-2])|(0?[1-9]))/((1[0-9][0-9][0-9])|(2[0-1][0-9][0-9]))";
        return data.matches(regex);
    }
    
}
