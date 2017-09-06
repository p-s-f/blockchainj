package com.kbtech.blockchain;

import static com.kbtech.blockchain.util.HashUtil.hashToSHA256;
import static com.kbtech.blockchain.util.HashUtil.isValidHashDifficulty;

public class BlockchainMain {

  public static void main(String[] args) {
    int nonce = 0;
    String input = "jeff the dog ate the cat when he went out in witby";
    String msg = "";
    String hash = hashToSHA256(input);
    float attempt = 0;
    int million = 0;

    System.out.println(String.format("Input: %s   -- Hash: %s", input, hash));
    while (!isValidHashDifficulty(hash, 6)) {
      nonce = nonce + 1;
      msg = String.format("%s%s", input, nonce);
      hash = hashToSHA256(msg);
      attempt++;
      if (attempt%1000000 == 0) {
        million++;
        System.out.println(String.format("%s million hashes!", million));
      }
    }

    System.out.println("got it!");
    hash = hashToSHA256(msg);
    System.out.println(String.format("Input: %s   - Hash: %s", msg, hash));

    System.out.println(String.format("Input: %s  -  Nonce: %s  -  Hash: %s", input, nonce, hash));
  }
}
