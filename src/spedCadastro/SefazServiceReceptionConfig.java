/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedCadastro;

import ferramenta.SpedConfig;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.barcellos
 */
public class SefazServiceReceptionConfig extends AbstractSefazServiceConfig {

    SpedConfig sped = new SpedConfig();

    //static String webServiceURL = "https://homologacao.nfe.fazenda.pr.gov.br/nfe/CadConsultaCadastro2";
    //static String webServiceURL = "https://cad.svrs.rs.gov.br/ws/cadconsultacadastro/cadconsultacadastro2.asmx";
    //static String serviceName = "CadConsultaCadastro2";
    //static String portName = "CadConsultaCadastroServicePort"; //Consulta no PR
    //static String portName = "CadConsultaCadastro2Soap12"; //Consulta em SC
    static String webServiceURL;
    static String serviceName;
    static String portName;
    static String definitionsTargetNamespaceURI;
    static String schemaTargetNamespaceURI;
    static String soapActionURI;

    @Override
    public String getWebServiceURL() {
        return webServiceURL;
    }

    @Override
    public String getDefinitionsTargetNamespaceURI() {
        //return "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro2";
        return definitionsTargetNamespaceURI;
    }

    @Override
    public String getSchemaTargetNamespaceURI() {
        //return "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro2";
        return schemaTargetNamespaceURI;
    }

    @Override
    public String getSoapActionURI() {
        //return "http://www.portalfiscal.inf.br/nfe/wsdl/NfeDownloadNF/CadConsultaCadastro2";
        return soapActionURI;
    }

    @Override
    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String getPortName() {
        return portName;
    }

    public void carregaConfig(String uf, String servico, String ambiente) {
        sped.setAmbiente(ambiente);
        sped.setUf(uf);
        sped.setServico(servico);

        webServiceURL = sped.carregaConfig("webServiceURL");
        serviceName = sped.carregaConfig("serviceName");
        portName = sped.carregaConfig("portName");
        definitionsTargetNamespaceURI = sped.carregaConfig("definitionsTargetNamespaceURI");
        schemaTargetNamespaceURI = sped.carregaConfig("SchemaTargetNamespaceURI");
        soapActionURI = sped.carregaConfig("soapActionURI");

        System.out.print("Estado: " + uf);
        System.out.print(", serviço: " + servico);
        System.out.print(". webServiceURL: " + webServiceURL);
        System.out.print(". serviceName: " + serviceName);
        System.out.print(". portName: " + portName);
        System.out.println(". definitionsTargetNamespaceURI: " + definitionsTargetNamespaceURI);
        System.out.print(". schemaTargetNamespaceURI: " + schemaTargetNamespaceURI);
        System.out.println(". soapActionURI: " + soapActionURI);

        if (webServiceURL == null
                || serviceName == null
                || portName == null
                || definitionsTargetNamespaceURI == null
                || schemaTargetNamespaceURI == null
                || soapActionURI == null) {
            JOptionPane.showMessageDialog(null, "Revise as configurações de configuração com o SPED para o \n"
                    + "Estado " + uf + ", serviço " + servico + ", ambiente " + ambiente + ".");
        }

    }

}
