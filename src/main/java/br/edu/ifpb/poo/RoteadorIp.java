package br.edu.ifpb.poo;

import java.util.ArrayList;
import java.util.LinkedList;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class RoteadorIp {
    private LinkedList<String> tabelaRoteamento;
    private ArrayList<String> interfaces;
    private static final int DESTINATION_IP = 0;
    private static final int GATEWAY  = 1;
    private static final int MASK  = 2;
    private static final int INTERFACE  = 3;


}
