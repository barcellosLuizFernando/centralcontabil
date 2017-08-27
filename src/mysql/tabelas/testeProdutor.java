/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql.tabelas;

import java.util.Date;

/**
 *
 * @author luiz.barcellos
 */
public class testeProdutor {

    public static void main(String[] args) {

        ProdutorBaixaCredito bx = new ProdutorBaixaCredito();
        ProdutorBaixaCreditoPK pk = new ProdutorBaixaCreditoPK();
        RelCreditoDisp resumo = new RelCreditoDisp();
        RelCreditoDispDAO resumoDAO = new RelCreditoDispDAO();

        /*
        pk.setId(
                3);
        pk.setOtc(
                98739897);
        bx.setDtBaixa(
                new Date());
        bx.setValor(
                20394.22);
        bx.setProdutorBaixaCreditoPK(pk);

        ProdutorBaixaCreditoDAO dao = new ProdutorBaixaCreditoDAO();

        bx = dao.salvar(bx);
        //bx = dao.deletar(pk);

        System.out.println(
                "Registro salvo: " + bx.getProdutorBaixaCreditoPK());*/
        
        resumo = resumoDAO.buscar(2);
        
        System.out.println("Nome: " + resumo.getNome());
        System.out.println("Credito: " + resumo.getCreditoLiberado());

    }

}
