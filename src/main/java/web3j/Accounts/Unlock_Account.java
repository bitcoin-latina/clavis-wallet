package web3j.Accounts;

import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;

import java.io.IOException;
import java.math.BigInteger;

public class Unlock_Account {
    public static boolean unlock_account(Admin web3j, String address, String password){
        try {
            PersonalUnlockAccount personalUnlockAccount =
                    web3j.personalUnlockAccount(address, password).send();
            return (personalUnlockAccount.accountUnlocked());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    //TODO password encryption
    public static boolean unlock_account_time(Admin web3j, String address, String password, double timeout){
        BigInteger duration = BigInteger.valueOf((long)(timeout*60000));
        try {
            PersonalUnlockAccount personalUnlockAccount =
                    web3j.personalUnlockAccount(address, password, duration).send();
            if(personalUnlockAccount.accountUnlocked()==null){
                return false;
            }
            return (personalUnlockAccount.accountUnlocked());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
