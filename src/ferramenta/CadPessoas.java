/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import View.ConexaoMySQL;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.barcellos
 */
public class CadPessoas {

    private ConexaoMySQL cn = new ConexaoMySQL();

    private String id, nome, inscricao_federal, uf, cidade, endereco, nro, complemento,
            bairro, cep, estado_civil, nacionalidade, inscEst_rg, telefone, email, sql;

    public String buscaPessoa(String id) {

        sql = "SELECT * FROM cad_pessoas WHERE id = '" + id + "';";

        if (cn.conecta()) {
            try {
                cn.executeConsulta(sql);
                while (cn.rs.next()) {
                    id = cn.rs.getString("id");
                    nome = cn.rs.getString("nome");
                    inscricao_federal = cn.rs.getString("inscricao_federal");
                    uf = cn.rs.getString("uf");
                    cidade = cn.rs.getString("cidade");
                    endereco = cn.rs.getString("endereco");
                    nro = cn.rs.getString("nro");
                    complemento = cn.rs.getString("complemento");
                    bairro = cn.rs.getString("bairro");
                    cep = cn.rs.getString("cep");
                    estado_civil = cn.rs.getString("estado_civil");
                    nacionalidade = cn.rs.getString("nacionalidade");
                    inscEst_rg = cn.rs.getString("inscEst_rg");
                    telefone = cn.rs.getString("telefone");
                    email = cn.rs.getString("email");

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao consultar cadastro de pessoas:\n" + e);
            } finally {
                cn.desconecta();
            }
        }

        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getInscricao_federal() {
        return inscricao_federal;
    }

    public void setInscricao_federal(String inscricao_federal) {
        this.inscricao_federal = inscricao_federal;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNro() {
        return nro;
    }

    public void setNro(String nro) {
        this.nro = nro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEstado_civil() {
        return estado_civil;
    }

    public void setEstado_civil(String estado_civil) {
        this.estado_civil = estado_civil;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getInscEst_rg() {
        return inscEst_rg;
    }

    public void setInscEst_rg(String inscEst_rg) {
        this.inscEst_rg = inscEst_rg;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

    
    public static void main(String[] args) {
        CadPessoas pess = new CadPessoas();
        System.out.println("Nome da pessoa: " + pess.buscaPessoa("10"));
    }
}
