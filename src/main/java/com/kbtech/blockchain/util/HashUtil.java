package com.kbtech.blockchain.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

  public HashUtil() {
  }

  public static String hashToSHA256(final String text) {
    return org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
  }

  public static boolean isValidHashDifficulty(final String hash, final int difficulty, boolean output) {
    StringBuilder howManyZero = new StringBuilder(256);

    for (int i = 0; i < hash.length(); i++) {
      if (hash.charAt(i) != '0') {
        if (output) {
          if (howManyZero.length() > 3) {
            System.out.println(howManyZero);
          }
        }
        return i >= difficulty;
      } else {
        howManyZero.append("0");
      }
    }
    return false;
  }


}
