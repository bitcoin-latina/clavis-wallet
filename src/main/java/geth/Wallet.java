package geth;

<<<<<<< HEAD
import ui.Global;
=======
import utils.Sys;
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b
import utils.Toolkit;

import java.io.File;

/**
 * Simple class to return wallet file path
 */
public class Wallet {
    public static String getWalletFile(String address){
        String keystore =File.separator+"BCL_Node"+File.separator+"keystore";
        if(!Toolkit.Is_Empty_Directory(keystore)){
<<<<<<< HEAD
            File folder = new File(Global.getPath()+keystore);
=======
            File folder = new File(Sys.getPath()+keystore);
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; ++i) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().contains(address.substring(2)))
<<<<<<< HEAD
                    return Global.getPath()+keystore+File.separator+listOfFiles[i].getName();
=======
                    return Sys.getPath()+keystore+File.separator+listOfFiles[i].getName();
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b
                }
            }
        }
        System.err.println("Keystore Folder Couldn't Be Found...");
        return "Err";
    }
}
