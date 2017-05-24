/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spedCadastro;

/**
 *
 * @author luiz.barcellos
 */
public interface SefazServiceConfig {

    void setProperties();
    
    void carregaConfig(String uf, String servico, String ambiente);

    String getWebServiceURL();

    String getDefinitionsTargetNamespaceURI();

    String getSchemaTargetNamespaceURI();

    String getSoapActionURI();

    String getServiceName();

    String getPortName();
}