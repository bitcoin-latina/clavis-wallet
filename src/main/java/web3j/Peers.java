package web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetPeerCount;

import java.io.IOException;
import java.math.BigInteger;

public class Peers {
    /**
     * This class handles web3 peer calls
     */
    public static BigInteger getPeerCount(Web3j web3){
        NetPeerCount netPeerCount = null;
        try {
            netPeerCount = web3.netPeerCount().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert netPeerCount != null;
        return netPeerCount.getQuantity();
    }
}
