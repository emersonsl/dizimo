/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Emers
 */
public class Backup {
    private static String origem, destino, usuario, senha, nomeDB;
    
    private static void carregarParametros() throws IOException {
        origem = Configuracao.getParametro("dir.mysql");
        destino = Configuracao.getParametro("dir.backups");
        usuario = Configuracao.getParametro("db.usuario");
        senha = Configuracao.getParametro("db.senha");
        nomeDB = Configuracao.getParametro("db.nome");
    }
    
    public static void executar() throws IOException{
        carregarParametros();
        
        String dataAtual = getDataFormatada(Date.valueOf(LocalDate.now()));
        String nomeArquivo = "backup - "+nomeDB+" - "+dataAtual+".sql";
        
        ProcessBuilder pb = new ProcessBuilder(
                origem+"\\mysqldump.exe",
                "--user="+usuario,
                "--password="+senha,
                nomeDB,
                "--result-file="+destino+"\\"+nomeArquivo
        );
        pb.start();
    }
    
    private static String getDataFormatada(Date data) {
        DateTimeFormatter formatoData = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return formatoData.format(data.toLocalDate());
    }
}
