package com.softwaredevelopmentstuff.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> blocks = new ArrayList<>();
    private int difficulty;

    Blockchain(int difficulty) {
        this.difficulty = difficulty;
    }

    public void createBlock(String data) {
        Block previousBlock = blocks.size() > 0 ? blocks.get(blocks.size() - 1) : null;
        Block newBlock = new Block(data, previousBlock);
        newBlock.mineBlock(difficulty);
        blocks.add(newBlock);
    }

    public boolean isValid() {
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blocks.size(); i++) {
            Block previousBlock = blocks.get(i - 1);
            Block block = blocks.get(i);

            // correct link with previous block
            if (!block.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Invalid blockchain - incorrect linking");
                return false;
            }

            // correct hash
            if (!block.getHash().equals(block.computeHash(block.getNonce()))) {
                System.out.println("Invalid blockchain - incorrect hash");
                return false;
            }

            // proof of work - check if hash is solved
            if (!block.getHash().substring(0, difficulty).equals(hashTarget)) {
                System.out.println("Invalid blockchain - has has not been solved");
                return false;
            }
        }

        return true;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(blocks);
    }
}
