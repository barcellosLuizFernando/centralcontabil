/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

//import conexoes.ConexaoMySQL;
import java.util.HashMap;
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
    
    ConexaoMySQL cn = new ConexaoMySQL();

    public void imprimir(String sql, String relatorio) {
        
        String rel = relatorio;//"reports/Capa.jasper";
        
        try {
            cn.conecta();
            cn.executeConsulta(sql);

            JRResultSetDataSource relatResult = new JRResultSetDataSource(cn.rs);

            JasperPrint jasperprint;
            jasperprint = JasperFillManager.fillReport(rel, new HashMap(), relatResult);
            JasperViewer jv = new JasperViewer(jasperprint, false);

            jv.setVisible(true);

        } catch (JRException je) {
            JOptionPane.showMessageDialog(null, je);
        }
    }
    
    public static void main(String[] args) {
        ImprimeRelatorio print = new ImprimeRelatorio();
        
        print.imprimir("SELECT * FROM rel_rpa", "//reports/rpa.jasper");
    }
}
