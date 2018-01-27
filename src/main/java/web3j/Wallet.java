package web3j;

import ui.Global;
import utils.Utils;

import java.io.File;

/**
 * Simple class to return wallet file path
 */
public class Wallet {
    public static String getWalletFile(String address){
        String keystore =File.separator+"BCL_Node"+File.separator+"keystore";
        if(!Utils.Is_Empty_Directory(keystore)){
            File folder = new File(Global.getPath()+keystore);
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; ++i) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().contains(address.substring(2)))
                    return Global.getPath()+keystore+File.separator+listOfFiles[i].getName();
                }
            }
        }
        System.err.println("Keystore Folder Couldn't Be Found...");
        return "Err";
    }
}
