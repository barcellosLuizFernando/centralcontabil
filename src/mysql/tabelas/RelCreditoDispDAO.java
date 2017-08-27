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
public class RelCreditoDispDAO {
    
    public EntityManager getEM() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("CentralContabilPU");

        return factory.createEntityManager();
    }
    
    public RelCreditoDisp buscar(int x) {
        EntityManager em = getEM();
        
        RelCreditoDisp disp = null;

        try {
            em.getTransaction().begin();
            disp = em.find(RelCreditoDisp.class, x);
            //em.remove(bx);
            em.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        
        return disp;
    }
    
}
