package br.edu.ifpb.poo;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        RoteadorIp roteador = new RoteadorIp();
        boolean continuar = true;

        while (continuar) {
            
            Menu.escreverMenu();
            
            int opcao = 0;
            String entrada = Menu.receberEntrada();

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("\nErro: Entrada inválida. Por favor, digite um número de 1 a 8.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("-> Opção 1: Cadastrar Interface Física");
                    System.out.print("   Digite o nome da Interface (ex: eth0, wlan0, loopback): ");
                    String nomeInterface = Menu.receberEntrada().trim();
                    
                    if (nomeInterface.isEmpty()) {
                        System.out.println("Erro: O nome da interface não pode ser vazio.");
                        break;
                    }
                    
                    roteador.cadastrarInterface(nomeInterface);
                    System.out.println("   Sucesso: Interface '" + nomeInterface + "' cadastrada com sucesso!");

                    break;
                case 2:
                    System.out.println("-> Opção 2: Cadastrar Rota");
                    
                    System.out.print("   Digite o IP de Destino (a.b.c.d): ");
                    String ipDestinoStr = Menu.receberEntrada().trim();
                    int[] destinationIp = Menu.converterIpParaOctetos(ipDestinoStr, "IP de Destino");
                    
                    System.out.print("   Digite o Gateway (a.b.c.d): ");
                    String gatewayStr = Menu.receberEntrada().trim();
                    int[] gateway = Menu.converterIpParaOctetos(gatewayStr, "Gateway");

                    System.out.print("   Digite a Máscara de Rede (a.b.c.d): ");
                    String maskStr = Menu.receberEntrada().trim();
                    int[] mask = Menu.converterIpParaOctetos(maskStr, "Máscara de Rede");

                    
                    System.out.print("   Digite o nome da Interface de Saída: ");
                    String netInterface = Menu.receberEntrada().trim();

                    if (netInterface.isEmpty()) {
                        System.out.println("O nome da interface não pode ser vazio.");
                        break;
                    }

                    roteador.cadastrarRota(destinationIp, gateway, mask, netInterface);

                    
                    System.out.println("   Sucesso: Rota para " 
                        + ipDestinoStr 
                        + " cadastrada via interface " + netInterface + ".");
                    break;


                case 3:
                    if (roteador.getTabelaRoteamento().isEmpty()) {
                        System.out.println(">> Tabela vazia.");
                        } else {
                            System.out.println(roteador);
                            }
                            break;


                case 4:
                    System.out.println("-> Opção 4: Alterar Rota Completa");
                    System.out.println("\n--- Identifique a Rota a ser alterada ---");
                    
                    System.out.print("   IP Destino ATUAL: ");
                    String destAntigoStr = Menu.receberEntrada().trim();
                    int[] destAntigo = Menu.converterIpParaOctetos(destAntigoStr, "IP Destino Atual");
                    
                    System.out.print("   Máscara ATUAL: ");
                    String maskAntigaStr = Menu.receberEntrada().trim();
                    int[] maskAntiga = Menu.converterIpParaOctetos(maskAntigaStr, "Máscara Atual");
                    
                    System.out.println("\n--- Digite os NOVOS dados da Rota ---");
                    
                    System.out.print("   NOVO IP Destino: ");
                    String novoDestStr = Menu.receberEntrada().trim();
                    int[] novoDest = Menu.converterIpParaOctetos(novoDestStr, "Novo IP Destino");

                    System.out.print("   NOVA Máscara: ");
                    String novaMaskStr = Menu.receberEntrada().trim();
                    int[] novaMask = Menu.converterIpParaOctetos(novaMaskStr, "Nova Máscara");
                    
                    System.out.print("   NOVO Gateway: ");
                    String newGwStr = Menu.receberEntrada().trim();
                    int[] newGw = Menu.converterIpParaOctetos(newGwStr, "Novo Gateway");
                    
                    System.out.print("   NOVA Interface: ");
                    String newIface = Menu.receberEntrada().trim();

                    boolean atualizou = roteador.atualizarRota(destAntigo, maskAntiga, novoDest, novaMask, newGw, newIface);
                    
                    if (atualizou) {
                        System.out.println(">> Rota atualizada com sucesso!");
                    } else {
                        System.out.println(">> Erro: Rota antiga não encontrada. Verifique se o IP/Máscara atuais estão corretos.");
                    }
                    break;


                case 5:
                    System.out.println("-> Opção 5: Excluir Rota");
                    System.out.println("\n--- Remover Rota ---");

                    System.out.print("   Digite o IP Destino da rota a remover (a.b.c.d): ");
                    String destStr = Menu.receberEntrada().trim();
                    int[] destRem = Menu.converterIpParaOctetos(destStr, "IP de Destino");
                    
                    System.out.print("   Digite a Máscara da rota a remover (a.b.c.d): ");
                    String maskString = Menu.receberEntrada().trim();
                    int[] maskRem = Menu.converterIpParaOctetos(maskString, "Máscara");
                    
                    
                    boolean removeu = roteador.removerRota(destRem, maskRem); 
                    
                    if (removeu) {
                        System.out.println(">> Rota para " + destStr + " removida com sucesso!");
                    } else {
                        System.out.println(">> Erro: Rota não encontrada.");
                    }
                    break;


                case 6:
                    System.out.println("-> Opção 6: Trocar Tipo de Exibição");
                    roteador.mudarNotacao();
                    System.out.print("Tipo de exibição trocada com Sucesso para: ");
                    if(roteador.isNotacaoCIDR()){
                        System.out.println("Notação CIDR");
                    } else {
                        System.out.println("Notação em bytes");
                    }
                    break;


                case 7:
                    System.out.println("-> Opção 7: Rotear um IP");
                    
                    System.out.print("   Digite o IP para rotamento (a.b.c.d): ");
                    String ipConsultaStr = Menu.receberEntrada().trim();
                    int[] ipConsulta = Menu.converterIpParaOctetos(ipConsultaStr, "IP para rotamento");
                    
                    
                    Rota rotaCalculada = roteador.calcularRota(ipConsulta);

                    
                    System.out.println("\n----------------------------------------");
                    System.out.println("   Resultado do Rotamento para " + ipConsultaStr);
                    
                    if (rotaCalculada != null) {
                        
                        String destino = rotaCalculada.getDestinationIp()[0] + "." 
                                    + rotaCalculada.getDestinationIp()[1] + "." 
                                    + rotaCalculada.getDestinationIp()[2] + "." 
                                    + rotaCalculada.getDestinationIp()[3];
                        
                        String gatewayCalculado = rotaCalculada.getGateway()[0] + "." 
                                            + rotaCalculada.getGateway()[1] + "." 
                                            + rotaCalculada.getGateway()[2] + "." 
                                            + rotaCalculada.getGateway()[3];
                        
                        System.out.println("   Rota Encontrada (Melhor Match):");
                        System.out.println("      - Destino: " + destino + "/" + rotaCalculada.calculaCIDR());
                        System.out.println("      - Gateway: " + gatewayCalculado);
                        System.out.println("      - Interface: " + rotaCalculada.getNetInterface());
                    } else {
                        System.out.println("Nenhuma rota correspondente encontrada na tabela.");
                    }
                    System.out.println("----------------------------------------");
                    break;


                case 8:
                    System.out.println("\n-> Opção 8: Resetar Tabela de Rotas");
                    roteador.resetarTabela(); 
                    System.out.println("   Sucesso: Tabela de Rotas e lista de Interfaces resetadas.");
                    break;
                default:
                    System.out.println("\nOpção inválida. Por favor, escolha um número de 1 a 8.");
            }

            if (opcao >= 1 && opcao <= 8) {
                String confirmacao;
                boolean entradaInvalida;
                
                do {
                    Menu.escreverConfirmacao();
                    confirmacao = Menu.receberEntrada().trim().toUpperCase();
                    
                    entradaInvalida = !confirmacao.equals("S") && !confirmacao.equals("N");
                    
                    if (entradaInvalida) {
                        System.out.println("Opção inválida. Digite 'S' para continuar ou 'N' para encerrar.");
                    }
                    
                } while (entradaInvalida); 
                
                if (confirmacao.equals("N")) {
                    continuar = false;
                }
            }
            
            System.out.println("========================================\n");
        }
        
        Menu.fecharScanner();
        System.out.println("Programa encerrado. Obrigado por usar o RoteadorIP!");
    }
}