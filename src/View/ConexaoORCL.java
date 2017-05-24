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

public class ConexaoORCL {

    private Connection conexao;
    private Statement st;
    public ResultSet rs;

    String url, driver, usuario, senha;

    public static int resultadoUpd = 99;
    
    public static Properties getProp() throws IOException {
        
        Properties props = new Properties();
        
        FileInputStream file = new FileInputStream(
                "./src/properties/orcl.properties");
        props.load(file);
        
        return props;

    }

    public void conecta() {

        try {
            Properties props = getProp();
            driver = props.getProperty("driver");
            url = props.getProperty("url");
            
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, props);
            //st = conexao.createStatement();

            conexao.setAutoCommit(false);
            
            resultadoUpd = 0;
            
            

            //st.executeUpdate("begin");

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o "
                    + "driver do banco de dados." + ex);
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Erro na conexão com o banco de dados. " + sqlEx);
        } catch (IOException ex) {
            Logger.getLogger(ConexaoORCL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconecta() {

        try {

            switch (resultadoUpd) {
                case 0:
                    //st = conexao.createStatement();
                    //st.executeUpdate("commit");
                    conexao.commit();
                    break;
                default:
                    conexao.rollback();
                    //st = conexao.createStatement();
                    //st.executeUpdate("rollback");
            }

            conexao.close();

        } catch (SQLException sqlEx) {

            JOptionPane.showMessageDialog(null, "Não foi possível desconectar o banco " + sqlEx);

        }

    }

    public void executeAtualizacao(String sql) {

        try {
            st = conexao.createStatement();
            st.executeUpdate(sql);

        } catch (SQLException sqlEx) {
            resultadoUpd = 1;
            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql" + sql + ".Erro " + sqlEx + " upd " + resultadoUpd);

        }

    }

    public void executeConsulta(String sql) {

        try {
            st = conexao.createStatement();

            rs = st.executeQuery(sql);

        } catch (SQLException sqlEx) {

            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql" + sql + "Erro " + sqlEx);

        }

    }
}
