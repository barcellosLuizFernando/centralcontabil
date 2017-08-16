/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import buscaCEP.CEP;
import buscaCEP.BuscaCEP;
import buscaCNPJ.BuscaCNPJ;
import buscaCNPJ.Empresa;
import ferramenta.FiltrarTabela;
import ferramenta.FormatarString;
import ferramenta.ValidaInscricao;
import java.awt.Dimension;
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
    private final ConexaoMySQL cn = new ConexaoMySQL();
    private FormatarString format = new FormatarString();
    private ValidaInscricao valid = new ValidaInscricao();
    private CEP cep;
    private BuscaCNPJ cnpj = new BuscaCNPJ();
    private Empresa pessoa;
    //private MaskFormatter cnpj;
    //private MaskFormatter cpf;

    public Cad_Pessoas() {

        /*try {
            cnpj = new javax.swing.text.MaskFormatter("##.###.###/####-##");
            cpf = new javax.swing.text.MaskFormatter("###.###.###-##");

        } catch (Exception e) {
        }*/
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
            String inscricaoFederal = "";
            //setMask(jTbl_Pessoas.getValueAt(linha, 2).toString().length());

            try {
                jTxt_Id.setText(jTbl_Pessoas.getValueAt(linha, 0).toString());
                jTxt_Nome.setText(jTbl_Pessoas.getValueAt(linha, 1).toString());

                if (jTbl_Pessoas.getValueAt(linha, 2).toString().length() > 11) {
                    System.out.println("Formatando por CNPJ.");

                    for (int i = 0; i < (14 - jTbl_Pessoas.getValueAt(linha, 2).toString().length()); i++) {
                        inscricaoFederal += "0";
                    }
                    inscricaoFederal += jTbl_Pessoas.getValueAt(linha, 2).toString();
                    inscricaoFederal = format.FormatarString(inscricaoFederal, 1);

                } else {
                    System.out.println("Formatando por CPF.");

                    for (int i = 0; i < (11 - jTbl_Pessoas.getValueAt(linha, 2).toString().length()); i++) {
                        inscricaoFederal += "0";
                    }
                    inscricaoFederal += jTbl_Pessoas.getValueAt(linha, 2).toString();
                    inscricaoFederal = format.FormatarString(inscricaoFederal, 2);
                }

                jTxt_CNPJ.setText(inscricaoFederal);

                jTxtCidade.setText(jTbl_Pessoas.getValueAt(linha, 3).toString());
                jTxtUF.setText(jTbl_Pessoas.getValueAt(linha, 4).toString());

                String sql = "SELECT * FROM cad_pessoas WHERE id = '" + jTxt_Id.getText() + "';";

                if (cn.conecta()) {
                    try {
                        cn.executeConsulta(sql);
                        while (cn.rs.next()) {
                            jTxtCEP.setText(cn.rs.getString("cep"));
                            jTxtEndereco.setText(cn.rs.getString("endereco"));
                            jTxtNumero.setText(cn.rs.getString("nro"));
                            jTxtBairro.setText(cn.rs.getString("bairro"));
                            jTxt_IE.setText(cn.rs.getString("inscest_rg"));
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    } finally {
                        cn.desconecta();
                    }
                }

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
        jTxt_CNPJ = new ferramenta.JTextFieldSomenteNumeros();
        jLbl_Cidade1 = new javax.swing.JLabel();
        jTxtCEP = new ferramenta.JTextFieldSomenteNumeros();
        jLbl_Cidade2 = new javax.swing.JLabel();
        jTxtEndereco = new javax.swing.JTextField();
        jLbl_Cidade3 = new javax.swing.JLabel();
        jTxtBairro = new javax.swing.JTextField();
        jLbl_Cidade4 = new javax.swing.JLabel();
        jTxtNumero = new javax.swing.JTextField();
        jTxt_IE = new ferramenta.JTextFieldSomenteNumeros();
        jLbl_CNPJ1 = new javax.swing.JLabel();

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
                        .addContainerGap())
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

        jTxt_CNPJ.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxt_CNPJ.setEnabled(false);
        jTxt_CNPJ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxt_CNPJFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxt_CNPJFocusLost(evt);
            }
        });
        jTxt_CNPJ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTxt_CNPJMouseClicked(evt);
            }
        });

        jLbl_Cidade1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_Cidade1.setText("CEP");

        jTxtCEP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtCEP.setEnabled(false);
        jTxtCEP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtCEPFocusLost(evt);
            }
        });

        jLbl_Cidade2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_Cidade2.setText("Endereço");

        jTxtEndereco.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtEndereco.setEnabled(false);
        jTxtEndereco.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtEnderecoFocusLost(evt);
            }
        });

        jLbl_Cidade3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_Cidade3.setText("Bairro");

        jTxtBairro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtBairro.setEnabled(false);
        jTxtBairro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtBairroFocusLost(evt);
            }
        });

        jLbl_Cidade4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_Cidade4.setText("Número");

        jTxtNumero.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNumero.setEnabled(false);
        jTxtNumero.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtNumeroFocusLost(evt);
            }
        });

        jTxt_IE.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxt_IE.setEnabled(false);
        jTxt_IE.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTxt_IEFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxt_IEFocusLost(evt);
            }
        });

        jLbl_CNPJ1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLbl_CNPJ1.setText("Inscrição Estadual");

        javax.swing.GroupLayout jPnlFormuláriosLayout = new javax.swing.GroupLayout(jPnlFormulários);
        jPnlFormulários.setLayout(jPnlFormuláriosLayout);
        jPnlFormuláriosLayout.setHorizontalGroup(
            jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlFormuláriosLayout.createSequentialGroup()
                        .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlFormuláriosLayout.createSequentialGroup()
                                .addComponent(jLbl_Nome)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxt_Nome))
                            .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxt_Pesquisar))
                            .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                                        .addComponent(jLbl_Id)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLbl_CNPJ1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxt_IE, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLbl_CNPJ)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxt_CNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPnlFormuláriosLayout.createSequentialGroup()
                                        .addComponent(jLbl_Cidade1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxtCEP)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLbl_Cidade)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxtUF, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTxtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jBtnPesquisaCidade))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlFormuláriosLayout.createSequentialGroup()
                                        .addComponent(jLbl_Cidade2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLbl_Cidade4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLbl_Cidade3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTxtBairro)))))
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
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLbl_CNPJ1)
                        .addComponent(jTxt_IE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLbl_Id)
                        .addComponent(jLbl_CNPJ)
                        .addComponent(jTxt_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtn_Incluir)
                        .addComponent(jTxt_CNPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLbl_Nome)
                    .addComponent(jTxt_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtn_Editar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnPesquisaCidade, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLbl_Cidade)
                        .addComponent(jBtn_Cancelar)
                        .addComponent(jTxtUF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLbl_Cidade1)
                        .addComponent(jTxtCEP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLbl_Cidade4)
                        .addComponent(jTxtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLbl_Cidade3))
                    .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLbl_Cidade2)
                        .addComponent(jTxtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnlFormuláriosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxt_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jBtn_Gravar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
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
                && !"".equals(jTxt_CNPJ.getText())
                && !"".equals(jTxtCidade.getText().toString())
                && validaInscricao(1)) {

            String id = jTxt_Id.getText();
            String nome = jTxt_Nome.getText();
            String cnpj = formataInscricao(jTxt_CNPJ.getText());
            String uf = jTxtUF.getText();
            String cidade = jTxtCidade.getText().toString();
            String endereco = jTxtEndereco.getText();
            String numero = jTxtNumero.getText();
            String bairro = jTxtBairro.getText();
            String cep = jTxtCEP.getText().replace(".", "").replace("-", "").replace("/", "");
            String ie = jTxt_IE.getText();
            String sql;

            if (cn.conecta()) {
                try {
                    if (jLblStatus.getText() == "INCLUINDO") {
                        sql = "INSERT INTO CAD_PESSOAS (NOME, INSCRICAO_FEDERAL, CIDADE, UF, "
                                + "ENDERECO, NRO, BAIRRO, CEP, INSCEST_RG) "
                                + "VALUES ('" + nome + "','" + cnpj + "','" + cidade + "',"
                                + "'" + uf + "','" + endereco + "','" + numero + "',"
                                + "'" + bairro + "','" + cep + "','" + ie + "')";
                    } else {
                        sql = "UPDATE CAD_PESSOAS SET NOME = '" + nome + "', "
                                + "INSCRICAO_FEDERAL = '" + cnpj + "', CIDADE = '" + cidade + "', "
                                + "UF = '" + uf + "',ENDERECO = '" + endereco + "',"
                                + "NRO = '" + numero + "',BAIRRO = '" + bairro + "',"
                                + "CEP = '" + cep + "',INSCEST_RG = '" + ie + "'"
                                + "WHERE id = " + Integer.parseInt(jTxt_Id.getText());
                    }
                    if (cn.executeAtualizacao(sql)) {
                        desabilitaEdicao();
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Não foi possível executar o comando desejado. ");
                } finally {
                    cn.desconecta();
                    MontaLista();
                }
            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Algum campo está em Branco.");
        }
    }//GEN-LAST:event_jBtn_GravarActionPerformed

    private void jBtn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_CancelarActionPerformed
        desabilitaEdicao();
        MontaLista();
        // TODO add your handling code here:

    }//GEN-LAST:event_jBtn_CancelarActionPerformed

    private void jBtn_IncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_IncluirActionPerformed
        habilitaEdicao();

        // TODO add your handling code here:
    }//GEN-LAST:event_jBtn_IncluirActionPerformed

    private void jBtn_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtn_EditarActionPerformed
        jTxt_Nome.setEnabled(true);
        jTxt_IE.setEnabled(true);
        jTxtEndereco.setEnabled(true);
        jTxt_Pesquisar.setEnabled(false);
        jBtnPesquisaCidade.setEnabled(true);
        jTxtEndereco.setEnabled(true);
        jTxtNumero.setEnabled(true);
        jTxtBairro.setEnabled(true);
        jTxtCEP.setEnabled(true);

        jLblStatus.setText("ALTERANDO");
        jBtn_Cancelar.setEnabled(true);
        jBtn_Gravar.setEnabled(true);
        jBtn_Editar.setEnabled(false);
    }//GEN-LAST:event_jBtn_EditarActionPerformed

    private void jTxt_PesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxt_PesquisarKeyReleased
        new FiltrarTabela(jTbl_Pessoas, jTxt_Pesquisar.getText());
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
        new FiltrarTabela(jTblConsulta_Multi, jTxtPesquisa_Multi.getText());
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

    private void jTxt_CNPJFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxt_CNPJFocusLost

        boolean resposta = true;

        resposta = validaInscricao(1);

        if (resposta == false) {
            JOptionPane.showMessageDialog(this, "Número da Inscrição Federal é inválido!");
            jTxt_CNPJ.grabFocus();
        } else {

            if (jTxt_CNPJ.getText().length() == 11) {
                jTxt_CNPJ.setText(format.FormatarString(jTxt_CNPJ.getText(), 2));
            } else {
                jTxt_CNPJ.setText(format.FormatarString(jTxt_CNPJ.getText(), 1));

                pesquisaReceitaFederal();

            }
        }


    }//GEN-LAST:event_jTxt_CNPJFocusLost

    private void jTxt_CNPJFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxt_CNPJFocusGained
        jTxt_CNPJ.setText(formataInscricao(jTxt_CNPJ.getText()));
    }//GEN-LAST:event_jTxt_CNPJFocusGained

    private void jTxtCEPFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtCEPFocusLost

        if (!"".equals(jTxtCEP.getText())) {

            int pesquisa = JOptionPane.showConfirmDialog(this, "Deseja consultar os dados nos Correios?", "Cadastro automático", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (pesquisa == 0) {

                try {
                    cep = new BuscaCEP().obtemPorNumeroCEP(jTxtCEP.getText());
                    jTxtCidade.setText(cep.getLocalidade());
                    jTxtUF.setText(cep.getUf());
                    jTxtEndereco.setText(cep.getLogradouro());
                    jTxtBairro.setText(cep.getBairro());

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
                    jTxtCidade.setText("");
                    jTxtUF.setText("");
                    jTxtEndereco.setText("");
                    jTxtBairro.setText("");
                    jTxtNumero.setText("");
                    jTxtCEP.grabFocus();
                }
            }
        }
    }//GEN-LAST:event_jTxtCEPFocusLost

    private void jTxtEnderecoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtEnderecoFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtEnderecoFocusLost

    private void jTxtBairroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtBairroFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtBairroFocusLost

    private void jTxtNumeroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNumeroFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtNumeroFocusLost

    private void jTxt_IEFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxt_IEFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_IEFocusGained

    private void jTxt_IEFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxt_IEFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxt_IEFocusLost

    private void jTxt_CNPJMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTxt_CNPJMouseClicked
        if (evt.getClickCount() > 1) {
            pesquisaReceitaFederal();
        }
    }//GEN-LAST:event_jTxt_CNPJMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnConfirmar;
    private javax.swing.JButton jBtnPesquisaCidade;
    private javax.swing.JButton jBtn_Cancelar;
    private javax.swing.JButton jBtn_Editar;
    private javax.swing.JButton jBtn_Gravar;
    private javax.swing.JButton jBtn_Incluir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLblStatus;
    private javax.swing.JLabel jLbl_CNPJ;
    private javax.swing.JLabel jLbl_CNPJ1;
    private javax.swing.JLabel jLbl_Cidade;
    private javax.swing.JLabel jLbl_Cidade1;
    private javax.swing.JLabel jLbl_Cidade2;
    private javax.swing.JLabel jLbl_Cidade3;
    private javax.swing.JLabel jLbl_Cidade4;
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
    private javax.swing.JTextField jTxtBairro;
    private javax.swing.JTextField jTxtCEP;
    private javax.swing.JTextField jTxtCidade;
    private javax.swing.JTextField jTxtEndereco;
    private javax.swing.JTextField jTxtNumero;
    private javax.swing.JTextField jTxtPesquisa_Multi;
    private javax.swing.JTextField jTxtUF;
    private javax.swing.JTextField jTxt_CNPJ;
    private javax.swing.JTextField jTxt_IE;
    private javax.swing.JTextField jTxt_Id;
    private javax.swing.JTextField jTxt_Nome;
    private javax.swing.JTextField jTxt_Pesquisar;
    // End of variables declaration//GEN-END:variables

    private void setMask(int numeros) {

        System.out.println("Caracteres: " + numeros);

        try {
            if (numeros > 11) {

                System.out.println("Formatação por CNPJ.");
            } else {

                System.out.println("Formatação por CPF.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao formatar campo Inscrição Federal:\n" + e);
        }

    }

    private String formataInscricao(String text) {

        return jTxt_CNPJ.getText().replace(".", "").replace("/", "").replace("-", "");
    }

    private boolean validaInscricao(int i) {
        boolean resposta = false;

        switch (i) {
            case 1:

                String inscricao = formataInscricao(jTxt_CNPJ.getText());

                if (inscricao.length() == 11) {
                    resposta = valid.isCPF(inscricao);
                } else {
                    resposta = valid.isCNPJ(inscricao);
                }
                break;
        }

        return resposta;
    }

    private void habilitaEdicao() {
        jTxt_Id.setText("");
        jTxt_Nome.setText("");
        jTxt_Nome.setEnabled(true);
        jTxt_CNPJ.setText("");
        jTxt_CNPJ.setEnabled(true);
        jTxt_Pesquisar.setText("");
        jTxt_Pesquisar.setEnabled(false);
        jBtnPesquisaCidade.setEnabled(true);
        jBtn_Cancelar.setEnabled(true);
        jBtn_Gravar.setEnabled(true);
        jLblStatus.setText("INCLUINDO");
        jBtn_Incluir.setEnabled(false);
        jTxtCEP.setEnabled(true);
        jTxtEndereco.setEnabled(true);
        jTxtBairro.setEnabled(true);
        jTxtNumero.setEnabled(true);
        jTxt_IE.setEnabled(true);

        jTxt_IE.grabFocus();

    }

    private void desabilitaEdicao() {

        jTxt_Id.setText("");
        jTxt_Nome.setText("");
        jTxt_Nome.setEnabled(false);
        jTxt_CNPJ.setText("");
        jTxtCidade.setText("");
        jTxtUF.setText("");
        jTxtEndereco.setText("");
        jTxtBairro.setText("");
        jTxtCEP.setText("");
        jTxtNumero.setText("");
        jTxt_CNPJ.setEnabled(false);
        jTxt_Pesquisar.setText("");
        jTxt_Pesquisar.setEnabled(true);
        jBtnPesquisaCidade.setEnabled(false);
        jBtn_Editar.setEnabled(false);
        jBtn_Cancelar.setEnabled(false);
        jBtn_Gravar.setEnabled(false);
        jBtn_Incluir.setEnabled(true);
        jLblStatus.setText("");
        jTxtCEP.setEnabled(false);
        jTxtEndereco.setEnabled(false);
        jTxtBairro.setEnabled(false);
        jTxtNumero.setEnabled(false);
        jTxt_IE.setEnabled(false);

    }

    private void pesquisaReceitaFederal() {
        int pesquisa = JOptionPane.showConfirmDialog(this, "Deseja consultar os dados na Receita Federal?", "Cadastro automático", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (pesquisa == 0) {

            pessoa = cnpj.buscaEmpresa(jTxt_CNPJ.getText());

            jTxt_Nome.setText(pessoa.getNome());
            jTxtCEP.setText(pessoa.getCep());
            jTxtCidade.setText(pessoa.getMunicipio());
            jTxtUF.setText(pessoa.getUf());
            jTxtEndereco.setText(pessoa.getLogradouro());
            jTxtNumero.setText(pessoa.getNumero());
            jTxtBairro.setText(pessoa.getBairro());

        }
    }

}
