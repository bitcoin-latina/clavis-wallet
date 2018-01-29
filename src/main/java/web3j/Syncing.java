package web3j;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.web3j.protocol.core.methods.response.EthSyncing;
import rpc.Params;
import rpc.RPC;
import ui.Global;

import java.io.IOException;
import java.math.BigInteger;

public class Syncing {
    public static double getSyncingProgress(){
        String result =RPC.rpc_call("eth_syncing",new Params(), false);
        if(result.toLowerCase().equals("error")){
            System.out.println("Syncing contains error");
            return 0;
        }
        else{
            //Parse
            if(result.toLowerCase().equals("false")){
                System.out.println("Syncing percentage returned false...");
                if(Blocks.getHighestBlockNumber(Global.getWeb3j()).longValue()>1){
                    if(.95<Blocks.getBlockNumber(Global.getWeb3j()).doubleValue()/
                            (Blocks.getHighestBlockNumber(Global.getWeb3j()).doubleValue()))
                        return 1;
                }
                return -1;
            }
            else {
                JSONObject res = new JSONObject(result);
                BigInteger highest_block = new BigInteger(res.getString("highestBlock").substring(2), 16);
                BigInteger currentBlock = new BigInteger(res.getString("currentBlock").substring(2), 16);
                return currentBlock.doubleValue() / highest_block.doubleValue();
            }
        }
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
