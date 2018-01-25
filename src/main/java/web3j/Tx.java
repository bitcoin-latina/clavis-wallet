package web3j;

import javafx.scene.control.Alert;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

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

    public Tx(String from_Address, String to_Address, long amount, String password, Web3j web3) {
        this.from_Address = from_Address;
        this.to_Address = to_Address;
        this.amount = amount;
        this.password = password;
        this.web3 = web3;
    }

    /**
     * Sending transaction from one party to the next
     */
    public void Send(){
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
