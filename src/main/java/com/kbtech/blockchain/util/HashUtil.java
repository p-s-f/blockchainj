package com.kbtech.blockchain.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

  public HashUtil() {}

  public static String hashToSHA256(final String text) {
//    MessageDigest digest = null;
//    try {
//      digest = MessageDigest.getInstance("SHA-256");
//    } catch (NoSuchAlgorithmException e) {
//      e.printStackTrace();
//    }
//    byte[] hash = digest.digest(text.getBytes());
//    return hash;

     String sha256hex = org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
     return sha256hex;
  }

  public static boolean isValidHashDifficulty(final String hash, final int difficulty) {
    StringBuilder howManyZero = new StringBuilder(256);

    for (int i=0; i < hash.length(); i++) {
      if (hash.charAt(i) != '0') {
//        if (howManyZero.length() > 3) {
//          System.out.println(howManyZero);
//        }
        return i >= difficulty;
      } else {
        howManyZero.append("0");
      }
    }
    return false;
  }


}
