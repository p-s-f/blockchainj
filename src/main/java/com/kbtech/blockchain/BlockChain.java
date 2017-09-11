package com.kbtech.blockchain;

import com.kbtech.blockchain.exceptions.BlockNotFoundException;
import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class BlockChain {

  private Map<BigInteger, Block> blockChain = new HashMap<BigInteger, Block>();
  private BigInteger currentIndex = BigInteger.valueOf(0);

  private static BlockChain instance;
  final static Logger logger = Logger.getLogger(BlockChain.class);

  private BlockChain() {}

  public static synchronized BlockChain getInstance(){
    if(instance == null){
      instance = new BlockChain();
    }
    return instance;
  }

  public Map<BigInteger, Block> getBlockChain() {
    return blockChain;
  }

  public void addBlock(final Block block) {
    currentIndex.add(BigInteger.valueOf(1));
    blockChain.put(currentIndex, block);
    logger.info(String.format("Added block to chain [%s]", block));
  }

  public Block getBlock(final BigInteger index) throws BlockNotFoundException {
    if (blockChain.containsKey(index)) {
      logger.debug(String.format("Found block index: [%s] - [%s]", index));
      return blockChain.get(index);
    }
    throw new BlockNotFoundException();
  }

  public BigInteger getCurrentIndex() {
    return currentIndex;
  }
}
