/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ferramenta;

import java.util.InputMismatchException;
import javax.swing.JOptionPane;

/**
 *
 * @author luiz.barcellos
 */
public class ValidaInscricao {

    private boolean validatePIS(String plPIS) {
        int liTamanho = 0;
        StringBuffer lsAux = null;
        StringBuffer lsMultiplicador = new StringBuffer("3298765432");
        int liTotalizador = 0;
        int liResto = 0;
        int liMultiplicando = 0;
        int liMultiplicador = 0;
        boolean lbRetorno = true;
        int liDigito = 99;
        lsAux = new StringBuffer().append(plPIS);
        liTamanho = lsAux.length();
        if (liTamanho != 11) {
            lbRetorno = false;
        }
        if (lbRetorno) {
            for (int i = 0; i < 10; i++) {
                liMultiplicando = Integer.parseInt(lsAux.substring(i, i + 1));
                liMultiplicador = Integer.parseInt(lsMultiplicador.substring(i, i + 1));
                liTotalizador += liMultiplicando * liMultiplicador;
            }
            liResto = 11 - liTotalizador % 11;
            liResto = liResto == 10 || liResto == 11 ? 0 : liResto;
            liDigito = Integer.parseInt("" + lsAux.charAt(10));
            lbRetorno = liResto == liDigito;

            System.out.println("Dígito Verificar do PIS digitado: " + liDigito);
            System.out.println("Dígito Verificar do PIS calculado: " + liResto);
        }
        System.out.println("Validação: " + lbRetorno);
        return lbRetorno;
    }

    public boolean isCPF(String CPF) {
// considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")
                || (CPF.length() != 11)) {
            return (false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
// converte o i-esimo caractere do CPF em um numero:
// por exemplo, transforma o caractere '0' no inteiro 0         
// (48 eh a posicao de '0' na tabela ASCII)         
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            } else {
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico
            }
// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char) (r + 48);
            }

// Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    public boolean isCNPJ(String CNPJ) {
// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111")
                || CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333")
                || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
                || CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777")
                || CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999")
                || (CNPJ.length() != 14)) {
            return (false);
        }
        
        char dig13, dig14;
        int sm, i, r, num, peso;

// "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
// Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
// converte o i-ésimo caractere do CNPJ em um número:
// por exemplo, transforma o caractere '0' no inteiro 0
// (48 eh a posição de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }

// Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }

// Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

    //VALIDA INSCRIÇÕES ESTADUAIS
    /**
     * Extrai os números de uma String.
     *
     * @param ie String contendo o número formatado.
     * @return
     */
    public static String removeMascara(String ie) {
        String strIE = "";
        for (int i = 0; i < ie.length(); i++) {
            if (Character.isDigit(ie.charAt(i))) {
                strIE += ie.charAt(i);
            }
        }
        return strIE;
    }

    public void validaIE(String inscricaoEstadual, String siglaUf) throws Exception {
        String strIE = removeMascara(inscricaoEstadual);
        System.out.println("\nValor recebido: " + inscricaoEstadual);
        System.out.println("Valor Formatado: " + strIE + ". UF: + " + siglaUf);
        siglaUf = siglaUf.toUpperCase();
        if (siglaUf.equals("AC")) {
            validaIEAcre(strIE);
        } else if (siglaUf.equals("AL")) {
            validaIEAlagoas(strIE);
        } else if (siglaUf.equals("AP")) {
            validaIEAmapa(strIE);
        } else if (siglaUf.equals("AM")) {
            validaIEAmazonas(strIE);
        } else if (siglaUf.equals("BA")) {
            validaIEBahia(strIE);
        } else if (siglaUf.equals("CE")) {
            validaIECeara(strIE);
        } else if (siglaUf.equals("ES")) {
            validaIEEspiritoSanto(strIE);
        } else if (siglaUf.equals("GO")) {
            validaIEGoias(strIE);
        } else if (siglaUf.equals("MA")) {
            validaIEMaranhao(strIE);
        } else if (siglaUf.equals("MT")) {
            validaIEMatoGrosso(strIE);
        } else if (siglaUf.equals("MS")) {
            validaIEMatoGrossoSul(strIE);
        } else if (siglaUf.equals("MG")) {
            validaIEMinasGerais(strIE);
        } else if (siglaUf.equals("PA")) {
            validaIEPara(strIE);
        } else if (siglaUf.equals("PB")) {
            validaIEParaiba(strIE);
        } else if (siglaUf.equals("PR")) {
            validaIEParana(strIE);
        } else if (siglaUf.equals("PE")) {
            validaIEPernambuco(strIE);
        } else if (siglaUf.equals("PI")) {
            validaIEPiaui(strIE);
        } else if (siglaUf.equals("RJ")) {
            validaIERioJaneiro(strIE);
        } else if (siglaUf.equals("RN")) {
            validaIERioGrandeNorte(strIE);
        } else if (siglaUf.equals("RS")) {
            validaIERioGrandeSul(strIE);
        } else if (siglaUf.equals("RO")) {
            validaIERondonia(strIE);
        } else if (siglaUf.equals("RR")) {
            validaIERoraima(strIE);
        } else if (siglaUf.equals("SC")) {
            validaIESantaCatarina(strIE);
        } else if (siglaUf.equals("SP")) {
            if (inscricaoEstadual.charAt(0) == 'P') {
                strIE = "P" + strIE;
            }
            validaIESaoPaulo(strIE);
        } else if (siglaUf.equals("SE")) {
            validaIESergipe(strIE);
        } else if (siglaUf.equals("TO")) {
            validaIETocantins(strIE);
        } else if (siglaUf.equals("DF")) {
            validaIEDistritoFederal(strIE);
        } else {
            throw new Exception("Estado não encontrado : " + siglaUf);
        }
    }

    /**
     * Valida inscrição estadual do estado do Acre
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEAcre(String ie) throws Exception { //inscrição do validaIEAcre()
        //valida a quantidade de dígitos
        if (ie.length() != 13) {
            throw new Exception("Quantidade de digitos inválida.");
        }

        //valida os dois primeiros digitos - devem ser iguais a 01
        for (int i = 0; i < 2; i++) {
            if (Integer.parseInt(String.valueOf(ie.charAt(i))) != i) {
                throw new Exception("Inscrição Estadual inválida");
            }
        }

        int soma = 0;
        int pesoInicial = 4;
        int pesoFinal = 9;
        int d1 = 0; //primeiro digito verificador
        int d2 = 0; //segundo digito verificador

        //calcula o primeiro digito
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }
        d1 = 11 - (soma % 11);
        if (d1 == 10 || d1 == 11) {
            d1 = 0;
        }

        //calcula o segundo digito
        soma = d1 * 2;
        pesoInicial = 5;
        pesoFinal = 9;
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 4) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }

        d2 = 11 - (soma % 11);
        if (d2 == 10 || d2 == 11) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new Exception("Digito verificador inválido.");
        }
    } //fim do validaIEAcre()

    /**
     * Valida inscrição estadual do estado do Alagoas
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEAlagoas(String ie) throws Exception {
        //valida quantidade de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de dígitos inválida.");
        }

        //valida os dois primeiros dígitos - deve ser iguais a 24
        if (!ie.substring(0, 2).equals("24")) {
            throw new Exception("Inscrição estadual inválida.");
        }

        //valida o terceiro dígito - deve ser 0,3,5,7,8
        int[] digits = {0, 3, 5, 7, 8};
        boolean check = false;
        for (int i = 0; i < digits.length; i++) {
            if (Integer.parseInt(String.valueOf(ie.charAt(2))) == digits[i]) {
                check = true;
                break;
            }
        }
        if (!check) {
            throw new Exception("Inscrição estadual inválida.");
        }

        //calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = 0; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }
        d = ((soma * 10) % 11);
        if (d == 10) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Amapá
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEAmapa(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //verifica os dois primeiros dígitos - deve ser igual 03
        if (!ie.substring(0, 2).equals("03")) {
            throw new Exception("Inscrição estadual inválida.");
        }

        //calcula o dígito verificador
        int d1 = -1;
        int soma = -1;
        int peso = 9;

        //configura o valor do dígito verificador e da soma de acordo com faixa das inscriçães
        long x = Long.parseLong(ie.substring(0, ie.length() - 1)); //x = inscrição estadual sem o dígito verificador
        if (x >= 3017001L && x <= 3019022L) {
            d1 = 1;
            soma = 9;
        } else if (x >= 3000001L && x <= 3017000L) {
            d1 = 0;
            soma = 5;
        } else if (x >= 3019023L) {
            d1 = 0;
            soma = 0;
        }

        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int d = 11 - ((soma % 11)); //d = armazena o dígito verificador após cálculo
        if (d == 10) {
            d = 0;
        } else if (d == 11) {
            d = d1;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Amazonas
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEAmazonas(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        if (soma < 11) {
            d = 11 - soma;
        } else if ((soma % 11) <= 1) {
            d = 0;
        } else {
            d = 11 - (soma % 11);
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Bahia
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEBahia(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 8 && ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas." + ie);
        }

        //Cálculo do módulo de acordo com o primeiro dígito da inscrição Estadual
        int modulo = 10;
        int firstDigit = Integer.parseInt(String.valueOf(ie.charAt(ie.length() == 8 ? 0 : 1)));
        if (firstDigit == 6 || firstDigit == 7 || firstDigit == 9) {
            modulo = 11;
        }

        //Cálculo do segundo dígito
        int d2 = -1; //segundo dígito verificador
        int soma = 0;
        int peso = ie.length() == 8 ? 7 : 8;
        for (int i = 0; i < ie.length() - 2; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % modulo;

        if (resto == 0 || (modulo == 11 && resto == 1)) {
            d2 = 0;
        } else {
            d2 = modulo - resto;
        }

        //Cálculo do primeiro dígito
        int d1 = -1; //primeiro dígito verificador
        soma = d2 * 2;
        peso = ie.length() == 8 ? 8 : 9;
        for (int i = 0; i < ie.length() - 2; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        resto = soma % modulo;

        if (resto == 0 || (modulo == 11 && resto == 1)) {
            d1 = 0;
        } else {
            d1 = modulo - resto;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new Exception("Digito verificador inválido." + ie);
        }
    }

    /**
     * Valida inscrição estadual do estado do Ceará
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIECeara(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11) {
            d = 0;
        }
        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Espírito Santo
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEEspiritoSanto(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % 11;
        if (resto < 2) {
            d = 0;
        } else if (resto > 1) {
            d = 11 - resto;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Goiás
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEGoias(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígito
        if (!"10".equals(ie.substring(0, 2))) {
            if (!"11".equals(ie.substring(0, 2))) {
                if (!"15".equals(ie.substring(0, 2))) {
                    throw new Exception("Inscrição estadual inválida");
                }
            }
        }

        if (ie.substring(0, ie.length() - 1).equals("11094402")) {
            if (!ie.substring(ie.length() - 1, ie.length()).equals("0")) {
                if (!ie.substring(ie.length() - 1, ie.length()).equals("1")) {
                    throw new Exception("Inscrição estadual inválida.");
                }
            }
        } else {

            //Cálculo do dígito verificador
            int soma = 0;
            int peso = 9;
            int d = -1; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }

            int resto = soma % 11;
            long faixaInicio = 10103105;
            long faixaFim = 10119997;
            long insc = Long.parseLong(ie.substring(0, ie.length() - 1));
            if (resto == 0) {
                d = 0;
            } else if (resto == 1) {
                if (insc >= faixaInicio && insc <= faixaFim) {
                    d = 1;
                } else {
                    d = 0;
                }
            } else if (resto != 0 && resto != 1) {
                d = 11 - resto;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                throw new Exception("Digito verificador inválido.");
            }
        }
    }

    /**
     * Valida inscrição estadual do estado do Maranhão
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEMaranhao(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("12")) {
            throw new Exception("Inscrição estadual inválida.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Mato Grosso
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEMatoGrosso(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 11) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int pesoInicial = 3;
        int pesoFinal = 9;
        int d = -1;

        for (int i = 0; i < ie.length() - 1; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Mato Grosso do Sul
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEMatoGrossoSul(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("28")) {
            throw new Exception("Inscrição estadual inválida.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % 11;
        int result = 11 - resto;
        if (resto == 0) {
            d = 0;
        } else if (resto > 0) {
            if (result > 9) {
                d = 0;
            } else if (result < 10) {
                d = result;
            }
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Minas Gerais
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEMinasGerais(String ie) throws Exception {
        /*
	 * FORMATO GERAL: A1A2A3B1B2B3B4B5B6C1C2D1D2
	 * Onde: A= Código do Municípios
	 * B= Número da inscrição
	 * C= Número de ordem do estabelecimento
	 * D= Dígitos de controle
         */

        // valida quantida de dígitos
        if (ie.length() != 13) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //iguala a casas para o cálculo
        //em inserir o algarismo zero "0" imediatamente após o número de código do município, 
        //desprezando-se os dígitos de controle.
        String str = "";
        for (int i = 0; i < ie.length() - 2; i++) {
            if (Character.isDigit(ie.charAt(i))) {
                if (i == 3) {
                    str += "0";
                    str += ie.charAt(i);
                } else {
                    str += ie.charAt(i);
                }
            }
        }

        //Cálculo do primeiro dígito verificador
        int soma = 0;
        int pesoInicio = 1;
        int pesoFim = 2;
        int d1 = -1; //primeiro dígito verificador
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0) {
                int x = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoInicio;
                String strX = Integer.toString(x);
                for (int j = 0; j < strX.length(); j++) {
                    soma += Integer.parseInt(String.valueOf(strX.charAt(j)));
                }
            } else {
                int y = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoFim;
                String strY = Integer.toString(y);
                for (int j = 0; j < strY.length(); j++) {
                    soma += Integer.parseInt(String.valueOf(strY.charAt(j)));
                }
            }
        }

        int dezenaExata = soma;
        while (dezenaExata % 10 != 0) {
            dezenaExata++;
        }
        d1 = dezenaExata - soma; //resultado - primeiro dígito verificador

        //Cálculo do segundo dígito verificador
        soma = d1 * 2;
        pesoInicio = 3;
        pesoFim = 11;
        int d2 = -1;
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11); //resultado - segundo dígito verificador
        if ((soma % 11 == 0) || (soma % 11 == 1)) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Pará;
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEPara(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("15")) {
            throw new Exception("Inscrição estadual inválida.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Paraíba
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEParaiba(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Paraná
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEParana(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 10) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do primeiro dígito
        int soma = 0;
        int pesoInicio = 3;
        int pesoFim = 7;
        int d1 = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d1 = 0;
        }

        //cálculo do segundo dígito
        soma = d1 * 2;
        pesoInicio = 4;
        pesoFim = 7;
        int d2 = -1; //segundo dígito
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Pernambuco
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEPernambuco(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 14) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verificador
        int soma = 0;
        int pesoInicio = 5;
        int pesoFim = 9;
        int d = -1; //dígito verificador

        for (int i = 0; i < ie.length() - 1; i++) {
            if (i < 5) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d = 11 - (soma % 11);
        if (d > 9) {
            d -= 10;
        }

        System.out.println(soma);
        System.out.println(11 - (soma % 11));
        System.out.println(d);

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Piauí
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEPiaui(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verficador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 11 || d == 10) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Rio de Janeiro
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIERioJaneiro(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 8) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verficador
        int soma = 0;
        int peso = 7;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            if (i == 0) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * 2;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
        }

        d = 11 - (soma % 11);
        if ((soma % 11) <= 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Rio Grande do Norte
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIERioGrandeNorte(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 10 && ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("20")) {
            throw new Exception("Inscrição estadual inválida.");
        }

        //calcula o dígito para inscrição de 9 dígitos
        if (ie.length() == 9) {
            int soma = 0;
            int peso = 9;
            int d = -1; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }

            d = ((soma * 10) % 11);
            if (d == 10) {
                d = 0;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                throw new Exception("Digito verificador inválido.");
            }
        } else {
            int soma = 0;
            int peso = 10;
            int d = -1; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
            d = ((soma * 10) % 11);
            if (d == 10) {
                d = 0;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                throw new Exception("Digito verificador inválido.");
            }
        }

    }

    /**
     * Valida inscrição estadual do estado do Rio Grande do Sul
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIERioGrandeSul(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 10) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verificador
        int soma = Integer.parseInt(String.valueOf(ie.charAt(0))) * 2;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 1; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Rondônia
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIERondonia(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 14) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verificador
        int soma = 0;
        int pesoInicio = 6;
        int pesoFim = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            if (i < 5) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d = 11 - (soma % 11);
        if (d == 11 || d == 10) {
            d -= 10;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Roraima
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIERoraima(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("24")) {
            throw new Exception("Inscrição estadual inválida.");
        }

        int soma = 0;
        int peso = 1;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso++;
        }

        d = soma % 9;

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Santa Catarina
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIESantaCatarina(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //Cálculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do São Paulo
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIESaoPaulo(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 12 && ie.length() != 13) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        if (ie.length() == 12) {
            int soma = 0;
            int peso = 1;
            int d1 = -1; //primeiro dígito verificador
            //cálculo do primeiro dígito verificador (nona posição)
            for (int i = 0; i < ie.length() - 4; i++) {
                if (i == 1 || i == 7) {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O dígito é igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //cálculo do segunfo dígito
            soma = 0;
            int pesoInicio = 3;
            int pesoFim = 10;
            int d2 = -1; //segundo dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                if (i < 2) {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                    pesoInicio--;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                    pesoFim--;
                }
            }

            d2 = soma % 11;
            String strD2 = Integer.toString(d2); //O dígito é igual ao algarismo mais a direita do resultado de (soma % 11)
            d2 = Integer.parseInt(String.valueOf(strD2.charAt(strD2.length() - 1)));

            //valida os dígitos verificadores
            if (!ie.substring(8, 9).equals(d1 + "")) {
                throw new Exception("Inscrição estadual inválida.");
            }
            if (!ie.substring(11, 12).equals(d2 + "")) {
                throw new Exception("Inscrição estadual inválida.");
            }

        } else {
            //valida o primeiro caracter
            if (ie.charAt(0) != 'P') {
                throw new Exception("Inscrição estadual inválida.");
            }

            String strIE = ie.substring(1, 10); //Obtém somente os dígitos utilizados no cálculo do dígito verificador
            int soma = 0;
            int peso = 1;
            int d1 = -1; //primeiro dígito verificador
            //cálculo do primeiro dígito verificador (nona posição)
            for (int i = 0; i < strIE.length() - 1; i++) {
                if (i == 1 || i == 7) {
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O dígito é igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //valida o dígito verificador
            if (!ie.substring(9, 10).equals(d1 + "")) {
                throw new Exception("Inscrição estadual inválida.");
            }
        }
    }

    /**
     * Valida inscrição estadual do estado do Sergipe
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIESergipe(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //cálculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 11 || d == 11 || d == 10) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Tocantins
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIETocantins(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 9 && ie.length() != 11) {
            throw new Exception("Quantidade de dígitos inválida.");
        } else if (ie.length() == 9) {
            ie = ie.substring(0, 2) + "02" + ie.substring(2);
        }

        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            if (i != 2 && i != 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
        }
        d = 11 - (soma % 11);
        if ((soma % 11) < 2) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Distrito Federal
     *
     * @param ie (Inscrição estadual)
     * @throws Exception
     */
    private static void validaIEDistritoFederal(String ie) throws Exception {
        //valida quantida de dígitos
        if (ie.length() != 13) {
            throw new Exception("Quantidade de digitos inválidas.");
        }

        //cálculo do primeiro dígito verificador
        int soma = 0;
        int pesoInicio = 4;
        int pesoFim = 9;
        int d1 = -1; //primeiro dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if (d1 == 11 || d1 == 10) {
            d1 = 0;
        }

        //cálculo do segundo dígito verificador
        soma = d1 * 2;
        pesoInicio = 5;
        pesoFim = 9;
        int d2 = -1; //segundo dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 4) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if (d2 == 11 || d2 == 10) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new Exception("Digito verificador inválido.");
        }
    }

    public static void main(String[] args) {
        ValidaInscricao val = new ValidaInscricao();

        try {
            val.validaIE("012781966", "SC");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

//System.out.println("Inscrição é: " + val.validaIE("05528196000105","PR"));
    }

}
