/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Emerson
 */
public enum Mes {
    JAN(1), FEV(2), MAR(3), ABR(4), MAIO(5), JUN(6), JUL(7), AGO(8), SET(9), OUT(10), NOV(11), DEZ(12);
    
    private final int mes;
    
    Mes (int mesV){
       mes = mesV; 
    }
    
    public int getMes(){
        return mes;
    }
    
    public Mes setMes(int index){
        int i=1;
        for(Mes m: Mes.values()){
            if(i==index){
                return m;
            }
            i++;
        }
        return Mes.JAN;
    }
}
