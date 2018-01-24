package init.build;

import utils.Export_Resource;
import utils.Sys;

import java.io.File;
import java.io.IOException;

public class Insert_Commands {
    private Export_Resource ex;
    {
        try {
            if(Sys.getOS().contains("win")){
                ex = new Export_Resource("/commands/windows/start.cmd", Sys.getPath()+
                        File.separator+"start.cmd");
                ex = new Export_Resource("/commands/windows/geth.cmd", Sys.getPath()+
                        File.separator+"geth.cmd");
                ex = new Export_Resource("/commands/windows/ethminer.cmd", Sys.getPath()+
                        File.separator+"ethminer.cmd");
            }
            if(Sys.getOS().contains("lin")|| Sys.getOS().contains("ux")){
                ex = new Export_Resource("/commands/linux/start.sh", Sys.getPath()+
                        File.separator+"start.sh");
                ex = new Export_Resource("/commands/linux/geth.sh", Sys.getPath()+
                        File.separator+"geth.sh");
                ex = new Export_Resource("/commands/linux/ethminer.sh", Sys.getPath()+
                        File.separator+"ethminer.sh");
            }
            if(Sys.getOS().contains("mac")){
                ex = new Export_Resource("/commands/mac/start.command", Sys.getPath()+
                        File.separator+"start.command");
                ex = new Export_Resource("/commands/mac/geth.command", Sys.getPath()+
                        File.separator+"geth.command");
                ex = new Export_Resource("/commands/mac/ethminer.command", Sys.getPath()+
                        File.separator+"ethminer.command");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
