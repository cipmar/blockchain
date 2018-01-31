package com.softwaredevelopmentstuff.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, JsonProcessingException {
        Blockchain blockchain = new Blockchain(5);

        blockchain.createBlock("First block");
        blockchain.createBlock("Second block");
        blockchain.createBlock("Third block");

        System.out.println(blockchain.toJson());
        System.out.println("Blockchain valid: " + blockchain.isValid());
    }
}
