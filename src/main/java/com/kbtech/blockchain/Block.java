package com.kbtech.blockchain;

import java.util.Date;

public class Block {

  final private String previousHash;
  final private Date timeStamp;
  final private Ledger data;
  final private String hash;
  final private double nonce;

  public Block(final String previousHash, final Date timeStamp, final Ledger data, final String hash, final double nonce) {
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

  public Ledger getData() {
    return data;
  }

  public String getHash() {
    return hash;
  }

  public double getNonce() {
    return nonce;
  }
}
