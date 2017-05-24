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
 * @author luiz.barcellos
 */
public final class Cad_Pessoas extends javax.swing.JInternalFrame {

    /**
     * Creates new form Cad_Pessoas1
     */
    ConexaoMySQL cn = new ConexaoMySQL();
    String url;
    String driver = "com.mysql.jdbc.Driver";
    String usuario;
    String senha;

    public Cad_Pessoas() {
        initComponents();
        
        ListaCidades();
        MontaLista();
    }

    DefaultTableModel lista = new DefaultTableModel();
    DefaultTableModel lista_cidades = new DefaultTableModel();

    int pesquisa; //VARIÁVEL QUE IRÁ DEFINIR ONDE A PESQUISA IRÁ INSERIR OS DADOS ENCONTRADOR

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

    public void ListaCidades() {
        lista_cidades.setColumnCount(0);  //limpa a tabela
        lista_cidades.setRowCount(0);
        lista_cidades.addColumn("Nome");
        lista_cidades.addColumn("UF");

        if ("".equals(jTxtPesquisa_Multi.getText())) {
            try {
                cn.conecta();
                String sql = "SELECT * FROM cad_munic "
                        + " ORDER BY nome";
                cn.executeConsulta(sql);

                while (cn.rs.next()) {
                    lista_cidades.addRow(new String[]{
                        cn.rs.getString("nome"),
                        cn.rs.getString("uf")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Não foi possível recuperar os Municípios");
            }
        } else {
            try {
                cn.conecta();
                String sql = "SELECT * FROM cad_munic "
                        + "WHERE UPPER(nome) "
                        + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                        + " ORDER BY nome";
                cn.executeConsulta(sql);

                while (cn.rs.next()) {
                    lista_cidades.addRow(new String[]{
                        cn.rs.getString("nome"),
                        cn.rs.getString("uf")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Não foi possível recuperar os Municípios");
            }
        }
        cn.desconecta();
        jTblConsulta_Multi.setModel(lista_cidades);
        jTblConsulta_Multi.getColumnModel().getColumn(1).setMaxWidth(30);
        jTblConsulta_Multi.getColumnModel().getColumn(1).setMinWidth(30);
        jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(30);
        jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(1).setMinWidth(30);

    }

    public void MontaLista() {

        lista.setColumnCount(0);  //limpa a tabela
        lista.setRowCount(0);     //limpa a tabela
        lista.addColumn("Id");
        lista.addColumn("Nome");
        lista.addColumn("CNPJ");
        lista.addColumn("Cidade");
        lista.addColumn("UF");

        if (!"".equals(jTxt_Pesquisar.getText())) {
            try {
                cn.conecta();
                String sql = "SELECT * FROM cad_pessoas "
                        + "WHERE UPPER(nome) "
                        + "LIKE '%" + jTxt_Pesquisar.getText().toUpperCase() + "%'"
                        + "or inscricao_federal "
                        + "LIKE '%" + jTxt_Pesquisar.getText().toUpperCase() + "%'"
                        + " ORDER BY nome";

                cn.executeConsulta(sql);

                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("inscricao_federal"),
                        cn.rs.getString("cidade"),
                        cn.rs.getString("uf")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }

        } else { //Configuração para quando não há texto no campo pesquisa
            try {
                cn.conecta();
                String sql = "SELECT * FROM cad_pessoas "
                        + " ORDER BY nome";

                cn.executeConsulta(sql);

                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("inscricao_federal"),
                        cn.rs.getString("cidade"),
                        cn.rs.getString("uf")});

                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }

            //Configuração dos botões, quando não há pesquisa
            //jBtn_Editar.setEnabled(false);
            //jBtn_Incluir.setEnabled(true);
        }
        cn.desconecta();
        jTbl_Pessoas.setModel(lista);
        jTbl_Pessoas.getColumnModel().getColumn(0).setMaxWidth(30);
        jTbl_Pessoas.getColumnModel().getColumn(0).setMinWidth(30);
        jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(30);
        jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(30);
    }

    public void IncluiPesquisa() {
        if (pesquisa == 1) {
            int linha = jTbl_Pessoas.getSelectedRow();

            try {
                jTxt_Id.setText(jTbl_Pessoas.getValueAt(linha, 0).toString());
                jTxt_Nome.setText(jTbl_Pessoas.getValueAt(linha, 1).toString());
                jFtxt_CNPJ.setText(jTbl_Pessoas.getValueAt(linha, 2).toString());
                jTxtCidade.setText(jTbl_Pessoas.getValueAt(linha, 3).toString());
                jTxtUF.setText(jTbl_Pessoas.getValueAt(linha, 4).toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Ops, algum campo não foi encontrado! " + e);
            }

        } else if (pesquisa == 2) {
            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtUF.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());
            jTxtCidade.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());

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
        jPanel1 = new javax.swing.JPanel();
        jTxtPesquisa_Multi = new javax.swing.JTextField();
        jBtnConfirmar = new javax.swing.JButton();
        jBtnCancelar = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTblConsulta_Multi = new javax.swing.JTable();
        jPnlCabeçalho = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLblStatus = new javax.swing.JLabel();
        jPnlFormulários = new javax.swing.JPanel();
        jLbl_Id = new javax.swing.JLabel();
        jTxt_Id = new javax.swing.JTextField();
        jLbl_CNPJ = new javax.swing.JLabel();
        jFtxt_CNPJ = new javax.swing.JFormattedTextField();
        jLbl_Nome = new javax.swing.JLabel();
        jTxt_Nome = new javax.swing.JTextField();
        jLbl_Cidade = new javax.swing.JLabel();
        jBtn_Gravar = new javax.swing.JButton();
        jBtn_Cancelar = new javax.swing.JButton();
        jBtn_Incluir = new javax.swing.JButton();
        jBtn_Editar = new javax.swing.JButton();
        jTxt_Pesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTbl_Pessoas = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jTxtUF = new javax.swing.JTextField();
        jTxtCidade = new javax.swing.JTextField();
        jBtnPesquisaCidade = new javax.swing.JButton();

        jPesquisar.setTitle("Pesquisar");
        jPesquisar.setMinimumSize(new java.awt.Dimension(400, 300));

        jTxtPesquisa_Multi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtPesquisa_Multi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxtPesquisa_MultiKeyReleased(evt);
            }
        });

        jBtnConfirmar.setText("É esse aqui!!");
        jBtnConfirmar.setEnabled(false);
        jBtnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnConfirmarActionPerformed(evt);
            }
        });

        jBtnCancelar.setText("Falha no engano :(");
        jBtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 128, Short.MAX_VALUE)
                        .addComponent(jBtnCancelar)
                        .addGap(18, 18, 18)
                        .addComponent(jBtnConfirmar))
                    .addComponent(jTxtPesquisa_Multi)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTxtPesquisa_Multi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnConfirmar)
                    .addComponent(jBtnCancelar))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPesquisarLayout = new javax.swing.GroupLayout(jPesquisar.getContentPane());
        jPesquisar.getContentPane().setLayout(jPesquisarLayout);
        jPesquisarLayout.setHorizontalGroup(
            jPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPesquisarLayout.setVerticalGroup(
            jPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        setTitle("Cadastro de Pessoas");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pessoas2.png"))); // NOI18N
        jLabel1.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Manutenção");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel14.setText("de Pessoas");

        jLblStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLblStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPnlCabeçalhoLayout = new javax.swing.GroupLayout(jPnlCabeçalho);
        jPnlCabeçalho.setLayout(jPnlCabeçalhoLayout);
        jPnlCabeçalhoLayout.setHorizontalGroup(
            jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(315, Short.MAX_VALUE))
                    .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPnlCabeçalhoLayout.setVerticalGroup(
            jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPnlFormulários.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPnlFormuláriosKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPnlFormuláriosKeyReleased(evt);
            }
        });

        jLbl_Id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_Id.setText("Id");

        jTxt_Id.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxt_Id.setEnabled(false);

        jLbl_CNPJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_CNPJ.setText("Inscrição Federal");

        jFtxt_CNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        jFtxt_CNPJ.setEnabled(false);
        jFtxt_CNPJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLbl_Nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_Nome.setText("Nome");

        jTxt_Nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxt_Nome.setEnabled(false);

        jLbl_Cidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_Cidade.setText("Cidade");

        jBtn_Gravar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Gravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/accept.png"))); // NOI18N
        jBtn_Gravar.setText("Gravar");
        jBtn_Gravar.setEnabled(false);
        jBtn_Gravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_GravarActionPerformed(evt);
            }
        });

        jBtn_Cancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        jBtn_Cancelar.setText("Cancelar");
        jBtn_Cancelar.setEnabled(false);
        jBtn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_CancelarActionPerformed(evt);
            }
        });

        jBtn_Incluir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Incluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jBtn_Incluir.setText("Incluir");
        jBtn_Incluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_IncluirActionPerformed(evt);
            }
        });

        jBtn_Editar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtn_Editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/table_edit.png"))); // NOI18N
        jBtn_Editar.setText("Editar");
        jBtn_Editar.setEnabled(false);
        jBtn_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtn_EditarActionPerformed(evt);
            }
        });

        jTxt_Pesquisar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxt_Pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxt_PesquisarActionPerformed(evt);
            }
        });
        jTxt_Pesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTxt_PesquisarKeyReleased(evt);
            }
        });

        jTbl_Pessoas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTbl_Pessoas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTbl_PessoasMouseClicked(evt);
            }
        });
        jTbl_Pessoas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTbl_PessoasKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTbl_Pessoas);

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N

        jTxtUF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtUF.setEnabled(false);

        jTxtCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtCidade.setEnabled(false);

        jBtnPesquisaCidade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaCidade.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaCidade.setEnabled(false);
        jBtnPesquisaCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaCidadeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlFormuláriosLayout = new javax.swing.GroupLayout(jPnlFormulários);
        jPnlFormulários.setLayout(jPnlFormuláriosLayout);
        jPnlFormuláriosLayout.setHorizontalGroup(
            jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlFormuláriosLayout.createSequentialGroup()
                        .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlFormuláriosLayout.createSequentialGroup()
                                .addComponent(jLbl_Nome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxt_Nome))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlFormuláriosLayout.createSequentialGroup()
                                .addComponent(jLbl_Id)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(122, 122, 122)
                                .addComponent(jLbl_CNPJ)
                                .addGap(18, 18, 18)
                                .addComponent(jFtxt_CNPJ))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlFormuláriosLayout.createSequentialGroup()
                                .addComponent(jLbl_Cidade)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxtUF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnPesquisaCidade)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxt_Pesquisar)))
                        .addGap(18, 18, 18)
                        .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtn_Incluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtn_Editar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtn_Cancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtn_Gravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPnlFormuláriosLayout.setVerticalGroup(
            jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_Id)
                    .addComponent(jLbl_CNPJ)
                    .addComponent(jTxt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFtxt_CNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Incluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_Nome)
                    .addComponent(jTxt_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Editar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_Cidade)
                    .addComponent(jBtn_Cancelar)
                    .addComponent(jTxtUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisaCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxt_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jBtn_Gravar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPnlCabeçalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPnlFormulários, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPnlCabeçalho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPnlFormulários, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtn_GravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_GravarActionPerformed
        if (!"".equals(jTxt_Nome.getText())
                && !"".equals(jFtxt_CNPJ.getText())
                && !"".equals(jTxtCidade.getText().toString())) {

            String id = jTxt_Id.getText();
            String nome = jTxt_Nome.getText();
            String cnpj = jFtxt_CNPJ.getText();
            String uf = jTxtUF.getText();
            String cidade = jTxtCidade.getText().toString();
            cn.conecta();
            if (jLblStatus.getText() == "INCLUINDO") {
                String sql = "INSERT INTO CAD_PESSOAS (NOME, INSCRICAO_FEDERAL, CIDADE, UF) "
                        + "VALUES ('" + nome + "','" + cnpj + "','" + cidade + "','" + uf + "')";
                cn.executeAtualizacao(sql);
            } else {
                String sql = "UPDATE CAD_PESSOAS SET NOME = '" + nome + "', "
                        + "INSCRICAO_FEDERAL = '" + cnpj + "', CIDADE = '" + cidade + "', "
                        + "UF = '" + uf + "'"
                        + "WHERE id = " + Integer.parseInt(jTxt_Id.getText());
                cn.executeAtualizacao(sql);
            }
            cn.desconecta();
            jTxt_Id.setText("");
            jTxt_Nome.setText("");
            jTxt_Nome.setEnabled(false);
            jFtxt_CNPJ.setText("");
            jLblStatus.setText("");
            jTxt_Pesquisar.setText("");
            jTxtUF.setText("");
            jTxtCidade.setText("");
            jFtxt_CNPJ.setEnabled(false);
            jTxt_Pesquisar.setEnabled(true);
            jBtn_Editar.setEnabled(false);
            jBtn_Cancelar.setEnabled(false);
            jBtn_Gravar.setEnabled(false);
            jBtnPesquisaCidade.setEnabled(false);
            jBtn_Incluir.setEnabled(true);
            MontaLista();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Algum campo está em Branco.");
        }
    }//GEN-LAST:event_jBtn_GravarActionPerformed

    private void jBtn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_CancelarActionPerformed
        jTxt_Id.setText("");
        jTxt_Nome.setText("");
        jTxt_Nome.setEnabled(false);
        jFtxt_CNPJ.setText("");
        jFtxt_CNPJ.setEnabled(false);
        jTxt_Pesquisar.setText("");
        jTxt_Pesquisar.setEnabled(true);
        jBtnPesquisaCidade.setEnabled(false);
        jBtn_Editar.setEnabled(false);
        jBtn_Cancelar.setEnabled(false);
        jBtn_Gravar.setEnabled(false);
        jBtn_Incluir.setEnabled(true);
        jLblStatus.setText("");
        MontaLista();
        // TODO add your handling code here:

    }//GEN-LAST:event_jBtn_CancelarActionPerformed

    private void jBtn_IncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_IncluirActionPerformed
        jTxt_Id.setText("");
        jTxt_Nome.setText("");
        jTxt_Nome.setEnabled(true);
        jFtxt_CNPJ.setText("");
        jFtxt_CNPJ.setEnabled(true);
        jTxt_Pesquisar.setText("");
        jTxt_Pesquisar.setEnabled(false);
        jBtnPesquisaCidade.setEnabled(true);
        jBtn_Cancelar.setEnabled(true);
        jBtn_Gravar.setEnabled(true);
        jLblStatus.setText("INCLUINDO");
        jBtn_Incluir.setEnabled(false);

        // TODO add your handling code here:
    }//GEN-LAST:event_jBtn_IncluirActionPerformed

    private void jBtn_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_EditarActionPerformed
        jTxt_Nome.setEnabled(true);
        jFtxt_CNPJ.setEnabled(true);
        jTxt_Pesquisar.setEnabled(false);
        jBtnPesquisaCidade.setEnabled(true);
        jLblStatus.setText("ALTERANDO");
        jBtn_Cancelar.setEnabled(true);
        jBtn_Gravar.setEnabled(true);
        jBtn_Editar.setEnabled(false);
    }//GEN-LAST:event_jBtn_EditarActionPerformed

    private void jTxt_PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxt_PesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_PesquisarActionPerformed

    private void jTxt_PesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxt_PesquisarKeyReleased
        MontaLista();        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_PesquisarKeyReleased

    private void jTbl_PessoasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTbl_PessoasMouseClicked
        pesquisa = 1;
        jBtn_Editar.setEnabled(true);
        IncluiPesquisa();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTbl_PessoasMouseClicked

    private void jTbl_PessoasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTbl_PessoasKeyReleased
        IncluiPesquisa();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTbl_PessoasKeyReleased

    private void jPnlFormuláriosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPnlFormuláriosKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPnlFormuláriosKeyPressed

    private void jPnlFormuláriosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPnlFormuláriosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jPnlFormuláriosKeyReleased

    private void jTxtPesquisa_MultiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPesquisa_MultiKeyReleased
        ListaCidades();
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPesquisa_MultiKeyReleased

    private void jBtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfirmarActionPerformed
        IncluiPesquisa();
        jPesquisar.setVisible(false);// TODO add your handling code here:
    }//GEN-LAST:event_jBtnConfirmarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        jPesquisar.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jTblConsulta_MultiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblConsulta_MultiMouseClicked
        jBtnConfirmar.setEnabled(true);    // TODO add your handling code here:
    }//GEN-LAST:event_jTblConsulta_MultiMouseClicked

    private void jTblConsulta_MultiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblConsulta_MultiMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTblConsulta_MultiMouseEntered

    private void jBtnPesquisaCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaCidadeActionPerformed
        pesquisa = 2;

        jPesquisar.setVisible(true);
        ListaCidades();
    }//GEN-LAST:event_jBtnPesquisaCidadeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnConfirmar;
    private javax.swing.JButton jBtnPesquisaCidade;
    private javax.swing.JButton jBtn_Cancelar;
    private javax.swing.JButton jBtn_Editar;
    private javax.swing.JButton jBtn_Gravar;
    private javax.swing.JButton jBtn_Incluir;
    private javax.swing.JFormattedTextField jFtxt_CNPJ;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLblStatus;
    private javax.swing.JLabel jLbl_CNPJ;
    private javax.swing.JLabel jLbl_Cidade;
    private javax.swing.JLabel jLbl_Id;
    private javax.swing.JLabel jLbl_Nome;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFrame jPesquisar;
    private javax.swing.JPanel jPnlCabeçalho;
    private javax.swing.JPanel jPnlFormulários;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTblConsulta_Multi;
    private javax.swing.JTable jTbl_Pessoas;
    private javax.swing.JTextField jTxtCidade;
    private javax.swing.JTextField jTxtPesquisa_Multi;
    private javax.swing.JTextField jTxtUF;
    private javax.swing.JTextField jTxt_Id;
    private javax.swing.JTextField jTxt_Nome;
    private javax.swing.JTextField jTxt_Pesquisar;
    // End of variables declaration//GEN-END:variables
}
