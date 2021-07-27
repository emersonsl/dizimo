/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.DizimistaDAO;
import model.bean.Dizimista;
import tools.Backup;
import tools.Configuracao;
import tools.ExportarPDF;

/**
 *
 * @author Emerson
 */
public class Test {

    public static void main(String[] args) {
        
            /*String command, dbName, SEPARATOR;
            
            command = "c:\\wamp64\\bin\\mysql\\mysql5.7.26\\bin\\mysqldump.exe";
            dbName = "dizimo_sao_joao_calabria";
            
            SEPARATOR = File.separator;
            ProcessBuilder pb = new ProcessBuilder(
            
            command,
            
            "--user=root",
            
            "--password=",
            
            dbName,
            
            "--result-file=" + ".\\Backup" + dbName + ".sql");
            
            System.out.println("separator: "+SEPARATOR);
            try {
            pb.start();
            } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Deu merda: "+ex.getMessage());
            }
            
            ProcessBuilder pd = new ProcessBuilder("notepad","file.txt");
            
            try {
            pd.start();
            } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }*/
        try {    
            Backup.executar();
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
