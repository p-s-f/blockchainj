package com.kbtech.blockchain;

import org.apache.commons.lang.time.StopWatch;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.List;

import static com.kbtech.blockchain.util.HashUtil.hashToSHA256;
import static com.kbtech.blockchain.util.HashUtil.isValidHashDifficulty;

public class Miner {

  final static Logger logger = Logger.getLogger(Miner.class);

  public Miner() {}

  Block mineBlock(final String input, final int difficulty, final String previousHash) {
    return mineBlock(input, difficulty, previousHash, BlockChain.getInstance().getCurrentIndex());
  }

  Block mineBlock(final String input, final int difficulty, final String previousHash, final long index) {
    double nonce = 0;
    String msg = "";
    String hash = hashToSHA256(input.toString());
    double attempt = 0;
    int million = 0;
    Date timestamp = new Date();

    logger.info(String.format("Input: %s  --  PreviousHash: %s  --  Hash: %s", input, previousHash, hash));

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    long currentTime = stopWatch.getTime();

    while (!isValidHashDifficulty(hash, difficulty)) {
      nonce++;
      million++;
      hash = hashToSHA256(String.format("%s%s%s%s%s",index , previousHash , timestamp , input , nonce));
      attempt++;
      if (attempt % 1000000 == 0) {
        million++;
        long timeDiff = (stopWatch.getTime() - currentTime) / 1000;
        logger.debug(String.format("%s million hashes! took [%ss]", million, timeDiff));
        currentTime = stopWatch.getTime();
      }
      logger.trace(String.format("Input: %s   - Hash: %s", msg, hash));
    }

    stopWatch.split();
    logger.info(String.format("got it! - took [%ss]", (stopWatch.getSplitTime()) / 1000));

    stopWatch.stop();

    logger.info(String.format("Input: %s   - Hash: %s", msg, hash));
    logger.info(String.format("Input: %s  -  Nonce: %s  -  Hash: %s", input, nonce, hash));

    Block minedBlock = new Block(previousHash, timestamp, input, hash, nonce);
    return minedBlock;
  }

}
