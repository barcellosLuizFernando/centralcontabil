/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedCadastro;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author luiz.barcellos
 */
public class RespostaCadastro {

    private SefazServiceReception cons = new SefazServiceReception();

    private String cUF;
    private String versaoDados;
    private String verAplic;
    private String cStat;
    private String xMotivo;
    private String uf;
    private String cnpj;
    private String dhCons;
    private String cUF2;
    private String ie;
    private String cnpj2;
    private String uf2;
    private String cSit;
    private String indCredNFe;
    private String indCredCTe;
    private String xNome;
    private String xFant;
    private String xRegApur;
    private String cnae;
    private String dIniAtiv;
    private String dUltSit;
    private String xLgr;
    private String nro;
    private String xBairro;
    private String cMun;
    private String xMun;
    private String cep;
    private String xCpl;

    private boolean leXML() {

        boolean resposta = true;

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse("src/spedCadastro/ResultadoPesquisa.xml");

            //CAPTURANDO DADOS DO nfeCabecMsg
            NodeList nfeCabecMsg = doc.getElementsByTagName("nfeCabecMsg");

            int tamNfeCabecMsg = nfeCabecMsg.getLength();
            System.out.println("CABEC - Tamanho da lista: " + tamNfeCabecMsg);

            for (int i = 0; i < tamNfeCabecMsg; i++) {

                Node noNfeCabecMsg = nfeCabecMsg.item(i);

                System.out.println("CABEC - Volta " + i + ", lendo: " + noNfeCabecMsg);

                if (noNfeCabecMsg.getNodeType() == Node.ELEMENT_NODE) {

                    Element elementoNfeCabecMsg = (Element) noNfeCabecMsg;

                    //CAPTURA DADOS DO ATRIBUTO
                    String xmlns = elementoNfeCabecMsg.getAttribute("xmlns");
                    System.out.println("XMLns: " + xmlns + "\n");

                    //CAPUTRA DADOS DOS FILHOS
                    NodeList filhos = elementoNfeCabecMsg.getChildNodes();
                    int tamanho = filhos.getLength();

                    System.out.println("\nCABEC Filho - Tamanho da lista: " + tamanho);

                    for (int j = 0; j < tamanho; j++) {
                        Node noFilho = filhos.item(j);

                        System.out.println("\nCabec Filho - volta " + j + ", lendo: " + noFilho);

                        if (noFilho.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilho = (Element) noFilho;
                            switch (elementoFilho.getTagName()) {
                                case "cUF":
                                    cUF = elementoFilho.getTextContent();
                                    System.out.println("Cabec - cUF: " + cUF + "\n");
                                    break;
                                case "versaoDados":
                                    versaoDados = elementoFilho.getTextContent();
                                    System.out.println("Cabec - Versão Dados: " + versaoDados + "\n");
                                    break;
                            }
                        }
                    }
                }
            }

            //CAPTURANDO DADOS DO nfeCabecMsg
            NodeList consultaCadastro2Result = doc.getElementsByTagName("infCons");

            int tamResult = consultaCadastro2Result.getLength();

            System.out.println("\n\nRESULTADO - Tamanho da lista: " + tamResult);

            for (int i = 0; i < tamResult; i++) {

                Node noResult = consultaCadastro2Result.item(i);

                System.out.println("\nResultado - Volta " + i + ", lendo: " + noResult);

                if (noResult.getNodeType() == Node.ELEMENT_NODE) {

                    Element elementoResult = (Element) noResult;

                    //CAPTURA DADOS DO ATRIBUTO
                    String xmlns = elementoResult.getAttribute("xmlns");
                    System.out.println("XMLns: " + xmlns + "\n");

                    //CAPUTRA DADOS DOS FILHOS
                    NodeList filhos = elementoResult.getChildNodes();
                    int tamanho = filhos.getLength();

                    for (int j = 0; j < tamanho; j++) {
                        Node noFilho = filhos.item(j);

                        System.out.println("\nResult Filho - volta " + j + ", lendo: " + noFilho);

                        if (noFilho.getNodeType() == Node.ELEMENT_NODE) {
                            Element elementoFilho = (Element) noFilho;
                            switch (elementoFilho.getTagName()) {
                                case "verAplic":
                                    verAplic = elementoFilho.getTextContent();
                                    System.out.println("Resultado - Versão Aplicação: " + verAplic);
                                    break;
                                case "cStat":
                                    cStat = elementoFilho.getTextContent();
                                    System.out.println("Resultado - Situação: " + cStat);
                                    break;
                                case "xMotivo":
                                    xMotivo = elementoFilho.getTextContent();
                                    System.out.println("Resultado - Motivo: " + xMotivo);
                                    break;
                                case "UF":
                                    uf = elementoFilho.getTextContent();
                                    System.out.println("Resultado - UF_infCons: " + uf);
                                    break;
                                case "CNPJ":
                                    cnpj = elementoFilho.getTextContent();
                                    System.out.println("Resultado - CNPJ_infCons: " + cnpj);
                                    break;
                                case "dhCons":
                                    dhCons = elementoFilho.getTextContent();
                                    System.out.println("Resultado - dhCons: " + dhCons);
                                    break;
                                case "cUF":
                                    cUF = elementoFilho.getTextContent();
                                    System.out.println("Resultado - cUF_infCons: " + cUF);
                                    break;
                                case "infCad":
                                    //JOptionPane.showMessageDialog(null, "Informações do Resultado.");

                                    //CAPTURANDO DADOS DO infCAD
                                    NodeList infCad = doc.getElementsByTagName("infCad");

                                    int tamInfCad = infCad.getLength();
                                    System.out.println("\n\nRESULTADO infCad - Tamanho da lista: " + tamInfCad);

                                    for (int k = 0; k < tamInfCad; k++) {

                                        Node noInfCad = infCad.item(k);

                                        System.out.println("RESULTADO infCad - Volta " + k + ", lendo: " + noInfCad);

                                        if (noInfCad.getNodeType() == Node.ELEMENT_NODE) {

                                            Element elementoInfCad = (Element) noInfCad;

                                            //CAPTURA DADOS DO ATRIBUTO
                                            xmlns = elementoInfCad.getAttribute("xmlns");
                                            System.out.println("XMLns: " + xmlns + "\n");

                                            //CAPUTRA DADOS DOS FILHOS
                                            NodeList filhos2 = elementoInfCad.getChildNodes();
                                            int tamanho2 = filhos2.getLength();

                                            System.out.println("\nResultado infCad - Tamanho da lista: " + tamanho2);

                                            for (int l = 0; l < tamanho2; l++) {
                                                Node noFilho2 = filhos2.item(l);

                                                System.out.println("\nResultado infCad Filho - volta " + l + ", lendo: " + noFilho2);

                                                if (noFilho2.getNodeType() == Node.ELEMENT_NODE) {
                                                    Element elementoFilho2 = (Element) noFilho2;
                                                    switch (elementoFilho2.getTagName()) {
                                                        case "IE":
                                                            ie = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - IE: " + ie);
                                                            break;
                                                        case "CNPJ":
                                                            cnpj2 = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - CNPJ: " + cnpj2);
                                                            break;
                                                        case "UF":
                                                            uf2 = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - UF: " + uf2);
                                                            break;
                                                        case "cSit":
                                                            cSit = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - cSit: " + cSit);
                                                            break;
                                                        case "indCredNFe":
                                                            indCredNFe = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - indCredNFe: " + indCredNFe);
                                                            break;
                                                        case "indCredCTe":
                                                            indCredCTe = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - indCredCTe: " + indCredCTe);
                                                            break;
                                                        case "xNome":
                                                            xNome = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - xNome: " + xNome);
                                                            break;
                                                        case "xFant":
                                                            xFant = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - xFant: " + xFant);
                                                            break;
                                                        case "xRegApur":
                                                            xRegApur = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - xRegApur: " + xRegApur);
                                                            break;
                                                        case "CNAE":
                                                            cnae = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - CNAE: " + cnae);
                                                            break;
                                                        case "dIniAtiv":
                                                            dIniAtiv = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - dIniAtiv: " + dIniAtiv);
                                                            break;
                                                        case "dUltSit":
                                                            dUltSit = elementoFilho2.getTextContent();
                                                            System.out.println("Resultado infCad - dUltSit: " + dUltSit);
                                                            break;
                                                        case "ender":

                                                            //CAPTURANDO DADOS DO Ender
                                                            NodeList ender = doc.getElementsByTagName("ender");

                                                            int tamEnder = ender.getLength();
                                                            System.out.println("\n\nRESULTADO ender - Tamanho da lista: " + tamEnder);

                                                            for (int m = 0; m < tamEnder; m++) {

                                                                Node noEnder = ender.item(m);

                                                                System.out.println("RESULTADO ender - Volta " + m + ", lendo: " + noEnder);

                                                                if (noEnder.getNodeType() == Node.ELEMENT_NODE) {

                                                                    Element elementoEnder = (Element) noEnder;

                                                                    //CAPTURA DADOS DO ATRIBUTO
                                                                    xmlns = elementoEnder.getAttribute("xmlns");
                                                                    System.out.println("XMLns: " + xmlns + "\n");

                                                                    //CAPUTRA DADOS DOS FILHOS
                                                                    NodeList filhos3 = elementoEnder.getChildNodes();
                                                                    int tamanho3 = filhos3.getLength();

                                                                    System.out.println("\nResultado ender - Tamanho da lista: " + tamanho3);

                                                                    for (int n = 0; n < tamanho3; n++) {
                                                                        Node noFilho3 = filhos3.item(n);

                                                                        System.out.println("\nResultado ender Filho - volta " + n + ", lendo: " + noFilho3);

                                                                        if (noFilho3.getNodeType() == Node.ELEMENT_NODE) {
                                                                            Element elementoFilho3 = (Element) noFilho3;
                                                                            switch (elementoFilho3.getTagName()) {
                                                                                case "xLgr":
                                                                                    xLgr = elementoFilho3.getTextContent();
                                                                                    System.out.println("Resultado ender - xLgr: " + xLgr);
                                                                                    break;
                                                                                case "nro":
                                                                                    nro = elementoFilho3.getTextContent();
                                                                                    System.out.println("Resultado ender - nro: " + nro);
                                                                                    break;
                                                                                case "xBairro":
                                                                                    xBairro = elementoFilho3.getTextContent();
                                                                                    System.out.println("Resultado ender - xBairro: " + xBairro);
                                                                                    break;
                                                                                case "cMun":
                                                                                    cMun = elementoFilho3.getTextContent();
                                                                                    System.out.println("Resultado ender - cMun: " + cMun);
                                                                                    break;
                                                                                case "xMun":
                                                                                    xMun = elementoFilho3.getTextContent();
                                                                                    System.out.println("Resultado ender - xMun: " + xMun);
                                                                                    break;
                                                                                case "CEP":
                                                                                    cep = elementoFilho3.getTextContent();
                                                                                    System.out.println("Resultado ender - CEP: " + cep);
                                                                                    break;
                                                                                case "xCpl":
                                                                                    xCpl = elementoFilho3.getTextContent();
                                                                                    System.out.println("Resultado ender - xCpl: " + xCpl);
                                                                                    break;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }

                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(RespostaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ie == null) {
            resposta = false;
            JOptionPane.showMessageDialog(null, xMotivo + " - Código: " + cStat);
        }

        return resposta;
    }

    public void consultaCadastro(String uf, String cnpj, String ie, String cpf) {

        try {

            if (escreveXML(cnpj, ie, cpf, uf)) {
                if (cons.consultaCad(uf, "CONS-CAD", "H")) {;
                    leXML();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(RespostaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * 
     * @param inscFederalPJ
     * @param inscEstadual
     * @param inscFederalPF
     * @param estado
     * @return
     * @throws IOException
     * @throws Exception 
     */
    private boolean escreveXML(String inscFederalPJ, String inscEstadual, String inscFederalPF, String estado) throws IOException, Exception {

        System.out.println("Escrevendo XML.");
        System.out.print("CNPJ: " + inscFederalPJ);
        System.out.print(". I.E.: " + inscEstadual);
        System.out.print(". CPF: " + inscFederalPF);
        System.out.println(". Estado: " + estado);

        boolean resposta = true;

        //Criar uma String no formato XML para o inicio da criação do arquivo.
        String xmlHeader;
        xmlHeader = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xmlHeader += "<ConsCad xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"2.00\">";
        xmlHeader += "</ConsCad>";
        /*
			 * Cria um InputStream a partir do string "xmlHeader". Se você for parsiar("abrir") o XML
			 * de um arquivo .xml já existente então pegue o InputStream do File (arquivo) do qual 
			 * você esta lendo.
			 * */
        ByteArrayInputStream xml = new ByteArrayInputStream(new String(xmlHeader.getBytes(), "UTF-8").getBytes());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbf.newDocumentBuilder();
        Document doc = docBuilder.parse(xml);
        //Pega o nó raíz da árvore do XML.
        Element rootNode = doc.getDocumentElement();
        //Cria e adiciona o nó base da NFe no nó raíz do XML.
        Element infCons = doc.createElement("infCons");

        Element xServ = doc.createElement("xServ");
        xServ.setTextContent("CONS-CAD");
        Element uf = doc.createElement("UF");
        uf.setTextContent(estado);
        Element cnpj = doc.createElement("CNPJ");
        cnpj.setTextContent(inscFederalPJ);
        Element cgcme = doc.createElement("IE");
        cgcme.setTextContent(inscEstadual);
        Element cpf = doc.createElement("CPF");
        cpf.setTextContent(inscFederalPF);

        infCons.appendChild(xServ);
        infCons.appendChild(uf);

        if (inscFederalPJ != null) {
            System.out.println("Pesquisando pelo CNPJ.");
            infCons.appendChild(cnpj);
        } else if (inscEstadual != null) {
            System.out.println("Pesquisando pela Inscrição Estadual.");
            infCons.appendChild(cgcme);
        } else if (inscFederalPF != null) {
            System.out.println("Pesquisando pelo CPF.");
            infCons.appendChild(cpf);
        } else {
            System.out.println("Inscrição não informada.");
            JOptionPane.showMessageDialog(null, "Informe um número de inscrição para continuar.");
            resposta = false;
        }

        if (resposta) {
            rootNode.appendChild(infCons);
            //Inicia a adição dos demais nós que devem conter o XML de acordo com a NFe.
            //generateHeaderNodes(nfe, doc, infNFe);
            //Salva o documento XML no diretório passado como parâmetro.			
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new FileOutputStream("src/spedCadastro/consultaCadastro.xml"));
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformer = transFactory.newTransformer();
            transformer.transform(source, result);

            System.out.println("Arquivo montado com sucesso.");
            resposta = true;
        }

        return resposta;
    }

    public String getcUF() {
        return cUF;
    }

    public String getxCpl() {
        return xCpl;
    }

    public String getVersaoDados() {
        return versaoDados;
    }

    public String getVerAplic() {
        return verAplic;
    }

    public String getcStat() {
        return cStat;
    }

    public String getxMotivo() {
        return xMotivo;
    }

    public String getUf() {
        return uf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getDhCons() {
        return dhCons;
    }

    public String getcUF2() {
        return cUF2;
    }

    public String getIe() {
        return ie;
    }

    public String getCnpj2() {
        return cnpj2;
    }

    public String getUf2() {
        return uf2;
    }

    public String getcSit() {
        return cSit;
    }

    public String getIndCredNFe() {
        return indCredNFe;
    }

    public String getIndCredCTe() {
        return indCredCTe;
    }

    public String getxNome() {
        return xNome;
    }

    public String getxFant() {
        return xFant;
    }

    public String getxRegApur() {
        return xRegApur;
    }

    public String getCnae() {
        return cnae;
    }

    public String getdIniAtiv() {
        return dIniAtiv;
    }

    public String getdUltSit() {
        return dUltSit;
    }

    public String getxLgr() {
        return xLgr;
    }

    public String getNro() {
        return nro;
    }

    public String getxBairro() {
        return xBairro;
    }

    public String getcMun() {
        return cMun;
    }

    public String getxMun() {
        return xMun;
    }

    public String getCep() {
        return cep;
    }

    public static void main(String[] args) {
        try {
            RespostaCadastro cad = new RespostaCadastro();

            //cad.leXML();
            //cad.escreveXML("07353613000151", null,"PR");
            cad.consultaCadastro("PR", "07353613000151", null, null);

            //System.out.println("cUF: " + cad.getcUF() + ". Versão Dados: " + cad.getVersaoDados());
        } catch (Exception ex) {
            Logger.getLogger(RespostaCadastro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
