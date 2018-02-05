package web3j;

import javafx.application.Platform;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import ui.Global;
import ui.controllers.Controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Handles web3 calls reguarding block information
 */
public class Blocks {
    private static final Logger LOGGER = Logger.getLogger(Blocks.class.getName());
    public static BigInteger getBlockNumber(Web3j web3) {
        LOGGER.addHandler(Global.getLog_fh());
        BigInteger blockNumber = BigInteger.valueOf(0);
        try {
            EthBlockNumber ethBlockNumber = web3.ethBlockNumber().send();
            blockNumber = ethBlockNumber.getBlockNumber();
        } catch (IOException e) {
            LOGGER.warning("UNABLE TO GET BLOCK NUM "+ Arrays.toString(e.getStackTrace()));
        }
        return blockNumber;
    }

    public static BigInteger getHighestBlockNumber(Web3j web3) {
        LOGGER.addHandler(Global.getLog_fh());
        StringBuilder sb = new StringBuilder(Global.getBCL_monitor());
        BigInteger blockNumber = BigInteger.valueOf(0);
        try {
            EthBlock highestBlock = web3.
                    ethGetBlockByNumber(DefaultBlockParameter.valueOf("pending"),
                            false).send();
            blockNumber = highestBlock.getBlock().getNumber();
            StringBuilder stringBuilder = new StringBuilder(("Block " + blockNumber + " mined\n"));
            stringBuilder = stringBuilder.reverse();
            if (!sb.toString().contains(stringBuilder.toString())) {
                sb.append(stringBuilder.toString());
                Global.setBCL_monitor(sb.reverse().toString());
                sb.reverse();
                Global.setBCL_monitor(sb.toString());
            }
        } catch (Exception e) {
            LOGGER.warning("UNABLE TO GET HIGHEST BLOCK NUM "+ Arrays.toString(e.getStackTrace()));
        }
        return blockNumber;
    }

    public void subscribe() {
        LOGGER.addHandler(Global.getLog_fh());
        try {
            //Update Every 5 Seconds
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            //Update Ui From Background Thread
            Platform.runLater(() -> {
                Global.update_information();
                //Display to UI
                Controller d = Global.getLoader().getController();
                d.initialize();
            });
        } catch (Exception e) {
            LOGGER.info("UNABLE TO UPDATE" + Arrays.toString(e.getStackTrace()));
        }
    }
}