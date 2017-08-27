/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import conexoes.ConexaoFB;
import conexoes.ConexaoMySQL;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.barcellos
 */
public class ImportaNFE {

    private ConexaoFB fb = new ConexaoFB();
    private ConexaoMySQL cn = new ConexaoMySQL();

    private void ConsultaFB(String numero, String cnpj) {

        String dti = "01.01.2010";

        String sql = " with especie_doc as ( "
                + "select d.empresa, d.codigo, d.nome as especie_doc, d.modelo_doc "
                + "from cad_especie_doc d "
                + "where d.importa_fiscal = 'S') "
                + "select np.codigo as numero_nf, np.serie, np.quantidade, "
                + " np.vlr_contabil_item as valor, n.dt_emissao as data_emissao, "
                + "t.nome as nome_terceiro, t.cpf_cnpj as cnpj_terceiro, "
                + "o.nome as natureza, replace(replace(replace(e.cnpj,'-',''),'/',''),'.','') as cnpj_proprio, "
                + "p.nome_reduzido as produto, p.ncm as ncmsh, md.nome as destino, "
                + "md.uf as uf_destino, 0 as nf_referenciada "
                + "from ven_notafiscal_produtos np "
                + "inner join ven_notafiscal n on (np.empresa=n.empresa and np.codigo=n.codigo and np.serie=n.serie) "
                + "inner join especie_doc d on (n.empresa=d.empresa and n.especie=d.codigo) "
                + "left join cad_produtos p on (np.empresa=p.empresa and np.produto=p.codigo) "
                + "left join cad_operacoes_fisc o on (np.empresa=o.empresa and np.operacao=o.codigo) "
                + "left join cad_estabelecimentos e on (n.empresa=e.empresa and n.estabelec=e.codigo) "
                + "left join cad_terceiros t on (t.empresa=n.empresa and t.codigo=n.cliente) "
                + "left join cad_terceiros_enderecos te on (te.empresa=n.empresa and te.codigo=n.cliente and te.sequencia=n.endereco) "
                + "left join cad_municipios md on (md.codigo=te.municipio) "
                + "where (n.dt_emissao between '" + dti + "' and current_date) "
                + "and (n.tipo_nf = 'S') and (n.cancelada != 'S' or n.cancelada is null) "
                + "and(d.modelo_doc <> '55' or((d.modelo_doc = '55' and n.nfe_status = 'AU' ) )) "
                + "and np.operacao = '2109' or np.operacao = '2112' "
                + "and np.codigo = '" + numero + "' and t.cpf_cnpj = '" + cnpj + "' "
                + "union all "
                + "select np.codigo as numero_nf, np.serie,np.quantidade,np.vlr_contabil_item as valor, "
                + "n.dt_emissao as data_emissao,t.nome as nome_terceiro,t.cpf_cnpj as cnpj_terceiro, "
                + "o.nome as natureza,replace(replace(replace(e.cnpj, '-',''),'/',''),'.','') as cnpj_proprio, "
                + "p.nome_reduzido as produto,p.ncm as ncmsh,md.nome as destino, "
                + "md.uf as uf_destino,n.ref_nf as nf_referenciada "
                + "from ven_notafiscal_produtos np "
                + "inner join ven_notafiscal n on(np.empresa = n.empresa and np.codigo = n.codigo and np.serie = n.serie) "
                + "inner join especie_doc d on(n.empresa = d.empresa and n.especie = d.codigo) "
                + "left join cad_produtos p on(np.empresa = p.empresa and np.produto = p.codigo) "
                + "left join cad_operacoes_fisc o on(np.empresa = o.empresa and np.operacao = o.codigo) "
                + "left join cad_estabelecimentos e on(n.empresa = e.empresa and n.estabelec = e.codigo) "
                + "left join cad_terceiros t on(t.empresa = n.empresa and t.codigo = n.cliente) "
                + "left join cad_terceiros_enderecos te on(te.empresa = n.empresa and te.codigo = n.cliente and te.sequencia = n.endereco)"
                + "left join cad_municipios md on(md.codigo = e.municipio) "
                + "where(n.dt_emissao between '" + dti + "' and current_date) "
                + "and(n.tipo_nf = 'E') "
                + "and(n.cancelada <> 'S' or n.cancelada is null) "
                + "and(d.modelo_doc <> '55' or((d.modelo_doc = '55' and n.nfe_status = 'AU') )) "
                + "and np.operacao = '6312' "
                + "and np.codigo = '" + numero + "' and t.cpf_cnpj = '" + cnpj + "' ";

        fb.conecta();
        fb.executeConsulta(sql);
    }

    public void ImportaNFe(String numeroNf, String cnpj) {
        String sql3 = "INSERT INTO `cad_notas_fiscais` (`numero_nf`,`serie_nf`,"
                + "`quantidade`,`valor`,`data_emissao`,`nome_terceiro`,"
                + "`cnpj_terceiro`,`natureza`,`cnpj_proprio`,`produto`,`ncmsh`,"
                + "`destino`,`uf_destino`,`nf_referenciada`) "
                + " SELECT * FROM notas_fiscais_novas ";

        cn.conecta();
        cn.executeAtualizacao("TRUNCATE tmp_cad_notas_fiscais;");

        ConsultaFB(numeroNf, cnpj);

        try {
            while (fb.rs.next()) {

                try {
                    String numero = fb.rs.getString(1);
                    String serie = fb.rs.getString(2);
                    String qtd = fb.rs.getString(3);
                    String valor = fb.rs.getString(4);
                    String emissao = fb.rs.getString(5);
                    String terceiro = fb.rs.getString(6);
                    String cnpj1 = fb.rs.getString(7);
                    String natureza = fb.rs.getString(8);
                    String cnpj2 = fb.rs.getString(9);
                    String produto = fb.rs.getString(10);
                    String ncm = fb.rs.getString(11);
                    String destino = fb.rs.getString(12);
                    String uf = fb.rs.getString(13);
                    String referencia = fb.rs.getString(14);

                    String sqlmy = "INSERT into tmp_cad_notas_fiscais (numero_nf,"
                            + "serie_nf,quantidade,valor,data_emissao,nome_terceiro,"
                            + "cnpj_terceiro,natureza,cnpj_proprio,produto,ncmsh,"
                            + "destino,uf_destino,nf_referenciada) VALUES ("
                            + "'" + numero + "','" + serie + "','" + qtd + "','" + valor + "',"
                            + "'" + emissao + "','" + terceiro + "','" + cnpj1 + "',"
                            + "'" + natureza + "','" + cnpj2 + "','" + produto + "',"
                            + "'" + ncm + "','" + destino + "','" + uf + "'," + referencia + ");";

                    cn.executeAtualizacao(sqlmy);

                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            }
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
        }

        String registros = null;
        cn.executeConsulta("SELECT count(cnpj_proprio) FROM notas_fiscais_novas;");
        try {
            while (cn.rs.next()) {
                registros = cn.rs.getString(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        cn.executeAtualizacao(sql3);
        cn.desconecta();
        fb.desconecta();

        JOptionPane.showMessageDialog(null, registros + " notas importadas!");
    }
    
    public static void main(String[] args) {
        ImportaNFE imp = new ImportaNFE();
        
        imp.ImportaNFe("3596", "84046101000940");
    }

}
