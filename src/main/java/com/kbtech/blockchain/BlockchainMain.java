package com.kbtech.blockchain;

import com.kbtech.blockchain.exceptions.BlockChainCorruptedException;
import org.apache.log4j.Logger;

import java.util.List;

public class BlockchainMain {

  final static Logger logger = Logger.getLogger(BlockchainMain.class);

  public static void main(String[] args) {

    // init new blockchain
    BlockChain blockChain = BlockChain.getInstance();
    BlockchainMain blockchainMain = new BlockchainMain();
    Block genesisBlock = blockchainMain.mineGenesisBlock();
    blockChain.addBlock(genesisBlock);
    Miner miner = new Miner();
    String previousHash = genesisBlock.getHash();
    for (int i=0; i < 20; i++) {
      Ledger instance = Ledger.getInstance();
      try {
        int rand = (int) Math.random();
        instance.storeMessage(String.format("%s jeff the dog ate the cat when he went out in witby",rand));
      } catch (Exception e) {
        e.printStackTrace();
      }
      Block nextBlock = miner.mineBlock(instance.getLedger(), 4, previousHash);
      logger.info("Mined a block");
      blockChain.addBlock(nextBlock);
      previousHash = nextBlock.getHash();
      logger.info("added block to blockChain");
      logger.info(String.format("blockchain length is now [%s] blocks!", blockChain.getChainLength()));
    }

    try {
      blockChain.validateChain();
    } catch (BlockChainCorruptedException e) {
      logger.fatal("OH NOES - SOMEONE IS HACKING WITH YOUR CHAIN!");
      System.exit(1);
    }
  }

  private Block mineGenesisBlock() {
    Miner miner = new Miner();
    Block genesisBlock = miner.mineBlock( "Genesis Block!", 4, "", 0);
    return genesisBlock;
  }
}
