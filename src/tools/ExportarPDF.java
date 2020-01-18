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
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.ConjugeDAO;
import model.DAO.ContribuicaoDAO;
import model.DAO.DizimistaDAO;
import model.bean.Conjuge;
import model.bean.Contribuicao;
import model.bean.Dizimista;

/**
 *
 * @author Emerson
 */
public class ExportarPDF {

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

        p3.add(getDizimistasAniversarios(dataInicio, dataFinal));
        p4.add(getDizimistasAniversariosCasamento(dataInicio, dataFinal));

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

    private static String getDizimistasAniversarios(Date dataInicio, Date dataFinal) {
        List<Dizimista> dizimistas = DizimistaDAO.recuperar(dataInicio, dataFinal);
        List<Aniversario> aniversarios = new ArrayList<>();

        Aniversario aniversario;
        for (Dizimista d : dizimistas) {
            aniversario = new Aniversario(d.getDataNascimento(), d.getNome());
            aniversarios.add(aniversario);
        }

        List<Conjuge> conjuges = ConjugeDAO.recuperarAniversarioConjuge(dataInicio, dataFinal);

        for (Conjuge c : conjuges) {
            aniversario = new Aniversario(c.getDataNascimento(), c.getNome());
            if (!aniversarios.contains(aniversario)) {
                aniversarios.add(aniversario);
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
            aniversarioCasamento = new AniversarioCasamento(c.getDataCasamento(), DizimistaDAO.recuperar(c.getId()).getNome(), c.getNome());
            if (!aniversariosCasamento.contains(aniversarioCasamento)) {
                aniversariosCasamento.add(aniversarioCasamento);
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

    private static String getIdade(Date data) {
        return String.valueOf(Year.now().getValue() - data.toLocalDate().getYear());
    }

    public static void ContribuicoesDoDizimista(Dizimista dizimista) throws FileNotFoundException, DocumentException, IOException {

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
            table.addCell(c.getValor().toString());
            table.addCell(c.getPlantonista().getNome());
        }

        return table;
    }

    public static void ContribuicoesDosDizimistas(Date dataInicio, Date dataFinal) throws FileNotFoundException, DocumentException, IOException {
        Document documento = new Document();

        PdfWriter.getInstance(documento, new FileOutputStream("./dizimo - contribuicoes dos dizimistas.pdf"));
        documento.open();

        Paragraph p1 = new Paragraph();
        Paragraph p2 = new Paragraph();

        Font f1 = new Font();
        Font f2 = new Font();

        f1.setSize(18);
        f1.setStyle(Font.BOLD);
        f2.setSize(14);
        f2.setStyle(Font.BOLD);

        p1.setSpacingAfter(10);
        p1.setFont(f1);
        p1.setAlignment(Paragraph.ALIGN_CENTER);

        p1.add("Contribuições dos dizimista\n");
        p1.setFont(f2);
        p1.add("entre " + getDataFormatada(dataInicio) + " e " + getDataFormatada(dataFinal));

        PdfPTable tableContribuicoes = getContribuicoesDizimistas(dataInicio, dataFinal);
        p2.add(tableContribuicoes);

        documento.add(p1);
        documento.add(p2);

        documento.close();

        Desktop.getDesktop().open(new File("./dizimo - contribuicoes dos dizimistas.pdf"));

    }

    private static PdfPTable getContribuicoesDizimistas(Date dataInicio, Date dataFinal) {
        PdfPTable table = new PdfPTable(6);
        List<Contribuicao> contribuicoes = ContribuicaoDAO.recuperar(dataInicio, dataFinal);

        table.setSpacingBefore(10);
        table.setSpacingAfter(10);

        table.addCell(getCellFormatter("Dizimista"));
        table.addCell(getCellFormatter("Plantão"));
        table.addCell(getCellFormatter("Mês"));
        table.addCell(getCellFormatter("Ano"));
        table.addCell(getCellFormatter("Valor"));
        table.addCell(getCellFormatter("Plantonista"));

        table.setHeaderRows(1);

        for (Contribuicao c : contribuicoes) {
            table.addCell(c.getDizimista().toString());
            table.addCell(getDataFormatada(c.getPlantao().getData()));
            table.addCell(c.getMes().name());
            table.addCell(c.getAno().toString());
            table.addCell(c.getValor().toString());
            table.addCell(c.getPlantonista().getNome());
        }

        return table;
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

        p1.add("Dizimistas Cadastrados\n");
        p1.setFont(f2);

        p2.add(getAllDizimistas());

        documento.add(p1);
        documento.add(p2);

        documento.close();

        Desktop.getDesktop().open(new File("./dizimo - lista.pdf"));

    }

    private static String getAllDizimistas() {

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

}
