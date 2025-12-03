package br.edu.ifpb.poo;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor

public class Rota {
    private int[] destinationIp;
    private int[] gateway;
    private int[] mask;
    private String netInterface;

    public Rota(int[] destinationIp, int[] gateway, int[] mask, String netInterface) {

        if (destinationIp.length != 4) {
            throw new IllegalArgumentException("O array destinationIp deve conter exatamente 4 elementos (formato IPv4). Tamanho atual: " + destinationIp.length);
        }
        
        if (gateway.length != 4) {
            throw new IllegalArgumentException("O array gateway deve conter exatamente 4 elementos (formato IPv4). Tamanho atual: " + gateway.length);
        }
        
        if (mask.length != 4) {
            throw new IllegalArgumentException("O array mask deve conter exatamente 4 elementos (formato IPv4). Tamanho atual: " + mask.length);
        }

        this.destinationIp = destinationIp;
        this.gateway = gateway;
        this.mask = mask;
        this.netInterface = netInterface;
    }

    public int calculaCIDR(){
        int cidr = 0;

        for(int numero: this.mask){
            String binario = Integer.toBinaryString(numero);
            cidr += binario.length() - binario.replace("1", "").length();
        }

        return cidr;
    }
    @Override
    public String toString() {

        String destStr = destinationIp[0] + "." + destinationIp[1] + "." + destinationIp[2] + "." + destinationIp[3];
        String maskStr = mask[0] + "." + mask[1] + "." + mask[2] + "." + mask[3];
        String gwStr = gateway[0] + "." + gateway[1] + "." + gateway[2] + "." + gateway[3];

        return String.format("Dest: %-15s | Mask: %-15s | Gw: %-15s | Iface: %s (CIDR /%d)", 
                             destStr, maskStr, gwStr, netInterface, calculaCIDR());
    }
}