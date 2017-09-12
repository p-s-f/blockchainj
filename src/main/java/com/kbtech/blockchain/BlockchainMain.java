package com.kbtech.blockchain;

import com.kbtech.blockchain.exceptions.BlockChainCorruptedException;
import org.apache.log4j.Logger;

public class BlockchainMain {

  final static Logger logger = Logger.getLogger(BlockchainMain.class);

  public static void main(String[] args) {

    // init new blockchain
    BlockChain blockChain = BlockChain.getInstance();
    Block genesisBlock = mineGenesisBlock();
    blockChain.addBlock(genesisBlock);
    Miner miner = new Miner();
    String previousHash = genesisBlock.getHash();
    for (int i=0; i < 20; i++) {
      Block nextBlock = miner.mineBlock("jeff the dog ate the cat when he went out in witby", 4, previousHash);
      logger.info("Mined a block");
      blockChain.addBlock(nextBlock);
      previousHash = nextBlock.getHash();
      logger.info("added block to blockChain");
      logger.info(String.format("blockchain length is now [%s] blocks!", blockChain.getChainLength()));
    }

    try {
      blockChain.validateChain();
    } catch (BlockChainCorruptedException e) {
      e.printStackTrace();
    }
  }

  private static Block mineGenesisBlock() {
    Miner miner = new Miner();
    Block genesisBlock = miner.mineBlock( "Genesis Block!", 4, "", 0);
    return genesisBlock;
  }
}
