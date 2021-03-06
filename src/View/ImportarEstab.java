/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import conexoes.ConexaoFB;
import conexoes.ConexaoMySQL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author luiz.barcellos
 */
public class ImportarEstab extends javax.swing.JInternalFrame {

    private int var_consulta;
    ConexaoFB fb = new ConexaoFB();
    ConexaoMySQL cn = new ConexaoMySQL();

    String descricao = null;
    String nirf = null;
    String incra = null;
    String matriculas = null;
    String endereco = null;
    String nro = null;
    String bairro = null;
    String municipio = null;
    String ie = null;
    String area = null;
    String id_my;

    /**
     * Creates new form ImportarPessoas
     */
    public ImportarEstab() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTxtPessoa = new javax.swing.JTextField();
        jTxtNomePessoa = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setTitle("Importar Cadastro de Estabelecimentos");

        jLabel1.setText("Pessoa");

        jTxtPessoa.setToolTipText("CNPJ ou CPF");
        jTxtPessoa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxtPessoaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtPessoaFocusLost(evt);
            }
        });

        jTxtNomePessoa.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(62, 62, 62)
                .addComponent(jTxtPessoa, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTxtNomePessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTxtPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomePessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("Importar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTxtPessoaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPessoaFocusLost
        String id = jTxtPessoa.getText();

        cn.conecta();
        try {
            cn.executeConsulta("SELECT id,nome FROM cad_pessoas WHERE inscricao_federal = " + id);
            while (cn.rs.next()) {
                id_my = cn.rs.getString(1);
                jTxtNomePessoa.setText(cn.rs.getString(2));
            }

            JOptionPane.showMessageDialog(this, "A id do cliente é: " + id_my);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Não foi possível recuperar a ID.");
        }
    }//GEN-LAST:event_jTxtPessoaFocusLost

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (!"".equals(jTxtPessoa.getText())) {

            var_consulta = 1;
            buscaPessoa();
        } else {
            JOptionPane.showMessageDialog(this, "Não há dados para importar.");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTxtPessoaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtPessoaFocusGained
        jTxtNomePessoa.setText("");
    }//GEN-LAST:event_jTxtPessoaFocusGained


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTxtNomePessoa;
    private javax.swing.JTextField jTxtPessoa;
    // End of variables declaration//GEN-END:variables

    private void buscaPessoa() {
        //jTxtNomePessoa.setText("");

        String sql;

        switch (var_consulta) {
            case 1:
                String id = jTxtPessoa.getText();

                fb.conecta();
                cn.conecta();

                sql = "SELECT DISTINCT b.nome, b.cpf_cnpj, a.descricao, a.nirf, "
                        + "a.incra, a.matriculas, c.endereco, c.numero, c.bairro, "
                        + "e.nome as municipio, c.cad_pro as ie "
                        + "from cad_terceiros_produtores_det a "
                        + "left join cad_terceiros b on (b.empresa = a.empresa and b.codigo = a.codigo) "
                        + "left join cad_terceiros_enderecos c on (c.empresa = a.empresa and c.codigo = a.codigo and a.sequencia_endereco = c.sequencia) "
                        + "left join cad_municipios e on (e.codigo = c.municipio) "
                        + "WHERE b.cpf_cnpj = '" + id + "';";

                fb.executeConsulta(sql);

                try {
                    while (fb.rs.next()) {
                        descricao = fb.rs.getString("descricao");
                        nirf = fb.rs.getString("nirf");
                        incra = fb.rs.getString("incra");
                        matriculas = fb.rs.getString("matriculas");
                        endereco = fb.rs.getString("endereco");
                        nro = fb.rs.getString("numero");
                        bairro = fb.rs.getString("bairro");
                        municipio = fb.rs.getString("municipio");
                        ie = fb.rs.getString("ie");
                        area = "0.00";

                        sql = "INSERT INTO icms_estabelecimentos (id_pessoa,"
                                + "descricao,nirf,incra,matricula,endereco,numero,"
                                + "bairro,municipio,inscri_est,total_area) VALUES "
                                + "('" + id_my + "','" + descricao + "',"
                                + "" + nirf + "," + incra + ",'" + matriculas + "',"
                                + "'" + endereco + "','" + nro + "','" + bairro + "',"
                                + "'" + municipio + "'," + ie + ",'" + area + "');";

                        cn.executeAtualizacao(sql);

                    }
                } catch (SQLException ex) {
                    ConexaoFB.resultadoUpd = 1;
                    ConexaoMySQL.resultadoUpd = 1;
                    
                    JOptionPane.showMessageDialog(this, ex);
                }
                if (ConexaoMySQL.resultadoUpd < 1) {

                    JOptionPane.showMessageDialog(this, "Importação realizada com sucesso.");

                    jTxtPessoa.setText("");
                    jTxtNomePessoa.setText("");
                }

                fb.desconecta();
                cn.desconecta();

                //jTxtNomePessoa.setText(nome);
                //JOptionPane.showMessageDialog(this, endereco);
                break;
        }
    }
}
