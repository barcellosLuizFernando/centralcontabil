/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.tabelas;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luiz.barcellos
 */
@Entity
@Table(name = "rel_credito_disp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelCreditoDisp.findAll", query = "SELECT r FROM RelCreditoDisp r")
    , @NamedQuery(name = "RelCreditoDisp.findById", query = "SELECT r FROM RelCreditoDisp r WHERE r.id = :id")
    , @NamedQuery(name = "RelCreditoDisp.findByInscriEst", query = "SELECT r FROM RelCreditoDisp r WHERE r.inscriEst = :inscriEst")
    , @NamedQuery(name = "RelCreditoDisp.findByNome", query = "SELECT r FROM RelCreditoDisp r WHERE r.nome = :nome")
    , @NamedQuery(name = "RelCreditoDisp.findByCreditoLiberado", query = "SELECT r FROM RelCreditoDisp r WHERE r.creditoLiberado = :creditoLiberado")
    , @NamedQuery(name = "RelCreditoDisp.findByCreditoRecebido", query = "SELECT r FROM RelCreditoDisp r WHERE r.creditoRecebido = :creditoRecebido")
    , @NamedQuery(name = "RelCreditoDisp.findByQuitado", query = "SELECT r FROM RelCreditoDisp r WHERE r.quitado = :quitado")
    , @NamedQuery(name = "RelCreditoDisp.findByProcesso", query = "SELECT r FROM RelCreditoDisp r WHERE r.processo = :processo")
    , @NamedQuery(name = "RelCreditoDisp.findByQtd", query = "SELECT r FROM RelCreditoDisp r WHERE r.qtd = :qtd")})
public class RelCreditoDisp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "ID")
    @Id
    private int id;
    @Column(name = "inscri_est")
    private BigInteger inscriEst;
    @Column(name = "nome")
    private String nome;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "credito_liberado")
    private Double creditoLiberado;
    @Column(name = "credito_recebido")
    private Double creditoRecebido;
    @Basic(optional = false)
    @Column(name = "quitado")
    private int quitado;
    @Column(name = "processo")
    private String processo;
    @Column(name = "qtd")
    private BigInteger qtd;

    public RelCreditoDisp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigInteger getInscriEst() {
        return inscriEst;
    }

    public void setInscriEst(BigInteger inscriEst) {
        this.inscriEst = inscriEst;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getCreditoLiberado() {
        return creditoLiberado;
    }

    public void setCreditoLiberado(Double creditoLiberado) {
        this.creditoLiberado = creditoLiberado;
    }

    public Double getCreditoRecebido() {
        return creditoRecebido;
    }

    public void setCreditoRecebido(Double creditoRecebido) {
        this.creditoRecebido = creditoRecebido;
    }

    public int getQuitado() {
        return quitado;
    }

    public void setQuitado(int quitado) {
        this.quitado = quitado;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public BigInteger getQtd() {
        return qtd;
    }

    public void setQtd(BigInteger qtd) {
        this.qtd = qtd;
    }
    
}
