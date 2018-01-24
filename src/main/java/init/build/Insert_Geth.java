package init.build;

import utils.Export_Resource;
import utils.Sys;

import java.io.File;
import java.io.IOException;

/**
 * Platform Independent File Creation
 * Inserts Included Web3_Geth File
 */
public class Insert_Geth {
    private Export_Resource ex;
    {
        try {
            if(Sys.getOS().contains("win")){
                ex = new Export_Resource("/binaries/windows/geth.exe", Sys.getPath()+ File.separator+"geth.exe");
            }
            if(Sys.getOS().contains("lin")|| Sys.getOS().contains("ux")){
                ex = new Export_Resource("/binaries/linux/geth", Sys.getPath()+ File.separator+"geth");
            }
            if(Sys.getOS().contains("mac")){
                ex = new Export_Resource("/binaries/mac/geth", Sys.getPath()+ File.separator+"geth");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
