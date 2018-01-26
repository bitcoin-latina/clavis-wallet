package web3j;

import javafx.scene.control.Alert;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import rpc.Params;
import rpc.RPC;
import ui.Global;
import utils.Utils;
import web3j.accounts.Accounts;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Handles web3 transaction calls
 * TODO creating/transacting with BCL Smart Contracts
 */
public class Tx {
    private String from_Address, to_Address, password;
    private Web3j web3;
    private long amount;
    private String gasPrice;

    public Tx(String from_Address, String to_Address, long amount, String gasPrice, String password, Web3j web3) {
        this.from_Address = from_Address;
        this.to_Address = to_Address;
        this.amount = amount;
        this.password = password;
        this.web3 = web3;
        this.gasPrice=gasPrice;
    }

    /**
     * Sending transaction from one party to the next
     */
    public void Send(){
        System.out.println("Attempting to Unlock Account");
        boolean b = Accounts.unlock_account_time(Global.getGeth(),from_Address, password, 60000);
        if(b) {
            BigInteger wei = BigInteger.valueOf(amount);
            BigInteger conversion = BigInteger.valueOf(1000000000000000000l);
            wei = wei.multiply(conversion);
            long gasP = (long) Double.parseDouble(gasPrice);
            System.out.println(gasP);
            System.out.println(wei.toString());
            //All values must be in hex
            Params params = new Params();
            params.addParam("from", from_Address);
            params.addParam("to", to_Address);
            params.addParam("gasPrice", Utils.toHex(BigInteger.valueOf(gasP)));
            params.addParam("value", Utils.toHex(wei));

            RPC.rpc_call("eth_sendTransaction", params);
        }
        else {
            createAlert("Incorrect Password");
        }
    }
    public void oldSend(){
        Credentials credentials;
        try {
//            getAnEstimate();
            credentials = WalletUtils.loadCredentials(password, Wallet.getWalletFile(from_Address));
            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                    web3, credentials, to_Address,
                    BigDecimal.valueOf(amount), Convert.Unit.ETHER).send();
            //Get The Receipt in the background
//            Thread background = new Thread() {
//                public void run() {
                    createAlert("The Transaction went through... Here is the hash: \n"
                            + transactionReceipt.getTransactionHash());
//                }
//            };
        } catch (Exception e) {
            e.printStackTrace();
//            createAlert("The Transaction did not go through: \n"+ e.getStackTrace().toString());
        }
    }
    private void getAnEstimate(){
        Transaction transaction = Transaction.createContractTransaction(from_Address, BigInteger.valueOf(0),
                Gas.getGasPrice(web3), Gas.getGasLimit(web3), BigInteger.valueOf(amount),"");
        System.out.println("Estimated Gas: " + Gas.estimateGas(web3, transaction));
    }
    private void createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.show();
    }
}
