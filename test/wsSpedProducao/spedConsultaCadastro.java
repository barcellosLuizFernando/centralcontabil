/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wsSpedProducao;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author ferna
 */
public class spedConsultaCadastro {

    private String escreveXML() throws Exception {

        ObjectFactory objectFactory = new ObjectFactory();
        final StringWriter out = new StringWriter();
        ConsultaCadastro2.NfeDadosMsg detalhes = new ConsultaCadastro2.NfeDadosMsg();
        ConsultaCadastro2 terc = new ConsultaCadastro2();
        NfeCabecMsg nfeCabecMsg = new NfeCabecMsg();

        nfeCabecMsg.setCUF("PR");
        nfeCabecMsg.setVersaoDados("3.10");

        detalhes.getContent().add(nfeCabecMsg);

        terc.setNfeDadosMsg(detalhes);

        /*JAXBContext context = null;

        try {
            context = JAXBContext.newInstance(terc.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(
                    javax.xml.bind.Marshaller.JAXB_FRAGMENT,
                    Boolean.TRUE
            );
            marshaller.marshal(terc, new StreamResult(out));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível escrever o XML: " + e);
        }

        System.out.println("XML escrito: " + out.toString());
         */
        String msg
                = "<nfeDadosMsg>"
                + "<consCad versao=\"2.00\" xmlns=\"http://www.portalfiscal.inf.br/nfe\">"
                + "<infCons>"
                + "<xServ>CONS-CAD</xServ>"
                + "<UF>RS</UF>"
                + "<IE>0000</IE>"
                + "<CNPJ>000000</CNPJ>"
                + "</infCons>"
                + "</consCad>"
                + "</nfeDadosMsg>";

        XMLStreamReader dadosXML = XMLInputFactory.newInstance().createXMLStreamReader(new StringReader(msg));
        //ConsultaCadastro2.NfeDadosMsg x = ConsultaCadastro2.NfeDadosMsg.Factory.parse(dadosXML);
        
        
        System.out.println("xmls " + dadosXML.getElementText());

        return out.toString();

    }

    public static void main(String[] args) {
        spedConsultaCadastro cons = new spedConsultaCadastro();

        try {
            cons.escreveXML();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
