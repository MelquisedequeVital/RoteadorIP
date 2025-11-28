package br.edu.ifpb.poo;

import java.util.ArrayList;
import java.util.LinkedList;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor

public class RoteadorIp {
    private ArrayList<Rota> tabelaRoteamento = new ArrayList<>();
    private LinkedList<String> netInterfaceList = new LinkedList<>();


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
        int match = 0;
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

        return rotaCalculada;
    }

    public void resetarTabela(){
        tabelaRoteamento.clear();
    }



}
