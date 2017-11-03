/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedCadastro;

import ferramenta.InfEstado;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JOptionPane;
import javax.xml.soap.SOAPException;

/**
 *
 * @author luiz.barcellos
 */
public class SefazServiceReception {
    
    InfEstado est = new InfEstado();

    public static void main(String[] args) throws SOAPException, Exception {
        /*//Cria a classe de configuração
        SefazServiceConfig config = new SefazServiceReceptionConfig();
        //carrega o xml, aqui deve ter apenas o xml, (<enviNFe xmlns="http://www.portalfiscal.inf.br/nfe" versao="2.00">.....</enviNFe>)
        InputStream xml = new FileInputStream("src/spedCadastro/consultaCadastro.xml");
        //instancia a classe de service
        SefazService sefazService = new SefazService(config);
        //chama o método para enviar o xml
        ByteArrayOutputStream result = (ByteArrayOutputStream) sefazService.sendXML(xml, "41");

        if (result != null) {

            InputStream in = new ByteArrayInputStream(result.toByteArray());
            File arquivo = new File("src/spedCadastro/ResultadoPesquisa.xml");
            FileOutputStream fout = new FileOutputStream(arquivo);
            copy(in, fout);
            result.writeTo(System.out);//imprime o retorno da Sefaz
        }*/
        SefazServiceReception rec = new SefazServiceReception();
        rec.consultaCad("SC", "CONS-CAD", "H");
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

    public boolean consultaCad(String uf, String servico, String ambiente) throws SOAPException, Exception {

        boolean resposta = false;
        String arquivoConsulta = null;
        String arquivoResposta = null;

        switch (servico) {
            case "CONS-CAD":
                arquivoConsulta = "src/spedCadastro/consultaCadastro.xml";
                arquivoResposta = "src/spedCadastro/ResultadoPesquisa.xml";
                break;

            default:
                JOptionPane.showMessageDialog(null, "Não foi definido o caminho para o arquivos da pesquisa " + servico);
                break;
        }

        //Cria a classe de configuração
        SefazServiceConfig config = new SefazServiceReceptionConfig();
        config.carregaConfig(uf, servico, ambiente);
        //carrega o xml, aqui deve ter apenas o xml, (<enviNFe xmlns="http://www.portalfiscal.inf.br/nfe" versao="2.00">.....</enviNFe>)
        InputStream xml = new FileInputStream(arquivoConsulta);
        //instancia a classe de service
        SefazService sefazService = new SefazService(config);
        //chama o método para enviar o xml
        est.carregaDados(uf);
        ByteArrayOutputStream result = (ByteArrayOutputStream) sefazService.sendXML(xml, est.getCod_uf());

        if (result != null) {

            InputStream in = new ByteArrayInputStream(result.toByteArray());
            File arquivo = new File(arquivoResposta);
            FileOutputStream fout = new FileOutputStream(arquivo);
            copy(in, fout);
            
            System.out.println("\nRETORNO DA SEFAZ: ");
            result.writeTo(System.out);//imprime o retorno da Sefaz

            resposta = true;

        }

        return resposta;
    }
}
