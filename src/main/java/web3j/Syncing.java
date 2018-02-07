package web3j;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthSyncing;
import rpc.Params;
import rpc.RPC;
import ui.Global;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Logger;

public class Syncing {
    private static final Logger LOGGER = Logger.getLogger(Syncing.class.getName());

    public static double getSyncingProgress() {
        LOGGER.addHandler(Global.getLog_fh());
        String result = RPC.rpc_call("eth_syncing", new Params(), false);
        LOGGER.info("Syncing RPC Call :" + result);
        if (result.toLowerCase().equals("error")) {
            return 0;
        } else {
            if (result.toLowerCase().equals("false")) {
                if (Blocks.getHighestBlockNumber(Global.getWeb3j()).longValue() > 1) {
                    if (.95 < Blocks.getBlockNumber(Global.getWeb3j()).doubleValue() /
                            (Blocks.getHighestBlockNumber(Global.getWeb3j()).doubleValue()))
                        return 1;
                }
                return -1;
            } else {
                JSONObject res = new JSONObject(result);
                BigInteger highest_block = new BigInteger(res.getString("highestBlock").substring(2), 16);
                BigInteger currentBlock = new BigInteger(res.getString("currentBlock").substring(2), 16);
                LOGGER.info("Highest Block Number "+ highest_block + "\nCurrent Block "+ currentBlock);
                return currentBlock.doubleValue() / highest_block.doubleValue();
            }
        }
    }

    public static String getWalletStatus() {
        if (isSyncing()) {
            return "Syncing...";
        } else if (getSyncingProgress() > .99) {
            return "Synced!";
        } else {
            return "Not Synced.";
        }
    }

    private static boolean isSyncing() {
        try {
            EthSyncing ethSyncing = Global.getWeb3j().ethSyncing().send();
            return ethSyncing.isSyncing();
        } catch (IOException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
        return false;
    }
}
