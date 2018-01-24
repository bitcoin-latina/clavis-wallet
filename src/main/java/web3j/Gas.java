package web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;

import java.io.IOException;
import java.math.BigInteger;

public class Gas {
    /**
     * This class handles web3 gas calls
     */
    public static BigInteger getGasPrice(Web3j web3){
        EthGasPrice ethGasPrice = null;
        try {
            ethGasPrice = web3.ethGasPrice().send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert ethGasPrice != null;
        return ethGasPrice.getGasPrice();
    }
    public static BigInteger getGasLimit(Web3j web3){
        EthBlock ethBlock = null;
        try {
            ethBlock = web3.ethGetBlockByNumber(DefaultBlockParameterName.PENDING,true).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert ethBlock.getBlock().getGasLimit() != null;
        return ethBlock.getBlock().getGasLimit();
    }
    public static BigInteger estimateGas(Web3j web3, org.web3j.protocol.core.methods.request.Transaction tx){
        try {
            EthEstimateGas estimateGas = web3.ethEstimateGas(tx).send();
            return estimateGas.getAmountUsed();
        } catch (IOException e) {
            e.printStackTrace();
        }
       return BigInteger.valueOf(0);
    }
}
