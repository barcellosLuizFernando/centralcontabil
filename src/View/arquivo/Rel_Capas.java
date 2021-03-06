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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public final class Rel_Capas extends javax.swing.JInternalFrame {

    /**
     * Creates new form Rel_Capas
     */
    
    ConexaoMySQL cn = new ConexaoMySQL();
    String url;
    String driver = "com.mysql.jdbc.Driver";
    String usuario;
    String senha;
    
    DateFormat dateIn = new SimpleDateFormat("dd/MM/yyyy");
    
    public Rel_Capas() {
        initComponents();
        this.setLocation(WIDTH, WIDTH);
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
    
    public void DadosConexao(){
        try(FileReader arq = new FileReader("conexao.txt")) {
                BufferedReader lerArq = new BufferedReader(arq);
                String linha = lerArq.readLine();
                int line = 1;
                while (linha != null){
                    switch (line) {
                        case 1:
                            url = "jdbc:mysql://"+linha;
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
            } catch(IOException e){
                JOptionPane.showMessageDialog(null,"Erro na abertura do arquivo: "+ e);

            }
    }
    
    public void MontaLista(){
        String criterio = jTxtPesquisar.getText();
        
        jTblPesquisar.setEnabled(true);
        jBtnEmitir.setEnabled(false);
        jTxtIdCapa.setText("");
        jTxtAssuntoCapa.setText("");
        jTxtIdEmpresa.setText("");
        jTxtNomeEmpresa.setText("");
        
        DefaultTableModel pesquisa = (DefaultTableModel)jTblPesquisar.getModel();
        
                        
                        
        // LISTA ESTABELECIMENTOS
        pesquisa.setColumnCount(0);
        pesquisa.setRowCount(0);

        pesquisa.addColumn("Id_Empresa");
        pesquisa.addColumn("Empresa");
        pesquisa.addColumn("Id_Assunto");
        pesquisa.addColumn("Assunto");
        pesquisa.addColumn("Data Inicial");
        pesquisa.addColumn("Data Final");
        
        jTblPesquisar.getColumnModel().getColumn(0).setMaxWidth(40);
        jTblPesquisar.getColumnModel().getColumn(0).setMinWidth(40);
        jTblPesquisar.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
        jTblPesquisar.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);
        jTblPesquisar.getColumnModel().getColumn(2).setMaxWidth(40);
        jTblPesquisar.getColumnModel().getColumn(2).setMinWidth(40);
        jTblPesquisar.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(40);
        jTblPesquisar.getTableHeader().getColumnModel().getColumn(2).setMinWidth(40);
        
        


        String sql = "SELECT id_empresa, nome_empresa, id, nome_assunto,"
                + "dt_inicial, dt_final "
                + "FROM cad_arquivo_capas "
                + "WHERE id = '"+criterio+"' "
                + "  OR nome_assunto LIKE '%"+criterio.toUpperCase()+"%'";
        
        if(!"".equals(criterio)){

            cn.conecta();
            cn.executeConsulta(sql);

            try{
                while(cn.rs.next()){
                    pesquisa.addRow(new String[]{
                        cn.rs.getString(1),
                        cn.rs.getString(2),    
                        cn.rs.getString(3),
                        cn.rs.getString(4),    
                        dateIn.format(cn.rs.getObject(5)),
                        dateIn.format(cn.rs.getObject(6))    
                    });
                }
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(this, ex);
            }
            cn.desconecta();
        }
    }
    
    public void IncluiPesquisa(){
        int linha;
        
        //INCLUI ASSUNTO
        linha = jTblPesquisar.getSelectedRow();

        jTxtIdEmpresa.setText(jTblPesquisar.getValueAt(linha, 0).toString());
        jTxtNomeEmpresa.setText(jTblPesquisar.getValueAt(linha, 1).toString());
        jTxtIdCapa.setText(jTblPesquisar.getValueAt(linha, 2).toString());
        jTxtAssuntoCapa.setText(jTblPesquisar.getValueAt(linha, 3).toString());
        
        jTblPesquisar.setEnabled(false);
        jBtnEmitir.setEnabled(true);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtIdEmpresa = new javax.swing.JTextField();
        jTxtIdCapa = new javax.swing.JTextField();
        jTxtNomeEmpresa = new javax.swing.JTextField();
        jTxtAssuntoCapa = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblPesquisar = new javax.swing.JTable();
        jTxtPesquisar = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jBtnEmitir = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();

        setClosable(true);
        setTitle("Impressão de Capas");
        setVisible(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Empresa");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Capa");

        jTxtIdEmpresa.setEditable(false);
        jTxtIdEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdEmpresa.setEnabled(false);

        jTxtIdCapa.setEditable(false);
        jTxtIdCapa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdCapa.setEnabled(false);

        jTxtNomeEmpresa.setEditable(false);
        jTxtNomeEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeEmpresa.setEnabled(false);

        jTxtAssuntoCapa.setEditable(false);
        jTxtAssuntoCapa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtAssuntoCapa.setEnabled(false);

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        jTblPesquisar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTblPesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblPesquisarMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTblPesquisar);

        jTxtPesquisar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtPesquisarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTxtIdCapa, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                            .addComponent(jTxtIdEmpresa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtNomeEmpresa)
                            .addComponent(jTxtAssuntoCapa, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)))
                    .addComponent(jTxtPesquisar))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTxtIdCapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtAssuntoCapa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTxtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jBtnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPesquisarKeyReleased
        MontaLista();
    }//GEN-LAST:event_jTxtPesquisarKeyReleased

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
       
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jTblPesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblPesquisarMouseClicked
        IncluiPesquisa();
    }//GEN-LAST:event_jTblPesquisarMouseClicked

    private void jBtnEmitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEmitirActionPerformed
        
        
        String id_capa = jTxtIdCapa.getText();
        String id_empresa = jTxtIdEmpresa.getText();
        String rel = "reports/Capa.jasper";
        
        String sql = " SELECT a.id AS Codigo, a.nome_assunto AS Assunto, "
                + "a.nome_empresa AS Empresa, a.nome_estabelecimento AS Estabelecimento, "
                + "a.dt_inicial AS 'Data Inicial', a.dt_final AS 'Data Final', "
                + "b.razao_social AS Estabelecimento, b.rua AS Endereço, b.numero AS Numero, "
                + "b.bairro AS Bairro, "
                + "INSERT(INSERT(b.cep, 6, 0, '-'),3,0,'.') AS CEP, "
                + "b.cidade AS Cidade, b.uf AS UF, b.inscricao_estadual AS 'Inscrição Estadual', "
                + " INSERT(INSERT(INSERT(INSERT(b.inscricao_federal, 13, 0, '-'),9,0,'/'),6,0,'.'),3,0,'.') AS 'C.N.P.J.', "
                + "b.inscricao_municipal AS 'Inscrição Municipal' "
                + "FROM cad_arquivo_capas a "
                + "LEFT JOIN cad_estabelecimentos b ON (b.id_empresa = a.id_empresa AND b.id = a.id_estabelecimento) "
                + "WHERE a.id = '"+id_capa+"' "
                + "AND a.id_empresa = '"+id_empresa+"' ";
        
        
        try{
            cn.conecta();
            cn.executeConsulta(sql);

            JRResultSetDataSource relatResult = new JRResultSetDataSource(cn.rs);
            
            JasperPrint jasperprint;
            jasperprint = JasperFillManager.fillReport(rel, new HashMap(), relatResult);
            JasperViewer jv = new JasperViewer(jasperprint,false);
            
            jv.setVisible(true);
            
            
            
        } catch (JRException je){
            JOptionPane.showMessageDialog(this, je);
        }
        
        
    }//GEN-LAST:event_jBtnEmitirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnEmitir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTblPesquisar;
    private javax.swing.JTextField jTxtAssuntoCapa;
    private javax.swing.JTextField jTxtIdCapa;
    private javax.swing.JTextField jTxtIdEmpresa;
    private javax.swing.JTextField jTxtNomeEmpresa;
    private javax.swing.JTextField jTxtPesquisar;
    // End of variables declaration//GEN-END:variables
}
