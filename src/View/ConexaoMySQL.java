package View;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexaoMySQL {

    private Connection conexao;
    private Statement st;
    public ResultSet rs;

    String url, driver, usuario, senha;

    public static int resultadoUpd = 99;

    public static Properties getProp() throws IOException {

        Properties props = new Properties();

        FileInputStream file = new FileInputStream(
                "./src/properties/mysql.properties");
        props.load(file);
        file.close();
        System.out.println("MySQL - Arquivo de Propriedades Capturado.");
        return props;

    }

    public boolean conecta() {
        boolean resposta = true;
        try {
            Properties props = getProp();
            driver = props.getProperty("driver");
            url = props.getProperty("url");

            Class.forName(driver);
            conexao = DriverManager.getConnection(url, props);
            st = conexao.createStatement();

            resultadoUpd = 0;

            st.executeUpdate("begin;");

            System.out.println("MySQL - Conectado com o Banco de Dados.");

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o "
                    + "driver do banco de dados." + ex);
            resposta = false;
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Erro na conexão com o banco de dados. " + sqlEx);
            resposta = false;
        } catch (IOException ex) {
            Logger.getLogger(ConexaoMySQL.class.getName()).log(Level.SEVERE, null, ex);
            resposta = false;
        }

        return resposta;
    }

    public void desconecta() {

        try {

            switch (resultadoUpd) {
                case 0:
                    st = conexao.createStatement();
                    st.executeUpdate("commit;");
                    break;
                default:
                    st = conexao.createStatement();
                    st.executeUpdate("rollback;");
            }

            conexao.close();
            System.out.println("MySQL - Desconectado com o Banco de Dados.");

        } catch (SQLException sqlEx) {

            JOptionPane.showMessageDialog(null, "Não foi possível desconectar o banco " + sqlEx);

        }

    }

    public boolean executeAtualizacao(String sql) {
        boolean resposta = true;
        try {
            System.out.println("MySQL - Executando atualização: " + sql);
            st = conexao.createStatement();
            st.executeUpdate(sql);


        } catch (SQLException sqlEx) {
            resultadoUpd = 1;
            resposta = false;
            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql" + sql + ".Erro " + sqlEx + " upd " + resultadoUpd);
        }
        return resposta;
    }

    public boolean executeConsulta(String sql) {

        boolean resposta = true;

        try {
            st = conexao.createStatement();

            rs = st.executeQuery(sql);

            System.out.println("MySQL - Executando consulta: " + sql);

        } catch (SQLException sqlEx) {

            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql" + sql + "Erro " + sqlEx);
            resposta = false;
        }

        return resposta;
    }
}
