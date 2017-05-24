package spedCadastro;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Properties;
import sun.security.pkcs11.SunPKCS11;

public abstract class AbstractSefazServiceConfig implements SefazServiceConfig {

    @SuppressWarnings("unused")
    private void setPropertiesA3() {
        Provider p = new SunPKCS11("{config for SUNPKCS 11 in config.cfg}");
        Security.addProvider(p);
        Properties properties = System.getProperties();
        properties.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        properties.setProperty("javax.net.ssl.keyStoreType", "PKCS11");
        properties.setProperty("javax.net.ssl.keyStore", "NONE");
        properties.setProperty("javax.net.ssl.keyStoreProvider", "SunPKCS11-SCR3310");
        properties.setProperty("javax.net.ssl.keyStorePassword", "1234");
        properties.setProperty("javax.net.ssl.trustStoreType", "JKS");
        properties.setProperty("javax.net.ssl.trustStore", "{seu key store path}");
        //
        try {
            KeyStore ks = KeyStore.getInstance("PKCS11");
            ks.load(null, "1234".toCharArray()); //TODO password for keystore
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPropertiesA1() {
        //preparar as propriedades
        Properties properties = System.getProperties();
        properties.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        properties.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        properties.setProperty("javax.net.ssl.keyStore", "cliente.pfx");//Aqui vem o arquivo do certificado do seu cliente  
        properties.setProperty("javax.net.ssl.keyStorePassword", "01510933000");//Aqui a senha deste certificado
        properties.setProperty("javax.net.ssl.trustStoreType", "JKS");
        properties.setProperty("javax.net.ssl.trustStore", "NFeCacerts");//Aqui vem o arquivo criado atravÃ©s do comando keytool no passo 3
        properties.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        properties.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        //properties.setProperty("javax.net.debug", "all"); //- for debug
    }

    @Override
    public final void setProperties() {
        setPropertiesA1();
    }
}
