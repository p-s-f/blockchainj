package com.kbtech.blockchain;

import org.apache.log4j.Logger;

import java.math.BigInteger;

public class BlockchainMain {

  final static Logger logger = Logger.getLogger(BlockchainMain.class);

  public static void main(String[] args) {

    // init new blockchain
    BlockChain blockChain = BlockChain.getInstance();
    Block genesisBlock = mineGenesisBlock();
    Miner miner = new Miner();
    Block previousBlock = genesisBlock;
    while(true) {
      String previousHash = previousBlock.getPreviousHash();
      Block nextBlock = miner.mineBlock("jeff the dog ate the cat when he went out in witby", 4, previousHash);
      logger.info("Mined a block");
      blockChain.addBlock(nextBlock);
      previousBlock = nextBlock;
      logger.info("added block to blockChain");
    }
  }

  private static Block mineGenesisBlock() {
    Miner miner = new Miner();
    Block genesisBlock = miner.mineBlock( "Genesis Block!", 4, "", BigInteger.valueOf(0));
    return genesisBlock;
  }
}
