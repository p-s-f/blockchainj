package com.kbtech.blockchain.exceptions;

public class BlockChainCorruptedException extends Exception {

  public BlockChainCorruptedException() {}

  public BlockChainCorruptedException(String message) {
    super(message);
  }
}
