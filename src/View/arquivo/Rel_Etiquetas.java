/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.arquivo;

import conexoes.ConexaoMySQL;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author ferna
 */
public final class Rel_Etiquetas extends javax.swing.JInternalFrame {

    /**
     * Creates new form Rel_Capas
     */
    ConexaoMySQL cn = new ConexaoMySQL();
    String url;
    String driver = "com.mysql.jdbc.Driver";
    String usuario;
    String senha;

    int var_consulta;

    public Rel_Etiquetas() {
        initComponents();
        this.setLocation(WIDTH, WIDTH);
        MontaTabelas();
        var_consulta = 1;
        MontaLista();
    }

    public void MontaLista() {

        DefaultTableModel lista = (DefaultTableModel) jTblConsulta.getModel();

        lista.setRowCount(0);

        cn.conecta();

        if (var_consulta == 2) {
            String sql;

            if (jCheckBox1.isSelected()) {
                sql = "SELECT * FROM rel_arquivo_etiquetas where data_impressao is not null and id_capa = 0";
            } else {
                sql = "SELECT * FROM rel_arquivo_etiquetas where data_impressao is not null and id_capa != 0";
            }

            cn.executeConsulta(sql);

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id_etiqueta"),
                        cn.rs.getString("nome_empresa"),
                        cn.rs.getString("nome_estabelecimento"),
                        cn.rs.getString("nome_departamento"),
                        cn.rs.getString("sequencia")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
        } else {
            String sql;

            if (jCheckBox1.isSelected()) {
                sql = "SELECT * FROM rel_arquivo_etiquetas where data_impressao is null and id_capa = 0";
            } else {
                sql = "SELECT * FROM rel_arquivo_etiquetas where data_impressao is null and id_capa != 0";
            }

            cn.executeConsulta(sql);

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id_etiqueta"),
                        cn.rs.getString("nome_empresa"),
                        cn.rs.getString("nome_estabelecimento"),
                        cn.rs.getString("nome_departamento"),
                        cn.rs.getString("sequencia")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
        }

        cn.desconecta();

    }

    public void MontaTabelas() {

        DefaultTableModel lista = (DefaultTableModel) jTblConsulta.getModel();

        lista.setColumnCount(0);  //limpa a tabela
        lista.setRowCount(0);//limpa a tabela

        lista.addColumn("Id");
        lista.addColumn("Empresa");
        lista.addColumn("Estabelecimento");
        lista.addColumn("Departamento");
        lista.addColumn("Etq");
        jTblConsulta.setModel(lista);
        jTblConsulta.getColumnModel().getColumn(0).setMaxWidth(40);
        jTblConsulta.getColumnModel().getColumn(0).setMinWidth(40);
        jTblConsulta.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
        jTblConsulta.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);
        jTblConsulta.getColumnModel().getColumn(4).setMaxWidth(40);
        jTblConsulta.getColumnModel().getColumn(4).setMinWidth(40);
        jTblConsulta.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(40);
        jTblConsulta.getTableHeader().getColumnModel().getColumn(4).setMinWidth(40);

        DefaultTableModel listaImpressao = (DefaultTableModel) jTblImpressao.getModel();
        listaImpressao.setColumnCount(0);  //limpa a tabela
        listaImpressao.setRowCount(0);//limpa a tabela

        listaImpressao.addColumn("Id");
        listaImpressao.addColumn("Empresa");
        listaImpressao.addColumn("Estabelecimento");
        listaImpressao.addColumn("Departamento");
        listaImpressao.addColumn("Etq");
        jTblImpressao.setModel(listaImpressao);
        jTblImpressao.getColumnModel().getColumn(0).setMaxWidth(40);
        jTblImpressao.getColumnModel().getColumn(0).setMinWidth(40);
        jTblImpressao.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
        jTblImpressao.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);
        jTblImpressao.getColumnModel().getColumn(4).setMaxWidth(40);
        jTblImpressao.getColumnModel().getColumn(4).setMinWidth(40);
        jTblImpressao.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(40);
        jTblImpressao.getTableHeader().getColumnModel().getColumn(4).setMinWidth(40);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void DadosConexao() {
        try (FileReader arq = new FileReader("conexao.txt")) {
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = lerArq.readLine();
            int line = 1;
            while (linha != null) {
                switch (line) {
                    case 1:
                        url = "jdbc:mysql://" + linha;
                        break;
                    case 2:
                        usuario = linha;
                        break;
                    case 3:
                        senha = linha;
                        break;
                    default:
                        break;
                }
                line++;
                linha = lerArq.readLine();
            }
            arq.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro na abertura do arquivo: " + e);

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bG_TipoRelatorio = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jBtnAddAll = new javax.swing.JButton();
        jBtnAddEtq = new javax.swing.JButton();
        jBtnRemoveAll = new javax.swing.JButton();
        jBtnRemoveEtq = new javax.swing.JButton();
        jBtnReimpressao = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblImpressao = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTblConsulta = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jBtnEmitir = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jRbNormal = new javax.swing.JRadioButton();
        jRbExtendida = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();

        setClosable(true);
        setTitle("Impressão de Capas");
        setVisible(true);

        jBtnAddAll.setText(">>");
        jBtnAddAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddAllActionPerformed(evt);
            }
        });

        jBtnAddEtq.setText(">");
        jBtnAddEtq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddEtqActionPerformed(evt);
            }
        });

        jBtnRemoveAll.setText("<<");
        jBtnRemoveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRemoveAllActionPerformed(evt);
            }
        });

        jBtnRemoveEtq.setText("<");
        jBtnRemoveEtq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnRemoveEtqActionPerformed(evt);
            }
        });

        jBtnReimpressao.setText("Reimpressão");
        jBtnReimpressao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnReimpressaoActionPerformed(evt);
            }
        });

        jTblImpressao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTblImpressao);

        jTblConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(jTblConsulta);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jBtnReimpressao, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAddAll, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnAddEtq, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnRemoveEtq, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnReimpressao, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAddAll, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAddEtq, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnRemoveEtq, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnRemoveAll, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jBtnEmitir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnEmitir.setText("Emitir");
        jBtnEmitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEmitirActionPerformed(evt);
            }
        });

        jBtnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        bG_TipoRelatorio.add(jRbNormal);
        jRbNormal.setText("Etiqueta Normal");

        bG_TipoRelatorio.add(jRbExtendida);
        jRbExtendida.setText("Etiqueta Resumida");
        jRbExtendida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRbExtendidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRbNormal)
                .addGap(18, 18, 18)
                .addComponent(jRbExtendida)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEmitir, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnEmitir)
                    .addComponent(jBtnCancelar)
                    .addComponent(jRbNormal)
                    .addComponent(jRbExtendida))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jCheckBox1.setText("Mostrar etiquetas sem capas");
        jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jBtnEmitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEmitirActionPerformed
        String etiquetas = "0";

        jTblImpressao.selectAll();

        try {
            for (int x = jTblImpressao.getSelectedRowCount(); x != 0; x--) {
                int linha = jTblImpressao.getSelectedRow();
                String id = jTblImpressao.getValueAt(linha, 0).toString();
                String empresa = jTblImpressao.getValueAt(linha, 1).toString();
                String estabelecimento = jTblImpressao.getValueAt(linha, 2).toString();
                String departamento = jTblImpressao.getValueAt(linha, 3).toString();

                etiquetas = etiquetas + "," + id;

                String etiqueta = id;

                String sql2 = "UPDATE cad_arquivo_pastas set data_impressao = current_date() "
                        + "where id = " + etiqueta;
                cn.conecta();
                cn.executeAtualizacao(sql2);
                cn.desconecta();

                jTblImpressao.removeRowSelectionInterval(linha, linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }

        String id_capa = "";
        String id_empresa = "";
        String sql = null;
        String rel = null;

        if (jRbNormal.isSelected()) {
            rel = "reports/Etiquetas.jasper";

            sql = " SELECT * from rel_arquivo_etiquetas "
                    + "where id_etiqueta in (" + etiquetas + ")";

        } else {
            rel = "reports/Etiquetas_resumidas.jasper";
            sql = " SELECT * from rel_arquivo_etqresumida "
                    + "where id_etiqueta in (" + etiquetas + ")";
        }

        try {
            cn.conecta();
            cn.executeConsulta(sql);

            JRResultSetDataSource relatResult = new JRResultSetDataSource(cn.rs);

            JasperPrint jasperprint;
            jasperprint = JasperFillManager.fillReport(rel, new HashMap(), relatResult);
            JasperViewer jv = new JasperViewer(jasperprint, false);

            jv.setVisible(true);

        } catch (JRException je) {
            JOptionPane.showMessageDialog(this, je);
        }

        cn.desconecta();

    }//GEN-LAST:event_jBtnEmitirActionPerformed

    private void jBtnAddAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddAllActionPerformed
        DefaultTableModel listaImpressao = (DefaultTableModel) jTblImpressao.getModel();

        jTblConsulta.selectAll();

        try {
            for (int x = jTblConsulta.getSelectedRowCount(); x != 0; x--) {
                int linha = jTblConsulta.getSelectedRow();
                String id = jTblConsulta.getValueAt(linha, 0).toString();
                String empresa = jTblConsulta.getValueAt(linha, 1).toString();
                String estabelecimento = jTblConsulta.getValueAt(linha, 2).toString();
                String departamento = jTblConsulta.getValueAt(linha, 3).toString();

                listaImpressao.addRow(new String[]{id, empresa, estabelecimento, departamento});

                //jTblConsulta.removeRowSelectionInterval(linha, linha);
                ((DefaultTableModel) jTblConsulta.getModel()).removeRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_jBtnAddAllActionPerformed

    private void jBtnAddEtqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddEtqActionPerformed
        DefaultTableModel listaImpressao = (DefaultTableModel) jTblImpressao.getModel();

        try {
            for (int x = jTblConsulta.getSelectedRowCount(); x != 0; x--) {
                int linha = jTblConsulta.getSelectedRow();
                String id = jTblConsulta.getValueAt(linha, 0).toString();
                String empresa = jTblConsulta.getValueAt(linha, 1).toString();
                String estabelecimento = jTblConsulta.getValueAt(linha, 2).toString();
                String departamento = jTblConsulta.getValueAt(linha, 3).toString();

                listaImpressao.addRow(new String[]{id, empresa, estabelecimento, departamento});

                //jTblConsulta.removeRowSelectionInterval(linha, linha);
                
                ((DefaultTableModel) jTblConsulta.getModel()).removeRow(linha);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_jBtnAddEtqActionPerformed

    private void jBtnRemoveAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRemoveAllActionPerformed
        DefaultTableModel listaImpressao = (DefaultTableModel) jTblImpressao.getModel();
        listaImpressao.setRowCount(0);
        
        MontaLista();

    }//GEN-LAST:event_jBtnRemoveAllActionPerformed

    private void jBtnRemoveEtqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnRemoveEtqActionPerformed
        if (jTblImpressao.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Ô, tontão. Tu precisa selecionar uma linha!");
        } else {
            for (int x = jTblImpressao.getSelectedRowCount(); x != 0; x--) {
                try {
                    ((DefaultTableModel) jTblImpressao.getModel()).removeRow(jTblImpressao.getSelectedRow());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível excluir a linha: " + e);
                }

            }
        }
    }//GEN-LAST:event_jBtnRemoveEtqActionPerformed

    private void jBtnReimpressaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnReimpressaoActionPerformed
        if ("Reimpressão".equals(jBtnReimpressao.getText())) {
            var_consulta = 2;
            jBtnReimpressao.setText("Voltar");
        } else {
            var_consulta = 1;
            jBtnReimpressao.setText("Reimpressão");
        }
        MontaLista();

    }//GEN-LAST:event_jBtnReimpressaoActionPerformed

    private void jRbExtendidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRbExtendidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRbExtendidaActionPerformed

    private void jCheckBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox1MouseClicked
        MontaLista();
    }//GEN-LAST:event_jCheckBox1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bG_TipoRelatorio;
    private javax.swing.JButton jBtnAddAll;
    private javax.swing.JButton jBtnAddEtq;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEmitir;
    private javax.swing.JButton jBtnReimpressao;
    private javax.swing.JButton jBtnRemoveAll;
    private javax.swing.JButton jBtnRemoveEtq;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRbExtendida;
    private javax.swing.JRadioButton jRbNormal;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTblConsulta;
    private javax.swing.JTable jTblImpressao;
    // End of variables declaration//GEN-END:variables
}
