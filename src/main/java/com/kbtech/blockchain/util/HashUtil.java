package com.kbtech.blockchain.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HashUtil {

  final static Logger logger = LogManager.getLogger(HashUtil.class);

  public HashUtil() {
  }

  public static String hashToSHA256(final String text) {
    return org.apache.commons.codec.digest.DigestUtils.sha256Hex(text);
  }

  public static boolean isValidHashDifficulty(final String hash, final int difficulty) {
    StringBuilder howManyZero = new StringBuilder(256);

    for (int i = 0; i < hash.length(); i++) {
      if (hash.charAt(i) != '0') {
//          if (howManyZero.length() > 3) {
//            logger.debug(howManyZero);
//          }
        return i >= difficulty;
      } else {
        howManyZero.append("0");
      }
    }
    return false;
  }


}
