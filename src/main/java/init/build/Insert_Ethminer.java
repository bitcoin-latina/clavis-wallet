package init.build;

import utils.Export_Resource;
import utils.Sys;

import java.io.File;
import java.io.IOException;

public class Insert_Ethminer {
    private Export_Resource ex;
    {
        try {
            if(Sys.getOS().contains("win")){
                ex = new Export_Resource("/binaries/windows/ethminer.exe", Sys.getPath()+ File.separator+"ethminer.exe");
            }
            if(Sys.getOS().contains("lin")|| Sys.getOS().contains("ux")){
                ex = new Export_Resource("/binaries/linux/ethminer", Sys.getPath()+ File.separator+"ethminer");
            }
            if(Sys.getOS().contains("mac")){
                ex = new Export_Resource("/binaries/mac/ethminer", Sys.getPath()+ File.separator+"ethminer");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
