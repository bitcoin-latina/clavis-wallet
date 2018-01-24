package init.build;

import utils.Export_Resource;
import utils.Sys;

import java.io.File;
import java.io.IOException;

/**
 * Platform Independent File Creation
 * Inserts Included Genesis File
 */
public class Insert_Genesis {
    private Export_Resource ex;
    {
        try {
            ex = new Export_Resource("/json/genesis.json",Sys.getPath()+File.separator+"genesis.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
