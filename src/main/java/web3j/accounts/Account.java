package web3j.accounts;

public class Account {
    private String address;
    private String balance;

    public Account(String address, String balance) {
        this.address = address;
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = String.format(String.format("%,.2f",Double.valueOf(balance)));
    }
    public void clear(){
        address=null;
        balance=null;
    }
}
