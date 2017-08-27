/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import conexoes.ConexaoMySQL;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luiz.barcellos
 */
public class ImprimeRelatorio {

    private final ConexaoMySQL cn = new ConexaoMySQL();
    private final SimpleDateFormat dateIn = new SimpleDateFormat("dd/MM/yyyy");

    public void imprimir(String sql, String relatorio, String titulo) {

        String rel = "./reports/" + relatorio;//"reports/Capa.jasper";

        if (cn.conecta()) {
            try {
                cn.executeConsulta(sql);

                JRResultSetDataSource relatResult = new JRResultSetDataSource(cn.rs);

                JasperPrint jasperprint;
                jasperprint = JasperFillManager.fillReport(rel, new HashMap(), relatResult);
                JasperViewer jv = new JasperViewer(jasperprint, false);

                jv.setTitle(titulo);
                jv.setVisible(true);

                System.out.println("Relatório emitido.");

            } catch (JRException | NullPointerException je) {
                JOptionPane.showMessageDialog(null, je);
            } finally {
                cn.desconecta();
            }
        }
    }

    public void imprimir(String sql, String relatorio, String titulo, Date dti, Date dtf, String param1, String param2) {

        String rel = "./reports/" + relatorio;//"reports/Capa.jasper";
        //String rel_sub = "./src/reports/conhecimentosRpa_detalhe.jasper";//"reports/Capa.jasper";

        if (cn.conecta()) {
            try {
                cn.executeConsulta(sql);

                JRResultSetDataSource relatResult = new JRResultSetDataSource(cn.rs);
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("dataInicio", dti);
                parametros.put("dataFim", dtf);
                parametros.put("SUBREPORT_DIR", "./src/reports/");
                parametros.put("REPORT_CONNECTION", cn.conexao);
                parametros.put("detalhe", param1);
                parametros.put("totalUnidades", param2);
                System.out.println("Parametros definidos: " + parametros);

                JasperPrint jasperprint;
                jasperprint = JasperFillManager.fillReport(rel, parametros, relatResult);
                System.out.println("JasperPrint: " + jasperprint);
                JasperViewer jv = new JasperViewer(jasperprint, false);
                System.out.println("JasperViewer: " + jv);

                jv.setTitle(titulo);
                jv.setVisible(true);

                System.out.println("Relatório emitido.");

            } catch (Exception je) {
                JOptionPane.showMessageDialog(null, je);
            } finally {
                cn.desconecta();
            }
        }
    }

    public static void main(String[] args) {
        ImprimeRelatorio print = new ImprimeRelatorio();

        print.imprimir("SELECT * FROM rel_rpa a order by a.cod_emp ASC,a.cod_est ASC,a.cod_pessoa ASC,a.numero ASC", "conhecimentosRpa.jasper", "Acompanhamento de Recibos", new Date("2017/04/01"), new Date("2017/04/30"), "N", "S");
        //String rel = "SELECT * FROM rel_rpa WHERE codigo = 1"
        //        + " union all "
        //        + "SELECT * FROM rel_rpa WHERE codigo = 1";
        //print.imprimir(rel, "rpa.jasper", "Recibo de Pagamento de Autônomo");
    }
}
