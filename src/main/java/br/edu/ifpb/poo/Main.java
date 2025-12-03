package br.edu.ifpb.poo;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RoteadorIp roteador = new RoteadorIp();
        System.out.println("--- Inicializando o Roteador IP ---");
        System.out.println("Rotas iniciais (Default): " + roteador.getTabelaRoteamento().size()); 
        
        // 1. Cadastrando interfaces
        try {
            roteador.cadastrarInterface("eth1"); 
            roteador.cadastrarInterface("eth2");
            roteador.cadastrarInterface("eth3");
            roteador.cadastrarInterface("eth4");
            System.out.println("Interfaces cadastradas: eth1, eth2, eth3, eth4.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar interface: " + e.getMessage());
        }

        System.out.println("\n--- Adicionando 10 Rotas de Teste ---");
        
        try {

            roteador.cadastrarRota(new int[]{192, 168, 1, 0}, new int[]{10, 0, 0, 1}, new int[]{255, 255, 255, 0}, "eth2");
            roteador.cadastrarRota(new int[]{172, 16, 0, 0}, new int[]{10, 0, 0, 2}, new int[]{255, 255, 0, 0}, "eth3");
            roteador.cadastrarRota(new int[]{192, 168, 10, 16}, new int[]{10, 0, 0, 3}, new int[]{255, 255, 255, 240}, "eth2");
            roteador.cadastrarRota(new int[]{127, 0, 0, 0}, new int[]{127, 0, 0, 1}, new int[]{255, 0, 0, 0}, "eth1");
            roteador.cadastrarRota(new int[]{10, 1, 1, 64}, new int[]{10, 0, 0, 4}, new int[]{255, 255, 255, 192}, "eth4");
            roteador.cadastrarRota(new int[]{10, 1, 2, 0}, new int[]{10, 0, 0, 5}, new int[]{255, 255, 255, 0}, "eth2");
            roteador.cadastrarRota(new int[]{10, 0, 0, 0}, new int[]{10, 0, 0, 6}, new int[]{255, 0, 0, 0}, "eth3");
            roteador.cadastrarRota(new int[]{192, 168, 1, 100}, new int[]{10, 0, 0, 7}, new int[]{255, 255, 255, 255}, "eth2");
            roteador.cadastrarRota(new int[]{172, 16, 0, 0}, new int[]{10, 0, 0, 8}, new int[]{255, 240, 0, 0}, "eth4");
            roteador.cadastrarRota(new int[]{192, 168, 20, 0}, new int[]{10, 0, 0, 9}, new int[]{255, 255, 255, 0}, "eth3");
            
            System.out.println("Total de Rotas na Tabela: " + roteador.getTabelaRoteamento().size());

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar rota: " + e.getMessage());
        }

        System.out.println("\n--- Testando o Cálculo de Rota (Automático) ---");
        int[] ipDestinoTeste = {200, 168, 1, 50};
        System.out.println("IP de Destino: " + Arrays.toString(ipDestinoTeste));
        Rota rotaTeste = roteador.calcularRota(ipDestinoTeste);
        if (rotaTeste != null) {
            System.out.println("Melhor Rota: " + rotaTeste.getNetInterface());
        } else {
            System.out.println("Nenhuma rota encontrada.");
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nESCOLHA UMA OPÇÃO:");
            System.out.println("1 - Listar todas as rotas atuais");
            System.out.println("2 - Remover uma rota");
            System.out.println("3 - Atualizar uma rota existente");
            System.out.println("4 - Adicionar nova rota");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            String input = scanner.nextLine();

            if (input.equals("0")) {
                System.out.println("Encerrando...");
                break;
            }

            try {
                switch (input) {
                    case "1": // Listar
                        if (roteador.getTabelaRoteamento().isEmpty()) {
                            System.out.println(">> Tabela vazia.");
                        } else {
                            System.out.println("\n--- Tabela de Roteamento Atual ---");
                            for (Rota r : roteador.getTabelaRoteamento()) {
                                System.out.printf("Dest: %-15s | Mask: %-15s | Gw: %-12s | Iface: %s\n",
                                    formatIp(r.getDestinationIp()),
                                    formatIp(r.getMask()),
                                    formatIp(r.getGateway()),
                                    r.getNetInterface());
                            }
                        }
                        break;

                    case "2": 
                        System.out.println("\n--- Remover Rota ---");
                        int[] destRem = lerIp(scanner, "Digite o IP Destino da rota a remover: ");
                        int[] maskRem = lerIp(scanner, "Digite a Máscara da rota a remover: ");
                        
                        // Requer método removerRota no RoteadorIp
                        boolean removeu = roteador.removerRota(destRem, maskRem); 
                        if (removeu) System.out.println(">> Rota removida com sucesso!");
                        else System.out.println(">> Erro: Rota não encontrada.");
                        break;

                    case "3": 
                        System.out.println("\n--- Atualizar Rota ---");
                        System.out.println("(Identifique a rota pelo Destino e Máscara)");
                        int[] destAtt = lerIp(scanner, "IP Destino: ");
                        int[] maskAtt = lerIp(scanner, "Máscara: ");
                        
                        System.out.println("(Novos dados)");
                        int[] newGw = lerIp(scanner, "Novo Gateway: ");
                        System.out.print("Nova Interface: ");
                        String newIface = scanner.nextLine();

                        boolean atualizou = roteador.atualizarRota(destAtt, maskAtt, newGw, newIface);
                        if (atualizou) System.out.println(">> Rota atualizada com sucesso!");
                        else System.out.println(">> Erro: Rota não encontrada ou interface inválida.");
                        break;

                    case "4": 
                        System.out.println("\n--- Nova Rota ---");
                        int[] d = lerIp(scanner, "Destino: ");
                        int[] m = lerIp(scanner, "Mascara: ");
                        int[] g = lerIp(scanner, "Gateway: ");
                        System.out.print("Interface: ");
                        String i = scanner.nextLine();
                        roteador.cadastrarRota(d, g, m, i);
                        System.out.println(">> Rota adicionada!");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro na operação: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static String formatIp(int[] ip) {
        return ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
    }

    private static int[] lerIp(Scanner sc, String msg) {
        while(true) {
            try {
                System.out.print(msg);
                String s = sc.nextLine();
                String[] parts = s.split("\\.");
                if(parts.length != 4) throw new Exception();
                
                int[] ip = new int[4];
                for(int i=0; i<4; i++) ip[i] = Integer.parseInt(parts[i]);
                return ip;
            } catch (Exception e) {
                System.out.println("Formato inválido! Use x.x.x.x (ex: 192.168.0.1)");
            }
        }
    }
}