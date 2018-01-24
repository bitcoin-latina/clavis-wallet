package web3j;

import javafx.application.Platform;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import rx.Subscription;
import ui.Global;
import ui.controllers.Controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * Handles web3 calls reguarding block information
 */
public class Blocks {
    /**
     * Function returns latest block number
     * @param web3
     * @return Latest Block number on your node
     */
    public static BigInteger getBlockNumber(Web3j web3){
        BigInteger blockNumber = BigInteger.valueOf(0);
        try {
            EthBlockNumber ethBlockNumber = web3.ethBlockNumber().send();
            blockNumber = ethBlockNumber.getBlockNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blockNumber;
    }
    public static BigInteger getHighestBlockNumber(Web3j web3) {
        StringBuilder sb=new StringBuilder(Global.getBCL_monitor());
        BigInteger blockNumber = BigInteger.valueOf(0);
        try {
            EthBlock highestBlock = web3.
                    ethGetBlockByNumber(DefaultBlockParameter.valueOf("pending"),
                            false).send();
            blockNumber = highestBlock.getBlock().getNumber();
            StringBuilder stringBuilder = new StringBuilder(("Block " +blockNumber +" mined\n"));
            stringBuilder = stringBuilder.reverse();
            if(!sb.toString().contains(stringBuilder.toString())) {
                sb.append(stringBuilder.toString());
                Global.setBCL_monitor(sb.reverse().toString());
                sb.reverse();
                Global.setBCL_monitor(sb.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return blockNumber;
    }
    public void subscribe(){
//        StringBuilder sb=new StringBuilder(Global.getBCL_monitor());
////        Subscription subscription = Global.getWeb3j().blockObservable(false).subscribe(block -> {
////            Platform.runLater(() -> {
////                try {
////                    //Appending blocks at the beginning
////                    StringBuilder stringBuilder = new StringBuilder("Block " + block.getBlock().getNumber() +
////                            " mined \n");
////                    stringBuilder = stringBuilder.reverse();
////                    sb.append(stringBuilder.toString());
////                    Global.setBCL_monitor(sb.reverse().toString());
//                    sb.reverse();
//                    Global.setBCL_monitor(sb.toString());
////                    //Update information everytime something is mined
////                    Global.update_information();
////                    //Display to UI
////                    Controller d= Global.getLoader().getController();
////                    d.initialize();
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////            });
////        }, Throwable::printStackTrace);
////        try {
////            TimeUnit.SECONDS.sleep(2);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Platform.runLater(() -> {
                Global.update_information();
                //Display to UI
                Controller d = Global.getLoader().getController();
                d.initialize();
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
