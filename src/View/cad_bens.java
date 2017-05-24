/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ferna
 */
public class cad_bens extends javax.swing.JInternalFrame {

    /**
     * Creates new form cad_bens
     */
    public cad_bens() {
        initComponents();
    }

    ConexaoMySQL cn = new ConexaoMySQL();
    String url;
    String driver = "com.mysql.jdbc.Driver";
    String usuario;
    String senha;

    int var_consulta;

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

    public void habilitarEdicao() {
        //jTxtIdEmpresa.setEnabled(true);
        jTxtNomeEmpresa.setEnabled(true);
        //jTxtIdTipo.setEnabled(true);
        jTxtDescricaoTipo.setEnabled(true);
        jTxtPlaca.setEnabled(true);
        jTxtModelo.setEnabled(true);
        jTxtDescricao.setEnabled(true);
        jTxtChassi.setEnabled(true);
        jTxtAnoFabricacao.setEnabled(true);

        jBtnGravar.setEnabled(true);
        jBtnPesquisaItem.setEnabled(false);
        jBtnPesquisaEmpresa.setEnabled(true);
        jBtnPesquisaTipo.setEnabled(true);
    }

    public void desabilitarEdicao() {
        jTxtIdEmpresa.setEnabled(false);
        jTxtNomeEmpresa.setEnabled(false);
        jTxtIdTipo.setEnabled(false);
        jTxtDescricaoTipo.setEnabled(false);
        jTxtPlaca.setEnabled(false);
        jTxtModelo.setEnabled(false);
        jTxtDescricao.setEnabled(false);
        jTxtChassi.setEnabled(false);
        jTxtAnoFabricacao.setEnabled(false);

        jBtnIncluir.setText("Adicionar");
        jBtnEditar.setText("Editar");

        jBtnGravar.setEnabled(false);
        jBtnPesquisaItem.setEnabled(true);
        jBtnPesquisaEmpresa.setEnabled(false);
        jBtnPesquisaTipo.setEnabled(false);
    }

    public void limpaCampos() {
        jTxtPlaca.setText("");
        jTxtModelo.setText("");
        jTxtDescricao.setText("");
        jTxtChassi.setText("");
        jTxtAnoFabricacao.setText("");
        jTxtIdEmpresa.setText("");
        jTxtNomeEmpresa.setText("");
        jTxtIdTipo.setText("");
        jTxtDescricaoTipo.setText("");
    }

    public void MontaLista() {

        DefaultTableModel lista = (DefaultTableModel) jTblConsulta_Multi.getModel();
        lista.setColumnCount(0);  //limpa a tabela
        lista.setRowCount(0);     //limpa a tabela

        
        jTxtPesquisa_Multi.grabFocus();

        switch (var_consulta) {

            case 1:

                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_empresas "
                            + "WHERE UPPER(nome) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY nome";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Razão Social");
                    lista.addColumn("Observação");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString("id"),
                                cn.rs.getString("nome"),
                                cn.rs.getString("observacao")});
                        }
                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                    }
                    cn.desconecta();
                    jTblConsulta_Multi.setModel(lista);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(10);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(10);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(10);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(10);

                } else { //Configuração para quando não há texto no campo pesquisa
                    lista.setColumnCount(0);  //limpa a tabela
                    lista.setRowCount(0);     //limpa a tabela
                    cn.conecta();
                    String sql = "SELECT * FROM cad_empresas "
                            + " ORDER BY nome";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Razão Social");
                    lista.addColumn("Observação");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString("id"),
                                cn.rs.getString("nome"),
                                cn.rs.getString("observacao")});

                        }
                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                    }
                    cn.desconecta();
                    jTblConsulta_Multi.setModel(lista);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

                    //Configuração dos botões, quando não há pesquisa
                    jBtnConfirmar.setEnabled(true);
                }
                break;

            case 2:

                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_seguros_tipos "
                            + "WHERE UPPER(descricao) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");
                    lista.addColumn("Observação");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString("id"),
                                cn.rs.getString("descricao"),
                                cn.rs.getString("observacao")});
                        }
                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                    }
                    cn.desconecta();
                    jTblConsulta_Multi.setModel(lista);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(10);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(10);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(10);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(10);

                } else { //Configuração para quando não há texto no campo pesquisa
                    lista.setColumnCount(0);  //limpa a tabela
                    lista.setRowCount(0);     //limpa a tabela
                    cn.conecta();
                    String sql = "SELECT * FROM cad_seguros_tipos "
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");
                    lista.addColumn("Observação");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString("id"),
                                cn.rs.getString("descricao"),
                                cn.rs.getString("observacao")});

                        }
                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                    }
                    cn.desconecta();
                    jTblConsulta_Multi.setModel(lista);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

                    //Configuração dos botões, quando não há pesquisa
                    jBtnConfirmar.setEnabled(true);
                }
                break;

            case 3:

                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    lista.setColumnCount(0);  //limpa a tabela
                    lista.setRowCount(0);     //limpa a tabela
                    cn.conecta();
                    String sql = "SELECT * FROM cad_bens a "
                            + "left join cad_empresas b on (b.id = a.id_empresa) "
                            + "left join cad_seguros_tipos C ON (C.id = a.tipo) "
                            + "WHERE UPPER(a.descricao) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY a.descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Id Empresa");
                    lista.addColumn("Tipo");
                    lista.addColumn("Descrição");
                    lista.addColumn("Placa");
                    lista.addColumn("Modelo");
                    lista.addColumn("Chassi");
                    lista.addColumn("Ano");
                    lista.addColumn("Nome Empresa");
                    lista.addColumn("Descrição Tipo");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2),
                                cn.rs.getString(3),
                                cn.rs.getString(4),
                                cn.rs.getString(5),
                                cn.rs.getString(6),
                                cn.rs.getString(7),
                                cn.rs.getString(8),
                                cn.rs.getString(10),
                                cn.rs.getString(13)});
                        }
                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                    }
                    cn.desconecta();
                    jTblConsulta_Multi.setModel(lista);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(10);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(10);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(10);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(10);

                } else { //Configuração para quando não há texto no campo pesquisa
                    lista.setColumnCount(0);  //limpa a tabela
                    lista.setRowCount(0);     //limpa a tabela
                    cn.conecta();
                    String sql = "SELECT * FROM cad_bens a "
                            + "left join cad_empresas b on (b.id = a.id_empresa) "
                            + "left join cad_seguros_tipos C ON (C.id = a.tipo) "
                            + " ORDER BY a.descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Id Empresa");
                    lista.addColumn("Tipo");
                    lista.addColumn("Descrição");
                    lista.addColumn("Placa");
                    lista.addColumn("Modelo");
                    lista.addColumn("Chassi");
                    lista.addColumn("Ano");
                    lista.addColumn("Nome Empresa");
                    lista.addColumn("Descrição Tipo");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2),
                                cn.rs.getString(3),
                                cn.rs.getString(4),
                                cn.rs.getString(5),
                                cn.rs.getString(6),
                                cn.rs.getString(7),
                                cn.rs.getString(8),
                                cn.rs.getString(10),
                                cn.rs.getString(13)});

                        }
                    } catch (SQLException ex) {

                        JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                    }
                    cn.desconecta();
                    jTblConsulta_Multi.setModel(lista);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
                    jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
                    jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

                    //Configuração dos botões, quando não há pesquisa
                    jBtnConfirmar.setEnabled(true);
                }
                break;

        }
    }

    public void IncluiPesquisa() {

        DefaultTableModel lista = (DefaultTableModel) jTblConsulta_Multi.getModel();

        if (var_consulta == 1) {

            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtIdEmpresa.setText(lista.getValueAt(linha, 0).toString());
            jTxtNomeEmpresa.setText(lista.getValueAt(linha, 1).toString());

        } else if (var_consulta == 2) {

            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtIdTipo.setText(lista.getValueAt(linha, 0).toString());
            jTxtDescricaoTipo.setText(lista.getValueAt(linha, 1).toString());

        } else if (var_consulta == 3) {
            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtId.setText(lista.getValueAt(linha, 0).toString());
            jTxtIdEmpresa.setText(lista.getValueAt(linha, 1).toString());
            jTxtIdTipo.setText(lista.getValueAt(linha, 2).toString());
            jTxtDescricao.setText(lista.getValueAt(linha, 3).toString());
            jTxtPlaca.setText(lista.getValueAt(linha, 4).toString());
            jTxtModelo.setText(lista.getValueAt(linha, 5).toString());
            jTxtChassi.setText(lista.getValueAt(linha, 6).toString());
            jTxtAnoFabricacao.setText(lista.getValueAt(linha, 7).toString());
            jTxtNomeEmpresa.setText(lista.getValueAt(linha, 8).toString());
            jTxtDescricaoTipo.setText(lista.getValueAt(linha, 9).toString());

        }

        jBtnEditar.setEnabled(false);
        if (!"".equals(jTxtDescricao.getText())) {
            jBtnEditar.setEnabled(true);
        }
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
        jPanel3 = new javax.swing.JPanel();
        jTxtPesquisa_Multi = new javax.swing.JTextField();
        jBtnConfirmar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTblConsulta_Multi = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTxtAnoFabricacao = new javax.swing.JTextField();
        jTxtChassi = new javax.swing.JTextField();
        jTxtModelo = new javax.swing.JTextField();
        jTxtPlaca = new javax.swing.JTextField();
        jTxtDescricao = new javax.swing.JTextField();
        jTxtId = new javax.swing.JTextField();
        jBtnPesquisaItem = new javax.swing.JButton();
        jTxtIdEmpresa = new javax.swing.JTextField();
        jTxtNomeEmpresa = new javax.swing.JTextField();
        jBtnPesquisaEmpresa = new javax.swing.JButton();
        jTxtIdTipo = new javax.swing.JTextField();
        jTxtDescricaoTipo = new javax.swing.JTextField();
        jBtnPesquisaTipo = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jBtnIncluir = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnGravar = new javax.swing.JButton();

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 453, Short.MAX_VALUE)
                        .addComponent(jBtnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnConfirmar))
                    .addComponent(jTxtPesquisa_Multi)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTxtPesquisa_Multi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnConfirmar)
                    .addComponent(jBtnCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPesquisarLayout = new javax.swing.GroupLayout(jPesquisar.getContentPane());
        jPesquisar.getContentPane().setLayout(jPesquisarLayout);
        jPesquisarLayout.setHorizontalGroup(
            jPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPesquisarLayout.setVerticalGroup(
            jPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setTitle("Bens");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tipo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Descrição");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Placa");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Modelo");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Chassi");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Ano Fabricação");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Empresa");

        jTxtAnoFabricacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtAnoFabricacao.setEnabled(false);

        jTxtChassi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtChassi.setEnabled(false);

        jTxtModelo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtModelo.setEnabled(false);

        jTxtPlaca.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtPlaca.setEnabled(false);

        jTxtDescricao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtDescricao.setEnabled(false);

        jTxtId.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtId.setEnabled(false);

        jBtnPesquisaItem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaItem.setText("...");
        jBtnPesquisaItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaItemActionPerformed(evt);
            }
        });

        jTxtIdEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdEmpresa.setEnabled(false);

        jTxtNomeEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeEmpresa.setEnabled(false);

        jBtnPesquisaEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaEmpresa.setText("...");
        jBtnPesquisaEmpresa.setEnabled(false);
        jBtnPesquisaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaEmpresaActionPerformed(evt);
            }
        });

        jTxtIdTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdTipo.setEnabled(false);

        jTxtDescricaoTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtDescricaoTipo.setEnabled(false);

        jBtnPesquisaTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaTipo.setText("...");
        jBtnPesquisaTipo.setEnabled(false);
        jBtnPesquisaTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtAnoFabricacao))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(50, 50, 50)
                        .addComponent(jTxtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtNomeEmpresa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnPesquisaEmpresa))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(65, 65, 65)
                        .addComponent(jTxtChassi, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(60, 60, 60)
                        .addComponent(jTxtModelo, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(72, 72, 72)
                        .addComponent(jTxtPlaca, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(77, 77, 77)
                        .addComponent(jTxtIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtDescricaoTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnPesquisaTipo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(44, 44, 44)
                        .addComponent(jTxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtDescricao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnPesquisaItem)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnPesquisaEmpresa))
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtDescricaoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtIdTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnPesquisaTipo))
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTxtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisaItem))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTxtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTxtChassi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTxtAnoFabricacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jBtnIncluir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnIncluir.setText("Adicionar");
        jBtnIncluir.setToolTipText("Insere mais um registro.");
        jBtnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIncluirActionPerformed(evt);
            }
        });

        jBtnEditar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.setEnabled(false);
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnGravar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnGravar.setText("Gravar");
        jBtnGravar.setEnabled(false);
        jBtnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGravarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnIncluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIncluirActionPerformed
        //limpaCampos();

        if ("Adicionar".equals(jBtnIncluir.getText())) {

            habilitarEdicao();

            jBtnIncluir.setText("Cancelar");
            jBtnEditar.setEnabled(false);
        } else {

            desabilitarEdicao();
        }
    }//GEN-LAST:event_jBtnIncluirActionPerformed

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        if ("Editar".equals(jBtnEditar.getText())) {
            habilitarEdicao();

            jBtnEditar.setText("Cancelar");
            jBtnIncluir.setEnabled(false);
        } else {
            limpaCampos();
            desabilitarEdicao();
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGravarActionPerformed
        //INSERINDO DADOS

        String id = jTxtId.getText();
        String descricao = jTxtDescricao.getText();
        String idempresa = jTxtIdEmpresa.getText();
        String nomeempresa = jTxtNomeEmpresa.getText();
        String idtipo = jTxtIdTipo.getText();
        String nometipo = jTxtDescricaoTipo.getText();
        String placa = jTxtPlaca.getText();
        String modelo = jTxtModelo.getText();
        String chassi = jTxtChassi.getText();
        String ano = jTxtAnoFabricacao.getText();

        

        if ("Cancelar".equals(jBtnIncluir.getText())) {
            String sql = "INSERT INTO cad_bens (id_empresa, tipo, descricao, "
                    + "placa, modelo, chassi, ano_fabricacao) VALUES "
                    + "('" + idempresa + "','" + idtipo + "','" + descricao + "','" + placa + "','"
                    + modelo + "','" + chassi + "','" + ano + "')";

            cn.conecta();
            cn.executeAtualizacao(sql);
            cn.desconecta();

        } else {
            String sql = "UPDATE cad_bens SET id_empresa = '" + idempresa + "', "
                    + "tipo = '" + idtipo + "', descricao = '" + descricao + "', "
                    + "placa = '" + placa + "', modelo = '" + modelo + "', "
                    + "chassi = '" + chassi + "', ano_fabricacao = '" + ano + "' "
                    + "WHERE id = '"+id+"'";
            
            
            cn.conecta();
            cn.executeAtualizacao(sql);
            cn.desconecta();
        }

        desabilitarEdicao();


    }//GEN-LAST:event_jBtnGravarActionPerformed

    private void jBtnPesquisaItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaItemActionPerformed
        var_consulta = 3;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaItemActionPerformed

    private void jBtnPesquisaEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaEmpresaActionPerformed
        var_consulta = 1;

        MontaLista();
        jPesquisar.setVisible(true);

    }//GEN-LAST:event_jBtnPesquisaEmpresaActionPerformed

    private void jBtnPesquisaTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaTipoActionPerformed
        var_consulta = 2;

        MontaLista();
        jPesquisar.setVisible(true);
    }//GEN-LAST:event_jBtnPesquisaTipoActionPerformed

    private void jTxtPesquisa_MultiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPesquisa_MultiKeyReleased

        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPesquisa_MultiKeyReleased

    private void jBtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfirmarActionPerformed
        IncluiPesquisa();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnConfirmar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnGravar;
    private javax.swing.JButton jBtnIncluir;
    private javax.swing.JButton jBtnPesquisaEmpresa;
    private javax.swing.JButton jBtnPesquisaItem;
    private javax.swing.JButton jBtnPesquisaTipo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JFrame jPesquisar;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTblConsulta_Multi;
    private javax.swing.JTextField jTxtAnoFabricacao;
    private javax.swing.JTextField jTxtChassi;
    private javax.swing.JTextField jTxtDescricao;
    private javax.swing.JTextField jTxtDescricaoTipo;
    private javax.swing.JTextField jTxtId;
    private javax.swing.JTextField jTxtIdEmpresa;
    private javax.swing.JTextField jTxtIdTipo;
    private javax.swing.JTextField jTxtModelo;
    private javax.swing.JTextField jTxtNomeEmpresa;
    private javax.swing.JTextField jTxtPesquisa_Multi;
    private javax.swing.JTextField jTxtPlaca;
    // End of variables declaration//GEN-END:variables
}
