package com.kbtech.blockchain;

import com.kbtech.blockchain.exceptions.BlockChainCorruptedException;
import com.kbtech.blockchain.util.TimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockchainMain {

  final static Logger logger = LogManager.getLogger(BlockchainMain.class);
  final static int difficulty = 6;

  public static List<Integer> hashCount = new ArrayList<>();

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
      Block nextBlock = miner.mineBlock("jeff the dog ate the cat when he went out in witby", difficulty, previousHash, BlockChain.getInstance().getCurrentIndex());
      logger.info("Mined a block");
      blockChain.addBlock(nextBlock);
      previousHash = nextBlock.getHash();
      logger.info("added block to blockChain");
      logger.info("blockchain length is now [{}] blocks!", blockChain.getChainLength());
    }

    try {
      blockChain.validateChain();
      final double timeTookSeconds = (System.currentTimeMillis() - startTime) / 1000.0;
      logger.info("Took {} to generate 20 valid blocks at a difficulty level of [{}]", TimeUtil.formatTime(timeTookSeconds), difficulty);
      logger.info("Took {} seconds to generate 20 valid blocks at a difficulty level of [{}]", timeTookSeconds, difficulty);
      logger.info(String.format("Average of %.2f million hashes to mine a block at %s difficulty", hashCount.stream()
              .mapToInt(Integer::intValue)
              .average()
              .orElse(0.0), difficulty));
      logger.debug("Hashes (in millions) per block were: {} ",hashCount.stream()
              .map(String::valueOf)
              .collect(Collectors.joining(", ")));
    } catch (BlockChainCorruptedException e) {
      logger.fatal("OH NOES - SOMEONE IS HACKING WITH YOUR CHAIN!");
      System.exit(1);
    }
  }

  private Block mineGenesisBlock(Miner miner) {
    return miner.mineBlock("Genesis Block!", difficulty, "", 0);
  }
}
