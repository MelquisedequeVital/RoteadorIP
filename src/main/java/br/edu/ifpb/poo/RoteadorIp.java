package br.edu.ifpb.poo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;

import java.util.Iterator; 
import java.util.Arrays;   
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RoteadorIp {
    private ArrayList<Rota> tabelaRoteamento = new ArrayList<>();
    private LinkedList<String> netInterfaceList = new LinkedList<>();
    private final int[] IP_DEFAULT = {0,0,0,0};
    private boolean notacaoCIDR = false;


    public void cadastrarInterface(String netInterface){
        if(!netInterfaceList.contains(netInterface)){
            netInterfaceList.add(netInterface);
        }
        else{
            throw new IllegalArgumentException("Interface já cadastrada");
        }
    }

    public void cadastrarRota(int[] ip, int[] gateway, int[] mask, String netInterface){
        if(!netInterfaceList.contains(netInterface)){
            throw new IllegalArgumentException("Interface não cadastrada");
        }

        tabelaRoteamento.add(new Rota(ip, gateway, mask, netInterface));

    }

    public Rota calcularRota(int[] ip){
        Rota rotaCalculada = null;
        Rota rotaAtual;
        int bestMatch = 0;
        int maiorCIDR = 0;

        for(Rota rota: tabelaRoteamento){
        boolean match = false;
        rotaAtual = rota;
        int[] mascaraAtual = rotaAtual.getMask();
        int[] redeAtual = rotaAtual.calcularRede();
        int[] verificaRede = new int[4];

        for (int i = 0; i < 4; i++) {
            verificaRede[i] = ip[i] & mascaraAtual[i];
        }

        if(Arrays.equals(verificaRede, redeAtual)){
            if(rotaAtual.calculaCIDR() > maiorCIDR){
                rotaCalculada = rotaAtual;
                maiorCIDR = rotaAtual.calculaCIDR();
            }
        }

        // for(int ipIndex = 0; ipIndex < ipAtual.length; ipIndex++){
        //     if(ipAtual[ipIndex] == ip[ipIndex]){
        //         match++;
        //     } else {
        //         break;
        //     }
        // }

        // if(match > bestMatch && rotaAtual.calculaCIDR() > maiorCIDR){
        //     bestMatch = match;
        //     maiorCIDR = rotaAtual.calculaCIDR();
        //     rotaCalculada = rotaAtual;
        // }


        }

        if(rotaCalculada == null){
            for(Rota rota:tabelaRoteamento){
                if(Arrays.equals(rota.getDestinationIp(),IP_DEFAULT)){
                    rotaCalculada = rota;
                }
            }

        }

        return rotaCalculada;
    }

    public void resetarTabela(){
        tabelaRoteamento.clear();
    }

    public boolean removerRota(int[] destinationIp, int[] mask) {
        Iterator<Rota> iterator = tabelaRoteamento.iterator();
        while (iterator.hasNext()) {
            Rota rota = iterator.next();
      
            if (Arrays.equals(rota.getDestinationIp(), destinationIp) && 
                Arrays.equals(rota.getMask(), mask)) {
                iterator.remove();
                return true; 
            }
        }
        return false; 
    }

    public boolean atualizarRota(int[] ipAntigo, int[] maskAntiga, int[] novoIp, int[] novaMask, int[] novoGateway, String novaInterface) {

        if (!netInterfaceList.contains(novaInterface)) {
            throw new IllegalArgumentException("Interface não cadastrada");
        }

        for (Rota rota : tabelaRoteamento) {
            if (Arrays.equals(rota.getDestinationIp(), ipAntigo) && 
                Arrays.equals(rota.getMask(), maskAntiga)) {
                
                
                rota.setDestinationIp(novoIp);
                rota.setMask(novaMask);
                rota.setGateway(novoGateway);
                rota.setNetInterface(novaInterface);
                return true; 
            }
        }
        return false; 
    }

    public void mudarNotacao(){
        if(notacaoCIDR == false){
            notacaoCIDR = true;
        } else{
            notacaoCIDR = false;
        }
    }

    @Override
    public String toString(){
        String tabelaString = "----------------------------------------TABELA DE ROTEAMENTO----------------------------------------\n";
        if(notacaoCIDR){
            for (Rota r : tabelaRoteamento) {
                int cidr = r.calculaCIDR(); 
                String cidrFormatado = String.format("| CIDR: %-15s", "/" + cidr);
                String regexCorrigida = "\\| Mask:\\s*\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\s*";
                tabelaString = tabelaString + r.toString().replaceAll(regexCorrigida, cidrFormatado) + "\n";
            }
        } else{
            for (Rota r : tabelaRoteamento) {
                tabelaString = tabelaString + r.toString() + "\n"; 
            }
        }

        return tabelaString;
    }
}
