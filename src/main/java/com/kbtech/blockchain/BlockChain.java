package com.kbtech.blockchain;

import com.kbtech.blockchain.exceptions.BlockChainCorruptedException;
import com.kbtech.blockchain.exceptions.BlockNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.kbtech.blockchain.util.HashUtil.hashToSHA256;

public class BlockChain {

  private final Map<Long, Block> blockChain = new HashMap<Long, Block>();
  private long currentIndex = 0;

  private static BlockChain instance;
  final static Logger logger = LogManager.getLogger(BlockChain.class);

  private BlockChain() {}

  public static synchronized BlockChain getInstance(){
    if(instance == null){
      instance = new BlockChain();
    }
    return instance;
  }

  public Map<Long, Block> getBlockChain() {
    return blockChain;
  }

  public void addBlock(final Block block) {
    blockChain.put(currentIndex, block);
    logger.info("Added block to chain [{}]", block);
    currentIndex++;
  }

  public Block getBlock(final long index) throws BlockNotFoundException {
    if (blockChain.containsKey(index)) {
      logger.debug("Found block index: [{}]", index);
      return blockChain.get(index);
    }
    throw new BlockNotFoundException();
  }

  public long getCurrentIndex() {
    return currentIndex;
  }

  public long getChainLength() {
    return currentIndex+1;
  }

  /**
   * Validates the integrity of the blockchain by iterating through all the blocks and ensuring
   * that the hash of each block is correctly calculated and matches the stored hash value.
   *
   * This method recalculates the hash of each block based on its properties (index, previous hash,
   * timestamp, data, and nonce) and compares the computed hash with the stored hash to detect any
   * corruption. If a mismatch is detected, a {@code BlockChainCorruptedException} is thrown,
   * indicating that the blockchain has been tampered with or corrupted.
   *
   * If no mismatches are found, the blockchain is considered valid.
   *
   * Any {@code BlockNotFoundException} encountered while retrieving a block will be caught
   * and its stack trace will be printed, but this does not interrupt the validation process
   * for the rest of the blockchain.
   *
   * @throws BlockChainCorruptedException if corruption is detected in the blockchain.
   */
  public void validateChain() throws BlockChainCorruptedException {
    for (long i=0; i<currentIndex; i++) {
      try {
        Block currentBlock = getBlock(i);
        String previousHash = currentBlock.getPreviousHash();
        Date timeStamp = currentBlock.getTimeStamp();
        String data = currentBlock.getData();
        String hash = currentBlock.getHash();
        double nonce = currentBlock.getNonce();
        String msgToHash = String.format("%s%s%s%s%s", i, previousHash , timeStamp , data , nonce);
        logger.info("HASHING {}", msgToHash);
        String compHash = hashToSHA256(msgToHash);
        if (!compHash.equalsIgnoreCase(hash)) {
          logger.fatal("BLOCKCHAIN HAS BECOME CORRUPTED!");
          throw new BlockChainCorruptedException();
        }
      } catch (BlockNotFoundException e) {
        e.printStackTrace();
      }
    }

  }
}
