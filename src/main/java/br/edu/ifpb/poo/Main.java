package br.edu.ifpb.poo;

public class Main {
    public static void main(String[] args) {

        int[] ip = {192,168,10,15};
        int[] mask = {255, 255, 255, 0};
        int[] gateway = {127,0,0,1};
        
        RoteadorIp roteador = new RoteadorIp();

        roteador.cadastrarInterface("eth0");

        roteador.cadastrarRota(ip, gateway, mask, "eth0");

        System.out.println(roteador);

        roteador.resetarTabela();

        System.out.println(roteador);
        
    }
}