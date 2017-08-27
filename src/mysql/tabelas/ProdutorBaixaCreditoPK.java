/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.tabelas;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author luiz.barcellos
 */
@Embeddable
public class ProdutorBaixaCreditoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id")
    private int id;
    @Basic(optional = false)
    @Column(name = "otc")
    private long otc;

    public ProdutorBaixaCreditoPK() {
    }

    public ProdutorBaixaCreditoPK(int id, long otc) {
        this.id = id;
        this.otc = otc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getOtc() {
        return otc;
    }

    public void setOtc(long otc) {
        this.otc = otc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) otc;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdutorBaixaCreditoPK)) {
            return false;
        }
        ProdutorBaixaCreditoPK other = (ProdutorBaixaCreditoPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.otc != other.otc) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mysql.tabelas.ProdutorBaixaCreditoPK[ id=" + id + ", otc=" + otc + " ]";
    }
    
}
