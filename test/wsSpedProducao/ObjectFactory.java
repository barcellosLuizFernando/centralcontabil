
package wsSpedProducao;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the wsSpedCadastro package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _NfeCabecMsg_QNAME = new QName("http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro2", "nfeCabecMsg");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: wsSpedCadastro
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConsultaCadastro2 }
     * 
     */
    public ConsultaCadastro2 createConsultaCadastro2() {
        return new ConsultaCadastro2();
    }

    /**
     * Create an instance of {@link ConsultaCadastro2Response }
     * 
     */
    public ConsultaCadastro2Response createConsultaCadastro2Response() {
        return new ConsultaCadastro2Response();
    }

    /**
     * Create an instance of {@link NfeCabecMsg }
     * 
     */
    public NfeCabecMsg createNfeCabecMsg() {
        return new NfeCabecMsg();
    }

    /**
     * Create an instance of {@link ConsultaCadastro2 .NfeDadosMsg }
     * 
     */
    public ConsultaCadastro2 .NfeDadosMsg createConsultaCadastro2NfeDadosMsg() {
        return new ConsultaCadastro2 .NfeDadosMsg();
    }

    /**
     * Create an instance of {@link ConsultaCadastro2Response.ConsultaCadastro2Result }
     * 
     */
    public ConsultaCadastro2Response.ConsultaCadastro2Result createConsultaCadastro2ResponseConsultaCadastro2Result() {
        return new ConsultaCadastro2Response.ConsultaCadastro2Result();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NfeCabecMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.portalfiscal.inf.br/nfe/wsdl/CadConsultaCadastro2", name = "nfeCabecMsg")
    public JAXBElement<NfeCabecMsg> createNfeCabecMsg(NfeCabecMsg value) {
        return new JAXBElement<NfeCabecMsg>(_NfeCabecMsg_QNAME, NfeCabecMsg.class, null, value);
    }

}
