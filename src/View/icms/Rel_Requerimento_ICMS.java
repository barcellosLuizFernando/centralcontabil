/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.icms;

import conexoes.ConexaoMySQL;
import ferramenta.ImprimeRelatorio;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author luiz.barcellos
 */
public class Rel_Requerimento_ICMS extends javax.swing.JInternalFrame {

    private int var_consulta;
    private String uf;

    private final DateFormat dateOut = new SimpleDateFormat("yyyy/MM/dd");
    private final DateFormat dateIn = new SimpleDateFormat("dd/MM/yyyy");

    private final DecimalFormat df = new DecimalFormat("#,##0.00");

    private final ConexaoMySQL cn = new ConexaoMySQL();
    
    private final ImprimeRelatorio rel = new ImprimeRelatorio();

    /**
     * Creates new form Rel_Requerimento_ICMS
     */
    public Rel_Requerimento_ICMS() {
        initComponents();
        montaTabela();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPesquisar = new javax.swing.JFrame();
        jPanel5 = new javax.swing.JPanel();
        jTxtPesquisa_Multi = new javax.swing.JTextField();
        jBtnConfirmar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTblConsulta_Multi = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtIdPessoa = new javax.swing.JTextField();
        jTxtNomePessoa = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jTxtNomeProcurador = new javax.swing.JTextField();
        jTxtIdProcurador = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        jPesquisar.setTitle("Pesquisar");
        jPesquisar.setMinimumSize(new java.awt.Dimension(700, 400));

        jTxtPesquisa_Multi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtPesquisa_Multi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtPesquisa_MultiKeyReleased(evt);
            }
        });

        jBtnConfirmar.setText("Adicionar");
        jBtnConfirmar.setEnabled(false);
        jBtnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConfirmarActionPerformed(evt);
            }
        });

        jBtnCancelar.setText("Cancelar");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

        jTblConsulta_Multi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTblConsulta_Multi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTblConsulta_Multi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblConsulta_MultiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTblConsulta_MultiMouseEntered(evt);
            }
        });
        jScrollPane4.setViewportView(jTblConsulta_Multi);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 453, Short.MAX_VALUE)
                        .addComponent(jBtnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnConfirmar))
                    .addComponent(jTxtPesquisa_Multi)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTxtPesquisa_Multi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnConfirmar)
                    .addComponent(jBtnCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPesquisarLayout = new javax.swing.GroupLayout(jPesquisar.getContentPane());
        jPesquisar.getContentPane().setLayout(jPesquisarLayout);
        jPesquisarLayout.setHorizontalGroup(
            jPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPesquisarLayout.setVerticalGroup(
            jPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("Emissão de Requerimentos");

        jLabel1.setText("Produtor");

        jTxtIdPessoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtIdPessoaFocusLost(evt);
            }
        });

        jTxtNomePessoa.setEnabled(false);

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTxtIdPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTxtNomePessoa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtIdPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomePessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton2.setText("Emitir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setText("REQUERIMENTO");

        jCheckBox1.setText("Anexar Check List");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jLabel3)
                    .addComponent(jCheckBox1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Procurador");

        jButton4.setText("...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTxtNomeProcurador.setEnabled(false);

        jTxtIdProcurador.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtIdProcuradorFocusLost(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton5.setText("Emitir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel4.setText("PROCURAÇÃO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtIdProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtNomeProcurador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtIdProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomeProcurador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton3.setText("Emitir");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setText("DECLARAÇÃO DE IMOBILIZADO");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        var_consulta = 1;

        jPesquisar.setVisible(true);

        montaLista();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        var_consulta = 2;

        jPesquisar.setVisible(true);

        montaLista();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTxtPesquisa_MultiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPesquisa_MultiKeyReleased
        montaLista();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPesquisa_MultiKeyReleased

    private void jBtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfirmarActionPerformed
        incluiPesquisa();
        jPesquisar.dispose();// TODO add your handling code here:
    }//GEN-LAST:event_jBtnConfirmarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        jPesquisar.setVisible(false);        // TODO add your handling code here:
        jPesquisar.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jTblConsulta_MultiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblConsulta_MultiMouseClicked
        jBtnConfirmar.setEnabled(true);    // TODO add your handling code here:
    }//GEN-LAST:event_jTblConsulta_MultiMouseClicked

    private void jTblConsulta_MultiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblConsulta_MultiMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTblConsulta_MultiMouseEntered

    private void jTxtIdPessoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtIdPessoaFocusLost
        jTxtNomePessoa.setText("");
        if (!"".equals(jTxtIdPessoa.getText())) {

            var_consulta = 11;

            incluiPesquisa();
        }
    }//GEN-LAST:event_jTxtIdPessoaFocusLost

    private void jTxtIdProcuradorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtIdProcuradorFocusLost
        jTxtNomeProcurador.setText("");
        if (!"".equals(jTxtIdProcurador.getText())) {

            var_consulta = 22;

            incluiPesquisa();
        }
    }//GEN-LAST:event_jTxtIdProcuradorFocusLost

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int linha = jTable1.getSelectedRow();

        if (linha >= 0 && !"".equals(jTxtNomePessoa.getText())) {

            String rel = "reports/RequerimentoICMS_SC.jasper";
            String id = jTable1.getValueAt(linha, 0).toString();

            String sql = "SELECT * FROM rel_icms_produtor WHERE id = '" + id + "';";

            cn.conecta();
            cn.executeConsulta(sql);
            try {

                JRResultSetDataSource relatResult = new JRResultSetDataSource(cn.rs);
                Map<String, Object> parametros = new HashMap<String, Object>();
                parametros.put("checklist", jCheckBox1.isSelected());

                JasperPrint jasperprint;
                jasperprint = JasperFillManager.fillReport(rel, parametros, relatResult);
                JasperViewer jv = new JasperViewer(jasperprint, false);

                jv.setVisible(true);

            } catch (JRException je) {
                JOptionPane.showMessageDialog(this, je);
            }
            cn.desconecta();
        } else {
            JOptionPane.showMessageDialog(this, "Você precisa informar um produtor e selecionar um requerimento.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int linha = jTable1.getSelectedRow();

        if (linha >= 0
                && !"".equals(jTxtNomePessoa.getText())
                && !"".equals(jTxtNomeProcurador.getText())) {

            String idProd = jTxtIdPessoa.getText();
            String idProc = jTxtIdProcurador.getText();
            String processo = jTable1.getValueAt(linha, 0).toString();
            String rel = "reports/ProcuracaoICMS.jasper";

            String sql = "SELECT DISTINCT upper(a.produtor) as produtor, a.ie, "
                    + "concat(repeat('0',11-length(a.cpf)),a.cpf) as cpf, "
                    + "a.endereco, a.municipio, a.uf_de_entrega, a.cep, "
                    + "b.id as procId, upper(b.nome) as procNome, "
                    + "b.nacionalidade as procNacionalidade, "
                    + "b.estado_civil as proc_EstadoCivil, "
                    + "concat(repeat('0',11-length(b.inscricao_federal)),b.inscricao_federal) as procCPF, "
                    + "b.inscEst_rg as procRG, b.endereco as procEndereco, b.cidade as procCidade, "
                    + "b.cep as procCep, b.telefone as procTelefone, b.email as procEmail, "
                    + "b.nro as procNro, b.uf as procUf "
                    + "FROM rel_icms_produtor a, cad_pessoas b "
                    + "WHERE a.id_produtor = '" + idProd + "' "
                    + "AND a.id = '" + processo + "' "
                    + "AND b.id = '" + idProc + "' ;";

            cn.conecta();
            cn.executeConsulta(sql);
            try {

                JRResultSetDataSource relatResult = new JRResultSetDataSource(cn.rs);

                JasperPrint jasperprint;
                jasperprint = JasperFillManager.fillReport(rel, new HashMap(), relatResult);
                JasperViewer jv = new JasperViewer(jasperprint, false);

                jv.setVisible(true);

            } catch (JRException je) {
                JOptionPane.showMessageDialog(this, je);
            }
            cn.desconecta();
        } else {
            JOptionPane.showMessageDialog(this, "Você precisa informar um produtor, selecionar um requerimento e informar um procurador.");
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int linha = jTable1.getSelectedRow();

        if (linha >= 0) {
            
            String sql = "SELECT * FROM controladoria.rel_imobilizado "
                    + "WHERE id = " + jTxtIdPessoa.getText() + " "
                    + "AND area_id = " + jTable1.getValueAt(linha, 8).toString();

            String relatorio = "DeclaracaoImob.jasper";
            
            rel.imprimir(sql, relatorio, "Declaração de Imobilizado");

        } else {
            JOptionPane.showMessageDialog(this, "Você precisa selecionar um requerimento para emitir a Declaração de Imobilizado.");
        }

    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnConfirmar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JFrame jPesquisar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTblConsulta_Multi;
    private javax.swing.JTextField jTxtIdPessoa;
    private javax.swing.JTextField jTxtIdProcurador;
    private javax.swing.JTextField jTxtNomePessoa;
    private javax.swing.JTextField jTxtNomeProcurador;
    private javax.swing.JTextField jTxtPesquisa_Multi;
    // End of variables declaration//GEN-END:variables

    private void incluiPesquisa() {
        String id;
        String sql;
        int linha = jTblConsulta_Multi.getSelectedRow();

        switch (var_consulta) {
            case 1:

                jTxtIdPessoa.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());

            case 11:

                id = jTxtIdPessoa.getText();

                sql = "SELECT DISTINCT a.id_produtor, a.produtor, concat(repeat(0,11-char_length(a.cpf)), a.cpf) as cpf FROM rel_icms_produtor a "
                        + "WHERE a.id_produtor = '" + id + "';";

                System.out.println(sql);

                cn.conecta();
                cn.executeConsulta(sql);
                try {
                    while (cn.rs.next()) {
                        jTxtNomePessoa.setText(cn.rs.getString("produtor"));
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível consultar o produtor.");
                }
                cn.desconecta();

                listaProdutos();
                break;

            case 2:
                jTxtIdProcurador.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());

            case 22:

                id = jTxtIdProcurador.getText();

                sql = "SELECT a.id, a.nome, "
                        + "concat(repeat(0,11-char_length(a.inscricao_federal)), a.inscricao_federal) as cpf "
                        + "FROM cad_pessoas a WHERE char_length(a.inscricao_federal) <=11 "
                        + "AND a.id = '" + id + "';";

                System.out.println(sql);

                cn.conecta();
                cn.executeConsulta(sql);
                try {
                    while (cn.rs.next()) {
                        jTxtNomeProcurador.setText(cn.rs.getString("nome"));
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível consultar os municípios.");
                }
                cn.desconecta();

                listaProdutos();
                break;
        }
    }

    private void montaTabela() {
        DefaultTableModel lista = (DefaultTableModel) jTable1.getModel();

        lista.setRowCount(0);
        lista.setColumnCount(0);

        lista.addColumn("Processo");
        lista.addColumn("Município");
        lista.addColumn("Inscrição Estadual");
        lista.addColumn("Compras");
        lista.addColumn("Créditos");
        lista.addColumn("uf");
        lista.addColumn("regional");
        lista.addColumn("Inclusão");
        lista.addColumn("id_area");

        jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
        jTable1.getColumnModel().getColumn(0).setMinWidth(100);
        jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(100);
        jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(100);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(5).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(6).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
        jTable1.getColumnModel().getColumn(8).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(8).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
    }

    private void montaLista() {
        DefaultTableModel lista = (DefaultTableModel) jTblConsulta_Multi.getModel();

        lista.setRowCount(0);
        lista.setColumnCount(0);

        lista.addColumn("Id");
        lista.addColumn("Pessoa");
        lista.addColumn("CPF");

        jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(100);
        jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(100);
        jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(100);
        jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(100);

        String sql;
        String condicao;

        switch (var_consulta) {

            case 1:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    condicao = " WHERE UPPER(a.produtor) LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%' ";
                } else {
                    condicao = " ";
                }

                sql = "SELECT DISTINCT a.id_produtor, a.produtor, concat(repeat(0,11-char_length(a.cpf)), a.cpf) as cpf FROM rel_icms_produtor a "
                        + condicao
                        + "ORDER BY a.produtor;";

                cn.conecta();
                cn.executeConsulta(sql);
                try {
                    while (cn.rs.next()) {

                        lista.addRow(new String[]{
                            cn.rs.getString("id_produtor"),
                            cn.rs.getString("produtor"),
                            cn.rs.getString("cpf")
                        });
                    }
                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                }
                cn.desconecta();
                break;

            case 2:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    condicao = " AND UPPER(a.nome) LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%' ";
                } else {
                    condicao = " ";
                }

                sql = "SELECT a.id, a.nome, "
                        + "concat(repeat(0,11-char_length(a.inscricao_federal)), a.inscricao_federal) as cpf "
                        + "FROM cad_pessoas a where char_length(a.inscricao_federal) <=11 "
                        + condicao
                        + "ORDER BY a.nome;";

                cn.conecta();
                cn.executeConsulta(sql);
                try {
                    while (cn.rs.next()) {

                        lista.addRow(new String[]{
                            cn.rs.getString("id"),
                            cn.rs.getString("nome"),
                            cn.rs.getString("cpf")
                        });
                    }
                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                }
                cn.desconecta();
                break;
        }

    }

    private void listaProdutos() {
        montaTabela();

        String sql = "SELECT DISTINCT a.id, a.municipio, a.ie, sum(a.valor_nf) as valor_nf, "
                + "sum(a.valor_credito) as valor_credito, a.uf_de_entrega as uf, "
                + "a.mun_de_entrega as regional, a.dt_emissao, a.id_estabelecimento "
                + "FROM rel_icms_produtor a "
                + "WHERE a.id_produtor = '" + jTxtIdPessoa.getText() + "' "
                + "GROUP BY a.id ";

        montaTabela();

        DefaultTableModel lista = (DefaultTableModel) jTable1.getModel();

        cn.conecta();
        cn.executeConsulta(sql);
        try {
            while (cn.rs.next()) {

                lista.addRow(new String[]{
                    cn.rs.getString("id"),
                    cn.rs.getString("municipio"),
                    cn.rs.getString("ie"),
                    df.format(cn.rs.getDouble("valor_nf")),
                    df.format(cn.rs.getDouble("valor_credito")),
                    cn.rs.getString("uf"),
                    cn.rs.getString("regional"),
                    dateIn.format(cn.rs.getDate("dt_emissao")),
                    cn.rs.getString("id_estabelecimento")
                });
            }
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
        }
        cn.desconecta();
    }

    private void limpaCampos() {
        jTxtIdPessoa.setText("");
        jTxtNomePessoa.setText("");
        jTxtIdProcurador.setText("");
        jTxtNomeProcurador.setText("");
    }
}
