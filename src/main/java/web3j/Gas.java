package web3j;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import ui.Global;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Logger;

public class Gas {
    /**
     * This class handles web3 gas calls
     */
    private static final Logger LOGGER = Logger.getLogger(Gas.class.getName());
    public static BigInteger getGasLimit(Web3j web3) {
        LOGGER.addHandler(Global.getLog_fh());
        EthBlock ethBlock = null;
        try {
            ethBlock = web3.ethGetBlockByNumber(DefaultBlockParameterName.PENDING, true).send();
        } catch (IOException e) {
            LOGGER.warning("UNABLE TO GET GAS LIMIT \n\n"+ Arrays.toString(e.getStackTrace()));
        }
        assert ethBlock != null;
        assert ethBlock.getBlock().getGasLimit() != null;
        return ethBlock.getBlock().getGasLimit();
    }
}
