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

    public void addBlock(Block block) {
        Block previousBlock = getLatestBlock();

        if (isBlockValid(block, previousBlock)) {
            blocks.add(block);
        }
    }

    public Block getLatestBlock() {
        return blocks.size() > 0 ? blocks.get(blocks.size() - 1) : null;
    }

    public boolean isValid() {
        for (int i = 1; i < blocks.size(); i++) {
            Block previousBlock = blocks.get(i - 1);
            Block block = blocks.get(i);

            if (!isBlockValid(block, previousBlock)) {
                return false;
            }
        }

        return true;
    }

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(blocks);
    }

    private boolean isBlockValid(Block block, Block previousBlock) {
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        // correct link with previous block
        if (previousBlock != null && !block.getPreviousHash().equals(previousBlock.getHash())) {
            System.out.println("Invalid block - incorrect linking");
            return false;
        }

        // correct hash
        if (!block.getHash().equals(block.computeHash())) {
            System.out.println("Invalid block - incorrect hash");
            return false;
        }

        // proof of work - check if hash is solved
        if (!block.getHash().substring(0, difficulty).equals(hashTarget)) {
            System.out.println("Invalid block - has has not been solved");
            return false;
        }

        return true;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
