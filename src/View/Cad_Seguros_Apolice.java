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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ferna
 */
public class Cad_Seguros_Apolice extends javax.swing.JInternalFrame {

    ConexaoMySQL cn = new ConexaoMySQL();
    String url;
    String driver = "com.mysql.jdbc.Driver";
    String usuario;
    String senha;

    int var_consulta; //UTILIZADO PARA REALIZAR CONSULTA SQL
    int bem; //UTILIZADO PARA DELETAR COBERTURAS
    int cobertura; //UTILIZADO PARA DELETAR COBERTURAS
    String id_seguro; //UTILIZADO PARA RETORNO DA CONSULTA

    DateFormat dateOut = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat dateIn = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form Cad_Seguros_Apolice
     */
    public Cad_Seguros_Apolice() {
        initComponents();
    }

    public void setPosicao() {
        Dimension d = this.getDesktopPane().getSize();
        this.setLocation((d.width - this.getSize().width) / 2, (d.height - this.getSize().height) / 2);
    }

    public void habilitarEdicao() {
        jBtnPesquisaEstabelecimento.setEnabled(true);
        jBtnPesquisaSeguradora.setEnabled(true);
        jBtnPesquisaCorretora.setEnabled(true);
        jBtnPesquisaTipo.setEnabled(true);
        jBtnPesquisaBem.setEnabled(true);
        jBtnPesquisaCobertura.setEnabled(true);
        jBtnAddCobertura.setEnabled(true);
        jBtnAddBem1.setEnabled(true);
        jBtnExcluiBem1.setEnabled(true);
        jBtnExcluiCobertura.setEnabled(true);
        jBtnGravar.setEnabled(true);
        jBtnPesquisaEmpresa.setEnabled(false);
        jBtnClonar.setEnabled(true);

        jTxtApolice.setEnabled(true);
        jTxtEndosso.setEnabled(true);
        jTxtContrato.setEnabled(true);
        jTxtVlrAdicional.setEnabled(true);
        jTxtVlrCobertura.setEnabled(true);
        jTxtVlrCusto.setEnabled(true);
        jTxtVlrIof.setEnabled(true);
        jTxtVlrJuros.setEnabled(true);
        jTxtVlrPremio.setEnabled(true);
        jTxtVlrPremioBem.setEnabled(true);
        jTxtVlrTotal.setEnabled(true);
        jTxtTipoCob.setEnabled(true);
        jTxtParcelas.setEnabled(true);
        jTxtTipo.setEnabled(true);
        jTxtCep.setEnabled(true);
        jTxtCategoria.setEnabled(true);
        jTxtProposta.setEnabled(true);

        jTxaObs.setEnabled(true);

        jSDataEmissao.setEnabled(true);
        jSDataInicial.setEnabled(true);
        jSDataFinal.setEnabled(true);

    }

    public void desabilitarEdicao() {
        jBtnPesquisaEstabelecimento.setEnabled(false);
        jBtnPesquisaSeguradora.setEnabled(false);
        jBtnPesquisaCorretora.setEnabled(false);
        jBtnPesquisaTipo.setEnabled(false);
        jBtnPesquisaBem.setEnabled(false);
        jBtnPesquisaCobertura.setEnabled(false);
        jBtnAddCobertura.setEnabled(false);
        jBtnAddBem1.setEnabled(false);
        jBtnExcluiBem1.setEnabled(false);
        jBtnExcluiCobertura.setEnabled(false);
        jBtnGravar.setEnabled(false);
        jBtnEditar.setEnabled(false);
        jBtnPesquisaEmpresa.setEnabled(true);
        jBtnIncluir.setEnabled(true);
        jBtnClonar.setEnabled(false);

        jBtnIncluir.setText("Adicionar");
        jBtnEditar.setText("Editar");

        jTxtApolice.setEnabled(false);
        jTxtEndosso.setEnabled(false);
        jTxtContrato.setEnabled(false);
        jTxtVlrAdicional.setEnabled(false);
        jTxtVlrCobertura.setEnabled(false);
        jTxtVlrCusto.setEnabled(false);
        jTxtVlrIof.setEnabled(false);
        jTxtVlrJuros.setEnabled(false);
        jTxtVlrPremio.setEnabled(false);
        jTxtVlrPremioBem.setEnabled(false);
        jTxtVlrTotal.setEnabled(false);
        jTxtTipoCob.setEnabled(false);
        jTxtParcelas.setEnabled(false);
        jTxtTipo.setEnabled(false);
        jTxtCep.setEnabled(false);
        jTxtCategoria.setEnabled(false);
        jTxtProposta.setEnabled(false);

        jTxaObs.setEnabled(false);

        jSDataEmissao.setEnabled(false);
        jSDataInicial.setEnabled(false);
        jSDataFinal.setEnabled(false);

    }

    public void MontaLista() {

        

        jTxtPesquisa_Multi.grabFocus();

        DefaultTableModel lista = (DefaultTableModel) jTblConsulta_Multi.getModel();

        lista.setColumnCount(0);
        lista.setRowCount(0);

        switch (var_consulta) {
            case 1:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_estabelecimentos "
                            + "WHERE UPPER(razao_social) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY razao_social";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Razão Social");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_estabelecimentos "
                            + " ORDER BY razao_social";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Razão Social");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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

                }
                break;
            case 2:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_pessoas "
                            + "WHERE UPPER(nome) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY nome";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Razão Social");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_pessoas "
                            + " ORDER BY nome";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Nome");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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

                }
                break;
            case 3:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_pessoas "
                            + "WHERE UPPER(nome) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY nome";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Razão Social");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_pessoas "
                            + " ORDER BY nome";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Nome");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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

                }
                break;
            case 4:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_seguros_tipos "
                            + "WHERE UPPER(descricao) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_seguros_tipos "
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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

                }
                break;
            case 5:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_bens "
                            + "WHERE UPPER(descricao) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%' OR "
                            + "UPPER(placa) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");
                    lista.addColumn("Placa");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(4),
                                cn.rs.getString(5)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_bens "
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");
                    lista.addColumn("Placa");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(4),
                                cn.rs.getString(5)});
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

                }
                break;
            case 6:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_seguros_coberturas "
                            + "WHERE UPPER(descricao) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT * FROM cad_seguros_coberturas "
                            + " ORDER BY descricao";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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

                }
                break;

            case 7:

                String sql7;

                lista.addColumn("Id");
                lista.addColumn("Apolice");
                //lista.addColumn("Empresa");
                lista.addColumn("Endosso");
                //lista.addColumn("Corretora");
                lista.addColumn("Bem");

                jTblConsulta_Multi.setModel(lista);
                jTblConsulta_Multi.getColumnModel().getColumn(0).setMaxWidth(40);
                jTblConsulta_Multi.getColumnModel().getColumn(0).setMinWidth(40);
                jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
                jTblConsulta_Multi.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);

                if (!"".equals(jTxtPesquisa_Multi.getText())) {

                    sql7 = "SELECT a.id,a.apolice,a.endosso,a.bem_segurado "
                            + "FROM cad_seguros a "
                            + "LEFT JOIN cad_empresas b on (b.id = a.id_empresa) "
                            + "left join cad_pessoas c on (c.id = a.id_seguradora) "
                            + "left join cad_pessoas d on (d.id = a.id_corretor) "
                            + "WHERE a.apolice "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%' "
                            + "OR UPPER(c.nome) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%' "
                            + " ORDER BY a.apolice,a.endosso";
                } else {
                    sql7 = "SELECT a.id,a.apolice,a.endosso,a.bem_segurado "
                            + "FROM cad_seguros a "
                            + "LEFT JOIN cad_empresas b on (b.id = a.id_empresa) "
                            + "left join cad_pessoas c on (c.id = a.id_seguradora) "
                            + "left join cad_pessoas d on (d.id = a.id_corretor) "
                            + " ORDER BY a.apolice,a.endosso";
                }

                
                cn.conecta();
                cn.executeConsulta(sql7);

                try {
                    while (cn.rs.next()) {

                        lista.addRow(new String[]{
                            cn.rs.getString(1),
                            cn.rs.getString(2),
                            cn.rs.getString(3),
                            cn.rs.getString(4)});
                    }
                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                }

                cn.desconecta();


                /*
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT a.id,a.apolice,b.nome,c.nome,d.nome,a.bem_segurado "
                            + "FROM cad_seguros a "
                            + "LEFT JOIN cad_empresas b on (b.id = a.id_empresa) "
                            + "left join cad_pessoas c on (c.id = a.id_seguradora) "
                            + "left join cad_pessoas d on (d.id = a.id_corretor) "
                            + "WHERE a.apolice "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%' "
                            + "OR UPPER(c.nome) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%' "
                            + " ORDER BY a.apolice";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Apolice");
                    lista.addColumn("Empresa");
                    lista.addColumn("Seguradora");
                    lista.addColumn("Corretora");
                    lista.addColumn("Bem");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2),
                                cn.rs.getString(3),
                                cn.rs.getString(4),
                                cn.rs.getString(5),
                                cn.rs.getString(6)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT a.id,a.apolice,b.nome,c.nome,d.nome,a.bem_segurado "
                            + "FROM cad_seguros a "
                            + "LEFT JOIN cad_empresas b on (b.id = a.id_empresa) "
                            + "left join cad_pessoas c on (c.id = a.id_seguradora) "
                            + "left join cad_pessoas d on (d.id = a.id_corretor) "
                            + " ORDER BY a.apolice";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Apolice");
                    lista.addColumn("Empresa");
                    lista.addColumn("Seguradora");
                    lista.addColumn("Corretora");
                    lista.addColumn("Bem");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2),
                                cn.rs.getString(3),
                                cn.rs.getString(4),
                                cn.rs.getString(5),
                                cn.rs.getString(6)});
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

                }
                 */
                break;

            case 8:
                if (!"".equals(jTxtPesquisa_Multi.getText())) {
                    cn.conecta();
                    String sql = "SELECT DISTINCT b.id, b.descricao FROM tmp_cad_seguros_bens_coberturas A "
                            + "LEFT JOIN cad_bens b on (b.id = a.id_bem) "
                            + "WHERE UPPER(b.descricao) "
                            + "LIKE '%" + jTxtPesquisa_Multi.getText().toUpperCase() + "%'"
                            + " ORDER BY b.descricao,b.id";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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
                } else {
                    cn.conecta();
                    String sql = "SELECT DISTINCT b.id, b.descricao FROM tmp_cad_seguros_bens_coberturas A "
                            + "LEFT JOIN cad_bens b on (b.id = a.id_bem) "
                            + " ORDER BY b.descricao, b.id";

                    cn.executeConsulta(sql);

                    lista.addColumn("Id");
                    lista.addColumn("Descrição");

                    try {
                        while (cn.rs.next()) {

                            lista.addRow(new String[]{
                                cn.rs.getString(1),
                                cn.rs.getString(2)});
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

                }
                break;
        }

    }

    public void IncluiPesquisa() {
        int linha;

        switch (var_consulta) {
            case 1:
                //INCLUI ESTABELECIMENTO
                linha = jTblConsulta_Multi.getSelectedRow();

                jTxtIdEstabelecimento.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
                jTxtNomeEstabelecimento.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

                var_consulta = 11;

                IncluiPesquisa();

            case 11:
                //INCLUI EMPRESA

                String sql = "SELECT a.id, b.id as id_empresa, b.nome "
                        + "FROM cad_estabelecimentos a "
                        + "LEFT JOIN cad_empresas b on (b.id = a.id_empresa) "
                        + "WHERE a.id =" + jTxtIdEstabelecimento.getText();

                cn.conecta();
                cn.executeConsulta(sql);

                try {
                    String id_empresa;
                    String nome_empresa;
                    while (cn.rs.next()) {

                        id_empresa = cn.rs.getString(2);
                        nome_empresa = cn.rs.getString(3);

                        jTxtIdEmpresa.setText(id_empresa);
                        jTxtNomeEmpresa.setText(nome_empresa);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex);
                }

                break;
            case 2:
                //INCLUI SEGURADORA

                linha = jTblConsulta_Multi.getSelectedRow();

                jTxtIdSeguradora.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
                jTxtNomeSeguradora.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

                break;
            case 3:
                //INCLUI CORRETORA
                linha = jTblConsulta_Multi.getSelectedRow();

                jTxtIdCorretora.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
                jTxtNomeCorretora.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

                break;
            case 4:
                //INCLUI TIPO SEGURO
                linha = jTblConsulta_Multi.getSelectedRow();

                jTxtTipoSeg.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

                break;
            case 5:
                //INCLUI BEM
                linha = jTblConsulta_Multi.getSelectedRow();

                jTxtIdBem1.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
                jTxtNomeBem1.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

                break;
            case 6:
                //INCLUI COBERTURA
                linha = jTblConsulta_Multi.getSelectedRow();

                jTxtIdCobertura.setText(jTblConsulta_Multi.getValueAt(linha, 0).toString());
                jTxtNomeCobertura.setText(jTblConsulta_Multi.getValueAt(linha, 1).toString());

                break;
            case 7:
                //INCLUI APOLICE PARA EDIÇÃO
                linha = jTblConsulta_Multi.getSelectedRow();

                id_seguro = "" + jTblConsulta_Multi.getValueAt(linha, 0);

                String sql7 = "SELECT * FROM cad_seguros a "
                        + "left join cad_empresas b on (b.id = a.id_empresa) "
                        + "left join cad_pessoas c on (c.id = a.id_seguradora) "
                        + "left join cad_pessoas d on (d.id = a.id_corretor) "
                        + "left join cad_estabelecimentos e on (e.id = a.id_estabelecimento) "
                        + "WHERE a.id = '" + id_seguro + "';";

                
                cn.conecta();
                cn.executeConsulta(sql7);

                try {
                    while (cn.rs.next()) {

                        String dte = dateIn.format(cn.rs.getObject(13));
                        String dti = dateIn.format(cn.rs.getObject(11));
                        String dtf = dateIn.format(cn.rs.getObject(12));

                        jTxtIdEmpresa.setText(cn.rs.getString(2));
                        jTxtNomeEmpresa.setText(cn.rs.getString(25));
                        jTxtIdEstabelecimento.setText(cn.rs.getString(3));
                        jTxtNomeEstabelecimento.setText(cn.rs.getString(38));
                        jTxtIdSeguradora.setText(cn.rs.getString(4));
                        jTxtNomeSeguradora.setText(cn.rs.getString(28));
                        jTxtIdCorretora.setText(cn.rs.getString(5));
                        jTxtNomeCorretora.setText(cn.rs.getString(33));
                        jTxtApolice.setText(cn.rs.getString(6));
                        jTxtEndosso.setText(cn.rs.getString(7));
                        jTxtContrato.setText(cn.rs.getString(8));
                        jTxtProposta.setText(cn.rs.getString(9));
                        jTxtTipoSeg.setText(cn.rs.getString(10));
                        jSDataEmissao.setValue(dateIn.parse(dte));
                        jSDataInicial.setValue(dateIn.parse(dti));
                        jSDataFinal.setValue(dateIn.parse(dtf));
                        jTxtVlrPremio.setText(cn.rs.getString(14).replace(".", ","));
                        jTxtVlrAdicional.setText(cn.rs.getString(15).replace(".", ","));
                        jTxtVlrCusto.setText(cn.rs.getString(16).replace(".", ","));
                        jTxtVlrIof.setText(cn.rs.getString(17).replace(".", ","));
                        jTxtVlrTotal.setText(cn.rs.getString(18).replace(".", ","));
                        jTxtVlrJuros.setText(cn.rs.getString(19).replace(".", ","));
                        jTxtTipoCob.setText(cn.rs.getString(20));
                        jTxtParcelas.setText(cn.rs.getString(21));
                        jTxtTipo.setText(cn.rs.getString(22));
                        jTxaObs.setText(cn.rs.getString(23));
                    }
                } catch (SQLException | ParseException ex) {
                    JOptionPane.showMessageDialog(this, ex);
                }
                cn.desconecta();

                //MONTA TABELA DE BENS
                String sql77 = "SELECT * FROM cad_seguros_bens a "
                        + "left join cad_bens b on (b.id = a.id_bem) "
                        + "LEFT join cad_seguros c on (c.apolice = a.id_seguro "
                        + "and c.id_seguradora = a.id_seguradora "
                        + "and c.endosso = a.endosso) "
                        + "WHERE c.id = '" + id_seguro + "';";

                limpaTabelas();

                DefaultTableModel listaBens = (DefaultTableModel) jTblBens.getModel();

                cn.conecta();
                cn.executeConsulta(sql77);

                try {
                    while (cn.rs.next()) {

                        listaBens.addRow(new String[]{
                            cn.rs.getString(4), //ID
                            cn.rs.getString(11), //NOME
                            cn.rs.getString(5), //CEP
                            cn.rs.getString(6)}); //CATEGORIA
                    }
                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                }
                cn.desconecta();

                String sql77a = "TRUNCATE tmp_cad_seguros_bens_coberturas; ";

                String seguradora = jTxtIdSeguradora.getText();
                String apolice = jTxtApolice.getText();
                String endosso = jTxtEndosso.getText();

                String sql77b = "INSERT INTO tmp_cad_seguros_bens_coberturas (id,"
                        + "id_seguradora,id_seguro,id_bem,id_cobertura,valor,vlr_premio,endosso) "
                        + "SELECT * FROM cad_seguros_bens_coberturas "
                        + "where cad_seguros_bens_coberturas.id_seguradora = '" + seguradora
                        + "' and cad_seguros_bens_coberturas.id_seguro = '" + apolice + "'"
                        + " and cad_seguros_bens_coberturas.endosso = '" + endosso + "';";

                cn.conecta();
                cn.executeAtualizacao(sql77a);
                cn.executeAtualizacao(sql77b);
                cn.desconecta();

                jBtnEditar.setEnabled(true);

                break;

            case 8:
                linha = jTblConsulta_Multi.getSelectedRow();

                String id_bem_clonado = (jTblConsulta_Multi.getValueAt(linha, 0).toString());
                String id_bem_novo = jTxtIdBem2.getText();

                String sql8 = "SELECT a.id_bem,b.descricao,a.id_cobertura,c.descricao,a.valor,a.vlr_premio "
                        + "FROM tmp_cad_seguros_bens_coberturas a "
                        + "LEFT JOIN cad_bens b on (b.id = a.id_bem) "
                        + "LEFT JOIN cad_seguros_coberturas c on (c.id = a.id_cobertura) "
                        + "WHERE a.id_bem = '" + id_bem_clonado + "' "
                        + "and condicao in (0,3);";

                ///////////////////////////////////////////////////////////////////////
                DefaultTableModel lista = (DefaultTableModel) jTblCoberturas.getModel();
                lista.setRowCount(0);

                jTpCadastro.setSelectedIndex(2);

                
                cn.conecta();
                cn.executeConsulta(sql8);

                try {
                    while (cn.rs.next()) {

                        String seguradora8 = jTxtIdSeguradora.getText();
                        String seguro = jTxtApolice.getText();
                        String id_bem = jTxtIdBem2.getText();
                        String id_cob = (cn.rs.getString(3));
                        String valor = (cn.rs.getString(5));
                        String premio = (cn.rs.getString(6));

                        String sql88 = "INSERT INTO tmp_cad_seguros_bens_coberturas (id_seguradora,id_seguro,"
                                + "id_bem,id_cobertura,valor,vlr_premio,condicao) VALUES ('" + seguradora8 + "','" + seguro + "',"
                                + "'" + id_bem + "','" + id_cob + "','" + valor + "','" + premio + "',3)";

                        
                        cn.executeAtualizacao(sql88);

                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, ex);
                }
                cn.desconecta();

                String sql89 = "SELECT a.id_bem,b.descricao,a.id_cobertura,c.descricao,a.valor,a.vlr_premio "
                        + "FROM tmp_cad_seguros_bens_coberturas a "
                        + "LEFT JOIN cad_bens b on (b.id = a.id_bem) "
                        + "LEFT JOIN cad_seguros_coberturas c on (c.id = a.id_cobertura) "
                        + "WHERE a.id_bem = '" + id_bem_novo + "' "
                        + "AND a.condicao in (0,3);";

                cn.conecta();
                cn.executeConsulta(sql89);

                try {
                    while (cn.rs.next()) {

                        lista.addRow(new String[]{
                            cn.rs.getString(1),
                            cn.rs.getString(2),
                            cn.rs.getString(3),
                            cn.rs.getString(4),
                            cn.rs.getString(5).replace(".", ","),
                            cn.rs.getString(6).replace(".", ",")});
                    }
                } catch (SQLException ex) {

                    JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
                }

                cn.desconecta();

        }
    }

    public void limpaTabelas() {
        DefaultTableModel bens = (DefaultTableModel) jTblBens.getModel();
        DefaultTableModel cobertura = (DefaultTableModel) jTblCoberturas.getModel();
        bens.setColumnCount(0);
        bens.setRowCount(0);
        cobertura.setColumnCount(0);
        cobertura.setRowCount(0);

        bens.addColumn("Id");
        bens.addColumn("Nome");
        bens.addColumn("CEP");
        bens.addColumn("Categoria");
        jTblBens.getColumnModel().getColumn(0).setMaxWidth(40);
        jTblBens.getColumnModel().getColumn(0).setMinWidth(40);
        jTblBens.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
        jTblBens.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);
        jTblBens.getColumnModel().getColumn(2).setMaxWidth(120);
        jTblBens.getColumnModel().getColumn(2).setMinWidth(120);
        jTblBens.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(120);
        jTblBens.getTableHeader().getColumnModel().getColumn(2).setMinWidth(120);

        cobertura.addColumn("Id");
        cobertura.addColumn("Nome");
        cobertura.addColumn("Id_C");
        cobertura.addColumn("Cobertura");
        cobertura.addColumn("Valor");
        cobertura.addColumn("Prêmio");
        jTblCoberturas.getColumnModel().getColumn(0).setMaxWidth(40);
        jTblCoberturas.getColumnModel().getColumn(0).setMinWidth(40);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(40);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(0).setMinWidth(40);
        jTblCoberturas.getColumnModel().getColumn(2).setMaxWidth(40);
        jTblCoberturas.getColumnModel().getColumn(2).setMinWidth(40);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(2).setMaxWidth(40);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(2).setMinWidth(40);
        jTblCoberturas.getColumnModel().getColumn(4).setMaxWidth(90);
        jTblCoberturas.getColumnModel().getColumn(4).setMinWidth(90);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(90);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(4).setMinWidth(90);
        jTblCoberturas.getColumnModel().getColumn(5).setMaxWidth(90);
        jTblCoberturas.getColumnModel().getColumn(5).setMinWidth(90);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(90);
        jTblCoberturas.getTableHeader().getColumnModel().getColumn(5).setMinWidth(90);

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
        jTpCadastro = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTxtIdEmpresa = new javax.swing.JTextField();
        jTxtIdEstabelecimento = new javax.swing.JTextField();
        jTxtNomeEmpresa = new javax.swing.JTextField();
        jTxtNomeEstabelecimento = new javax.swing.JTextField();
        jBtnPesquisaEmpresa = new javax.swing.JButton();
        jBtnPesquisaEstabelecimento = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTxtIdSeguradora = new javax.swing.JTextField();
        jTxtNomeSeguradora = new javax.swing.JTextField();
        jBtnPesquisaSeguradora = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jTxtIdCorretora = new javax.swing.JTextField();
        jTxtNomeCorretora = new javax.swing.JTextField();
        jBtnPesquisaCorretora = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTxtApolice = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTxtEndosso = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTxtContrato = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTxtTipoSeg = new javax.swing.JTextField();
        jLblData = new javax.swing.JLabel();
        jSDataEmissao = new javax.swing.JSpinner();
        jBtnPesquisaTipo = new javax.swing.JButton();
        jLblData1 = new javax.swing.JLabel();
        jSDataInicial = new javax.swing.JSpinner();
        jLblData2 = new javax.swing.JLabel();
        jSDataFinal = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jTxtVlrPremio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTxtVlrAdicional = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTxtVlrCusto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTxtVlrIof = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTxtVlrTotal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTxtVlrJuros = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTxtTipoCob = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTxtParcelas = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTxtTipo = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxaObs = new javax.swing.JTextArea();
        jLabel19 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTxtProposta = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jTxtIdBem1 = new javax.swing.JTextField();
        jTxtNomeBem1 = new javax.swing.JTextField();
        jBtnPesquisaBem = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jTxtCep = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTxtCategoria = new javax.swing.JTextField();
        jBtnAddBem1 = new javax.swing.JButton();
        jBtnExcluiBem1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTblBens = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jTxtIdBem2 = new javax.swing.JTextField();
        jTxtNomeBem2 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTxtIdCobertura = new javax.swing.JTextField();
        jTxtNomeCobertura = new javax.swing.JTextField();
        jBtnPesquisaCobertura = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jTxtVlrCobertura = new javax.swing.JTextField();
        jTxtVlrPremioBem = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTblCoberturas = new javax.swing.JTable();
        jBtnExcluiCobertura = new javax.swing.JButton();
        jBtnAddCobertura = new javax.swing.JButton();
        jBtnClonar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
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
        setTitle("Apólices");
        setPreferredSize(new java.awt.Dimension(633, 583));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Empresa");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Estabelecimento");

        jTxtIdEmpresa.setEditable(false);
        jTxtIdEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdEmpresa.setEnabled(false);

        jTxtIdEstabelecimento.setEditable(false);
        jTxtIdEstabelecimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdEstabelecimento.setEnabled(false);

        jTxtNomeEmpresa.setEditable(false);
        jTxtNomeEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeEmpresa.setEnabled(false);

        jTxtNomeEstabelecimento.setEditable(false);
        jTxtNomeEstabelecimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeEstabelecimento.setEnabled(false);

        jBtnPesquisaEmpresa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaEmpresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaEmpresaActionPerformed(evt);
            }
        });

        jBtnPesquisaEstabelecimento.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaEstabelecimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaEstabelecimento.setEnabled(false);
        jBtnPesquisaEstabelecimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaEstabelecimentoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Seguradora");

        jTxtIdSeguradora.setEditable(false);
        jTxtIdSeguradora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdSeguradora.setEnabled(false);

        jTxtNomeSeguradora.setEditable(false);
        jTxtNomeSeguradora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeSeguradora.setEnabled(false);

        jBtnPesquisaSeguradora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaSeguradora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaSeguradora.setEnabled(false);
        jBtnPesquisaSeguradora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaSeguradoraActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Corretora");

        jTxtIdCorretora.setEditable(false);
        jTxtIdCorretora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdCorretora.setEnabled(false);

        jTxtNomeCorretora.setEditable(false);
        jTxtNomeCorretora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeCorretora.setEnabled(false);

        jBtnPesquisaCorretora.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaCorretora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaCorretora.setEnabled(false);
        jBtnPesquisaCorretora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaCorretoraActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Apólice");

        jTxtApolice.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtApolice.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Endosso");

        jTxtEndosso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtEndosso.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Contrato");

        jTxtContrato.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtContrato.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tipo de Seguro");

        jTxtTipoSeg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtTipoSeg.setEnabled(false);
        jTxtTipoSeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtTipoSegActionPerformed(evt);
            }
        });

        jLblData.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblData.setText("Emissão");

        jSDataEmissao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSDataEmissao.setModel(new javax.swing.SpinnerDateModel());
        jSDataEmissao.setEditor(new javax.swing.JSpinner.DateEditor(jSDataEmissao, "dd/MM/yyyy"));
        jSDataEmissao.setEnabled(false);

        jBtnPesquisaTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaTipo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaTipo.setEnabled(false);
        jBtnPesquisaTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaTipoActionPerformed(evt);
            }
        });

        jLblData1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblData1.setText("Início");

        jSDataInicial.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSDataInicial.setModel(new javax.swing.SpinnerDateModel());
        jSDataInicial.setEditor(new javax.swing.JSpinner.DateEditor(jSDataInicial, "dd/MM/yyyy"));
        jSDataInicial.setEnabled(false);

        jLblData2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLblData2.setText("Fim");

        jSDataFinal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jSDataFinal.setModel(new javax.swing.SpinnerDateModel());
        jSDataFinal.setEditor(new javax.swing.JSpinner.DateEditor(jSDataFinal, "dd/MM/yyyy"));
        jSDataFinal.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Prêmio");

        jTxtVlrPremio.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrPremio.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Adicional");

        jTxtVlrAdicional.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrAdicional.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Custo");

        jTxtVlrCusto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrCusto.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("I.O.F.");

        jTxtVlrIof.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrIof.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Total");

        jTxtVlrTotal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrTotal.setEnabled(false);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Juros");

        jTxtVlrJuros.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrJuros.setEnabled(false);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Tipo Cobrança");

        jTxtTipoCob.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtTipoCob.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Parcelas");

        jTxtParcelas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtParcelas.setEnabled(false);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Tipo");

        jTxtTipo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtTipo.setEnabled(false);

        jTxaObs.setColumns(20);
        jTxaObs.setRows(5);
        jTxaObs.setEnabled(false);
        jScrollPane1.setViewportView(jTxaObs);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Observações");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel27.setText("Proposta");

        jTxtProposta.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtProposta.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTxtIdEstabelecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTxtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTxtIdSeguradora, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTxtIdCorretora, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTxtNomeEstabelecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTxtNomeEmpresa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLblData)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel27)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTxtProposta, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTxtTipoSeg, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jBtnPesquisaTipo))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLblData1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLblData2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jSDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel16)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTxtTipoCob, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel17)
                                                .addGap(18, 18, 18)
                                                .addComponent(jTxtParcelas, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTxtVlrPremio, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel13)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jTxtVlrIof, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel11))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                        .addGap(40, 40, 40)
                                                        .addComponent(jLabel14)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jTxtVlrTotal)
                                                    .addComponent(jTxtVlrAdicional, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jTxtVlrJuros, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(jLabel12)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTxtVlrCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addGap(10, 10, 10)
                                                        .addComponent(jLabel18)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jTxtTipo)))))))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTxtApolice, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTxtEndosso, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTxtContrato))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(165, 165, 165)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jTxtNomeSeguradora)
                                        .addComponent(jTxtNomeCorretora))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jBtnPesquisaCorretora, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jBtnPesquisaSeguradora, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jBtnPesquisaEstabelecimento, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jBtnPesquisaEmpresa, javax.swing.GroupLayout.Alignment.TRAILING)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnPesquisaEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(jTxtIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTxtIdEstabelecimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtNomeEstabelecimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnPesquisaEstabelecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jTxtIdSeguradora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtNomeSeguradora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnPesquisaSeguradora, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jTxtIdCorretora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtNomeCorretora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jBtnPesquisaCorretora, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTxtApolice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTxtContrato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jTxtEndosso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jTxtProposta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTxtTipoSeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBtnPesquisaTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLblData1)
                    .addComponent(jSDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblData2)
                    .addComponent(jSDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLblData)
                    .addComponent(jSDataEmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTxtVlrPremio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTxtVlrAdicional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTxtVlrCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTxtVlrIof, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jTxtVlrTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jTxtVlrJuros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jTxtTipoCob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jTxtParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTxtTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTpCadastro.addTab("Apólice", jPanel1);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Bem Segurado");

        jTxtIdBem1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdBem1.setEnabled(false);

        jTxtNomeBem1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeBem1.setEnabled(false);

        jBtnPesquisaBem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaBem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaBem.setEnabled(false);
        jBtnPesquisaBem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaBemActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("C.E.P. de Pernoite");

        jTxtCep.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtCep.setEnabled(false);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("Categoria Tarifária");

        jTxtCategoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtCategoria.setEnabled(false);

        jBtnAddBem1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnAddBem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jBtnAddBem1.setText("Adicionar");
        jBtnAddBem1.setEnabled(false);
        jBtnAddBem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddBem1ActionPerformed(evt);
            }
        });

        jBtnExcluiBem1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnExcluiBem1.setText("(-) Remover");
        jBtnExcluiBem1.setEnabled(false);
        jBtnExcluiBem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExcluiBem1ActionPerformed(evt);
            }
        });

        jTblBens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jTblBens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTblBensMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTblBens);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(jTxtIdBem1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTxtNomeBem1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnPesquisaBem))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTxtCategoria))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jBtnExcluiBem1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnAddBem1)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnPesquisaBem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel20)
                        .addComponent(jTxtIdBem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtNomeBem1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTxtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jTxtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnAddBem1)
                    .addComponent(jBtnExcluiBem1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTpCadastro.addTab("Bens", jPanel2);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel23.setText("Bem Segurado");

        jTxtIdBem2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdBem2.setEnabled(false);

        jTxtNomeBem2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeBem2.setEnabled(false);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("Cobertura");

        jTxtIdCobertura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtIdCobertura.setEnabled(false);

        jTxtNomeCobertura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtNomeCobertura.setEnabled(false);

        jBtnPesquisaCobertura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnPesquisaCobertura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/zoom.png"))); // NOI18N
        jBtnPesquisaCobertura.setEnabled(false);
        jBtnPesquisaCobertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnPesquisaCoberturaActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel25.setText("Valor");

        jTxtVlrCobertura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrCobertura.setEnabled(false);
        jTxtVlrCobertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTxtVlrCoberturaActionPerformed(evt);
            }
        });

        jTxtVlrPremioBem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jTxtVlrPremioBem.setEnabled(false);

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel26.setText("Prêmio");

        jTblCoberturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(jTblCoberturas);

        jBtnExcluiCobertura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnExcluiCobertura.setText("(-) Remover");
        jBtnExcluiCobertura.setEnabled(false);
        jBtnExcluiCobertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnExcluiCoberturaActionPerformed(evt);
            }
        });

        jBtnAddCobertura.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnAddCobertura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/add.png"))); // NOI18N
        jBtnAddCobertura.setText("Adicionar");
        jBtnAddCobertura.setEnabled(false);
        jBtnAddCobertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAddCoberturaActionPerformed(evt);
            }
        });

        jBtnClonar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnClonar.setText("( |>| ) Clonar");
        jBtnClonar.setEnabled(false);
        jBtnClonar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnClonarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(jTxtIdBem2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTxtIdCobertura, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jTxtNomeCobertura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBtnPesquisaCobertura))
                            .addComponent(jTxtNomeBem2)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel26)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTxtVlrPremioBem, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addGap(108, 108, 108)
                                    .addComponent(jTxtVlrCobertura, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBtnAddCobertura, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jBtnClonar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBtnExcluiCobertura, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTxtIdBem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTxtNomeBem2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBtnPesquisaCobertura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel24)
                        .addComponent(jTxtIdCobertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTxtNomeCobertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jTxtVlrCobertura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBtnAddCobertura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTxtVlrPremioBem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jBtnExcluiCobertura)
                        .addComponent(jBtnClonar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTpCadastro.addTab("Coberturas", jPanel4);

        jBtnIncluir.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnIncluir.setMnemonic('A');
        jBtnIncluir.setText("Adicionar");
        jBtnIncluir.setToolTipText("Insere mais um registro.");
        jBtnIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnIncluirActionPerformed(evt);
            }
        });

        jBtnEditar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnEditar.setMnemonic('E');
        jBtnEditar.setText("Editar");
        jBtnEditar.setEnabled(false);
        jBtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnEditarActionPerformed(evt);
            }
        });

        jBtnGravar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jBtnGravar.setMnemonic('G');
        jBtnGravar.setText("Gravar");
        jBtnGravar.setEnabled(false);
        jBtnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnGravarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBtnIncluir, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnIncluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jTpCadastro)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTpCadastro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnPesquisaEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaEmpresaActionPerformed
        var_consulta = 7;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaEmpresaActionPerformed

    private void jBtnPesquisaEstabelecimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaEstabelecimentoActionPerformed
        var_consulta = 1;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaEstabelecimentoActionPerformed

    private void jBtnPesquisaSeguradoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaSeguradoraActionPerformed
        var_consulta = 2;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaSeguradoraActionPerformed

    private void jBtnPesquisaCorretoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaCorretoraActionPerformed
        var_consulta = 3;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaCorretoraActionPerformed

    private void jBtnPesquisaTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaTipoActionPerformed
        var_consulta = 4;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaTipoActionPerformed

    private void jBtnIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnIncluirActionPerformed
        /*        limpaCampos();*/

        if ("Adicionar".equals(jBtnIncluir.getText())) {

            String sql = "TRUNCATE tmp_cad_seguros_bens_coberturas";
            
            cn.conecta();
            cn.executeAtualizacao(sql);
            cn.desconecta();

            habilitarEdicao();
            limpaTabelas();

            jBtnIncluir.setText("Cancelar");
            jBtnIncluir.setMnemonic('C');
            jBtnEditar.setEnabled(false);
        } else {

            jBtnIncluir.setMnemonic('A');

            desabilitarEdicao();
        }
    }//GEN-LAST:event_jBtnIncluirActionPerformed

    private void jBtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnEditarActionPerformed
        if ("Editar".equals(jBtnEditar.getText())) {
            habilitarEdicao();

            jBtnEditar.setText("Cancelar");
            jBtnIncluir.setEnabled(false);

            jBtnEditar.setMnemonic('C');

            jBtnPesquisaSeguradora.setEnabled(false);
            //jTxtIdSeguradora.setEnabled(false);
            jTxtApolice.setEnabled(false);
            jTxtEndosso.setEnabled(false);
        } else {
            //limpaCampos();
            jBtnEditar.setMnemonic('E');
            desabilitarEdicao();
        }
    }//GEN-LAST:event_jBtnEditarActionPerformed

    private void jBtnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnGravarActionPerformed
        String id_empresa = jTxtIdEmpresa.getText();
        String id_estabelecimento = jTxtIdEstabelecimento.getText();
        String id_seguradora = jTxtIdSeguradora.getText();
        String id_corretora = jTxtIdCorretora.getText();
        String apolice = jTxtApolice.getText();
        String endosso = jTxtEndosso.getText();
        String contrato = jTxtContrato.getText();
        String proposta = jTxtProposta.getText();
        String tiposeg = jTxtTipoSeg.getText();
        String dt_emissao = dateOut.format(jSDataEmissao.getValue());
        String vg_inicial = dateOut.format(jSDataInicial.getValue());
        String vg_final = dateOut.format(jSDataFinal.getValue());
        String premio = jTxtVlrPremio.getText().replace(",", ".");
        String adicional = jTxtVlrAdicional.getText().replace(",", ".");
        String custo = jTxtVlrCusto.getText().replace(",", ".");
        String iof = jTxtVlrIof.getText().replace(",", ".");
        String total = jTxtVlrTotal.getText().replace(",", ".");
        String juros = jTxtVlrJuros.getText().replace(",", ".");
        String tipocob = jTxtTipoCob.getText();
        String parcelas = jTxtParcelas.getText();
        String tipo = jTxtTipo.getText();
        String obs = jTxaObs.getText();

        //VARIÁVEIS INICIALIZADAS NO LAÇO
        String id_bem, cep, categoria, id_cobertura, valorcob, premiocob;
        
        
        cn.conecta();

//INSERINDO DADOS
        if (!"".equals(id_empresa)
                && !"".equals(id_corretora)
                && !"".equals(id_estabelecimento)
                && !"".equals(id_seguradora)
                && !"".equals(apolice)
                && !"".equals(endosso)
                && !"".equals(contrato)
                && !"".equals(proposta)
                && !"".equals(tiposeg)
                && !"".equals(premio)
                && !"".equals(adicional)
                && !"".equals(custo)
                && !"".equals(iof)
                && !"".equals(total)
                && !"".equals(juros)
                && !"".equals(tipocob)
                && !"".equals(parcelas)
                && !"".equals(tipo)
                && !"".equals(obs)
                && "Cancelar".equals(jBtnIncluir.getText())) {

            String sql = "INSERT INTO cad_seguros (id_empresa, id_estabelecimento, "
                    + "id_seguradora, id_corretor, apolice, endosso, contrato, "
                    + "proposta, bem_segurado, vigencia_inicial, vigencia_final, "
                    + "data_emissao, vlr_premio_liquido, vlr_adicional, vlr_custo, "
                    + "vlr_iof, vlr_premio_total, vlr_juros, tipo_cobranca, parcelas, "
                    + "tipo, observacoes) VALUES ('" + id_empresa + "','" + id_estabelecimento
                    + "','" + id_seguradora + "','" + id_corretora + "','" + apolice
                    + "','" + endosso + "','" + contrato + "','" + proposta
                    + "','" + tiposeg + "','" + vg_inicial + "','" + vg_final
                    + "','" + dt_emissao + "','" + premio + "','" + adicional
                    + "','" + custo + "','" + iof + "','" + total + "','" + juros
                    + "','" + tipocob + "','" + parcelas + "','" + tipo
                    + "','" + obs + "')";
            cn.executeAtualizacao(sql);

            for (int x = 0; x < jTblBens.getRowCount(); x++) {
                id_bem = String.valueOf(jTblBens.getValueAt(x, 0));
                cep = String.valueOf(jTblBens.getValueAt(x, 2));
                categoria = String.valueOf(jTblBens.getValueAt(x, 3));

                String sql1 = "INSERT INTO cad_seguros_bens (id_seguradora, id_seguro, "
                        + "id_bem, cep_pernoite, categoria_tarifaria,endosso) "
                        + "VALUES ('" + id_seguradora + "','" + apolice
                        + "','" + id_bem + "','" + cep + "','" + categoria + "','" + endosso + "')";
                cn.executeAtualizacao(sql1);
            }

            String sql2 = "INSERT INTO cad_seguros_bens_coberturas (id_seguradora,"
                    + "id_seguro,id_bem,id_cobertura,valor,vlr_premio,endosso) "
                    + "SELECT id_seguradora,id_seguro,id_bem,id_cobertura,valor,"
                    + "vlr_premio,endosso FROM tmp_cad_seguros_bens_coberturas;";
            cn.executeAtualizacao(sql2);

            desabilitarEdicao();

            // ALTERANDO DADOS
        } else if (!"".equals(id_empresa)
                && !"".equals(id_corretora)
                && !"".equals(id_estabelecimento)
                && !"".equals(id_seguradora)
                && !"".equals(apolice)
                && !"".equals(endosso)
                && !"".equals(contrato)
                && !"".equals(tiposeg)
                && !"".equals(premio)
                && !"".equals(adicional)
                && !"".equals(custo)
                && !"".equals(iof)
                && !"".equals(total)
                && !"".equals(juros)
                && !"".equals(tipocob)
                && !"".equals(parcelas)
                && !"".equals(tipo)
                && !"".equals(obs)
                && "Cancelar".equals(jBtnEditar.getText())) {

            String sql = "UPDATE cad_seguros SET id_empresa = '" + id_empresa + "',"
                    + "id_estabelecimento = '" + id_estabelecimento + "',id_corretor = '" + id_corretora + "',"
                    + "apolice = '" + apolice + "', endosso = '" + endosso + "', contrato = '" + contrato + "',"
                    + "proposta = '" + proposta + "', bem_segurado = '" + tiposeg + "',"
                    + "vigencia_inicial = '" + vg_inicial + "',vigencia_final = '" + vg_final + "',"
                    + "data_emissao = '" + dt_emissao + "', vlr_premio_liquido = '" + premio + "',"
                    + "vlr_adicional = '" + adicional + "',vlr_custo = '" + custo + "',"
                    + "vlr_iof = '" + iof + "', vlr_premio_total = '" + total + "',"
                    + "vlr_juros = '" + juros + "', tipo_cobranca = '" + tipocob + "',"
                    + "parcelas = '" + parcelas + "', tipo = '" + tipo + "',observacoes = '" + obs + "' "
                    + "WHERE id = '" + id_seguro + "'";

            String sql1 = "DELETE from cad_seguros_bens_coberturas WHERE id IN "
                    + "(SELECT ID from tmp_cad_seguros_bens_coberturas where condicao in (1,2));";

            String sql2 = "DELETE from cad_seguros_bens WHERE "
                    //+ "id_bem IN (SELECT ID_BEM from tmp_cad_seguros_bens_coberturas where condicao = 2) AND "
                    + "id_seguradora = '" + id_seguradora + "' "
                    + "AND id_seguro = '" + apolice + "'"
                    + "AND endosso = '" + endosso + "';";

            String sql4 = "INSERT INTO cad_seguros_bens_coberturas (id_seguradora,"
                    + "id_seguro,id_bem,id_cobertura,valor,vlr_premio,endosso) "
                    + "SELECT id_seguradora,id_seguro,id_bem,id_cobertura,valor,vlr_premio,endosso "
                    + "FROM tmp_cad_seguros_bens_coberturas "
                    + "WHERE condicao = 3;";

            cn.executeAtualizacao(sql);
            cn.executeAtualizacao(sql1);
            cn.executeAtualizacao(sql2);

            for (int x = 0; x < jTblBens.getRowCount(); x++) {
                id_bem = String.valueOf(jTblBens.getValueAt(x, 0));
                cep = String.valueOf(jTblBens.getValueAt(x, 2));
                categoria = String.valueOf(jTblBens.getValueAt(x, 3));

                String sql3 = "INSERT INTO cad_seguros_bens (id_seguradora, id_seguro, "
                        + "id_bem, cep_pernoite, categoria_tarifaria,endosso) "
                        + "VALUES ('" + id_seguradora + "','" + apolice
                        + "','" + id_bem + "','" + cep + "','" + categoria + "','" + endosso + "');";

                //cn.conecta();
                cn.executeAtualizacao(sql3);
                //cn.desconecta();
            }
            cn.executeAtualizacao(sql4);

            desabilitarEdicao();

        } else {
            JOptionPane.showMessageDialog(rootPane, "Algum campo não está preenchido!");
        }
        cn.desconecta();
        
    }//GEN-LAST:event_jBtnGravarActionPerformed

    private void jBtnPesquisaBemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaBemActionPerformed
        var_consulta = 5;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaBemActionPerformed

    private void jBtnAddBem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddBem1ActionPerformed
        String id_bem = jTxtIdBem1.getText();
        String nome_bem = jTxtNomeBem1.getText();
        String cep = jTxtCep.getText();
        String categoria = jTxtCategoria.getText();

        DefaultTableModel lista = (DefaultTableModel) jTblCoberturas.getModel();

        if (!"".equals(nome_bem)
                && !"".equals(cep)
                && !"".equals(categoria)) {
            DefaultTableModel bens = (DefaultTableModel) jTblBens.getModel();
            bens.addRow(new String[]{id_bem, nome_bem, cep, categoria});

            jTxtIdBem1.setText("");
            jTxtNomeBem1.setText("");
            jTxtCep.setText("");
            jTxtCategoria.setText("");

            jTxtIdBem2.setText(id_bem);
            jTxtNomeBem2.setText(nome_bem);

            jTpCadastro.setSelectedIndex(2);

            lista.setRowCount(0);

        } else {
            JOptionPane.showMessageDialog(rootPane, "Até dá pra lançar por aqui, mas você precisa digitar os dados.");
        }
    }//GEN-LAST:event_jBtnAddBem1ActionPerformed

    private void jBtnExcluiBem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExcluiBem1ActionPerformed
        if (jTblBens.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Ô, tontão. Tu precisa selecionar uma linha!");
        } else if ("Editar".equals(jBtnEditar.getText())) {
            for (int x = jTblBens.getSelectedRowCount(); x != 0; x--) {
                try {
                    int linha = jTblBens.getSelectedRow();

                    String id_bem = jTblBens.getValueAt(linha, 0).toString();

                    String sql = "DELETE FROM tmp_cad_seguros_bens_coberturas "
                            + "where id_bem = '" + id_bem + "'";

                    cn.conecta();
                    cn.executeAtualizacao(sql);
                    cn.desconecta();

                    ((DefaultTableModel) jTblBens.getModel()).removeRow(jTblBens.getSelectedRow());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível excluir a linha: " + e);
                }

            }
        } else {
            for (int x = jTblBens.getSelectedRowCount(); x != 0; x--) {
                try {
                    int linha = jTblBens.getSelectedRow();

                    String id_bem = jTblBens.getValueAt(linha, 0).toString();

                    String sql = "UPDATE tmp_cad_seguros_bens_coberturas SET condicao  = '2'"
                            + "where id_bem = '" + id_bem + "'";

                    cn.conecta();
                    cn.executeAtualizacao(sql);
                    cn.desconecta();

                    ((DefaultTableModel) jTblBens.getModel()).removeRow(jTblBens.getSelectedRow());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível excluir a linha: " + e);
                }

            }
        }
    }//GEN-LAST:event_jBtnExcluiBem1ActionPerformed

    private void jBtnPesquisaCoberturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnPesquisaCoberturaActionPerformed
        var_consulta = 6;

        jPesquisar.setVisible(true);

        MontaLista();
    }//GEN-LAST:event_jBtnPesquisaCoberturaActionPerformed

    private void jTxtVlrCoberturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtVlrCoberturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtVlrCoberturaActionPerformed

    private void jBtnExcluiCoberturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnExcluiCoberturaActionPerformed
        int linha = jTblCoberturas.getSelectedRow();

        
        String id_bem = jTblCoberturas.getValueAt(linha, 0).toString();
        String id_cobertura = jTblCoberturas.getValueAt(linha, 2).toString();

        if (jTblCoberturas.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(rootPane, "Ô, tontão. Tu precisa selecionar uma linha!");
        } else if ("Editar".equals(jBtnEditar.getText())) {
            for (int x = jTblCoberturas.getSelectedRowCount(); x != 0; x--) {
                try {

                    String sql = "DELETE FROM tmp_cad_seguros_bens_coberturas "
                            + "where id_bem = '" + id_bem + "' and id_cobertura = '" + id_cobertura + "'";

                    cn.conecta();
                    cn.executeAtualizacao(sql);
                    cn.desconecta();

                    ((DefaultTableModel) jTblCoberturas.getModel()).removeRow(jTblCoberturas.getSelectedRow());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível excluir a linha: " + e);
                }
            }
        } else {
            for (int x = jTblCoberturas.getSelectedRowCount(); x != 0; x--) {
                try {

                    String sql = "UPDATE tmp_cad_seguros_bens_coberturas SET condicao = '1' "
                            + "where id_bem = '" + id_bem + "' and id_cobertura = '" + id_cobertura + "'";

                    cn.conecta();
                    cn.executeAtualizacao(sql);
                    cn.desconecta();

                    ((DefaultTableModel) jTblCoberturas.getModel()).removeRow(jTblCoberturas.getSelectedRow());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Não foi possível excluir a linha: " + e);
                }
            }
        }
    }//GEN-LAST:event_jBtnExcluiCoberturaActionPerformed

    private void jBtnAddCoberturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAddCoberturaActionPerformed
        String id_bem = jTxtIdBem2.getText();
        String nome_bem = jTxtNomeBem2.getText();
        String id_cob = jTxtIdCobertura.getText();
        String nome_cobertura = jTxtNomeCobertura.getText();
        String valor = jTxtVlrCobertura.getText().replace(",", ".");
        String premio = jTxtVlrPremioBem.getText().replace(",", ".");
        String seguro = jTxtApolice.getText();
        String seguradora = jTxtIdSeguradora.getText();
        String endosso = jTxtEndosso.getText();

        if (!"".equals(nome_bem)
                && !"".equals(nome_cobertura)
                && !"".equals(premio)
                && !"".equals(seguro)
                && !"".equals(seguradora)
                && !"".equals(valor)) {

            DefaultTableModel bens = (DefaultTableModel) jTblCoberturas.getModel();

            //TROCAR MÉTODO, PARA QUE APAREÇA NA JTABLE APENAS DEPOIS QUE INSERIR NO BANCO
            bens.addRow(new String[]{id_bem, nome_bem, id_cob, nome_cobertura, valor, premio});

            //POPULA O BANCO. DEPOIS, BUSCA DOS DADOS VIA SELECT E POPULA A JTABLE
            String sql = "INSERT INTO tmp_cad_seguros_bens_coberturas (id_seguradora,id_seguro,"
                    + "id_bem,id_cobertura,valor,vlr_premio,endosso,condicao) VALUES ('" + seguradora + "','" + seguro + "',"
                    + "'" + id_bem + "','" + id_cob + "','" + valor + "','" + premio + "','" + endosso + "',3)";

            
            cn.conecta();
            cn.executeAtualizacao(sql);
            cn.desconecta();

            //jTxtIdBem2.setText("");
            //jTxtNomeBem2.setText("");
            jTxtIdCobertura.setText("");
            jTxtNomeCobertura.setText("");
            jTxtVlrCobertura.setText("");
            jTxtVlrPremioBem.setText("");

            //jTpCadastro.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(rootPane, "Até dá pra lançar por aqui, mas você precisa digitar os dados.");
        }
    }//GEN-LAST:event_jBtnAddCoberturaActionPerformed

    private void jTxtPesquisa_MultiKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtPesquisa_MultiKeyReleased
        MontaLista();
    }//GEN-LAST:event_jTxtPesquisa_MultiKeyReleased

    private void jBtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnConfirmarActionPerformed
        IncluiPesquisa();
        jPesquisar.dispose();
        jTxtPesquisa_Multi.setText("");
// TODO add your handling code here:
    }//GEN-LAST:event_jBtnConfirmarActionPerformed

    private void jBtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCancelarActionPerformed
        jPesquisar.setVisible(false);        // TODO add your handling code here:
        jPesquisar.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnCancelarActionPerformed

    private void jTblConsulta_MultiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblConsulta_MultiMouseClicked
        jBtnConfirmar.setEnabled(true);    // TODO add your handling code here:
    }//GEN-LAST:event_jTblConsulta_MultiMouseClicked

    private void jTblBensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTblBensMouseClicked
        int linha = jTblBens.getSelectedRow();

        DefaultTableModel lista = (DefaultTableModel) jTblCoberturas.getModel();
        lista.setRowCount(0);

        String id_bem = jTblBens.getValueAt(linha, 0).toString();
        String nome_bem = jTblBens.getValueAt(linha, 1).toString();
        String seguradora = jTxtIdSeguradora.getText();

        String sql = "SELECT a.id_bem,b.descricao,a.id_cobertura,c.descricao,a.valor,a.vlr_premio "
                + "FROM tmp_cad_seguros_bens_coberturas a "
                + "LEFT JOIN cad_bens b on (b.id = a.id_bem) "
                + "LEFT JOIN cad_seguros_coberturas c on (c.id = a.id_cobertura) "
                + "WHERE a.id_bem = '" + id_bem + "' "
                + "AND a.condicao in (0,3);";

        jTxtIdBem2.setText(id_bem);
        jTxtNomeBem2.setText(nome_bem);

        jTpCadastro.setSelectedIndex(2);

        
        cn.conecta();
        cn.executeConsulta(sql);

        try {
            while (cn.rs.next()) {

                lista.addRow(new String[]{
                    cn.rs.getString(1),
                    cn.rs.getString(2),
                    cn.rs.getString(3),
                    cn.rs.getString(4),
                    cn.rs.getString(5).replace(".", ","),
                    cn.rs.getString(6).replace(".", ",")});
            }
        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(null, "Erro ao consultar o banco de dados: " + ex);
        }
        cn.desconecta();

        /*jTxtIdBem1.setText(jTblBens.getValueAt(linha, 0).toString());
        jTxtNomeBem1.setText(jTblBens.getValueAt(linha, 1).toString());
        jTxtCep.setText(jTblBens.getValueAt(linha, 2).toString());
        jTxtCategoria.setText(jTblBens.getValueAt(linha, 3).toString());*/
    }//GEN-LAST:event_jTblBensMouseClicked

    private void jTxtTipoSegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTxtTipoSegActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTxtTipoSegActionPerformed

    private void jBtnClonarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnClonarActionPerformed
        if (!"".equals(jTxtIdBem2.getText())) {
            var_consulta = 8;
            jPesquisar.setVisible(true);
            MontaLista();

        } else {
            JOptionPane.showMessageDialog(this, "Selecione um Bem!");
        }

    }//GEN-LAST:event_jBtnClonarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAddBem1;
    private javax.swing.JButton jBtnAddCobertura;
    private javax.swing.JButton jBtnCancelar;
    private javax.swing.JButton jBtnClonar;
    private javax.swing.JButton jBtnConfirmar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnExcluiBem1;
    private javax.swing.JButton jBtnExcluiCobertura;
    private javax.swing.JButton jBtnGravar;
    private javax.swing.JButton jBtnIncluir;
    private javax.swing.JButton jBtnPesquisaBem;
    private javax.swing.JButton jBtnPesquisaCobertura;
    private javax.swing.JButton jBtnPesquisaCorretora;
    private javax.swing.JButton jBtnPesquisaEmpresa;
    private javax.swing.JButton jBtnPesquisaEstabelecimento;
    private javax.swing.JButton jBtnPesquisaSeguradora;
    private javax.swing.JButton jBtnPesquisaTipo;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLblData;
    private javax.swing.JLabel jLblData1;
    private javax.swing.JLabel jLblData2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JFrame jPesquisar;
    private javax.swing.JSpinner jSDataEmissao;
    private javax.swing.JSpinner jSDataFinal;
    private javax.swing.JSpinner jSDataInicial;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTblBens;
    private javax.swing.JTable jTblCoberturas;
    private javax.swing.JTable jTblConsulta_Multi;
    private javax.swing.JTabbedPane jTpCadastro;
    private javax.swing.JTextArea jTxaObs;
    private javax.swing.JTextField jTxtApolice;
    private javax.swing.JTextField jTxtCategoria;
    private javax.swing.JTextField jTxtCep;
    private javax.swing.JTextField jTxtContrato;
    private javax.swing.JTextField jTxtEndosso;
    private javax.swing.JTextField jTxtIdBem1;
    private javax.swing.JTextField jTxtIdBem2;
    private javax.swing.JTextField jTxtIdCobertura;
    private javax.swing.JTextField jTxtIdCorretora;
    private javax.swing.JTextField jTxtIdEmpresa;
    private javax.swing.JTextField jTxtIdEstabelecimento;
    private javax.swing.JTextField jTxtIdSeguradora;
    private javax.swing.JTextField jTxtNomeBem1;
    private javax.swing.JTextField jTxtNomeBem2;
    private javax.swing.JTextField jTxtNomeCobertura;
    private javax.swing.JTextField jTxtNomeCorretora;
    private javax.swing.JTextField jTxtNomeEmpresa;
    private javax.swing.JTextField jTxtNomeEstabelecimento;
    private javax.swing.JTextField jTxtNomeSeguradora;
    private javax.swing.JTextField jTxtParcelas;
    private javax.swing.JTextField jTxtPesquisa_Multi;
    private javax.swing.JTextField jTxtProposta;
    private javax.swing.JTextField jTxtTipo;
    private javax.swing.JTextField jTxtTipoCob;
    private javax.swing.JTextField jTxtTipoSeg;
    private javax.swing.JTextField jTxtVlrAdicional;
    private javax.swing.JTextField jTxtVlrCobertura;
    private javax.swing.JTextField jTxtVlrCusto;
    private javax.swing.JTextField jTxtVlrIof;
    private javax.swing.JTextField jTxtVlrJuros;
    private javax.swing.JTextField jTxtVlrPremio;
    private javax.swing.JTextField jTxtVlrPremioBem;
    private javax.swing.JTextField jTxtVlrTotal;
    // End of variables declaration//GEN-END:variables
}
