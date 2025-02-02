package com.kbtech.blockchain.util;

public class HashUtil {

  public static String hashToSHA256(final String text) {
    return org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
  }

  public static boolean isValidHashDifficulty(String hash, int difficulty) {
    if (hash == null || hash.length() < difficulty) {
        return false;
    }
    return hash.startsWith("0".repeat(difficulty));
  }
}
