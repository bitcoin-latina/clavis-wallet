package init.build;

import utils.Sys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Platform Independent Folder Creation
 * Makes BCL Folder at home dir
 */
public class Create_BCL_CL {
    public Create_BCL_CL(){
        Path path = Paths.get(Sys.getPath()+File.separator+"BCL_CL");
        boolean success = false;
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Unable to create BCL_CL folder: @ " + Sys.getPath());
            System.exit(1);
        }
    }
}
