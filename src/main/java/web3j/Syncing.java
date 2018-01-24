package web3j;

import org.web3j.protocol.core.methods.response.EthSyncing;
import ui.Global;

import java.io.IOException;

public class Syncing {
    public static double getSyncingProgress(){
        Double result;
        System.out.println("Highest Block is = "+ Blocks.getHighestBlockNumber(Global.getWeb3j()));
        System.out.println("Current Block is = "+ Blocks.getBlockNumber(Global.getWeb3j()));
        result = (Blocks.getBlockNumber(Global.getWeb3j()).doubleValue()/
                Blocks.getHighestBlockNumber(Global.getWeb3j()).doubleValue());
        return result;
    }
    public static String getWalletStatus(){
        if(isSyncing()){ return "Syncing..."; }
        else if (getSyncingProgress() > .99) { return "Synced!"; }
        else{ return "Not Synced."; }
    }
    public static boolean isSyncing(){
        try {
            EthSyncing ethSyncing = Global.getWeb3j().ethSyncing().send();
            return ethSyncing.isSyncing();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
