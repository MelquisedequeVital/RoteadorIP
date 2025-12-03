package br.edu.ifpb.poo;

import java.util.Scanner;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Menu {
    private static final String MENU_STRING = "========================================\n"
                    + "||  ROTEADOR DE IP  ||\n"
                    + "========================================\n"
                    + "|| 1 - Cadastrar Interface Física       ||\n"
                    + "|| 2 - Cadastrar Rota                   ||\n"
                    + "|| 3 - Visualizar Tabela de Rotas       ||\n"
                    + "|| 4 - Alterar Rota                     ||\n"
                    + "|| 5 - Excluir Rota                     ||\n"
                    + "|| 6 - Escolher Tipo de Exibição        ||\n"
                    + "|| 7 - Rotear um IP                     ||\n"
                    + "|| 8 - Resetar Tabela de Rotas          ||\n"
                    + "========================================\n"
                    + "|| Digite o número da opção desejada:";
    
    private static final String CONFIRMAÇÂO_STRING = "\n" 
                        + "----------------------------------------\n"
                        + " Deseja realizar outra operação? (S/N)\n"
                        + "----------------------------------------\n"
                        + "Sua escolha: ";

    private static Scanner sc = new Scanner(System.in);

    public static void escreverMenu(){
        System.out.print(MENU_STRING);
    }

    public static void escreverConfirmacao(){
        System.out.print(CONFIRMAÇÂO_STRING);
    }

    public static String receberEntrada(){
        return sc.nextLine();
    }

    public static void fecharScanner(){
    if (sc != null) {
        sc.close();
    }
    }

    public static int[] converterIpParaOctetos(String ipStr, String prompt) throws NumberFormatException, IllegalArgumentException {
        String[] partes = ipStr.split("\\.");

        if (partes.length != 4) {
            throw new IllegalArgumentException(prompt + ": Formato inválido. Esperado 4 octetos separados por ponto.");
        }

        int[] ip = new int[4];
        for (int i = 0; i < 4; i++) {
            // NumberFormatException ocorrerá aqui se não for número
            int octeto = Integer.parseInt(partes[i].trim()); 
            
            if (octeto < 0 || octeto > 255) {
                throw new IllegalArgumentException(prompt + ": Octeto '" + partes[i] + "' inválido. Os valores devem estar entre 0 e 255.");
            }
            ip[i] = octeto;
        }
        return ip;
    }
    
}
