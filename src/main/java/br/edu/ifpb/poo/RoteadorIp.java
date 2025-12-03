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
        int match = 0;

        for(Rota rota: tabelaRoteamento){
        match = 0;
        rotaAtual = rota;
        int[] ipAtual = rotaAtual.getDestinationIp();

        for(int ipIndex = 0; ipIndex < ipAtual.length; ipIndex++){
            if(ipAtual[ipIndex] == ip[ipIndex]){
                match++;
            } else {
                break;
            }
        }

        if(match > bestMatch && rotaAtual.calculaCIDR() > maiorCIDR){
            bestMatch = match;
            maiorCIDR = rotaAtual.calculaCIDR();
            rotaCalculada = rotaAtual;
        }
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

    public boolean atualizarRota(int[] destinationIp, int[] mask, int[] novoGateway, String novaInterface) {
        for (Rota rota : tabelaRoteamento) {
            if (Arrays.equals(rota.getDestinationIp(), destinationIp) && 
                Arrays.equals(rota.getMask(), mask)) {
                
                if (!netInterfaceList.contains(novaInterface)) {
                    throw new IllegalArgumentException("Interface não cadastrada");
                }

                rota.setGateway(novoGateway);
                rota.setNetInterface(novaInterface);
                return true; 
            }
        }
        return false; 
    }
    
    public ArrayList<Rota> getTabelaRoteamento() {
        return tabelaRoteamento;
    }

}
