package web3j;

import org.web3j.protocol.admin.Admin;
import ui.Global;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Simple Class to create accoutn
 * TODO need to encrypt pass w/ BCRYPT
 */
public class Personal {
    private Admin geth;
    private String password;
    private BigInteger timeout = BigInteger.valueOf(10000);
    private static final Logger LOGGER = Logger.getLogger(Personal.class.getName());
    public Personal(Admin geth, String pass) {
        this.geth = geth;
        password = pass;
        LOGGER.addHandler(Global.getLog_fh());
    }

    public String createAccount(){
        try {
            return geth.personalNewAccount(password).send().getAccountId();
        } catch (IOException e) {
            LOGGER.warning("UNABLE TO CREATE ACCOUNT\n\n"+ Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
