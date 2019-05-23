/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Date;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.ConjugeDAO;
import model.DAO.DizimistaDAO;
import model.bean.Conjuge;
import model.bean.Dizimista;

/**
 *
 * @author Emerson
 */
public class ExportarPDF {

    public static void aniversarioDosDizimistas(Date dataInicio, Date dataFinal) {
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream("./document.pdf"));
            documento.open();
            

            Paragraph p1 = new Paragraph();
            Paragraph p2 = new Paragraph();
            Paragraph p3 = new Paragraph();
            Paragraph p4 = new Paragraph();

            Font f1 = new Font();
            Font f2 = new Font();

            f1.setSize(18);
            f1.setStyle(Font.BOLD);
            f2.setSize(14);
            f2.setStyle(Font.BOLD);

            p1.setSpacingAfter(10);
            p1.setFont(f1);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setFont(f2);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setSpacingBefore(10);
            p2.setSpacingAfter(10);

            p1.add("Aniversário dos Dizimistas\n");
            p1.setFont(f2);
            p1.add("entre "+getDataFormatada(dataInicio)+" e "+getDataFormatada(dataFinal));
            
            p2.add("Aniversário de casamento\n");
            
            p3.add(getDizimistasAniversarios(dataInicio, dataFinal));
            p4.add(getDizimistasAniversariosCasamento(dataInicio, dataFinal));
            
            documento.add(p1);
            documento.add(p3);
            documento.add(p2);
            documento.add(p4);
            
            documento.close();

        } catch (FileNotFoundException | DocumentException ex) {
            Logger.getLogger(ExportarPDF.class.getName()).log(Level.SEVERE, null, ex);
        }

        Paragraph p = new Paragraph();
    }

    private static String getDataFormatada(Date data) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatoData.format(data.toLocalDate());
    }
    
    private static String getDizimistasAniversarios(Date dataInicio, Date dataFinal){
        List <Dizimista> dizimistas = DizimistaDAO.recuperar(dataInicio, dataFinal);
        
        StringBuilder dizimistasAniversario = new StringBuilder();
        for(Dizimista d: dizimistas){
            dizimistasAniversario.append(getDataFormatada(d.getDataNascimento()));
            dizimistasAniversario.append(" - ");
            dizimistasAniversario.append(d.getNome());
            dizimistasAniversario.append(" - ");
            dizimistasAniversario.append(getIdade(d.getDataNascimento()));
            dizimistasAniversario.append(" anos \n");
        }
        return dizimistasAniversario.toString();
    }
    
    private static String getDizimistasAniversariosCasamento(Date dataInicio, Date dataFinal){
        List <Conjuge> conjuges = ConjugeDAO.recuperar(dataInicio, dataFinal);
        
        StringBuilder dizimistasAniversarioCasamento = new StringBuilder();
        
        StringBuilder dizimistasAniversario = new StringBuilder();
        for(Conjuge c: conjuges){
            dizimistasAniversarioCasamento.append(getDataFormatada(c.getDataCasamento()));
            dizimistasAniversarioCasamento.append(" - ");
            dizimistasAniversarioCasamento.append(c.getNome());
            dizimistasAniversarioCasamento.append(" e ");
            dizimistasAniversarioCasamento.append(DizimistaDAO.recuperar(c.getId()).getNome());
            dizimistasAniversarioCasamento.append(" - ");
            dizimistasAniversarioCasamento.append(getIdade(c.getDataCasamento()));
            dizimistasAniversarioCasamento.append(" anos \n");
        }
        return dizimistasAniversarioCasamento.toString();
    }
    
    private static String getIdade(Date data){
        return String.valueOf(Year.now().getValue() - data.toLocalDate().getYear());
    }
}
