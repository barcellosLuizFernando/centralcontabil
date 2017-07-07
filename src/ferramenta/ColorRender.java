/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {

        setFont(new Font("Arial", Font.PLAIN, 11));

        super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);

        boolean result = false;

        //Se for uma tabela com conta totalizadora, irá encontrar esta linha e formatar conforme;
        try {
            result = "T".equals(table.getModel().getValueAt(row, 0));
        } catch (Exception e) {
        }

        //se for uma linha selecionada
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
                setFont(new Font("Arial", Font.ITALIC, 11));
        } else {
            //se o tipo de conta for totalizadora
            if (result) {
                setBackground(Color.DARK_GRAY);
                setForeground(Color.white);
                setFont(new Font("Arial", Font.BOLD, 11));
            } else {
                setFont(new Font("Arial", Font.PLAIN, 11));

                // se não , colore alternado as linhas
                if (row % 2 == 0) {
                    setBackground(Color.LIGHT_GRAY);
                    setForeground(Color.black);
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.black);
                }
            }
        }

        try {
            Double valor = (Double.parseDouble(value.toString().replace(".", "").replace(",", ".")));
            setHorizontalAlignment(SwingConstants.RIGHT);
            System.out.println("Converteu: " + value);
        } catch (Exception e) {
            System.out.println("Não converteu: " + value);
            setHorizontalAlignment(SwingConstants.LEADING);
        }

        return this;
    }
}
