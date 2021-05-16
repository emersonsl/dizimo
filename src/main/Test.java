/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.DizimistaDAO;
import model.bean.Dizimista;
import tools.ExportarPDF;

/**
 *
 * @author Emerson
 */
public class Test {

    public static void main(String[] args) {
        /*String command, dbName, SEPARATOR;
        
        command = "C:\\wamp64\\bin\\mysql\\mysql5.7.26\\bin\\mysqldump.exe";
        dbName = "dizimo_sao_joao_calabria";
        
        SEPARATOR = "\\";
        ProcessBuilder pb = new ProcessBuilder(

        command,

        "--user=root",

        "--password=",

        dbName,

        "--result-file=" + "." + SEPARATOR + "Backup" + SEPARATOR + dbName + ".sql");
        
        try {
            pb.start();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Deu merda: "+ex.getMessage());
        }*/
        
        Date dataI = new Date(121, 0, 1);
        Date dataF = new Date(121, 4, 16);
        try {
            ExportarPDF.listarSorteio(dataI, dataF);
        } catch (DocumentException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
