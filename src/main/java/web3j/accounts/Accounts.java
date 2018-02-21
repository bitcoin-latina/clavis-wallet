package web3j.accounts;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;
import ui.Global;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Accounts {
    private static final Logger LOGGER = Logger.getLogger(Accounts.class.getName());
    private List<String> accounts;
    private List<Account> accountList = new ArrayList<>();

    public Accounts() throws IOException {
        LOGGER.addHandler(Global.getLog_fh());
        EthAccounts ethAccounts = Global.getWeb3j().ethAccounts().send();
        accounts = ethAccounts.getAccounts();
    }

    public List<Account> getAccounts() {
        try {
            for (String account : accounts) {
                EthGetBalance ethGetBalance = Global.getWeb3j()
                        .ethGetBalance(account, DefaultBlockParameterName.LATEST)
                        .send();
                BigDecimal b = Convert.fromWei(new BigDecimal(ethGetBalance.getBalance()), Convert.Unit.ETHER);
                accountList.add(new Account(account, b.setScale(2, RoundingMode.FLOOR)
                        + " BCL"));
            }
            return accountList;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public String getTotalBalance() {
        BigDecimal d = BigDecimal.valueOf(0L);
        try {
            for (String account : accounts) {
                EthGetBalance ethGetBalance = Global.getWeb3j()
                        .ethGetBalance(account, DefaultBlockParameterName.LATEST)
                        .send();
                d = d.add(Convert.fromWei(new BigDecimal(ethGetBalance.getBalance()), Convert.Unit.ETHER));
            }
            return d.setScale(2, RoundingMode.FLOOR).toString();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    public static boolean accounts_check(){
        try {
            EthAccounts ethAccounts = Global.getWeb3j().ethAccounts().send();
            return ethAccounts.getAccounts().size() != 0;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,e.getMessage(), e);
            e.printStackTrace();
            accounts_check();
        }
        return false;
    }

    public static boolean unlock_account_time(Admin web3j, String address, String password, double timeout) {
        BigInteger duration = BigInteger.valueOf((long) (timeout * 60000));
        try {
            PersonalUnlockAccount personalUnlockAccount =
                    web3j.personalUnlockAccount(address, password, duration).send();
            if (personalUnlockAccount.accountUnlocked() == null) {
                return false;
            }
            return (personalUnlockAccount.accountUnlocked());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,e.getMessage(), e);
            e.printStackTrace();
        }
        return false;
    }
}
