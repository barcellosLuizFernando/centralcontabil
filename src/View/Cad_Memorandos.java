/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import conexoes.ConexaoMySQL;
import ferramenta.JTextFieldSomenteNumeros;
import ferramenta.ColorRender;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.UIManager;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author luiz.barcellos
 */
public final class Cad_Memorandos extends javax.swing.JInternalFrame {

    /**
     * Creates new form NovoJInternalFrame
     */
    ConexaoMySQL cn = new ConexaoMySQL();

    public Cad_Memorandos() {
        initComponents();

        ListaMemorandos();
    }

    int var_consulta;
    int tipo_nota_fiscal; //variável é preenchida no botão Gravar
    String cnpj_exportador = "";

    DefaultTableModel lista = new DefaultTableModel(); //LISTA PESQUISA MULTIPLA
    DefaultTableModel lista_m = new DefaultTableModel(); //LISTA MEMORANDOS
    DefaultTableModel lista_nfr = new DefaultTableModel(); //LISTA NOTAS FISCAIS DE REMESSA
    DefaultTableModel lista_nfv = new DefaultTableModel(); //LISTA NOTAS FISCAIS DE VENDA
    DefaultTableModel lista_nfp = new DefaultTableModel(); //LISTA NOTAS FISCAIS DE VENDA

    DateFormat dateOut = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat dateIn = new SimpleDateFormat("dd/MM/yyyy");

    NumberFormat valorOut = new DecimalFormat("###,###,##0.00", new DecimalFormatSymbols(new Locale("en", "US")));
    NumberFormat valorOutTotal = new DecimalFormat("###,###,##0.##", new DecimalFormatSymbols(new Locale("en", "US")));
    NumberFormat valorOutUnitario = new DecimalFormat("###,###,##0.00", new DecimalFormatSymbols(new Locale("en", "US")));
    NumberFormat valorIn = new DecimalFormat("###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void LimpaCampos() {
        jTxtNroDespacho.setText("");
        jTxtNFPropria.setText("");
        jTxtNro.setText("");
        jTxtValorExportado.setText("");
        jTxtVlrT.setText("");
        jTxtValorUn.setText("");
        jTxtRegistro.setText("");
        jTxtQtd.setText("");
        jTxtNavio.setText("");
        jTxtNfExportacao.setText("");
    }

    public void ValorUnitario() {
        if (!"".equals(jTxtVlrT.getText())
                && !"".equals(jTxtQtd.getText())) {
            Double valor_total = Double.parseDouble(jTxtVlrT.getText().replace(",", "."));
            Double quantidade = Double.parseDouble(jTxtQtd.getText().replace(",", "."));
            Double calculo = valor_total / quantidade;
            String valor_unitario = calculo.toString();
            jTxtValorUn.setText(valor_unitario.replace(".", ","));
        }
    }

    public void HabilitarEdicao() {
        //HABILITAR TABELAS
        jTblRemessa.setEnabled(true);
        //jTblVenda.setEnabled(true);
        jTblMemorandos.setEnabled(false);
        //jTblMemorandos.setVisible(false);

//HABILITAR BOTOES
        jBtnTeste.setEnabled(true);
        jBtnIncluir.setEnabled(false);
        jBtnEditar.setEnabled(false);
        //jBtnAddNF.setEnabled(true);
        jBtnPesquisaPessoas.setEnabled(true);
        jBtnPesquisaDestino.setEnabled(true);
        jBtnPesquisaPaisImportador.setEnabled(true);
        jBtnPesquisaPorto.setEnabled(true);
        jBtnCancelarMem.setEnabled(true);
        jBtnGravar.setEnabled(true);
        jBtnEditar.setEnabled(false);
        jBtnDeletar.setEnabled(false);
        jBtnPesquisaNF.setEnabled(true);
        jBtnExcluiRemessa.setEnabled(true);
        //jBtnExcluiVenda.setEnabled(true);

//HABILITAR CAMPO TEXTO
        jTxtValorUn.setEnabled(true);
        jTxtNro.setEnabled(true);
        jTxtNroDespacho.setEnabled(true);
        jTxtQtd.setEnabled(true);
        jTxtRegistro.setEnabled(true);
        jTxtUnd.setEnabled(true);
        jTxtVlrT.setEnabled(true);
        jTxtNfExportacao.setEnabled(true);
        jTxtNavio.setEnabled(true);
        //jTxtNFPropria.setEnabled(true);
        //jTxtCNPJProprio.setEnabled(true);
        jSDataEmbarque.setEnabled(true);
        jSDataEmissaoMem.setEnabled(true);
        jSDataEmissaoNF.setEnabled(true);
        jCBProduto.setEnabled(true);
        //jCBTipoNF.setEnabled(true);
    }

    public void DesabilitarEdicao() {
        //cnpj_exportador = "";

        //DESABILITAR TABELAS
        jTblRemessa.setEnabled(false);
        jTblMemorandos.setEnabled(true);
        jTblMemorandos.setVisible(true);
        //DESABILITAR BOTOES
        jBtnTeste.setEnabled(false);
        jBtnIncluir.setEnabled(true);
        jBtnEditar.setEnabled(false);
        jBtnAddNF.setEnabled(false);
        jBtnPesquisaPessoas.setEnabled(false);
        jBtnPesquisaDestino.setEnabled(false);
        jBtnPesquisaPaisImportador.setEnabled(false);
        jBtnPesquisaPorto.setEnabled(false);
        jBtnCancelarMem.setEnabled(false);
        jBtnGravar.setEnabled(false);
        jBtnDeletar.setEnabled(false);
        jBtnPesquisaNF.setEnabled(false);
        jBtnAddNF.setEnabled(false);
        jBtnExcluiRemessa.setEnabled(false);
        //DESABILITAR CAMPO TEXTO
        jTxtValorUn.setEnabled(false);
        jTxtNro.setEnabled(false);
        jTxtNroDespacho.setEnabled(false);
        jTxtQtd.setEnabled(false);
        jTxtRegistro.setEnabled(false);
        jTxtUnd.setEnabled(false);
        jTxtVlrT.setEnabled(false);
        jTxtNfExportacao.setEnabled(false);
        jTxtNavio.setEnabled(false);
        jTxtNFPropria.setEnabled(false);
        jTxtCNPJProprio.setEnabled(false);
        jSDataEmbarque.setEnabled(false);
        jSDataEmissaoMem.setEnabled(false);
        jSDataEmissaoNF.setEnabled(false);
        jCBProduto.setEnabled(false);
        jCBTipoNF.setEnabled(false);
        //LIMPAR LABEL
        jLblStatus.setText("");
        jLblIdMem.setText("");
    }

    public void ListaPessoas() {
        jTxtPesquisa_Multi.grabFocus();
        if (!"".equals(jTxtPesquisa_Multi.getText())) {
            lista.setColumnCount(0);  //limpa a tabela
            lista.setRowCount(0);     //limpa a tabela
            cn.conecta();
            String sql = "SELECT * FROM cad_pessoas "
                    + "WHERE UPPER(nome) "
                    + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                    + "or inscricao_federal "
                    + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                    + " ORDER BY nome";

            cn.executeConsulta(sql);

            lista.addColumn("Id");
            lista.addColumn("Nome");
            lista.addColumn("CNPJ");
            lista.addColumn("Cidade");

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("inscricao_federal"),
                        cn.rs.getString("cidade")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblConsulta_Multi.setModel(lista);
            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(30);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(30);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(30);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(30);

        } else { //Configuração para quando não há texto no campo pesquisa
            lista.setColumnCount(0);  //limpa a tabela
            lista.setRowCount(0);     //limpa a tabela
            cn.conecta();
            String sql = "SELECT * FROM cad_pessoas "
                    + " ORDER BY nome";

            cn.executeConsulta(sql);

            lista.addColumn("Id");
            lista.addColumn("Nome");
            lista.addColumn("CNPJ");
            lista.addColumn("Cidade");

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("inscricao_federal"),
                        cn.rs.getString("cidade")});

                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblConsulta_Multi.setModel(lista);

            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(30);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(30);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(30);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(30);

            //Configuração dos botões, quando não há pesquisa
            jBtnConfirmar.setEnabled(true);
        }
    }

    public void ListaPaises() {
        jTxtPesquisa_Multi.grabFocus();
        if (!"".equals(jTxtPesquisa_Multi.getText())) {
            lista.setColumnCount(0);  //limpa a tabela
            lista.setRowCount(0);     //limpa a tabela
            cn.conecta();
            String sql = "SELECT * FROM cad_paises "
                    + "WHERE UPPER(nome) "
                    + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                    + " ORDER BY nome";

            cn.executeConsulta(sql);

            lista.addColumn("Id");
            lista.addColumn("Nome");
            lista.addColumn("Siscomex");

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("cod_siscomex")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblConsulta_Multi.setModel(lista);
            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

        } else { //Configuração para quando não há texto no campo pesquisa
            lista.setColumnCount(0);  //limpa a tabela
            lista.setRowCount(0);     //limpa a tabela
            cn.conecta();
            String sql = "SELECT * FROM cad_paises "
                    + " ORDER BY nome";

            cn.executeConsulta(sql);

            lista.addColumn("Id");
            lista.addColumn("Nome");
            lista.addColumn("Siscomex");

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("cod_siscomex")});

                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblConsulta_Multi.setModel(lista);

            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

            //Configuração dos botões, quando não há pesquisa
            jBtnConfirmar.setEnabled(true);
        }
    }

    public void ListaCidades() {
        jTxtPesquisa_Multi.grabFocus();
        if (!"".equals(jTxtPesquisa_Multi.getText())) {
            lista.setColumnCount(0);  //limpa a tabela
            lista.setRowCount(0);     //limpa a tabela
            cn.conecta();
            String sql = "SELECT * FROM cad_munic "
                    + "WHERE UPPER(nome) "
                    + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                    + " ORDER BY nome";

            cn.executeConsulta(sql);

            lista.addColumn("Id");
            lista.addColumn("Nome");
            lista.addColumn("UF");
            lista.addColumn("IBGE");

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("uf"),
                        cn.rs.getString("cod_ibge")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblConsulta_Multi.setModel(lista);
            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

        } else { //Configuração para quando não há texto no campo pesquisa
            lista.setColumnCount(0);  //limpa a tabela
            lista.setRowCount(0);     //limpa a tabela
            cn.conecta();
            String sql = "SELECT * FROM cad_munic "
                    + " ORDER BY nome";

            cn.executeConsulta(sql);

            lista.addColumn("Id");
            lista.addColumn("Nome");
            lista.addColumn("UF");
            lista.addColumn("IBGE");

            try {
                while (cn.rs.next()) {

                    lista.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("nome"),
                        cn.rs.getString("uf"),
                        cn.rs.getString("cod_ibge")});

                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblConsulta_Multi.setModel(lista);

            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
            jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

            //Configuração dos botões, quando não há pesquisa
            jBtnConfirmar.setEnabled(true);
        }
    }

    public void IncluiPesquisa() {

        if (var_consulta == 1) {
            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtIdExportador.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
            jTxtNomeExportador.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

            cnpj_exportador = jTblConsulta_Multi.getValueAt(linha, 2).toString();

        } else if (var_consulta == 2) {
            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtIdPaisDestino.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
            jTxtNomePaisDestino.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());
        } else if (var_consulta == 3) {
            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtIdPaisImportador.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
            jTxtNomePaisImportador.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());
        } else if (var_consulta == 4) {
            int linha = jTblConsulta_Multi.getSelectedRow();

            jTxtIdPortoEmbarque.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
            jTxtNomePortoEmbarque.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

            jTPnlGuias.setSelectedIndex(1);
            jBtnPesquisaNF.grabFocus();

        } else if (var_consulta == 5) {
            int linha = jTblMemorandos.getSelectedRow();

            try {
                jLblIdMem.setText(jTblMemorandos.getValueAt(linha, 0).toString());
                jTxtIdExportador.setText(jTblMemorandos.getValueAt(linha, 1).toString());
                jTxtNomeExportador.setText(jTblMemorandos.getValueAt(linha, 2).toString());
                jTxtNro.setText(jTblMemorandos.getValueAt(linha, 3).toString());
                jSDataEmissaoMem.setValue(dateIn.parse(jTblMemorandos.getValueAt(linha, 4).toString()));
                jTxtNroDespacho.setText(jTblMemorandos.getValueAt(linha, 5).toString());
                jTxtRegistro.setText(jTblMemorandos.getValueAt(linha, 6).toString());
                jCBProduto.setSelectedItem(jTblMemorandos.getValueAt(linha, 7).toString());
                jTxtQtd.setText(jTblMemorandos.getValueAt(linha, 8).toString());
                jTxtUnd.setText(jTblMemorandos.getValueAt(linha, 9).toString());
                jTxtVlrT.setText(jTblMemorandos.getValueAt(linha, 10).toString().replace(".", ""));
                jTxtValorUn.setText(jTblMemorandos.getValueAt(linha, 11).toString());
                jTxtIdPaisDestino.setText(jTblMemorandos.getValueAt(linha, 12).toString());
                jTxtNomePaisDestino.setText(jTblMemorandos.getValueAt(linha, 13).toString());
                jTxtNfExportacao.setText(jTblMemorandos.getValueAt(linha, 14).toString());
                jSDataEmissaoNF.setValue(dateIn.parse(jTblMemorandos.getValueAt(linha, 15).toString()));
                jSDataEmbarque.setValue(dateIn.parse(jTblMemorandos.getValueAt(linha, 16).toString()));
                jTxtIdPaisImportador.setText(jTblMemorandos.getValueAt(linha, 17).toString());
                jTxtNomePaisImportador.setText(jTblMemorandos.getValueAt(linha, 18).toString());
                jTxtNavio.setText(jTblMemorandos.getValueAt(linha, 19).toString());
                jTxtIdPortoEmbarque.setText(jTblMemorandos.getValueAt(linha, 20).toString());
                jTxtNomePortoEmbarque.setText(jTblMemorandos.getValueAt(linha, 21).toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        } else if (var_consulta == 6) {
            DefaultTableModel modelr = (DefaultTableModel) jTblRemessa.getModel();
            jTblRemessa.getColumnModel().getColumn(0).setMaxWidth(0);
            jTblRemessa.getColumnModel().getColumn(0).setMinWidth(0);
            jTblRemessa.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jTblRemessa.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

            for (int x = jTblConsulta_Multi.getSelectedRowCount(); x != 0; x--) {
                int linha = jTblConsulta_Multi.getSelectedRow();
                String nf_propria = jTblConsulta_Multi.getValueAt(linha, 2).toString();
                String cnpj_proprio = jTblConsulta_Multi.getValueAt(linha, 3).toString();
                String quantidade = jTblConsulta_Multi.getValueAt(linha, 6).toString();

                modelr.addRow(new String[]{"", nf_propria, cnpj_proprio, quantidade});

                jTblConsulta_Multi.removeRowSelectionInterval(linha, linha);
            }

            SomaNotas();
        }
    }

    private void ListaMemorandos() {

        jTPnlGuias.setSelectedIndex(0);
        cn.conecta();

        if ("INCLUINDO".equals(jLblStatus.getText())) {
            lista_m.setColumnCount(0);  //limpa a tabela
            lista_m.setRowCount(0);     //limpa a tabela
            jTblMemorandos.setModel(lista_m);

        } else { //Configuração para quando não há texto no campo pesquisa
            lista_m.setColumnCount(0);  //limpa a tabela
            lista_m.setRowCount(0);     //limpa a tabela
            String sql = "SELECT * FROM cad_memorandos "
                    + " ORDER BY data_emissao";

            cn.executeConsulta(sql);

            lista_m.addColumn("Id");
            lista_m.addColumn("id_exportador");
            lista_m.addColumn("Exportador");
            lista_m.addColumn("Memorando");
            lista_m.addColumn("Emissão");
            lista_m.addColumn("Despacho");
            lista_m.addColumn("Registro");
            lista_m.addColumn("Produto");
            lista_m.addColumn("Quantidade");
            lista_m.addColumn("Unidade");
            lista_m.addColumn("Valor Total");
            lista_m.addColumn("Valor Unitário");
            lista_m.addColumn("id_pais_destino");
            lista_m.addColumn("nome_pais_destino");
            lista_m.addColumn("NF Exportação");
            lista_m.addColumn("Emissão");
            lista_m.addColumn("Embarque");
            lista_m.addColumn("id_pais_importador");
            lista_m.addColumn("nome_pais_importador");
            lista_m.addColumn("navio");
            lista_m.addColumn("id_porto");
            lista_m.addColumn("nome_porto");

            jTblMemorandos.setDefaultRenderer(Object.class, new ColorRender());

            try {
                while (cn.rs.next()) {

                    try {
                        lista_m.addRow(new String[]{
                            cn.rs.getString("id"),
                            cn.rs.getString("id_exportador"),
                            cn.rs.getString("nome_exportador"),
                            cn.rs.getString("numero_memorando"),
                            dateIn.format(cn.rs.getObject("data_emissao")).toString(),
                            cn.rs.getString("numero_despacho"),
                            cn.rs.getString("numero_registro"),
                            cn.rs.getString("produto"),
                            cn.rs.getString("quantidade").replace(".", ","),
                            cn.rs.getString("unidade"),
                            df.format(cn.rs.getDouble("valor_total")),
                            cn.rs.getString("valor_unitario").replace(".", ","),
                            cn.rs.getString("id_pais_destino"),
                            cn.rs.getString("nome_pais_destino"),
                            cn.rs.getString("numero_nf_exportacao"),
                            dateIn.format(cn.rs.getObject("data_emissao_nf")).toString(),
                            dateIn.format(cn.rs.getObject("data_embarque")).toString(),
                            cn.rs.getString("id_pais_importador"),
                            cn.rs.getString("nome_pais_importador"),
                            cn.rs.getString("navio"),
                            cn.rs.getString("id_porto"),
                            cn.rs.getString("nome_porto")});
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }

                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            jTblMemorandos.setModel(lista_m);

            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            jTblMemorandos.getColumnModel().getColumn(0).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(0).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(1).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(1).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(2).setMaxWidth(210);
            jTblMemorandos.getColumnModel().getColumn(2).setMinWidth(210);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(210);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(2).setMinWidth(210);
            jTblMemorandos.getColumnModel().getColumn(3).setMaxWidth(80);
            jTblMemorandos.getColumnModel().getColumn(3).setMinWidth(80);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(80);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(3).setMinWidth(80);
            jTblMemorandos.getColumnModel().getColumn(4).setMaxWidth(100);
            jTblMemorandos.getColumnModel().getColumn(4).setMinWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(4).setMinWidth(100);
            jTblMemorandos.getColumnModel().getColumn(5).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(5).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(6).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(6).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(7).setMaxWidth(100);
            jTblMemorandos.getColumnModel().getColumn(7).setMinWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(7).setMinWidth(100);
            jTblMemorandos.getColumnModel().getColumn(8).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(8).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(8).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(8).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(9).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(9).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(9).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(9).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(10).setMaxWidth(100);
            jTblMemorandos.getColumnModel().getColumn(10).setMinWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(10).setMaxWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(10).setMinWidth(100);
            jTblMemorandos.getColumnModel().getColumn(11).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(11).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(11).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(11).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(12).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(12).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(12).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(12).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(13).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(13).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(13).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(13).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(14).setMaxWidth(100);
            jTblMemorandos.getColumnModel().getColumn(14).setMinWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(14).setMaxWidth(100);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(14).setMinWidth(100);
            jTblMemorandos.getColumnModel().getColumn(15).setMaxWidth(90);
            jTblMemorandos.getColumnModel().getColumn(15).setMinWidth(90);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(15).setMaxWidth(90);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(15).setMinWidth(90);
            jTblMemorandos.getColumnModel().getColumn(16).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(16).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(16).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(16).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(17).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(17).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(17).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(17).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(18).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(18).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(18).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(18).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(19).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(19).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(19).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(19).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(20).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(20).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(20).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(20).setMinWidth(0);
            jTblMemorandos.getColumnModel().getColumn(21).setMaxWidth(0);
            jTblMemorandos.getColumnModel().getColumn(21).setMinWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(21).setMaxWidth(0);
            jTblMemorandos.getTableHeader().getColumnModel().getColumn(21).setMinWidth(0);

            //Configuração dos botões, quando não há pesquisa
        }
        cn.desconecta();
    }

    public void IncluiNotasFiscaisRemessa() {
        String id_exportador = jTxtIdExportador.getText();
        String memorando = jTxtNro.getText();

        lista_nfr.setColumnCount(0);  //limpa a tabela
        lista_nfr.setRowCount(0);     //limpa a tabela
        cn.conecta();
        String sql = "SELECT * FROM cad_memorandos_detalhe "
                + "WHERE id_exportador = '" + id_exportador + "'"
                + "AND numero_memorando = '" + memorando + "'"
                + "AND natureza = 'REMESSA'"
                + " ORDER BY numero_nf";

        cn.executeConsulta(sql);

        lista_nfr.addColumn("Id");
        lista_nfr.addColumn("Número");
        lista_nfr.addColumn("CNPJ");
        lista_nfr.addColumn("Quantidade");

        try {
            while (cn.rs.next()) {

                try {
                    //lista_nfr.addRow();
                    lista_nfr.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("numero_nf"),
                        cn.rs.getString("inscricao_federal"),
                        cn.rs.getString("quantidade"),});
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }

            }
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
        }
        cn.desconecta();
        jTblRemessa.setModel(lista_nfr);
        jTblRemessa.getColumnModel().getColumn(0).setMaxWidth(0);
        jTblRemessa.getColumnModel().getColumn(0).setMinWidth(0);
        jTblRemessa.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jTblRemessa.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
         */

        //Configuração dos botões, quando não há pesquisa
        SomaNotas();
    }

    /*    public void IncluiNotasFiscaisVenda() {
            String id_exportador = jTxtIdExportador.getText();
            String memorando = jTxtNro.getText();
        
            lista_nfv.setColumnCount(0);  //limpa a tabela
            lista_nfv.setRowCount(0);     //limpa a tabela
            cn.conecta();
            String sql = "SELECT * FROM cad_memorandos_detalhe "
                    + "WHERE id_exportador = '" + id_exportador + "'"
                    + "AND numero_memorando = '" + memorando + "'"
                    + "AND natureza = 'VENDA'"
                    + " ORDER BY numero_nf";

            cn.executeConsulta(sql);
            
            lista_nfv.addColumn("Id");
            lista_nfv.addColumn("Número");
            lista_nfv.addColumn("CNPJ");
            lista_nfv.addColumn("Quantidade");
            
            try {
                while (cn.rs.next()) {

                    try{lista_nfv.addRow(new String[]{
                        cn.rs.getString("id"),
                        cn.rs.getString("numero_nf"),
                        cn.rs.getString("inscricao_federal")});
                        } catch (Exception e){
                        JOptionPane.showMessageDialog(null, e);
                    }

                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblVenda.setModel(lista_nfv);
            jTblVenda.getColumnModel().getColumn(0).setMaxWidth(30);
            jTblVenda.getColumnModel().getColumn(0).setMinWidth(30);
            jTblVenda.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(30);
            jTblVenda.getTableHeader().getColumnModel().getColumn(0).setMinWidth(30);
    }*/
    public void ListaNotasPróprias() {
        jTxtPesquisa_Multi.grabFocus();
        try {
            lista_nfp.setColumnCount(0);  //limpa a tabela
            lista_nfp.setRowCount(0);     //limpa a tabela
            lista_nfp.addColumn("Cliente");
            lista_nfp.addColumn("CNPJ Cliente");
            lista_nfp.addColumn("Nota Fiscal");
            lista_nfp.addColumn("CNPJ Próprio");
            lista_nfp.addColumn("Valor");
            lista_nfp.addColumn("Natureza");
            lista_nfp.addColumn("Quantidade");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problema para formar as colunas " + e);
        }

        if (!"".equals(jTxtPesquisa_Multi.getText())) {
            cn.conecta();
            String sql = "SELECT * FROM notas_disponiveis "
                    + "WHERE (UPPER(nome_terceiro) "
                    + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                    + "or numero_nf LIKE '%" + jTxtPesquisa_Multi.getText() + "%')"
                    + "and cnpj_terceiro = " + cnpj_exportador
                    + " ORDER BY nome_terceiro, numero_nf";

            cn.executeConsulta(sql);

            try {
                while (cn.rs.next()) {
                    //lista_nfp.addRow(new Object[]{Boolean.FALSE});
                    lista_nfp.addRow(new String[]{
                        cn.rs.getString("nome_terceiro"),
                        cn.rs.getString("cnpj_terceiro"),
                        cn.rs.getString("numero_nf"),
                        cn.rs.getString("cnpj_proprio"),
                        cn.rs.getString("valor"),
                        cn.rs.getString("natureza"),
                        cn.rs.getString("quantidade")});
                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
            jTblConsulta_Multi.setModel(lista_nfp);
            /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
             */
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
            //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);

        } else { //Configuração para quando não há texto no campo pesquisa
            cn.conecta();
            String sql = "SELECT * FROM notas_disponiveis "
                    + "WHERE cnpj_terceiro = " + cnpj_exportador
                    + " ORDER BY nome_terceiro, numero_nf";

            cn.executeConsulta(sql);

            try {
                while (cn.rs.next()) {
                    lista_nfp.addRow(new String[]{
                        cn.rs.getString("nome_terceiro"),
                        cn.rs.getString("cnpj_terceiro"),
                        cn.rs.getString("numero_nf"),
                        cn.rs.getString("cnpj_proprio"),
                        cn.rs.getString("valor"),
                        cn.rs.getString("natureza"),
                        cn.rs.getString("quantidade")});

                }
            } catch (SQLException ex) {

                JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
            }
            cn.desconecta();
        }
        jTblConsulta_Multi.setModel(lista_nfp);

        /* Abaixo, configuração para ocultar colunas na jTable
             * Porém, quando clica, aparecem os dados para edição e consulta
         */
        //jTbl_Pessoas.getColumnModel().getColumn(2).setMaxWidth(0);
        //jTbl_Pessoas.getColumnModel().getColumn(2).setMinWidth(0);
        //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(0);
        //jTbl_Pessoas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(0);
        //Configuração dos botões, quando não há pesquisa
        jBtnConfirmar.setEnabled(true);
    }

    public void SomaNotas() {
        try {
            Double soma = 0.00;
            Double qtd_memorando = Double.parseDouble(jTxtQtd.getText().replace(",", "."));
            Double qtd_faltante = 0.00;
            String nro_linhas_remessa = "" + jTblRemessa.getRowCount();
            jTxtQtdeNF.setText(nro_linhas_remessa);
            for (int i = 0; i < jTblRemessa.getRowCount(); i++) {
                Double valorAux = Double.parseDouble(jTblRemessa.getValueAt(i, 3).toString());
                soma += valorAux.doubleValue();
                qtd_faltante = qtd_memorando - soma;
                jTxtConfereQtd.setText(String.valueOf(qtd_faltante));
            }
            jTxtValorExportado.setText(String.valueOf(soma));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível calcular as quantidades: " + e);
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
        jTPnlGuias = new javax.swing.JTabbedPane();
        jPnlMemorando = new javax.swing.JPanel();
        jLblNro = new javax.swing.JLabel();
        jTxtNro = new JTextFieldSomenteNumeros();
        jLblData = new javax.swing.JLabel();
        jLblExportador = new javax.swing.JLabel();
        jTxtIdExportador = new javax.swing.JTextField();
        jTxtNomeExportador = new javax.swing.JTextField();
        JlblProduto = new javax.swing.JLabel();
        jCBProduto = new javax.swing.JComboBox<>();
        jLblQtd = new javax.swing.JLabel();
        jLblUnd = new javax.swing.JLabel();
        jTxtUnd = new javax.swing.JTextField();
        jLblVlrT = new javax.swing.JLabel();
        jLblValorUn = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTxtNroDespacho = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTxtRegistro = new JTextFieldSomenteNumeros();
        jBtnPesquisaPessoas = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTxtIdPaisDestino = new javax.swing.JTextField();
        jTxtNomePaisDestino = new javax.swing.JTextField();
        jBtnPesquisaDestino = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTxtNfExportacao = new JTextFieldSomenteNumeros();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTxtIdPaisImportador = new javax.swing.JTextField();
        jTxtNomePaisImportador = new javax.swing.JTextField();
        jBtnPesquisaPaisImportador = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTxtNavio = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTxtIdPortoEmbarque = new javax.swing.JTextField();
        jTxtNomePortoEmbarque = new javax.swing.JTextField();
        jBtnPesquisaPorto = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblMemorandos = new javax.swing.JTable();
        jSDataEmissaoMem = new javax.swing.JSpinner();
        jSDataEmissaoNF = new javax.swing.JSpinner();
        jSDataEmbarque = new javax.swing.JSpinner();
        jTxtQtd = new javax.swing.JTextField();
        jTxtVlrT = new javax.swing.JTextField();
        jTxtValorUn = new javax.swing.JTextField();
        JPnlNotasFiscais = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTblRemessa = new javax.swing.JTable();
        jBtnAddNF = new javax.swing.JButton();
        jBtnPesquisaNF = new javax.swing.JButton();
        jBtnExcluiRemessa = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jTxtNFPropria = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTxtCNPJProprio = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jCBTipoNF = new javax.swing.JComboBox<>();
        jTxtCnpjExp = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTxtQtdeNF = new javax.swing.JTextField();
        jTxtValorExportado = new javax.swing.JTextField();
        jBtnTeste = new javax.swing.JButton();
        jTxtConfereQtd = new javax.swing.JTextField();
        jPnlBotoes = new javax.swing.JPanel();
        jBtnGravar = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnIncluir = new javax.swing.JButton();
        jBtnCancelarMem = new javax.swing.JButton();
        jBtnDeletar = new javax.swing.JButton();
        jPnlCabeçalho = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPnlStatus = new javax.swing.JPanel();
        jLblIdMem = new javax.swing.JLabel();
        jLblStatus = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 453, Short.MAX_VALUE)
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
        setTitle("Cadastro de Memorandos");
        setMaximumSize(new java.awt.Dimension(800, 800));
        setMinimumSize(new java.awt.Dimension(800, 800));
        setPreferredSize(new java.awt.Dimension(800, 800));
        setVisible(true);

        jTPnlGuias.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPnlMemorando.setPreferredSize(new java.awt.Dimension(795, 500));

        jLblNro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblNro.setText("Número");

        jTxtNro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNro.setEnabled(false);
        jTxtNro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtNroActionPerformed(evt);
            }
        });
        jTxtNro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtNroFocusLost(evt);
            }
        });

        jLblData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblData.setText("Data de Emissão");

        jLblExportador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblExportador.setText("Exportador:");

        jTxtIdExportador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdExportador.setEnabled(false);

        jTxtNomeExportador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeExportador.setEnabled(false);

        JlblProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        JlblProduto.setText("Produto Exportado");

        jCBProduto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBProduto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SOJA EM GRÃOS", "MILHO", "TRIGO", "FEIJÃO", "AVEIA", "CEVADA" }));
        jCBProduto.setSelectedIndex(-1);
        jCBProduto.setEnabled(false);

        jLblQtd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblQtd.setText("Quantidade");

        jLblUnd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblUnd.setText("Unidade");

        jTxtUnd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtUnd.setEnabled(false);

        jLblVlrT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblVlrT.setText("Valor Total");

        jLblValorUn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblValorUn.setText("Valor Unitário");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Despacho Exportação");

        jTxtNroDespacho.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNroDespacho.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Registro Exportação");

        jTxtRegistro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtRegistro.setEnabled(false);
        jTxtRegistro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtRegistroFocusLost(evt);
            }
        });

        jBtnPesquisaPessoas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaPessoas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaPessoas.setEnabled(false);
        jBtnPesquisaPessoas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaPessoasActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("País Destino da Mercadoria");

        jTxtIdPaisDestino.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdPaisDestino.setEnabled(false);

        jTxtNomePaisDestino.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomePaisDestino.setEnabled(false);

        jBtnPesquisaDestino.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaDestino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaDestino.setEnabled(false);
        jBtnPesquisaDestino.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaDestinoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Nota Fiscal Exportação");

        jTxtNfExportacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNfExportacao.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Data de Emissão");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("País do Importador");

        jTxtIdPaisImportador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdPaisImportador.setEnabled(false);

        jTxtNomePaisImportador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomePaisImportador.setEnabled(false);
        jTxtNomePaisImportador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtNomePaisImportadorActionPerformed(evt);
            }
        });

        jBtnPesquisaPaisImportador.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaPaisImportador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaPaisImportador.setEnabled(false);
        jBtnPesquisaPaisImportador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaPaisImportadorActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Data de Embarque");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Navio");

        jTxtNavio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNavio.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Porto de Embarque");

        jTxtIdPortoEmbarque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdPortoEmbarque.setEnabled(false);

        jTxtNomePortoEmbarque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomePortoEmbarque.setEnabled(false);

        jBtnPesquisaPorto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaPorto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaPorto.setEnabled(false);
        jBtnPesquisaPorto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jBtnPesquisaPortoFocusLost(evt);
            }
        });
        jBtnPesquisaPorto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaPortoActionPerformed(evt);
            }
        });

        jTblMemorandos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTblMemorandos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTblMemorandos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblMemorandosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTblMemorandos);

        jSDataEmissaoMem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSDataEmissaoMem.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        jSDataEmissaoMem.setEditor(new javax.swing.JSpinner.DateEditor(jSDataEmissaoMem, "dd/MM/yyyy"));
        jSDataEmissaoMem.setEnabled(false);

        jSDataEmissaoNF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSDataEmissaoNF.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        jSDataEmissaoNF.setEditor(new javax.swing.JSpinner.DateEditor(jSDataEmissaoNF, "dd/MM/yyyy"));
        jSDataEmissaoNF.setEnabled(false);

        jSDataEmbarque.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSDataEmbarque.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        jSDataEmbarque.setEditor(new javax.swing.JSpinner.DateEditor(jSDataEmbarque, "dd/MM/yyyy"));
        jSDataEmbarque.setEnabled(false);

        jTxtQtd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtQtd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtQtd.setEnabled(false);
        jTxtQtd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtQtdFocusLost(evt);
            }
        });

        jTxtVlrT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrT.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTxtVlrT.setEnabled(false);
        jTxtVlrT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTxtVlrTFocusLost(evt);
            }
        });

        jTxtValorUn.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtValorUn.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jTxtValorUn.setEnabled(false);

        javax.swing.GroupLayout jPnlMemorandoLayout = new javax.swing.GroupLayout(jPnlMemorando);
        jPnlMemorando.setLayout(jPnlMemorandoLayout);
        jPnlMemorandoLayout.setHorizontalGroup(
            jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlMemorandoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlMemorandoLayout.createSequentialGroup()
                        .addComponent(jLblNro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtNro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLblData)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSDataEmissaoMem, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtNroDespacho))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlMemorandoLayout.createSequentialGroup()
                        .addComponent(jLblQtd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLblUnd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtUnd, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLblVlrT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtVlrT)
                        .addGap(18, 18, 18)
                        .addComponent(jLblValorUn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtValorUn, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlMemorandoLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtRegistro)
                        .addGap(18, 18, 18)
                        .addComponent(JlblProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCBProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlMemorandoLayout.createSequentialGroup()
                        .addComponent(jLblExportador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtIdExportador, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtNomeExportador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnPesquisaPessoas))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlMemorandoLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtIdPaisDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtNomePaisDestino)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnPesquisaDestino))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlMemorandoLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtNfExportacao, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSDataEmissaoNF, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel11)
                        .addGap(10, 10, 10)
                        .addComponent(jSDataEmbarque, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPnlMemorandoLayout.createSequentialGroup()
                        .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPnlMemorandoLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxtNavio, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxtIdPortoEmbarque, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtNomePortoEmbarque))
                            .addGroup(jPnlMemorandoLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxtIdPaisImportador, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTxtNomePaisImportador)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnPesquisaPaisImportador)
                            .addComponent(jBtnPesquisaPorto, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
        );
        jPnlMemorandoLayout.setVerticalGroup(
            jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlMemorandoLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblExportador)
                    .addComponent(jTxtIdExportador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomeExportador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisaPessoas))
                .addGap(18, 18, 18)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblNro)
                    .addComponent(jTxtNro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblData)
                    .addComponent(jLabel3)
                    .addComponent(jTxtNroDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSDataEmissaoMem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JlblProduto)
                    .addComponent(jCBProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTxtRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblVlrT)
                    .addComponent(jLblValorUn)
                    .addComponent(jLblQtd)
                    .addComponent(jLblUnd)
                    .addComponent(jTxtUnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtVlrT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtValorUn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTxtIdPaisDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomePaisDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisaDestino))
                .addGap(18, 18, 18)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTxtNfExportacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jSDataEmissaoNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSDataEmbarque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTxtIdPaisImportador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomePaisImportador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisaPaisImportador))
                .addGap(18, 18, 18)
                .addGroup(jPnlMemorandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTxtNavio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTxtIdPortoEmbarque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomePortoEmbarque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnPesquisaPorto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTPnlGuias.addTab("Memorando", jPnlMemorando);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Remessa");

        jTblRemessa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTblRemessa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Número", "CNPJ do Emissor", "Quantidade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTblRemessa.setEnabled(false);
        jTblRemessa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblRemessaMouseClicked(evt);
            }
        });
        jTblRemessa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTblRemessaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTblRemessa);

        jBtnAddNF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnAddNF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jBtnAddNF.setText("Adicionar");
        jBtnAddNF.setEnabled(false);
        jBtnAddNF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddNFActionPerformed(evt);
            }
        });

        jBtnPesquisaNF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaNF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaNF.setText("Pesquisar");
        jBtnPesquisaNF.setEnabled(false);
        jBtnPesquisaNF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaNFActionPerformed(evt);
            }
        });

        jBtnExcluiRemessa.setText("-");
        jBtnExcluiRemessa.setEnabled(false);
        jBtnExcluiRemessa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExcluiRemessaActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Número da NF");

        jTxtNFPropria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNFPropria.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("CNPJ do emissor");

        jTxtCNPJProprio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtCNPJProprio.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Tipo da Nota Fiscal");

        jCBTipoNF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCBTipoNF.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "REMESSA", "VENDA" }));
        jCBTipoNF.setEnabled(false);

        jTxtCnpjExp.setEnabled(false);

        jLabel18.setText("Mostrando todas as notas para o Exportador:");
        jLabel18.setEnabled(false);

        jTxtQtdeNF.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtQtdeNF.setEnabled(false);

        jTxtValorExportado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtValorExportado.setEnabled(false);

        jBtnTeste.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnTeste.setText("Teste");
        jBtnTeste.setEnabled(false);
        jBtnTeste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnTesteActionPerformed(evt);
            }
        });

        jTxtConfereQtd.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtConfereQtd.setEnabled(false);

        javax.swing.GroupLayout JPnlNotasFiscaisLayout = new javax.swing.GroupLayout(JPnlNotasFiscais);
        JPnlNotasFiscais.setLayout(JPnlNotasFiscaisLayout);
        JPnlNotasFiscaisLayout.setHorizontalGroup(
            JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                        .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCBTipoNF, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                                .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16))
                                .addGap(21, 21, 21)
                                .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTxtNFPropria, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                                    .addComponent(jTxtCNPJProprio))))
                        .addGap(18, 18, 18)
                        .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jBtnAddNF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnPesquisaNF, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnTeste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                            .addComponent(jTxtQtdeNF, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTxtValorExportado, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTxtConfereQtd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnExcluiRemessa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6))
                .addGap(27, 27, 27)
                .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTxtCnpjExp)
                    .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        JPnlNotasFiscaisLayout.setVerticalGroup(
            JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                        .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(jCBTipoNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jBtnAddNF))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel15)
                                .addComponent(jTxtNFPropria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jBtnPesquisaNF)))
                    .addGroup(JPnlNotasFiscaisLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(2, 2, 2)
                        .addComponent(jTxtCnpjExp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTxtCNPJProprio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnTeste))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(JPnlNotasFiscaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnExcluiRemessa)
                    .addComponent(jTxtQtdeNF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtValorExportado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtConfereQtd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTPnlGuias.addTab("Notas Fiscais", JPnlNotasFiscais);

        jBtnGravar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/accept.png"))); // NOI18N
        jBtnGravar.setText("Gravar");
        jBtnGravar.setEnabled(false);
        jBtnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGravarActionPerformed(evt);
            }
        });

        jBtnEditar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/table_edit.png"))); // NOI18N
        jBtnEditar.setText("Editar");
        jBtnEditar.setEnabled(false);
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnIncluir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnIncluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jBtnIncluir.setText("Adicionar");
        jBtnIncluir.setToolTipText("Insere mais um registro.");
        jBtnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIncluirActionPerformed(evt);
            }
        });

        jBtnCancelarMem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnCancelarMem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel.png"))); // NOI18N
        jBtnCancelarMem.setText("Cancelar");
        jBtnCancelarMem.setEnabled(false);
        jBtnCancelarMem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnCancelarMemActionPerformed(evt);
            }
        });

        jBtnDeletar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete.png"))); // NOI18N
        jBtnDeletar.setText("Deletar");
        jBtnDeletar.setEnabled(false);
        jBtnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnDeletarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPnlBotoesLayout = new javax.swing.GroupLayout(jPnlBotoes);
        jPnlBotoes.setLayout(jPnlBotoesLayout);
        jPnlBotoesLayout.setHorizontalGroup(
            jPnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBtnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnCancelarMem, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jBtnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPnlBotoesLayout.setVerticalGroup(
            jPnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPnlBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnCancelarMem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnDeletar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/1471763350_Citycons_ship.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("Memorandos");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel14.setText("de Exportação");

        javax.swing.GroupLayout jPnlCabeçalhoLayout = new javax.swing.GroupLayout(jPnlCabeçalho);
        jPnlCabeçalho.setLayout(jPnlCabeçalhoLayout);
        jPnlCabeçalhoLayout.setHorizontalGroup(
            jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPnlCabeçalhoLayout.setVerticalGroup(
            jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlCabeçalhoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPnlCabeçalhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnlCabeçalhoLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)))
                .addContainerGap())
        );

        jPnlStatus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLblIdMem.setForeground(new java.awt.Color(240, 240, 240));

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPnlStatusLayout = new javax.swing.GroupLayout(jPnlStatus);
        jPnlStatus.setLayout(jPnlStatusLayout);
        jPnlStatusLayout.setHorizontalGroup(
            jPnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlStatusLayout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addComponent(jLblIdMem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
            .addGroup(jPnlStatusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTextField1)
        );
        jPnlStatusLayout.setVerticalGroup(
            jPnlStatusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPnlStatusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblIdMem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPnlCabeçalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(18, 18, 18)
                            .addComponent(jPnlStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jTPnlGuias, javax.swing.GroupLayout.PREFERRED_SIZE, 770, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPnlBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPnlCabeçalho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPnlStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTPnlGuias, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPnlBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTxtNroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtNroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtNroActionPerformed

    private void jBtnPesquisaPessoasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaPessoasActionPerformed
        jPesquisar.setVisible(true);
        jTxtPesquisa_Multi.setText(jTxtNomeExportador.getText());
        var_consulta = 1;
        ListaPessoas();
    }//GEN-LAST:event_jBtnPesquisaPessoasActionPerformed

    private void jBtnPesquisaDestinoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaDestinoActionPerformed
        jPesquisar.setVisible(true);
        jTxtPesquisa_Multi.setText(jTxtNomePaisDestino.getText());
        var_consulta = 2;
        ListaPaises();    // TODO add your handling code here:
    }//GEN-LAST:event_jBtnPesquisaDestinoActionPerformed

    private void jTxtNomePaisImportadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtNomePaisImportadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtNomePaisImportadorActionPerformed

    private void jBtnPesquisaPaisImportadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaPaisImportadorActionPerformed
        jPesquisar.setVisible(true);
        jTxtPesquisa_Multi.setText(jTxtNomePaisImportador.getText());
        var_consulta = 3;
        ListaPaises();    // TODO add your handling code here:
    }//GEN-LAST:event_jBtnPesquisaPaisImportadorActionPerformed

    private void jBtnPesquisaPortoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaPortoActionPerformed
        jPesquisar.setVisible(true);
        jTxtPesquisa_Multi.setText(jTxtNomePortoEmbarque.getText());
        var_consulta = 4;
        ListaCidades();    // TODO add your handling code here:
    }//GEN-LAST:event_jBtnPesquisaPortoActionPerformed

    private void jTblMemorandosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblMemorandosMouseClicked
        var_consulta = 5;
        IncluiPesquisa();
        IncluiNotasFiscaisRemessa();
        //IncluiNotasFiscaisVenda();
        ValorUnitario();
        SomaNotas();
        jBtnEditar.setEnabled(true);
        jBtnDeletar.setEnabled(true);
    }//GEN-LAST:event_jTblMemorandosMouseClicked

    private void jBtnAddNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddNFActionPerformed
        String nf_propria = jTxtNFPropria.getText();
        String cnpj_proprio = jTxtCNPJProprio.getText();

        if (!"".equals(nf_propria)
                && !"".equals(cnpj_proprio)) {
            if ("REMESSA".equals(jCBTipoNF.getSelectedItem().toString())) {
                DefaultTableModel modelr = (DefaultTableModel) jTblRemessa.getModel();
                modelr.addRow(new String[]{"", nf_propria, cnpj_proprio});

            }
        } else {
            JOptionPane.showMessageDialog(rootPane, "Até dá pra lançar por aqui, mas você precisa digitar os dados.");
        }
    }//GEN-LAST:event_jBtnAddNFActionPerformed

    private void jBtnExcluiRemessaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExcluiRemessaActionPerformed
        if (jTblRemessa.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Ô, tontão. Tu precisa selecionar uma linha!");
        } else {
            for (int x = jTblRemessa.getSelectedRowCount(); x != 0; x--) {
                try {
                    ((DefaultTableModel) jTblRemessa.getModel()).removeRow(jTblRemessa.getSelectedRow());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível excluir a linha: " + e);
                }

            }
            SomaNotas();
        }
    }//GEN-LAST:event_jBtnExcluiRemessaActionPerformed

    private void jBtnPesquisaNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaNFActionPerformed
        jTxtCnpjExp.setText(cnpj_exportador);
        if ("".equals(cnpj_exportador)) {
            JOptionPane.showMessageDialog(rootPane, "Você está editando. Escolha novamente o exportador.");
            var_consulta = 1;
            jPesquisar.setVisible(true);
            ListaPessoas();
        } else {

            var_consulta = 6;
            jTxtPesquisa_Multi.setText("");
            jPesquisar.setVisible(true);
            ListaNotasPróprias();
        }
    }//GEN-LAST:event_jBtnPesquisaNFActionPerformed

    private void jBtnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGravarActionPerformed
        try {
            cn.conecta();
            String id = jTxtIdExportador.getText();
            String nome_exportador = jTxtNomeExportador.getText();
            String memorando = jTxtNro.getText();
            String data_emissao = dateOut.format(jSDataEmissaoMem.getValue());
            String numero_despacho = jTxtNroDespacho.getText();
            String numero_registro = jTxtRegistro.getText();
            String produto = jCBProduto.getSelectedItem().toString();
            String quantidade = jTxtQtd.getText().replace(",", ".");
            String unidade = jTxtUnd.getText();
            String valor_total = jTxtVlrT.getText().replace(",", ".");
            String valor_unitario = jTxtValorUn.getText().replace(",", ".");
            String id_pais_destino = jTxtIdPaisDestino.getText();
            String nome_pais_destino = jTxtNomePaisDestino.getText();
            String numero_nf_exportacao = jTxtNfExportacao.getText();
            String data_emissao_nf = dateOut.format(jSDataEmissaoNF.getValue());
            String data_embarque = dateOut.format(jSDataEmbarque.getValue());
            String id_pais_importador = jTxtIdPaisImportador.getText();
            String nome_pais_importador = jTxtNomePaisImportador.getText();
            String navio = jTxtNavio.getText();
            String id_porto = jTxtIdPortoEmbarque.getText();
            String nome_porto = jTxtNomePortoEmbarque.getText();
            int usuario = TelaInicial.usuariosys;

            if ("INCLUINDO".equals(jLblStatus.getText())) {
                String sql = "INSERT INTO CAD_MEMORANDOS (id_exportador, nome_exportador, "
                        + "numero_memorando, data_emissao, numero_despacho, numero_registro, "
                        + " produto, quantidade, unidade, valor_total, valor_unitario, "
                        + " id_pais_destino, nome_pais_destino, numero_nf_exportacao, "
                        + " data_emissao_nf, data_embarque, id_pais_importador, "
                        + " nome_pais_importador, navio, id_porto, nome_porto,usu_inc, data_inc, hora_inc) "
                        + " VALUES ('" + id + "','" + nome_exportador + "','" + memorando + "','" + data_emissao + "',"
                        + "'" + numero_despacho + "','" + numero_registro + "','" + produto + "','" + quantidade + "','" + unidade + "',"
                        + "'" + valor_total + "','" + valor_unitario + "','" + id_pais_destino + "','" + nome_pais_destino + "',"
                        + "'" + numero_nf_exportacao + "','" + data_emissao_nf + "','" + data_embarque + "','" + id_pais_importador + "',"
                        + "'" + nome_pais_importador + "','" + navio + "','" + id_porto + "','" + nome_porto + "',"
                        + "'" + usuario + "', current_date(), current_time())";
                cn.executeAtualizacao(sql);

            } else if ("ALTERANDO".equals(jLblStatus.getText())) {
                try {
                    //ALTERA O CADASTRO DO MEMORANDO, SEM ALTERAR AS NOTAS FISCAIS
                    String sql = "UPDATE CAD_MEMORANDOS SET id_exportador = '" + id + "', nome_exportador = '" + nome_exportador + "',"
                            + "numero_memorando = '" + memorando + "', data_emissao = '" + data_emissao + "', "
                            + "numero_despacho = '" + numero_despacho + "', numero_registro = '" + numero_registro + "', "
                            + "produto = '" + produto + "', quantidade = '" + quantidade + "', unidade = '" + unidade + "', "
                            + "valor_total = '" + valor_total + "', valor_unitario = '" + valor_unitario + "', "
                            + "id_pais_destino = '" + id_pais_destino + "', nome_pais_destino = '" + nome_pais_destino + "', "
                            + "numero_nf_exportacao = '" + numero_nf_exportacao + "', data_emissao_nf = '" + data_emissao_nf + "', "
                            + "data_embarque = '" + data_embarque + "', id_pais_importador = '" + id_pais_importador + "', "
                            + "nome_pais_importador = '" + nome_pais_importador + "', navio = '" + navio + "', "
                            + "id_porto ='" + id_porto + "', nome_porto = '" + nome_porto + "', "
                            + "usu_alt = '" + usuario + "', data_alt = current_date() "
                            + "WHERE id = '" + Integer.parseInt(jLblIdMem.getText()) + "'; ";

                    //EXCLUI AS NOTAS FISCAIS, PORQUE A ALTERAÇÃO PODE ENVOLVER ACRÉSCIMO OU EXCLUSÃO DE REGISTROS
                    String sql2 = "DELETE FROM cad_memorandos_detalhe "
                            + "WHERE ID_EXPORTADOR = " + id + " and numero_memorando = " + memorando;

                    cn.executeAtualizacao(sql);
                    cn.executeAtualizacao(sql2);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Não vai ter exceção!");
            }

            for (int x = 0; x < jTblRemessa.getRowCount(); x++) {
                String natureza = "REMESSA";
                String numero_nf = String.valueOf(jTblRemessa.getValueAt(x, 1));
                String cnpj = String.valueOf(jTblRemessa.getValueAt(x, 2));
                String qtd = String.valueOf(jTblRemessa.getValueAt(x, 3));

                if ("INCLUINDO".equals(jLblStatus.getText())) {
                    String sql = "INSERT INTO CAD_MEMORANDOS_DETALHE (id_exportador, numero_memorando, numero_nf, natureza, inscricao_federal, quantidade) "
                            + "VALUES ('" + id + "','" + memorando + "','" + numero_nf + "','" + natureza + "','" + cnpj + "','" + qtd + "')";

                    cn.executeAtualizacao(sql);

                } else if ("ALTERANDO".equals(jLblStatus.getText())) {
                    String sql = "INSERT INTO CAD_MEMORANDOS_DETALHE (id_exportador, numero_memorando, numero_nf, natureza, inscricao_federal, quantidade) "
                            + "VALUES ('" + id + "','" + memorando + "','" + numero_nf + "','" + natureza + "','" + cnpj + "','" + qtd + "')";

                    cn.executeAtualizacao(sql);
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Não vai ter exceção");
                }
            }

            /* cn.conecta();
        for (int y = 0; y < jTblVenda.getRowCount(); y++) {
            String natureza = "VENDA";
            String numero_nf = String.valueOf(jTblVenda.getValueAt(y, 1));
            String cnpj = String.valueOf(jTblVenda.getValueAt(y, 2));

            if ("INCLUINDO".equals(jLblStatus.getText())) {
                String sql = "INSERT INTO CAD_MEMORANDOS_DETALHE (id_exportador, numero_memorando, numero_nf, natureza, inscricao_federal) "
                        + "VALUES ('" + id + "','" + memorando + "','" + numero_nf + "','" + natureza + "','"+cnpj+"')";
                cn.executeAtualizacao(sql);
            } else if ("ALTERANDO".equals(jLblStatus.getText())) {
                //ESTE COMANDO APENAS INSERE AS NOTAS, PORQUE A EXCLUSÃO JÁ OCORREU NO COMANDO ANTERIOR
                String sql = "INSERT INTO CAD_MEMORANDOS_DETALHE (id_exportador, numero_memorando, numero_nf, natureza, inscricao_federal) "
                        + "VALUES ('" + id + "','" + memorando + "','" + numero_nf + "','" + natureza + "','"+cnpj+"')";
                
                cn.executeAtualizacao(sql);
            } else {
                JOptionPane.showMessageDialog(rootPane, "Não vai ter exceção");
            }
        }
        cn.desconecta();*/
            DesabilitarEdicao();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não deu pra capturar todos os campos. " + ex);
        }
        cn.desconecta();

        ListaMemorandos();

    }//GEN-LAST:event_jBtnGravarActionPerformed

    private void jBtnCancelarMemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarMemActionPerformed

        DesabilitarEdicao();
        ListaMemorandos();
    }//GEN-LAST:event_jBtnCancelarMemActionPerformed

    private void jBtnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIncluirActionPerformed
        jTPnlGuias.setSelectedIndex(0);
        jLblIdMem.setText("");
        jLblStatus.setText("INCLUINDO");
        lista_nfr.setRowCount(0);

        LimpaCampos();
        HabilitarEdicao();

    }//GEN-LAST:event_jBtnIncluirActionPerformed

    private void jTxtPesquisa_MultiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPesquisa_MultiKeyReleased
        if (var_consulta == 1) {
            ListaPessoas();
        } else if (var_consulta == 4) {
            ListaCidades();
        } else if (var_consulta == 6) {
            ListaNotasPróprias();

        } else {
            ListaPaises();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtPesquisa_MultiKeyReleased

    private void jBtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfirmarActionPerformed
        if (var_consulta == 6) {

            UIManager.put("OptionPane.yesButtonText", "REMESSA");
            UIManager.put("OptionPane.noButtonText", "VENDA");
            tipo_nota_fiscal = 0;// JOptionPane.showConfirmDialog(rootPane, "Qual o tipo de Nota Fiscal que será inserida?",null,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        }

        IncluiPesquisa();
        //SomaNotas();
        //jPesquisar.setVisible(false);
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

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        jLblStatus.setText("ALTERANDO");
        HabilitarEdicao();
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jTblRemessaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblRemessaMouseClicked
SomaNotas();    }//GEN-LAST:event_jTblRemessaMouseClicked

    private void jBtnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnDeletarActionPerformed
        String id_mem = jLblIdMem.getText();
        String id = jTxtIdExportador.getText();
        String memorando = jTxtNro.getText();

        int escolha = JOptionPane.showConfirmDialog(null, "Tem certeza que vai deletar o memorando " + memorando + "?", null, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (escolha == 0) {
            try {
                String sql = "DELETE FROM cad_memorandos WHERE id = " + id_mem;
                String sql2 = "DELETE FROM cad_memorandos_detalhe "
                        + "WHERE ID_EXPORTADOR = " + id + " and numero_memorando = " + memorando;
                cn.conecta();
                cn.executeAtualizacao(sql);
                cn.executeAtualizacao(sql2);

                jLblStatus.setText("");
                jLblIdMem.setText("");
                jBtnIncluir.setEnabled(true);
                jTblMemorandos.setEnabled(true);
                jTblMemorandos.setVisible(true);
                jBtnEditar.setEnabled(true);
                jBtnAddNF.setEnabled(false);
                jBtnPesquisaPessoas.setEnabled(false);
                jBtnPesquisaDestino.setEnabled(false);
                jBtnPesquisaPaisImportador.setEnabled(false);
                jBtnPesquisaPorto.setEnabled(false);
                jBtnCancelarMem.setEnabled(false);
                jBtnGravar.setEnabled(false);
                jBtnEditar.setEnabled(false);
                jTxtNro.setEnabled(false);
                jTxtNroDespacho.setEnabled(false);
                jTxtQtd.setEnabled(false);
                jTxtRegistro.setEnabled(false);
                jTxtUnd.setEnabled(false);
                jTxtVlrT.setEnabled(false);
                jTxtNfExportacao.setEnabled(false);
                jTxtNavio.setEnabled(false);
                jSDataEmbarque.setEnabled(false);
                jSDataEmissaoMem.setEnabled(false);
                jSDataEmissaoNF.setEnabled(false);
                jCBProduto.setEnabled(false);
                jTblRemessa.setEnabled(false);
                jBtnExcluiRemessa.setEnabled(false);

                JOptionPane.showMessageDialog(null, "Se era isso o que queria, está feito!!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Registro não deletado: " + e);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Foi por pouco. Presta mais atenção, pô!");
        }
        cn.desconecta();

        ListaMemorandos();
    }//GEN-LAST:event_jBtnDeletarActionPerformed

    private void jTxtVlrTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtVlrTFocusLost
        ValorUnitario();
    }//GEN-LAST:event_jTxtVlrTFocusLost

    private void jBtnTesteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnTesteActionPerformed
        IncluiNotasFiscaisRemessa();        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnTesteActionPerformed

    private void jTxtRegistroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtRegistroFocusLost

// TODO add your handling code here:
    }//GEN-LAST:event_jTxtRegistroFocusLost

    private void jTblRemessaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTblRemessaKeyReleased
        SomaNotas();// TODO add your handling code here:
    }//GEN-LAST:event_jTblRemessaKeyReleased

    private void jTxtNroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtNroFocusLost
        for (int i = 0; i < jTblMemorandos.getRowCount(); i++) {
            String NRO1 = jTxtNro.getText();
            String NRO2 = jTblMemorandos.getValueAt(i, 3).toString();
            String DT = jTblMemorandos.getValueAt(i, 4).toString();
            String REG = jTblMemorandos.getValueAt(i, 6).toString();
            if (NRO2.equals(NRO1)) {
                JOptionPane.showMessageDialog(rootPane, "Este Memorando de Exportação já existe! Registro " + REG + ", Data " + DT, null, JOptionPane.INFORMATION_MESSAGE);

            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtNroFocusLost

    private void jTxtQtdFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTxtQtdFocusLost
        ValorUnitario();        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtQtdFocusLost

    private void jBtnPesquisaPortoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jBtnPesquisaPortoFocusLost

    }//GEN-LAST:event_jBtnPesquisaPortoFocusLost

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        DefaultTableModel tabela_clientes = (DefaultTableModel) jTblMemorandos.getModel();
        final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tabela_clientes);
        jTblMemorandos.setRowSorter(sorter);
        String text = jTextField1.getText();
        if (text.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            try {
                sorter.setRowFilter(
                        RowFilter.regexFilter(text));
            } catch (PatternSyntaxException pse) {
                System.err.println("Erro");
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPnlNotasFiscais;
    private javax.swing.JLabel JlblProduto;
    private javax.swing.JButton jBtnAddNF;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnCancelarMem;
    private javax.swing.JButton jBtnConfirmar;
    private javax.swing.JButton jBtnDeletar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnExcluiRemessa;
    private javax.swing.JButton jBtnGravar;
    private javax.swing.JButton jBtnIncluir;
    private javax.swing.JButton jBtnPesquisaDestino;
    private javax.swing.JButton jBtnPesquisaNF;
    private javax.swing.JButton jBtnPesquisaPaisImportador;
    private javax.swing.JButton jBtnPesquisaPessoas;
    private javax.swing.JButton jBtnPesquisaPorto;
    private javax.swing.JButton jBtnTeste;
    private javax.swing.JComboBox<String> jCBProduto;
    private javax.swing.JComboBox<String> jCBTipoNF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblData;
    private javax.swing.JLabel jLblExportador;
    private javax.swing.JLabel jLblIdMem;
    private javax.swing.JLabel jLblNro;
    private javax.swing.JLabel jLblQtd;
    private javax.swing.JLabel jLblStatus;
    private javax.swing.JLabel jLblUnd;
    private javax.swing.JLabel jLblValorUn;
    private javax.swing.JLabel jLblVlrT;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFrame jPesquisar;
    private javax.swing.JPanel jPnlBotoes;
    private javax.swing.JPanel jPnlCabeçalho;
    private javax.swing.JPanel jPnlMemorando;
    private javax.swing.JPanel jPnlStatus;
    private javax.swing.JSpinner jSDataEmbarque;
    private javax.swing.JSpinner jSDataEmissaoMem;
    private javax.swing.JSpinner jSDataEmissaoNF;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTPnlGuias;
    private javax.swing.JTable jTblConsulta_Multi;
    private javax.swing.JTable jTblMemorandos;
    private javax.swing.JTable jTblRemessa;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTxtCNPJProprio;
    private javax.swing.JTextField jTxtCnpjExp;
    private javax.swing.JTextField jTxtConfereQtd;
    private javax.swing.JTextField jTxtIdExportador;
    private javax.swing.JTextField jTxtIdPaisDestino;
    private javax.swing.JTextField jTxtIdPaisImportador;
    private javax.swing.JTextField jTxtIdPortoEmbarque;
    private javax.swing.JTextField jTxtNFPropria;
    private javax.swing.JTextField jTxtNavio;
    private javax.swing.JTextField jTxtNfExportacao;
    private javax.swing.JTextField jTxtNomeExportador;
    private javax.swing.JTextField jTxtNomePaisDestino;
    private javax.swing.JTextField jTxtNomePaisImportador;
    private javax.swing.JTextField jTxtNomePortoEmbarque;
    private javax.swing.JTextField jTxtNro;
    private javax.swing.JTextField jTxtNroDespacho;
    private javax.swing.JTextField jTxtPesquisa_Multi;
    private javax.swing.JTextField jTxtQtd;
    private javax.swing.JTextField jTxtQtdeNF;
    private javax.swing.JTextField jTxtRegistro;
    private javax.swing.JTextField jTxtUnd;
    private javax.swing.JTextField jTxtValorExportado;
    private javax.swing.JTextField jTxtValorUn;
    private javax.swing.JTextField jTxtVlrT;
    // End of variables declaration//GEN-END:variables

}
