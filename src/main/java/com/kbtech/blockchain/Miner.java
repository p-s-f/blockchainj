package com.kbtech.blockchain;

import org.apache.commons.lang.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

import static com.kbtech.blockchain.util.HashUtil.hashToSHA256;
import static com.kbtech.blockchain.util.HashUtil.isValidHashDifficulty;

public class Miner {

  final static Logger logger = LogManager.getLogger(Miner.class);

  public Miner() {}

  Block mineBlock(final String input, final int difficulty, final String previousHash) {
    return mineBlock(input, difficulty, previousHash, BlockChain.getInstance().getCurrentIndex());
  }

  Block mineBlock(final String input, final int difficulty, final String previousHash, final long index) {
    double nonce = 0;
    String msg = "";
    String hash = hashToSHA256(input);
    double attempt = 0;
    int million = 0;
    Date timestamp = new Date();

    logger.info("Input: {}  --  PreviousHash: {}  --  Hash: {}", input, previousHash, hash);

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    long currentTime = stopWatch.getTime();

    while (!isValidHashDifficulty(hash, difficulty)) {
      nonce++;
      hash = hashToSHA256(String.format("%s%s%s%s%s",index , previousHash , timestamp , input , nonce));
      attempt++;
      if (attempt % 1000000 == 0) {
        million++;
        long timeDiff = (stopWatch.getTime() - currentTime);
        logger.debug("{} million hashes! took [{}s]", million, timeDiff);
        currentTime = stopWatch.getTime();
      }
      logger.trace("Input: {}   - Hash: {}", msg, hash);
    }

    stopWatch.split();
    logger.info("got it! - took [{}s]", (stopWatch.getSplitTime()) / 1000);

    stopWatch.stop();

    logger.info("Input: {}   - Hash: {}", msg, hash);
    logger.info("Input: {}  -  Nonce: {}  -  Hash: {}", input, nonce, hash);

    return new Block(previousHash, timestamp, input, hash, nonce);
  }

}
