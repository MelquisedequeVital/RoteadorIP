package br.edu.ifpb.poo;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        // Assume que a classe RoteadorIp está definida no mesmo pacote (br.edu.ifpb.poo)
        RoteadorIp roteador = new RoteadorIp();
        System.out.println("--- Inicializando o Roteador IP ---");
        System.out.println("Rotas iniciais (Default): " + roteador.getTabelaRoteamento().size()); 
        
        // 1. Cadastrando interfaces
        try {
            roteador.cadastrarInterface("eth2");
            roteador.cadastrarInterface("eth3");
            roteador.cadastrarInterface("eth4");
            System.out.println("Interfaces cadastradas: eth1, eth2, eth3, eth4.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar interface: " + e.getMessage());
        }

        System.out.println("\n--- Adicionando 10 Rotas de Teste ---");
        
        // Dados de teste para 10 Rotas
        try {
            // Rota 1: Padrão /24 (Ex: 192.168.1.0/24)
            roteador.cadastrarRota(
                new int[]{192, 168, 1, 0},   // IP de Destino
                new int[]{10, 0, 0, 1},      // Gateway
                new int[]{255, 255, 255, 0}, // Máscara
                "eth2"                       // Interface
            );

            // Rota 2: Padrão /16 (Ex: 172.16.0.0/16)
            roteador.cadastrarRota(
                new int[]{172, 16, 0, 0}, 
                new int[]{10, 0, 0, 2}, 
                new int[]{255, 255, 0, 0}, 
                "eth3"
            );

            // Rota 3: Mais específica /28 (Ex: 192.168.10.16/28)
            roteador.cadastrarRota(
                new int[]{192, 168, 10, 16}, 
                new int[]{10, 0, 0, 3}, 
                new int[]{255, 255, 255, 240}, // Máscara /28
                "eth2"
            );

            // Rota 4: Rota de loopback (Ex: 127.0.0.0/8)
            roteador.cadastrarRota(
                new int[]{127, 0, 0, 0}, 
                new int[]{127, 0, 0, 1}, 
                new int[]{255, 0, 0, 0}, 
                "eth1"
            );
            
            // Rota 5: Rota /26 (Ex: 10.1.1.64/26)
            roteador.cadastrarRota(
                new int[]{10, 1, 1, 64}, 
                new int[]{10, 0, 0, 4}, 
                new int[]{255, 255, 255, 192}, // Máscara /26
                "eth4"
            );

            // Rota 6: /24 (Ex: 10.1.2.0/24)
            roteador.cadastrarRota(
                new int[]{10, 1, 2, 0}, 
                new int[]{10, 0, 0, 5}, 
                new int[]{255, 255, 255, 0}, 
                "eth2"
            );

            // Rota 7: /8 (Ex: 10.0.0.0/8) - Catch-all para rede 10.x.x.x
            roteador.cadastrarRota(
                new int[]{10, 0, 0, 0}, 
                new int[]{10, 0, 0, 6}, 
                new int[]{255, 0, 0, 0}, 
                "eth3"
            );
            
            // Rota 8: /32 (Host específico) (Ex: 192.168.1.100/32)
            roteador.cadastrarRota(
                new int[]{192, 168, 1, 100}, 
                new int[]{10, 0, 0, 7}, 
                new int[]{255, 255, 255, 255}, // Máscara /32
                "eth2"
            );

            // Rota 9: /12 (Ex: 172.16.0.0/12)
            roteador.cadastrarRota(
                new int[]{172, 16, 0, 0}, 
                new int[]{10, 0, 0, 8}, 
                new int[]{255, 240, 0, 0}, // Máscara /12
                "eth4"
            );

            // Rota 10: Outra /24 (Ex: 192.168.20.0/24)
            roteador.cadastrarRota(
                new int[]{192, 168, 20, 0}, 
                new int[]{10, 0, 0, 9}, 
                new int[]{255, 255, 255, 0}, 
                "eth3"
            );
            
            System.out.println("Total de Rotas na Tabela: " + roteador.getTabelaRoteamento().size());

        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar rota: " + e.getMessage());
        }

        // --- Testando o método calcularRota ---
        System.out.println("\n--- Testando o Cálculo de Rota ---");
        
        // IP de destino para teste
        int[] ipDestino = {200, 168, 1, 50};
        System.out.println("IP de Destino: " + Arrays.toString(ipDestino));
        
        Rota melhorRota = roteador.calcularRota(ipDestino);
        
        if (melhorRota != null) {
            System.out.println("Melhor Rota Encontrada:");
            System.out.println("  Destino: " + Arrays.toString(melhorRota.getDestinationIp()));
            System.out.println("  Máscara: " + Arrays.toString(melhorRota.getMask()));
            System.out.println("  CIDR: /" + melhorRota.calculaCIDR());
            System.out.println("  Interface: " + melhorRota.getNetInterface());
        } else {
            System.out.println("Nenhuma rota correspondente encontrada (rotaCalculada é null).");
        }
    }
}