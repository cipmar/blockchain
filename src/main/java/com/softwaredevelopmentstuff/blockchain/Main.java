package com.softwaredevelopmentstuff.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        Blockchain blockchain = new Blockchain(6);

        IntStream.range(1, 5).forEach(i -> {
            Block previousBlock = blockchain.getLatestBlock();

            Block block = new Block("block " + i, previousBlock);
            block.mine(blockchain.getDifficulty());

            blockchain.addBlock(block);
        });

        System.out.println(blockchain.toJson());
        System.out.println("Blockchain valid: " + blockchain.isValid());
    }
}
