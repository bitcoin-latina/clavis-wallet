package web3j.Accounts;

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

public class Get_Accounts {
    private List<String> accounts;
    private List<Account> accountList = new ArrayList<Account>();

    public Get_Accounts() throws IOException {
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
        BigInteger b = BigInteger.valueOf(0l);
        for (String account : accounts) {
            try {
                EthGetBalance ethGetBalance = Global.getWeb3j()
                        .ethGetBalance(account, DefaultBlockParameterName.LATEST)
                        .send();
                b = b.add(ethGetBalance.getBalance());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BigInteger conversion_factor = new BigInteger("1000000000000000000");
        BigDecimal quotient = new BigDecimal(b)
                .divide(new BigDecimal(conversion_factor), 2, RoundingMode.FLOOR);
        return quotient.toString();
    }
}
