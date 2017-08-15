/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

/**
 * Formatar os mais diversos tipos de String. Recebe uma string e um valor
 * Integer que define o tipo de formatação que se espera para o retorno.
 *
 *
 * @author luiz.barcellos
 */
public class FormatarString {

    private String textoFormatado;

    /**
     *
     * @param valor String a ser formatada
     * @param tipo 1 - CNPJ; 2 - CPF
     */
    public String FormatarString(String valor, int tipo) {

        switch (tipo) {

            case 1:

                textoFormatado = cnpj(valor);
                break;

            case 2:
                textoFormatado = cpf(valor);
                break;

        }

        return textoFormatado;
    }

    public FormatarString() {

    }

    private String cnpj(String valor) {

        String bloco1 = valor.substring(0, 2);
        String bloco2 = valor.substring(2, 5);
        String bloco3 = valor.substring(5, 8);
        String bloco4 = valor.substring(8, 12);
        String bloco5 = valor.substring(12, 14);
        textoFormatado = String.format("%s.%s.%s/%s-%s", bloco1, bloco2, bloco3, bloco4, bloco5);
        System.out.println("CNPJ formatado: " + textoFormatado);

        return textoFormatado;
    }

    private String cpf(String valor) {

        String bloco1 = valor.substring(0, 3);
        String bloco2 = valor.substring(3, 6);
        String bloco3 = valor.substring(6, 9);
        String bloco4 = valor.substring(9, 11);
        textoFormatado = String.format("%s.%s.%s-%s", bloco1, bloco2, bloco3, bloco4);
        System.out.println("CPF formatado: " + textoFormatado);

        return textoFormatado;
    }

    public static void main(String[] args) {
        FormatarString format = new FormatarString();
        format.FormatarString("05528196000105", 1);
    }

}
