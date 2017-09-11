package com.kbtech.blockchain.exceptions;

public class BlockNotFoundException extends Exception {

  public BlockNotFoundException() {}

  public BlockNotFoundException(String message) {
    super(message);
  }
}