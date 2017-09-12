package com.kbtech.blockchain;

import com.kbtech.blockchain.exceptions.BlockChainCorruptedException;
import com.kbtech.blockchain.exceptions.BlockNotFoundException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.kbtech.blockchain.util.HashUtil.hashToSHA256;

public class BlockChain {

  private Map<Long, Block> blockChain = new HashMap<Long, Block>();
  private long currentIndex = 0;

  private static BlockChain instance;
  final static Logger logger = Logger.getLogger(BlockChain.class);

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
    logger.info(String.format("Added block to chain [%s]", block));
    currentIndex++;
  }

  public Block getBlock(final long index) throws BlockNotFoundException {
    if (blockChain.containsKey(index)) {
      logger.debug(String.format("Found block index: [%s]", index));
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

  public void validateChain() throws BlockChainCorruptedException {
    for (long i=0; i<=currentIndex; i++) {
      try {
        Block currentBlock = getBlock(i);
        String previousHash = currentBlock.getPreviousHash();
        Date timeStamp = currentBlock.getTimeStamp();
        String data = currentBlock.getData();
        String hash = currentBlock.getHash();
        double nonce = currentBlock.getNonce();
        String msgToHash = String.format("%s%s%s%s%s", i, previousHash , timeStamp , data , nonce);
        logger.info(String.format("HASHING %s", msgToHash));
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
