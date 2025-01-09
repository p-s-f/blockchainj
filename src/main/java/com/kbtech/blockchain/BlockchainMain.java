package com.kbtech.blockchain;

import com.kbtech.blockchain.exceptions.BlockChainCorruptedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockchainMain {

  final static Logger logger = LogManager.getLogger(BlockchainMain.class);
  final static int difficulty = 7;

  public static void main(String[] args) {

    // init new blockchain
    final Miner miner = new Miner();
    final BlockChain blockChain = BlockChain.getInstance();
    final BlockchainMain blockchainMain = new BlockchainMain();
    final long startTime = System.currentTimeMillis();
    final Block genesisBlock = blockchainMain.mineGenesisBlock(miner);
    blockChain.addBlock(genesisBlock);
    String previousHash = genesisBlock.getHash();
    for (int i=0; i < 20; i++) {
      Ledger instance = Ledger.getInstance();
      try {
        int rand = (int) Math.random();
        instance.storeMessage(String.format("%s jeff the dog ate the cat when he went out in witby",rand));
      } catch (Exception e) {
        e.printStackTrace();
      }
      Block nextBlock = miner.mineBlock("jeff the dog ate the cat when he went out in witby", 4, previousHash);
      logger.info("Mined a block");
      blockChain.addBlock(nextBlock);
      previousHash = nextBlock.getHash();
      logger.info("added block to blockChain");
      logger.info("blockchain length is now [{}] blocks!", blockChain.getChainLength());
    }

    try {
      blockChain.validateChain();
      final long timeTook = (System.currentTimeMillis() - startTime) / 1000;
      logger.info((String.format("Took %s seconds to generate 20 valid blocks at a difficulty level of [%s]", timeTook, difficulty)));
    } catch (BlockChainCorruptedException e) {
      logger.fatal("OH NOES - SOMEONE IS HACKING WITH YOUR CHAIN!");
      System.exit(1);
    }
  }

  private Block mineGenesisBlock(Miner miner) {
    return miner.mineBlock("Genesis Block!", difficulty, "", 0);
  }
}
