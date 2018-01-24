package init.build;

import utils.Export_Resource;
import utils.Sys;

import java.io.File;
import java.io.IOException;

public class Insert_Static_Node {
    private Export_Resource ex;
    {
        try {
            ex = new Export_Resource("/json/static-nodes.json", Sys.getPath()+ File.separator+"BCL_Node"
                    +File.separator+"geth"+File.separator+"static-nodes.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
