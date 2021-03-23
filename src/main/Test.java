/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalDateTimeStringConverter;
import tools.ExportarPDF;

/**
 *
 * @author Emerson
 */
public class Test {

    public static void main(String[] args) {
        Date data = new Date(Date.valueOf(LocalDate.now()).getTime());
        System.out.println("data: "+data);
        
        System.out.println("data: "+data);

        
    }
    
    public static void data(Date data){
        data.setTime(1+data.getTime());        
    }
}
