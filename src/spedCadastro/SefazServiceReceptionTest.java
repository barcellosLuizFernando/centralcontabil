/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedCadastro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.soap.SOAPException;

/**
 *
 * @author luiz.barcellos
 */
public class SefazServiceReceptionTest {

    public static void main(String[] args) throws SOAPException, Exception {
        //Cria a classe de configuração
        SefazServiceConfig config = new SefazServiceReceptionConfig();
        //carrega o xml, aqui deve te r apenas o xml, (<enviNFe xmlns="http://www.portalfiscal.inf.br/nfe" versao="2.00">.....</enviNFe>)
        InputStream xml = new FileInputStream("consultaCadastro.xml");
        //instância a classe de service
        SefazService sefazService = new SefazService(config);
        //chama o método para enviar o xml
        ByteArrayOutputStream result = (ByteArrayOutputStream) sefazService.sendXML(xml, "41");

        if (result != null) {

            InputStream in = new ByteArrayInputStream(result.toByteArray());
            File arquivo = new File("src/xml/ResultadoPesquisa.xml");
            FileOutputStream fout = new FileOutputStream(arquivo);
            copy(in, fout);
            result.writeTo(System.out);//imprime o retorno da Sefaz
        }
    }

    private static void copy(InputStream in, FileOutputStream out) throws IOException {

        byte[] buffer = new byte[1024 * 4]; //4 Kb
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
        }
        out.flush();

        out.close();
        in.close();
    }
}
