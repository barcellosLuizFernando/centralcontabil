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
public class InfEstado {

    ConexaoMySQL cn = new ConexaoMySQL();
    private String uf;
    private String cod_uf;

    public void carregaDados(String estado) {

        boolean resposta;

        this.uf = estado;

        if (cn.conecta()) {
            try {
                resposta = cn.executeConsulta("SELECT DISTINCT LEFT(cod_ibge,2) as cod_uf from cad_munic WHERE uf = '" + uf + "';");
                while (cn.rs.next()) {
                    this.cod_uf = cn.rs.getString("cod_uf");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possível consultar o código do Estado.");
            } finally {
                cn.desconecta();
            }
        }
    }

    public String getUf() {
        return uf;
    }

    public String getCod_uf() {
        return cod_uf;
    }
    
    

}
