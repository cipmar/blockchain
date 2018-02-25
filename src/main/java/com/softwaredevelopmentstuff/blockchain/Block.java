package com.softwaredevelopmentstuff.blockchain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static com.softwaredevelopmentstuff.blockchain.HashUtil.sha256;

public class Block {
    private String hash;
    private String previousHash;
    private String data;
    private long timestamp;
    private int nonce = 0;

    Block(String data, Block previousBlock) {
        this.previousHash = previousBlock != null ? previousBlock.getHash() : "";
        this.data = data;
        this.timestamp = System.currentTimeMillis();
        this.hash = computeHash(nonce);
    }

    public String computeHash(int nonce) {
        return sha256(previousHash + data + String.valueOf(timestamp) + nonce);
    }

    public void mineBlock(int difficulty) {
        final String target = new String(new char[difficulty]).replace('\0', '0');
        final AtomicBoolean found = new AtomicBoolean(false);
        final int nThreads = 8;

        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Collection<Callable<Integer>> callables = new ArrayList<>();

        IntStream.range(0, 8).forEach(i -> callables.add(() -> {
            int currentNo = i;

            while (!found.get()) {
                String tmpHash = computeHash(currentNo);

                if (tmpHash.substring(0, difficulty).equals(target)) {
                    found.set(true);

                    synchronized (this) {
                        this.nonce = currentNo;
                        this.hash = tmpHash;
                    }
                }
                currentNo += nThreads;
            }

            return currentNo;
        }));

        try {
            executorService.invokeAll(callables);
        } catch (InterruptedException e) {
            throw new RuntimeException("Unable to mine block. Tried for 1 hour.", e);
        }

        System.out.println("Block mined! " + hash + " Nonce: " + nonce);
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getData() {
        return data;
    }

    public long getTimestamp() {
        return timestamp;
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
