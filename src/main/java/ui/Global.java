package ui;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import ui.controllers.Send_Controller;
import web3j.Blocks;
import web3j.Syncing;
import web3j.accounts.Account;
import web3j.accounts.Accounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Global {
    private static FileHandler log_fh;
    private static String path = "";
    private static String OS = "";
    private static int state = 1; //0 is infant //1 is initialized
    private static String total_balance = "";
    private static String BCL_monitor = "";
    private static String wallet_status = "";
    private static String syncing = "";
    private static String newest_block = "";
    private static List<Account> accountList = new ArrayList<Account>();
    private static Account main_account = new Account("", "");
    private static Web3j web3j;
    private static Admin geth;
    private static Stage stage;
    private static FXMLLoader loader;
    private static List<Thread> appThreads = new ArrayList<Thread>();
    private static List<Process> appProcesses = new ArrayList<Process>();
    private static Thread gethThread;
    private static double isSynced=0;
    private static Thread uiThread;
    private static Thread updateThread;
    private static final Logger LOGGER = Logger.getLogger(Global.class.getName());

    // Getters | Setters
    public static Thread getGethThread() {
        return gethThread;
    }
    public static void setGethThread(Thread gethThread) {
        Global.gethThread = gethThread;
    }
    public static Thread getUiThread() {
        return uiThread;
    }
    public static Thread getUpdateThread() {
        return updateThread;
    }
    public static void setUpdateThread(Thread updateThread) {
        Global.updateThread = updateThread;
    }
    public static void setUiThread(Thread uiThread) {
        Global.uiThread = uiThread;
    }
    public static FileHandler getLog_fh() { return log_fh; }
    public static void setLog_fh(FileHandler log_fh) { Global.log_fh = log_fh; }
    public static List<Thread> getAppThreads() {
        return appThreads;
    }
    public static void setAppThreads(List<Thread> appThreads) {
        Global.appThreads = appThreads;
    }
    public static List<Process> getAppProcesses() {
        return appProcesses;
    }
    public static void setAppProcesses(List<Process> appProcesses) {
        Global.appProcesses = appProcesses;
    }
    public static Stage getStage() {
        return stage;
    }
    public static void setStage(Stage stage) {
        Global.stage = stage;
    }
    public static FXMLLoader getLoader() {
        return loader;
    }
    public static void setLoader(FXMLLoader loader) {
        Global.loader = loader;
    }
    public static Web3j getWeb3j() {
        return web3j;
    }
    public static void setWeb3j(Web3j web3j) {
        Global.web3j = web3j;
    }
    public static Admin getGeth() {
        return geth;
    }
    public static void setGeth(Admin geth) {
        Global.geth = geth;
    }
    public static Account getMain_account() {
        return main_account;
    }
    public static void setMain_account(Account main_account) {
        Global.main_account = main_account;
    }
    public static String getTotal_balance() {
        return total_balance;
    }
    public static void setTotal_balance(String total_balance) {
        Global.total_balance = total_balance;
    }
    public static String getBCL_monitor() {
        return BCL_monitor;
    }
    public static void setBCL_monitor(String BCL_monitor) {
        Global.BCL_monitor = BCL_monitor;
    }
    public static String getWallet_status() {
        return wallet_status;
    }
    public static void setWallet_status(String wallet_status) {
        Global.wallet_status = wallet_status;
    }
    public static String getSyncing() {
        return syncing;
    }
    public static void setSyncing(String syncing) {
        Global.syncing = syncing;
    }
    public static String getNewest_block() {
        return newest_block;
    }
    public static void setNewest_block(String newest_block) {
        Global.newest_block = "# " + newest_block;
    }
    public static List<Account> getAccountList() {
        return accountList;
    }
    public static void setAccountList(List<Account> accountList) {
        Global.accountList = accountList;
    }
    public static int getState() {
        return state;
    }
    public static void setState(int state) {
        Global.state = state;
    }
    public static String getPath() {
        return path;
    }
    public static void setPath(String path) {
        Global.path = path;
    }
    public static String getOS() {
        return OS;
    }
    public static void setOS(String OS) {
        Global.OS = OS;
    }

    public static void update_information() {
        //Updates all global values
        try {
            //Initialize web3j functionality
            Global.setAccountList(new Accounts().getAccounts());
            Global.setNewest_block(Blocks.getHighestBlockNumber(Global.getWeb3j()).toString());
            Global.setTotal_balance((new Accounts()).getTotalBalance());
            if(isSynced!=1) {
                syncingCheck();
                Global.setWallet_status(Syncing.getWalletStatus());
            }
            Global.setMain_account(new Accounts().getAccounts().get(0));
        } catch (Exception e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }

    //Max FIle Size 25 MB
    private static final int FILE_SIZE = 26214400;

    public static void setup_logger_fh(){
        try {
            log_fh = new FileHandler(System.getProperty("user.home")+
                    File.separator+"Documents"+File.separator+ "BCL.log", FILE_SIZE, 1,true);
            SimpleFormatter formatter = new SimpleFormatter();
            log_fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }
    private static void syncingCheck(){
        LOGGER.addHandler(log_fh);
        Double syncing = Syncing.getSyncingProgress();
        if(syncing==1){//Synced up so change priority
            LOGGER.info("Syncing Is at 100%");
            LOGGER.info("Reordering Thread Priorities to Favor UI");
            Global.setSyncing(String.format("%.2f", syncing * 100) + " %");
            isSynced=1;
            gethThread.setPriority(Thread.MIN_PRIORITY);
            uiThread.setPriority(Thread.NORM_PRIORITY);
            updateThread.setPriority(Thread.NORM_PRIORITY);
        }
        else if (!(syncing<0))
            Global.setSyncing(String.format("%.2f", syncing * 100) + " %");
    }
}
