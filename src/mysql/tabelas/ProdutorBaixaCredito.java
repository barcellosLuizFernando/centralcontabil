/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.tabelas;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luiz.barcellos
 */
@Entity
@Table(name = "produtor_baixa_credito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProdutorBaixaCredito.findAll", query = "SELECT p FROM ProdutorBaixaCredito p")
    , @NamedQuery(name = "ProdutorBaixaCredito.findById", query = "SELECT p FROM ProdutorBaixaCredito p WHERE p.produtorBaixaCreditoPK.id = :id")
    , @NamedQuery(name = "ProdutorBaixaCredito.findByOtc", query = "SELECT p FROM ProdutorBaixaCredito p WHERE p.produtorBaixaCreditoPK.otc = :otc")
    , @NamedQuery(name = "ProdutorBaixaCredito.findByDtBaixa", query = "SELECT p FROM ProdutorBaixaCredito p WHERE p.dtBaixa = :dtBaixa")
    , @NamedQuery(name = "ProdutorBaixaCredito.findByValor", query = "SELECT p FROM ProdutorBaixaCredito p WHERE p.valor = :valor")
    , @NamedQuery(name = "ProdutorBaixaCredito.findByNotaFiscal", query = "SELECT p FROM ProdutorBaixaCredito p WHERE p.notaFiscal = :notaFiscal")})
public class ProdutorBaixaCredito implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProdutorBaixaCreditoPK produtorBaixaCreditoPK;
    @Basic(optional = false)
    @Column(name = "dt_baixa")
    @Temporal(TemporalType.DATE)
    private Date dtBaixa;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @Column(name = "nota_fiscal")
    private Integer notaFiscal;

    public ProdutorBaixaCredito() {
    }

    public ProdutorBaixaCredito(ProdutorBaixaCreditoPK produtorBaixaCreditoPK) {
        this.produtorBaixaCreditoPK = produtorBaixaCreditoPK;
    }

    public ProdutorBaixaCredito(ProdutorBaixaCreditoPK produtorBaixaCreditoPK, Date dtBaixa, double valor) {
        this.produtorBaixaCreditoPK = produtorBaixaCreditoPK;
        this.dtBaixa = dtBaixa;
        this.valor = valor;
    }

    public ProdutorBaixaCredito(int id, long otc) {
        this.produtorBaixaCreditoPK = new ProdutorBaixaCreditoPK(id, otc);
    }

    public ProdutorBaixaCreditoPK getProdutorBaixaCreditoPK() {
        return produtorBaixaCreditoPK;
    }

    public void setProdutorBaixaCreditoPK(ProdutorBaixaCreditoPK produtorBaixaCreditoPK) {
        this.produtorBaixaCreditoPK = produtorBaixaCreditoPK;
    }

    public Date getDtBaixa() {
        return dtBaixa;
    }

    public void setDtBaixa(Date dtBaixa) {
        this.dtBaixa = dtBaixa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Integer getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(Integer notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (produtorBaixaCreditoPK != null ? produtorBaixaCreditoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdutorBaixaCredito)) {
            return false;
        }
        ProdutorBaixaCredito other = (ProdutorBaixaCredito) object;
        if ((this.produtorBaixaCreditoPK == null && other.produtorBaixaCreditoPK != null) || (this.produtorBaixaCreditoPK != null && !this.produtorBaixaCreditoPK.equals(other.produtorBaixaCreditoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mysql.tabelas.ProdutorBaixaCredito[ produtorBaixaCreditoPK=" + produtorBaixaCreditoPK + " ]";
    }
    
}
