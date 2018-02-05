package web3j;

import javafx.scene.control.Alert;
import org.web3j.protocol.Web3j;
import rpc.Params;
import rpc.RPC;
import ui.Global;
import utils.Utils;
import web3j.accounts.Accounts;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Logger;

/**
 * Handles web3 transaction calls
 * TODO creating/transacting with BCL Smart Contracts
 */
public class Tx {
    private String from_Address, to_Address, password;
    private double amount;
    private String gasPrice;
    private static final Logger LOGGER = Logger.getLogger(Tx.class.getName());
    public Tx(String from_Address, String to_Address, double amount, String gasPrice, String password, Web3j web3) {
        this.from_Address = from_Address;
        this.to_Address = to_Address;
        this.amount = amount;
        this.password = password;
        this.gasPrice = gasPrice;
        LOGGER.addHandler(Global.getLog_fh());
    }

    /**
     * Sending transaction from one party to the next
     */
    public void Send() {
        LOGGER.info("Unlocking Account...");
        boolean b = Accounts.unlock_account_time(Global.getGeth(), from_Address, password, 60000);
        if (b) {
            LOGGER.info(" -> Success");
            BigInteger conversion = BigInteger.valueOf(1000000000000000000L);
            BigDecimal wei = BigDecimal.valueOf(amount * (conversion).doubleValue());
            long gasP = (long) Double.parseDouble(gasPrice);
            LOGGER.info("Gas Price ->" + gasP);
            LOGGER.info("Amount ->" + wei.toString());
            //All values must be in hex
            Params params = new Params();
            params.addParam("from", from_Address);
            params.addParam("to", to_Address);
            params.addParam("gasPrice", Utils.toHex(BigInteger.valueOf(gasP)));
            params.addParam("value", Utils.toHex(wei.toBigInteger()));
            RPC.rpc_call("eth_sendTransaction", params, true);
        } else {
            createAlert();
            LOGGER.warning("UNABLE TO UNLOCK THE ACCOUNT (Incorrect Pass)");
        }
    }

    private void createAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText("Incorrect Password");
        alert.show();
    }
}
