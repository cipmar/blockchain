package com.softwaredevelopmentstuff.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Blockchain blockchain = new Blockchain(6);

        IntStream.range(1, 10).forEach(i -> blockchain.createBlock("block" + i));

        System.out.println(blockchain.toJson());
        System.out.println("Blockchain valid: " + blockchain.isValid());
    }
}
