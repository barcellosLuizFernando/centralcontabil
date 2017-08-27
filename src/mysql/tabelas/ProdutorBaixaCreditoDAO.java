/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.tabelas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.barcellos
 */
public class ProdutorBaixaCreditoDAO {

    public EntityManager getEM() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CentralContabilPU");

        return factory.createEntityManager();
    }

    public ProdutorBaixaCredito salvar(ProdutorBaixaCredito x) {
        EntityManager em = getEM();

        try {
            em.getTransaction().begin();
            em.persist(x);
            em.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        
        return x;
    }
    
    public ProdutorBaixaCredito deletar(ProdutorBaixaCreditoPK x) {
        EntityManager em = getEM();
        
        ProdutorBaixaCredito bx = null;

        try {
            em.getTransaction().begin();
            bx = em.find(ProdutorBaixaCredito.class, x);
            em.remove(bx);
            em.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        
        return bx;
    }
    
    public ProdutorBaixaCredito buscar(ProdutorBaixaCreditoPK x) {
        EntityManager em = getEM();
        
        ProdutorBaixaCredito bx = null;

        try {
            em.getTransaction().begin();
            bx = em.find(ProdutorBaixaCredito.class, x);
            //em.remove(bx);
            em.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        
        return bx;
    }

}
