/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ferna
 */
public class FiltrarTabela {
    
    public FiltrarTabela(JTable x, String txt) {
        
        DefaultTableModel lista = (DefaultTableModel) x.getModel();
        
        TableRowSorter sorter = new TableRowSorter(lista);
        x.setRowSorter(sorter);
        String texto = txt;
        if (texto.length() == 0) {
            sorter.setRowFilter(null);
            //JOptionPane.showMessageDialog(null, "Você Deve Informar um Valor!!!", "AVISO - Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Valor Não Encontrado!!!", "AVISO - Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    
    
}
