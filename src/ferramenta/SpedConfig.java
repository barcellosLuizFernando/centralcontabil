/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import conexoes.ConexaoMySQL;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.barcellos
 */
public class SpedConfig {

    private ConexaoMySQL cn = new ConexaoMySQL();

    private String ambiente;
    private String cod_uf;
    private String uf;
    private String servico;
    private String config;
    private String parametro;

    public String carregaConfig(String config) {
        boolean resposta = false;
        parametro = null;
        
        if(config != null){
            this.config = config;
        }

        String sql = "SELECT * FROM spedconfig WHERE uf = '" + uf + "' "
                + "AND servico = '" + servico + "' "
                + "AND ambiente = '" + ambiente + "' "
                + "AND config = '" + this.config + "';";

        if (cn.conecta()) {
            try {
                resposta = cn.executeConsulta(sql);
                while (cn.rs.next()) {
                    this.ambiente = cn.rs.getString("ambiente");
                    this.cod_uf = cn.rs.getString("cod_uf");
                    this.uf = cn.rs.getString("uf");
                    this.servico = cn.rs.getString("servico");
                    this.config = cn.rs.getString("config");
                    this.parametro = cn.rs.getString("parametro");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados de configuração para o SPED.");
                resposta = false;
            } finally {
                cn.desconecta();
            }
        }

        return getParametro();
    }

    public boolean gravaConfig(String operacao) {
        boolean resposta = false;

        String sql;

        if ("INSERT".equals(operacao)) {
            
            InfEstado est = new InfEstado();
            est.carregaDados(this.uf);
            cod_uf = est.getCod_uf();
            
            sql = "INSERT INTO spedconfig (cod_uf,uf,servico,ambiente,config,"
                    + "parametro) VALUES ('" + getCod_uf() + "','" + getUf() + "',"
                    + "'" + getServico() + "','" + getAmbiente() + "',"
                    + "'" + getConfig() + "','" + getParametro() + "');";
        } else {
            sql = "UPDATE spedconfig SET parametro = '" + getParametro() + "' "
                    + "WHERE uf = '" + getUf() + "' AND ambiente = '" + getAmbiente() + "' "
                    + "AND servico = '" + getServico() + "' AND config = '" + getConfig() + "';";
        }

        if (cn.conecta()) {
            try {
                resposta = cn.executeAtualizacao(sql);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível atualizar o " + getConfig() + ".");
            } finally {
                cn.desconecta();
            }
        }

        return resposta;
    }

    public String getAmbiente() {
        return ambiente;
    }

    public void setAmbiente(String ambiente) {
        this.ambiente = ambiente;
    }

    public String getCod_uf() {
        return cod_uf;
    }

    public void setCod_uf(String cod_uf) {
        this.cod_uf = cod_uf;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public static void main(String[] args) {
        SpedConfig conf = new SpedConfig();

        conf.carregaConfig("DefinitionsTargetNamespaceURI");
        System.out.print("Código da UF: " + conf.getCod_uf());
        System.out.print(". Estado: " + conf.getUf());
        System.out.print(". Serviço: " + conf.getServico());
        System.out.print(". Ambiente: " + conf.getAmbiente());
        System.out.print(". Configuração: " + conf.getConfig());
        System.out.println(". Parâmetro: " + conf.getParametro());
    }

}
