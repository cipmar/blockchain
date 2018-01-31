package com.softwaredevelopmentstuff.blockchain;

import java.security.NoSuchAlgorithmException;

import static com.softwaredevelopmentstuff.blockchain.HashUtil.sha256;

public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timestamp;
    private int nonce = 0;

    Block(String data, Block previousBlock) throws NoSuchAlgorithmException {
        this.previousHash = previousBlock != null ? previousBlock.getHash() : "";
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.hash = computeHash();
    }

    public String computeHash() throws NoSuchAlgorithmException {
        return sha256(previousHash + data + String.valueOf(timestamp) + nonce);
    }

    public void mineBlock(int difficulty) throws NoSuchAlgorithmException {
        String target = new String(new char[difficulty]).replace('\0', '0');

        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = computeHash();
        }

        System.out.println("Block mined! " + hash);
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                '}';
    }
}
