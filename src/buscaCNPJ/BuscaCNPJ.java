/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaCNPJ;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import conexoes.ConexaoHttp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ferna
 */
public class BuscaCNPJ {

    ArrayList<Object> id = new ArrayList<>();
    ArrayList<Object> ap = new ArrayList<>();
    ArrayList<Object> as = new ArrayList<>();
    private Empresa cliente = new Empresa();
    private String cnpj;
    public static String situation;
    
    public Empresa buscaEmpresa (String inscricao){
        
        buscaDados(inscricao);
        
        return cliente;
    }

    private void buscaDados(String inscricao) {

        String retorno = null;
        Gson gson = new Gson();
        ConexaoHttp a = new ConexaoHttp();

        cnpj = inscricao;
        cnpj = cnpj.replaceAll("/", "");
        cnpj = cnpj.replaceAll("-", "");
        cnpj = cnpj.replaceAll("[.]", "");
        //teste
        String url = "http://www.receitaws.com.br/v1/cnpj/" + cnpj;

        try {
            retorno = a.sendGet(url);
        } catch (Exception ex) {
            Logger.getLogger(BuscaCNPJ.class.getName()).log(Level.SEVERE, null, ex);
        }

        JsonObject obj = new JsonParser().parse(retorno).getAsJsonObject();

        cliente.setNome(obj.get("nome").getAsString());
        cliente.setData_situacao(obj.get("data_situacao").getAsString());
        cliente.setUf(obj.get("uf").getAsString());
        cliente.setTelefone(obj.get("telefone").getAsString());
        cliente.setSituacao(obj.get("situacao").getAsString());
        situation = obj.get("situacao").getAsString();
        cliente.setBairro(obj.get("bairro").getAsString());
        cliente.setLogradouro(obj.get("logradouro").getAsString());
        cliente.setNumero(obj.get("numero").getAsString());
        cliente.setCep(obj.get("cep").getAsString());
        cliente.setMunicipio(obj.get("municipio").getAsString());
        cliente.setAbertura(obj.get("abertura").getAsString());
        cliente.setNaturza_jurifica(obj.get("natureza_juridica").getAsString());
        cliente.setFantasia(obj.get("fantasia").getAsString());
        cliente.setCnpj(obj.get("cnpj").getAsString());
        cliente.setUltima_atualizacao(obj.get("ultima_atualizacao").getAsString());
        cliente.setStatus(obj.get("status").getAsString());
        cliente.setTipo(obj.get("tipo").getAsString());
        cliente.setComplemento(obj.get("complemento").getAsString());
        cliente.setEmail(obj.get("email").getAsString());
        cliente.setEfr(obj.get("efr").getAsString());
        cliente.setMotivo_situacao(obj.get("motivo_situacao").getAsString());
        cliente.setSituacao(obj.get("situacao_especial").getAsString());
        cliente.setData_situacao_especial(obj.get("data_situacao_especial").getAsString());
        cliente.setCapital_social(obj.get("capital_social").getAsString());

        id.add(obj.get("qsa").getAsJsonArray());
        ap.add(obj.get("atividade_principal").getAsJsonArray());
        as.add(obj.get("atividades_secundarias").getAsJsonArray());
    }

    public static void main(String[] args) {

        BuscaCNPJ cnpj = new BuscaCNPJ();
        
        Empresa pessoa = cnpj.buscaEmpresa("05528196000105");

        System.out.println("RAZAO SOCIAL: " + pessoa.getNome());

    }

}
