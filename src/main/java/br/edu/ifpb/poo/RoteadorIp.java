package br.edu.ifpb.poo;

import java.util.ArrayList;
import java.util.LinkedList;

import lombok.Data;



@Data

public class RoteadorIp {
    private ArrayList<Rota> tabelaRoteamento = new ArrayList<>();
    private LinkedList<String> netInterfaceList = new LinkedList<>();
    private static final int[] DEFAULT_IP = {0,0,0,0};
    private static final int[] DEFAULT_MASK = {0,0,0,0};
    private static final int[] DEFAULT_GATEWAY = {0,0,0,0};
    private static final String DEFAULT_NET_INTERFACE = "eth1";


    public RoteadorIp(){
        netInterfaceList.add(DEFAULT_NET_INTERFACE);
        tabelaRoteamento.add(new Rota(DEFAULT_IP, DEFAULT_MASK, DEFAULT_MASK, DEFAULT_NET_INTERFACE));
    }

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

    public void resetarTabela(){
        tabelaRoteamento.clear();
    }



}
