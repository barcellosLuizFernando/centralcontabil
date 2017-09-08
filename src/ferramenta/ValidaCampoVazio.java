/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

/**
 *
 * @author luiz.barcellos
 */
public class ValidaCampoVazio {

    public String validar(String x) {

        if (!"".equals(x)) {

            return "'" + x + "'";

        } else {
            return null;
        }

    }

}
