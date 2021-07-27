/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Emers
 */
public class Configuracao {
    
    private static HashMap<String, String> configuracoes;

    private static Properties getProp(String caminho) throws IOException {
        Properties props = new Properties();
        FileInputStream file = new FileInputStream(
                caminho);
        props.load(file);
        return props;
    }

    public static HashMap<String, String> getConfiguracao() throws IOException {
        HashMap<String, String> config = new HashMap<>();

        Properties prop = getProp("./config/configuracao.properties");

        //configurações de banco de dados
        config.put("db.local", prop.getProperty("db.local"));
        config.put("db.nome", prop.getProperty("db.nome"));
        config.put("db.idInicial", prop.getProperty("db.idInicial"));
        config.put("db.idFinal", prop.getProperty("db.idFinal"));
        config.put("db.usuario", prop.getProperty("db.usuario"));
        config.put("db.senha", prop.getProperty("db.senha"));

        //configurações de backup
        config.put("dir.mysql", prop.getProperty("dir.mysql"));
        config.put("dir.backups", prop.getProperty("dir.backups"));

        return config;
    }

    public static String getParametro(String chave) throws IOException {
        if(configuracoes==null){
            configuracoes=getConfiguracao();
        }
        return configuracoes.get(chave);
    }

    public static String getAll() throws IOException {
        String host = getParametro("db.local");
        String nome = getParametro("db.nome");
        String idInicial = getParametro("db.idInicial");
        String idFinal = getParametro("db.idFinal");
        String usuario = getParametro("db.usuario");
        String senha = getParametro("db.senha");

        return "Local: " + host + "\nNome: " + nome + "\nID Inicial: " + idInicial + "\nID Final: " + idFinal + "\nUsuário: " + usuario + "\nSenha: " + senha;
    }
}
