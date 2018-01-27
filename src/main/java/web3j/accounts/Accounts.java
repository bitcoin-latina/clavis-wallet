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
import java.util.List;

public class Accounts {
    private List<String> accounts;
    private List<Account> accountList = new ArrayList<Account>();

    public Accounts() throws IOException {
        EthAccounts ethAccounts = Global.getWeb3j().ethAccounts().send();
        accounts = ethAccounts.getAccounts();
    }

    public List<Account> getAccounts() {
        try {
            for (int i = 0; i < accounts.size(); ++i) {
                EthGetBalance ethGetBalance = Global.getWeb3j()
                        .ethGetBalance(accounts.get(i), DefaultBlockParameterName.LATEST)
                        .send();
                BigDecimal b = Convert.fromWei(new BigDecimal(ethGetBalance.getBalance()), Convert.Unit.ETHER);
                accountList.add(new Account(accounts.get(i), b.setScale(2, RoundingMode.FLOOR)
                        + " BCL"));
            }
            return accountList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTotalBalance() {
        BigDecimal d = BigDecimal.valueOf(0l);
        try {
            for (String account : accounts) {
                EthGetBalance ethGetBalance = Global.getWeb3j()
                        .ethGetBalance(account, DefaultBlockParameterName.LATEST)
                        .send();
                d = d.add(Convert.fromWei(new BigDecimal(ethGetBalance.getBalance()), Convert.Unit.ETHER));
            }
            return d.setScale(2, RoundingMode.FLOOR).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
            e.printStackTrace();
        }
        return false;
    }

    public static boolean unlock_account(Admin web3j, String address, String password) {
        try {
            PersonalUnlockAccount personalUnlockAccount =
                    web3j.personalUnlockAccount(address, password).send();
            return (personalUnlockAccount.accountUnlocked());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
