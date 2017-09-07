package com.kbtech.blockchain;

import org.apache.commons.lang.time.StopWatch;

import static com.kbtech.blockchain.util.HashUtil.hashToSHA256;
import static com.kbtech.blockchain.util.HashUtil.isValidHashDifficulty;

public class BlockchainMain {

  public static void main(String[] args) {
    double nonce = 0;
    String input = "jeff the dog ate the cat when he went out in witby";
    String msg = "";
    String hash = hashToSHA256(input);
    double attempt = 0;
    int million = 0;

    System.out.println(String.format("Input: %s   -- Hash: %s", input, hash));

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    long startTime = stopWatch.getStartTime();
    long currentTime = stopWatch.getTime();

    while (!isValidHashDifficulty(hash, 8, false)) {
      nonce ++;
      msg = String.format("%s%s", input, nonce);
      hash = hashToSHA256(msg);
      attempt ++;
      if (attempt%1000000 == 0) {
        million++;
        long timeDiff = (stopWatch.getTime() - currentTime) / 1000;
        System.out.println(String.format("%s million hashes! took [%ss]", million, timeDiff));
        currentTime = stopWatch.getTime();
      }
//      System.out.println(String.format("Input: %s   - Hash: %s", msg, hash));
    }

    stopWatch.stop();

    System.out.println(String.format("got it! - took [%ss]", (stopWatch.getTime() - startTime) / 1000));
    hash = hashToSHA256(msg);
    System.out.println(String.format("Input: %s   - Hash: %s", msg, hash));

    System.out.println(String.format("Input: %s  -  Nonce: %s  -  Hash: %s", input, nonce, hash));
  }
}
