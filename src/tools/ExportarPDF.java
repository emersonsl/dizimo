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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.DAO.ConjugeDAO;
import model.DAO.ContribuicaoDAO;
import model.DAO.DizimistaDAO;
import model.DAO.PlantaoDAO;
import model.bean.Conjuge;
import model.bean.Contribuicao;
import model.bean.Dizimista;
import model.bean.Plantao;

/**
 *
 * @author Emerson
 */
public class ExportarPDF {

    private static DecimalFormat df = new DecimalFormat("#.00");

    public static void aniversarioDosDizimistas(Date dataInicio, Date dataFinal) throws FileNotFoundException, DocumentException, IOException {
        Document documento = new Document();

        PdfWriter.getInstance(documento, new FileOutputStream("./dizimo - aniversariantes.pdf"));
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
        p1.add("entre " + getDataFormatada(dataInicio) + " e " + getDataFormatada(dataFinal));

        p2.add("Aniversário de casamento\n");

        tratarBuscaData(dataInicio, dataFinal);
        p3.add(getDizimistasAniversarios(dataInicio, dataFinal));
        p4.add(getDizimistasAniversariosCasamento(dataInicio, dataFinal));

        if (p3.isEmpty()) {
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            p3.add("Não houveram aniversários nesse período.");
        }

        if (p4.isEmpty()) {
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            p4.add("Não houveram aniversários de casamento nesse período.");
        }

        documento.add(p1);
        documento.add(p3);
        documento.add(p2);
        documento.add(p4);

        documento.close();

        Desktop.getDesktop().open(new File("./dizimo - aniversariantes.pdf"));

    }

    private static String getDataFormatada(Date data) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return formatoData.format(data.toLocalDate());
    }
    
    private static String getHoraFormatada(Time hora){
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm");
        return formatoHora.format(hora.toLocalTime());
    }

    private static String getDizimistasAniversarios(Date dataInicio, Date dataFinal) {
        System.out.println("data: "+dataFinal);
        List<Dizimista> dizimistas = DizimistaDAO.recuperar(dataInicio, dataFinal);
        List<Aniversario> aniversarios = new ArrayList<>();

        Aniversario aniversario;
        for (Dizimista d : dizimistas) {
            if (validarBuscaData(d.getDataNascimento(), dataInicio, dataFinal)) {
                aniversario = new Aniversario(d.getDataNascimento(), d.getNome());
                aniversarios.add(aniversario);
            }
        }

        List<Conjuge> conjuges = ConjugeDAO.recuperarAniversarioConjuge(dataInicio, dataFinal);

        for (Conjuge c : conjuges) {
            if (validarBuscaData(c.getDataNascimento(), dataInicio, dataFinal)) {
                aniversario = new Aniversario(c.getDataNascimento(), c.getNome());
                if (!aniversarios.contains(aniversario)) {
                    aniversarios.add(aniversario);
                }
            }
        }

        Collections.sort(aniversarios);

        StringBuilder textAniversario = new StringBuilder();

        for (Aniversario a : aniversarios) {
            textAniversario.append(a);
            textAniversario.append("\n");
        }

        return textAniversario.toString();
    }

    private static String getDizimistasAniversariosCasamento(Date dataInicio, Date dataFinal) {
        List<Conjuge> conjuges = ConjugeDAO.recuperarAniversarioCasamento(dataInicio, dataFinal);
        List<AniversarioCasamento> aniversariosCasamento = new ArrayList<>();

        AniversarioCasamento aniversarioCasamento;
        for (Conjuge c : conjuges) {
            if (validarBuscaData(c.getDataCasamento(), dataInicio, dataFinal)) {
                aniversarioCasamento = new AniversarioCasamento(c.getDataCasamento(), DizimistaDAO.recuperar(c.getId()).getNome(), c.getNome());

                if (!aniversariosCasamento.contains(aniversarioCasamento)) {
                    aniversariosCasamento.add(aniversarioCasamento);
                }
            }
        }

        Collections.sort(aniversariosCasamento);
        StringBuilder textAniversarioCasamento = new StringBuilder();

        for (AniversarioCasamento a : aniversariosCasamento) {
            textAniversarioCasamento.append(a);
            textAniversarioCasamento.append("\n");
        }

        return textAniversarioCasamento.toString();
    }

    public static void contribuicoesDoDizimista(Dizimista dizimista) throws FileNotFoundException, DocumentException, IOException {

        Document documento = new Document();

        PdfWriter.getInstance(documento, new FileOutputStream("./dizimo - contribuicoes do dizimista.pdf"));
        documento.open();

        Paragraph p1 = new Paragraph();
        Paragraph p2 = new Paragraph();
        Paragraph p3 = new Paragraph();

        Font f1 = new Font();
        Font f2 = new Font();
        Font f3 = new Font();

        f1.setSize(18);
        f1.setStyle(Font.BOLD);
        f2.setSize(14);
        f2.setStyle(Font.BOLD);
        f3.setSize(14);

        p1.setSpacingAfter(10);
        p1.setFont(f1);
        p1.setAlignment(Paragraph.ALIGN_CENTER);
        p2.setFont(f2);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setSpacingBefore(10);
        p2.setSpacingAfter(10);

        p1.add("Contribuições do dizimista\n");
        p1.setFont(f2);

        p2.add("Número: ");
        p2.setFont(f3);
        p2.add(dizimista.getId() + "\n");
        p2.setFont(f2);
        p2.add("Nome: ");
        p2.setFont(f3);
        p2.add(dizimista.getNome() + "\n");

        PdfPTable tableContribuicoes = getContribuicoesDizimista(dizimista);
        p3.add(tableContribuicoes);

        documento.add(p1);
        documento.add(p2);
        documento.add(p3);

        documento.close();

        Desktop.getDesktop().open(new File("./dizimo - contribuicoes do dizimista.pdf"));

    }

    private static PdfPTable getContribuicoesDizimista(Dizimista dizimista) {
        PdfPTable table = new PdfPTable(5);
        List<Contribuicao> contribuicoes = ContribuicaoDAO.recuperar(dizimista);

        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        table.addCell(getCellFormatter("Plantão"));
        table.addCell(getCellFormatter("Mês"));
        table.addCell(getCellFormatter("Ano"));
        table.addCell(getCellFormatter("Valor"));
        table.addCell(getCellFormatter("Plantonista"));

        table.setHeaderRows(1);

        for (Contribuicao c : contribuicoes) {
            table.addCell(getDataFormatada(c.getPlantao().getData()));
            table.addCell(c.getMes().name());
            table.addCell(c.getAno().toString());
            table.addCell(df.format(c.getValor()));
            table.addCell(c.getPlantonista().getNome());
        }

        return table;
    }

    public static void contribuicoesDosDizimistas(Date dataInicio, Date dataFinal) throws FileNotFoundException, DocumentException, IOException {
        Document documento = new Document();

        PdfWriter.getInstance(documento, new FileOutputStream("./dizimo - contribuicoes dos dizimistas.pdf"));
        documento.open();

        Paragraph p1 = new Paragraph();

        Font f1 = new Font();
        Font f2 = new Font();

        f1.setSize(18);
        f1.setStyle(Font.BOLD);
        f2.setSize(14);
        f2.setStyle(Font.BOLD);

        p1.setSpacingAfter(10);
        p1.setFont(f1);
        p1.setAlignment(Paragraph.ALIGN_CENTER);

        if(dataInicio.equals(dataFinal)){
            p1.add("Relatório do Plantão\n");
        }else{
            p1.add("Relatório de Plantões\n");
            p1.setFont(f2);
            p1.add("entre " + getDataFormatada(dataInicio) + " e " + getDataFormatada(dataFinal));
        }
        
        documento.add(p1); //adicionando cabeçalho
        
        getPlantoes(dataInicio, dataFinal, documento); //adicionando informações dos plantões

        documento.close();

        Desktop.getDesktop().open(new File("./dizimo - contribuicoes dos dizimistas.pdf"));

    }
    
    private static void getPlantoes(Date dataInicio, Date dataFinal, Document documento) throws DocumentException{
        List<Plantao> plantoes = PlantaoDAO.recuperar(dataInicio, dataFinal);
        Paragraph paragraphCabecalho;
        Paragraph paragraphTabela;
        Font fonteNegrito = new Font();
        Font fontePadrao = new Font();
        
        fontePadrao.setSize(14);
        fonteNegrito.setSize(14);
        fonteNegrito.setStyle(Font.BOLD);
        
        Collections.reverse(plantoes);
        
        for(Plantao p:plantoes){
            paragraphCabecalho = new Paragraph();
            paragraphTabela = new Paragraph();
            
            paragraphCabecalho.setFont(fonteNegrito);
            paragraphCabecalho.add("Data: ");
            paragraphCabecalho.setFont(fontePadrao);
            paragraphCabecalho.add(getDataFormatada(p.getData())+"\n");
            
            paragraphCabecalho.setFont(fonteNegrito);
            paragraphCabecalho.add("Horário: ");
            paragraphCabecalho.setFont(fontePadrao);
            paragraphCabecalho.add(getHoraFormatada(p.getHora())+"\n");
            
            paragraphCabecalho.setFont(fonteNegrito);
            paragraphCabecalho.add("Lançador: ");
            paragraphCabecalho.setFont(fontePadrao);
            paragraphCabecalho.add(p.getLancador().getNome()+"\n");
            
            paragraphCabecalho.setFont(fonteNegrito);
            paragraphCabecalho.add("Presidente: ");
            paragraphCabecalho.setFont(fontePadrao);
            paragraphCabecalho.add(p.getPresidente().toString()+"\n\n");
            
            documento.add(paragraphCabecalho);
            getContribuicoesDizimistas(p, paragraphTabela);
            documento.add(paragraphTabela);
            documento.newPage();
        }

        
    }

    private static void getContribuicoesDizimistas(Plantao plantao, Paragraph paragraph) {
        PdfPTable table = new PdfPTable(6);
        List<Contribuicao> contribuicoes = ContribuicaoDAO.recuperar(plantao);
        Font fonteNegrito = new Font();
        Font fontePadrao = new Font();
        
        fontePadrao.setSize(14);
        fonteNegrito.setSize(14);
        fonteNegrito.setStyle(Font.BOLD);
        
        table.addCell(getCellFormatter("Dizimista"));
        table.addCell(getCellFormatter("Plantão"));
        table.addCell(getCellFormatter("Mês"));
        table.addCell(getCellFormatter("Ano"));
        table.addCell(getCellFormatter("Valor"));
        table.addCell(getCellFormatter("Plantonista"));

        table.setHeaderRows(1);

        Double soma = 0.0;
        
        for (Contribuicao c : contribuicoes) {
            table.addCell(c.getDizimista().toString());
            table.addCell(getDataFormatada(c.getPlantao().getData()));
            table.addCell(c.getMes().name());
            table.addCell(c.getAno().toString());
            table.addCell(df.format(c.getValor()));
            table.addCell(c.getPlantonista().getNome());
            soma+=c.getValor();
        }

        paragraph.add(table);
        
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        paragraph.setFont(fonteNegrito);
        paragraph.add("\nTotal: ");
        paragraph.setFont(fontePadrao);
        paragraph.add(df.format(soma));
    }

    private static PdfPCell getCellFormatter(String text) {
        PdfPCell cell = new PdfPCell();
        Paragraph p1 = new Paragraph();
        Font f1 = new Font();
        f1.setStyle(Font.BOLD);
        p1.setFont(f1);
        p1.add(text);
        cell.addElement(p1);
        return cell;
    }

    public static void listarDizimistas() throws FileNotFoundException, DocumentException, IOException {
        Document documento = new Document();

        PdfWriter.getInstance(documento, new FileOutputStream("./dizimo - lista.pdf"));
        documento.open();

        Paragraph p1 = new Paragraph();
        Paragraph p2 = new Paragraph();
        Paragraph p3 = new Paragraph();

        Font f1 = new Font();
        Font f2 = new Font();

        f1.setSize(18);
        f1.setStyle(Font.BOLD);
        f2.setSize(12);

        p1.setSpacingAfter(10);
        p1.setFont(f1);
        p1.setAlignment(Paragraph.ALIGN_CENTER);
        p2.setFont(f2);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setSpacingBefore(10);
        p2.setSpacingAfter(10);
        p3.setAlignment(Paragraph.ALIGN_CENTER);
        f1.setSize(12);
        p3.setFont(f1);
        
        p1.add("Dizimistas Cadastrados\n");
        p1.setFont(f2);

        p2.add(getAllDizimistas());
        
        p3.add("Total: ");
        p3.setFont(f2);
        p3.add(DizimistaDAO.recuperar().size()+" dizimistas cadastrados");
        
        documento.add(p1);
        documento.add(p2);
        documento.add(p3);

        documento.close();

        Desktop.getDesktop().open(new File("./dizimo - lista.pdf"));

    }

    public static void listarSorteio(Date dataInicio, Date dataFinal) throws FileNotFoundException, DocumentException, IOException {
        Document documento = new Document();

        PdfWriter.getInstance(documento, new FileOutputStream("./dizimo - sorteio.pdf"));
        documento.open();

        Paragraph p1 = new Paragraph();
        Paragraph p2 = new Paragraph();
        Paragraph p3 = new Paragraph();

        Font f1 = new Font();
        Font f2 = new Font();

        f1.setSize(18);
        f1.setStyle(Font.BOLD);
        f2.setSize(18);

        p1.setSpacingAfter(10);
        p1.setFont(f1);
        p1.setAlignment(Paragraph.ALIGN_CENTER);
        p2.setFont(f2);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setSpacingBefore(10);
        p2.setSpacingAfter(10);

        p1.add("Sorteio\n");
        p1.add("contribuicões entre " + getDataFormatada(dataInicio) + " e " + getDataFormatada(dataFinal));
        p1.setFont(f2);

        p2.add(getSorteio(dataInicio, dataFinal));
        
        p3.setAlignment(Paragraph.ALIGN_CENTER);
        p3.setFont(f1);
        p3.add("Total: ");
        p3.setFont(f2);
        p3.add(""+DizimistaDAO.recuperarSorteio(dataInicio, dataFinal).size());
        p3.add(" dizimistas");
        
        documento.add(p1);
        documento.add(p2);
        documento.add(p3);

        documento.close();

        Desktop.getDesktop().open(new File("./dizimo - sorteio.pdf"));

    }
    
    public static String getAllDizimistas() {

        List<Dizimista> dizimistas = DizimistaDAO.recuperar();

        StringBuilder dizimistasAniversario = new StringBuilder();
        for (Dizimista d : dizimistas) {
            dizimistasAniversario.append(d.getId());
            dizimistasAniversario.append(" - ");
            dizimistasAniversario.append(d.getNome());
            dizimistasAniversario.append("\n");
        }
        return dizimistasAniversario.toString();
    }
    
    public static String getSorteio(Date dataInicio, Date dataFinal) {

        List<Dizimista> dizimistas = DizimistaDAO.recuperarSorteio(dataInicio, dataFinal);

        StringBuilder dizimistasAniversario = new StringBuilder();
        for (Dizimista d : dizimistas) {
            dizimistasAniversario.append(d.getId());
            dizimistasAniversario.append(" - ");
            dizimistasAniversario.append(d.getNome());
            dizimistasAniversario.append("\n\n");
        }
        return dizimistasAniversario.toString();
    }

    private static void tratarBuscaData(Date dataInicio, Date dataFinal) {

        LocalDate dataI = dataInicio.toLocalDate();
        LocalDate dataF = dataFinal.toLocalDate();

        if (dataI.getYear() % 4 == 0 && dataI.getDayOfYear() > 60) { //ano bisexto após fevereiro
            dataInicio.setTime(Date.valueOf(dataI.plusDays(-1)).getTime());
        } else if (dataI.getYear() % 4 != 0 && dataF.getDayOfYear() > 59) { //ano não bisexto após fevereiro
            dataFinal.setTime(Date.valueOf(dataF.plusDays(1)).getTime());
        }
    }

    private static boolean validarBuscaData(Date dataAniversario, Date dataInicio, Date dataFinal) {

        LocalDate dataA = dataAniversario.toLocalDate();
        LocalDate dataI = dataInicio.toLocalDate();
        LocalDate dataF = dataFinal.toLocalDate();

        if (dataI.getYear() % 4 == 0 && dataI.getDayOfYear() > 60) { //quando da data inicial foi alterada
            if (dataA.getMonthValue() == dataI.getMonthValue() && dataA.getDayOfMonth() == dataI.getDayOfMonth()) {
                return false;
            }
        } else if (dataF.getYear() % 4 != 0 && dataF.getDayOfYear() > 59) { //quando da data final foi alterada
            if (dataA.getMonthValue() == dataF.getMonthValue() && dataA.getDayOfMonth() == dataF.getDayOfMonth()) {
                return false;
            }
        }

        if (dataA.getMonthValue() == dataI.getMonthValue() && dataA.getDayOfMonth() == dataI.getDayOfMonth() - 1) { //quando a data inicial não foi alterada
            return false;
        }
        if (dataA.getMonthValue() == dataF.getMonthValue() && dataA.getDayOfMonth() == dataF.getDayOfMonth() + 1) { //quando a data final não foi alterada
            return false;
        }

        //se for o dia primeiro do mês 
        if (dataA.getDayOfMonth() == 1 && dataA.getMonthValue()-1 == dataF.getMonthValue() && dataA.getDayOfYear() == dataF.getDayOfYear()) {
            return false;
        }
        
        return true;
    }
}
