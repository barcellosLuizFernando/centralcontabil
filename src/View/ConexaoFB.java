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

public class ConexaoFB {

    private Connection conexao;
    private Statement st;
    public ResultSet rs;

    String url, driver, usuario, senha;

    public static int resultadoUpd;

    public static Properties getProp() throws IOException {

        Properties props = new Properties();

        FileInputStream file = new FileInputStream(
                "./src/properties/fb.properties");
        props.load(file);
        file.close();
        
        System.out.println("Firebird - Arquivo de Propriedades Capturado.");
        return props;

    }

    public void conecta() {

        try {
            Properties props = getProp();
            driver = props.getProperty("driver");
            url = props.getProperty("url");

            Class.forName(driver);
            conexao = DriverManager.getConnection(url, props);
            st = conexao.createStatement();

            resultadoUpd = 0;
            
            System.out.println("Firebird - Conectado com o Banco de Dados.");

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar o "
                    + "driver do banco de dados." + ex);
        } catch (SQLException sqlEx) {
            JOptionPane.showMessageDialog(null, "Erro na conexão com o banco de dados. " + sqlEx);
        } catch (IOException ex) {
            Logger.getLogger(ConexaoFB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconecta() {

        try {

            switch (resultadoUpd) {
                case 0:
                    st = conexao.createStatement();
                    break;
                default:
            }

            conexao.close();
            System.out.println("Firebird - Desconectado com o Banco de Dados.");
        } catch (SQLException sqlEx) {

            JOptionPane.showMessageDialog(null, "Não foi possível desconectar o banco " + sqlEx);

        }

    }

    public void executeConsulta(String sql) {

        try {
            st = conexao.createStatement();

            rs = st.executeQuery(sql);
            
            System.out.println("Firebird - Executando consulta ao Banco de dados: " + sql);

        } catch (SQLException sqlEx) {

            JOptionPane.showMessageDialog(null, "Não foi possível executar o comando sql" + sql + "Erro " + sqlEx);

        }

    }
}
