package com.softwaredevelopmentstuff.blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class HashUtil {
    static String sha256(final String input) {
        MessageDigest messageDigest;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] digest = messageDigest.digest(input.getBytes());

        StringBuilder sb = new StringBuilder();

        for (byte aDigest : digest) {
            String hex = Integer.toHexString(0xff & aDigest);

            if (hex.length() == 1) {
                hex = "0" + hex;
            }

            sb.append(hex);
        }

        return sb.toString();
    }
}
