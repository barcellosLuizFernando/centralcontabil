/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import com.sun.scenario.effect.AbstractShadow;
import conexoes.ConexaoMySQL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Luiz Fernando Dill Barcellos
 */
public class IcmsProcessos {

    private conexoes.ConexaoMySQL cn = new ConexaoMySQL();
    private DateFormat dateOut = new SimpleDateFormat("yyyy/MM/dd");

    private Date dtEmissao, dtAnalise, dtInicial, dtFinal;
    private String nomeProdutor, inscricaoEstadual, nro_informacao, nro_processo,
            codigo, sql;
    private Double creditoSolicitado, creditoLiberado, aproveitamento, vendas;
    private boolean analisado = false, deferido = false;

    public Date getDtEmissao() {
        return dtEmissao;
    }

    public String getNomeProdutor() {
        return nomeProdutor;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public Double getCreditoSolicitado() {
        return creditoSolicitado;
    }

    public Date getDtAnalise() {
        return dtAnalise;
    }

    public void setDtAnalise(Date dtAnalise) {
        this.dtAnalise = dtAnalise;
    }

    public Date getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(Date dtInicial) {
        this.dtInicial = dtInicial;
    }

    public Date getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

    public String getNro_informacao() {
        return nro_informacao;
    }

    public void setNro_informacao(String nro_informacao) {
        this.nro_informacao = nro_informacao;
    }

    public String getNro_processo() {
        return nro_processo;
    }

    public void setNro_processo(String nro_processo) {
        this.nro_processo = nro_processo;
    }

    public Double getCreditoLiberado() {
        return creditoLiberado;
    }

    public void setCreditoLiberado(Double creditoLiberado) {
        this.creditoLiberado = creditoLiberado;
    }

    public Double getAproveitamento() {
        return aproveitamento;
    }

    public void setAproveitamento(Double aproveitamento) {
        this.aproveitamento = aproveitamento;
    }

    public Double getVendas() {
        return vendas;
    }

    public void setVendas(Double vendas) {
        this.vendas = vendas;
    }

    public boolean isAnalisado() {
        return analisado;
    }

    public boolean isDeferido() {
        return deferido;
    }

    public void setDeferido(boolean deferido) {
        this.deferido = deferido;
    }

    public void carregaDados(String codigo) {
        sql = "SELECT a.id, a.id_produtor, a.id_estabelecimento, "
                + "a.dt_inicio_processo, b.tipo_produto, "
                + "sum(b.valor_credito) as credito, c.nome as produtor, "
                + "d.inscri_est, a.credito_liberado, a.deferido, a.dt_analise, "
                + "a.dt_inicial, a.dt_final, a.informacao, "
                + "a.percentual_aproveitado, a.processo, a.vendas "
                + "FROM controladoria.produtor_icms a "
                + "LEFT JOIN controladoria.produtor_icms_notas b on (b.id = a.id) "
                + "LEFT JOIN controladoria.cad_pessoas c on (c.id = a.id_produtor) "
                + "LEFT JOIN controladoria.icms_estabelecimentos d on (d.id = a.id_estabelecimento) "
                + "WHERE a.id = '" + codigo + "' "
                + "group by a.id;";

        if (cn.conecta()) {
            try {
                cn.executeConsulta(sql);
                while (cn.rs.next()) {
                    dtEmissao = cn.rs.getDate("dt_inicio_processo");
                    dtAnalise = cn.rs.getDate("dt_analise");
                    dtInicial = cn.rs.getDate("dt_inicial");
                    dtFinal = cn.rs.getDate("dt_final");
                    nomeProdutor = cn.rs.getString("produtor");
                    inscricaoEstadual = cn.rs.getString("inscri_est");
                    nro_informacao = cn.rs.getString("informacao");
                    nro_processo = cn.rs.getString("processo");
                    this.codigo = cn.rs.getString("id");
                    creditoSolicitado = cn.rs.getDouble("credito");
                    creditoLiberado = cn.rs.getDouble("credito_liberado");
                    aproveitamento = cn.rs.getDouble("percentual_aproveitado");
                    vendas = cn.rs.getDouble("vendas");
                    if (cn.rs.getString("deferido") == null) {
                        deferido = false;
                        analisado = false;
                    } else {
                        deferido = cn.rs.getBoolean("deferido");
                        analisado = true;
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                cn.desconecta();
            }
        }
        System.out.print("Produtor: " + nomeProdutor);
        System.out.print(". Total do crédito solicitado: " + creditoSolicitado);
        System.out.print(". Processo deferido: " + deferido);
        System.out.println(". Processo analisado: " + analisado);
    }

    public boolean atualizaDados() {
        boolean resposta = false;

        if (cn.conecta()) {
            try {
                sql = "UPDATE produtor_icms SET deferido = " + deferido + ", "
                        + "informacao = '" + nro_informacao + "', "
                        + "processo = '" + nro_processo + "', "
                        + "dt_analise = '" + dateOut.format(dtAnalise) + "' "
                        + "WHERE id = " + codigo + ";";
                resposta = cn.executeAtualizacao(sql);

                if (deferido) {
                    sql = "UPDATE produtor_icms SET credito_liberado = '" + creditoLiberado + "', "
                            + "dt_inicial = '" + dateOut.format(dtInicial) + "', "
                            + "dt_final = '" + dateOut.format(dtFinal) + "', "
                            + "percentual_aproveitado = '" + aproveitamento + "', "
                            + "vendas = '" + vendas + "' "
                            + "WHERE id = '" + codigo + "';";
                    resposta = cn.executeAtualizacao(sql);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
                resposta = false;
            } finally {
                cn.desconecta();
            }

        }

        return resposta;
    }

    public boolean gravaVendas(boolean del, String x) {
        boolean resposta = false;

        if (cn.conecta()) {
            try {
                if (del) {
                    sql = "DELETE FROM produtor_icms_vendas WHERE id = '" + codigo + "';";
                    resposta = cn.executeAtualizacao(sql);
                }

                if (x != null) {
                    sql = "INSERT INTO produtor_icms_vendas (id,produto,valor) VALUES " + x;
                    resposta = cn.executeAtualizacao(sql);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                cn.desconecta();
            }
        }
        return resposta;
    }

    private boolean gravaCompras(String nf, String vlr_credito, String vlr_liberado, String percentual) {
        boolean resposta = false;

        sql = "UPDATE produtor_icms_notas SET vlr_credito = '" + vlr_credito.replace(".", "").replace(",", ".") + "', "
                + "vlr_liberado = '" + vlr_liberado.replace(".", "").replace(",", ".") + "', "
                + "percentual = '" + percentual.replace(".", "").replace(",", ".") + "' "
                + "WHERE id = '" + codigo + "' AND nro_nf = '" + nf + "';";
        if (cn.conecta()) {
            try {
                resposta = cn.executeAtualizacao(sql);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            } finally {
                cn.desconecta();
            }
        }

        return resposta;
    }

    public boolean gravaCompras(boolean x, String nf, String vlr_credito, String vlr_liberado, String percentual) {
        boolean resposta = false;

        if (x) {
            sql = "UPDATE produtor_icms_notas SET vlr_credito = null, "
                    + "vlr_liberado = null, "
                    + "percentual = null "
                    + "WHERE id = '" + codigo + "';";
            if (cn.conecta()) {
                try {
                    resposta = cn.executeAtualizacao(sql);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                } finally {
                    cn.desconecta();
                }
            }
        } else {
            resposta = true;
        }

        if (resposta) {
            resposta = gravaCompras(nf, vlr_credito, vlr_liberado, percentual);
        } else {
            JOptionPane.showMessageDialog(null, "Não foi possível preparar as notas para atualização.");
        }

        return resposta;
    }

    public static void main(String[] args) {
        IcmsProcessos proc = new IcmsProcessos();
        //proc.carregaDados("7");
        //proc.setDeferido(true);
        //proc.atualizaDados();

        //proc.gravaVendas("('2','1','1234.22')");
        //proc.codigo = "2";
        //proc.gravaCompras(false, "2311", "123,42", "5345,34", "54,00");
    }
}
