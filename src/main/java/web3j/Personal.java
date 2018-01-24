package web3j;

import org.web3j.protocol.admin.Admin;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Simple Class to create accoutn
 * TODO need to encrypt pass w/ BCRYPT
 */
public class Personal {
    private Admin geth;
    private String password;
    private BigInteger timeout = BigInteger.valueOf(10000);
    public Personal(Admin geth, String pass) {
        this.geth = geth;
        password = pass;
    }

    public String createAccount(){
        try {
            return geth.personalNewAccount(password).send().getAccountId();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void unlockAccount(String address){
        try {
            geth.personalUnlockAccount(address,password, timeout).send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
