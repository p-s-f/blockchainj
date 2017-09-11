package com.kbtech.blockchain;

import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Date;

@Immutable
public class Block {

  final private String previousHash;
  final private Date timeStamp;
  final private String data;
  final private String hash;
  final private double nonce;

  public Block(final String previousHash, final Date timeStamp, final String data, final String hash, final double nonce) {
    this.previousHash = previousHash;
    this.timeStamp = timeStamp;
    this.data = data;
    this.hash = hash;
    this.nonce = nonce;
  }

  public String getPreviousHash() {
    return previousHash;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public String getData() {
    return data;
  }

  public String getHash() {
    return hash;
  }

  public double getNonce() {
    return nonce;
  }
}
