package init;

import init.build.*;
import utils.Sys;
import utils.Toolkit;

import java.io.File;

/**
 * Structure check:
 * 1) BCL Directory
 * 2) Geth within BCL
 * 3) BCL_Node Directory within BCL
 * 4) genesis.json
 */

public class Structure_Check {
    public Structure_Check() {
        if(!BCL_Directory_Check()){
            System.err.println("No BCL_CL Folder Found... Creating Structure");
            Sys.setState(0);
            new Create_BCL_CL();
        }
        Sys.setPath(Sys.getPath()+File.separator+"BCL_CL");
        if(!Geth_File_Check()){
            System.err.println("No Geth File Found... Creating Structure");
            Sys.setState(0);
            new Insert_Geth();
        }
        if(!Genesis_File_Check()){
            System.err.println("No Genesis File Found... Creating Structure");
            Sys.setState(0);
            new Insert_Genesis();
        }
        if(!ethMiner_File_Check()){
            System.err.println("No Ethminer File Found... Creating Structure");
            Sys.setState(0);
            new Insert_Ethminer();
        }
        if(!Command_Files_Check()){
            System.err.println("No command files found... Creating Structure");
            Sys.setState(0);
            new Insert_Commands();
            Permission_Commands.permission();
        }
        if(!Static_Node_Check()){
            System.err.println("No static config file found... Creating Structure");
            Sys.setState(0);
            new Insert_Static_Node();
        }

        if(Sys.getState()==0){
            System.err.println("State is unitialized. Starting start command");
            //Not initialized yet so run start command
            Commands.start();
        }
        else{
            Commands.geth();
        }
    }
    private static boolean BCL_Directory_Check(){ //Checks for BCL Dir
        System.err.println("Checking for BCL folder... at" + Sys.getPath()+ File.separator+"BCL_CL");
        File f = new File(Sys.getPath()+ File.separator+"BCL_CL");
        return f.exists() && f.isDirectory();
    }
    private static boolean Command_Files_Check(){//Checks for command files os dependent
        switch (Sys.getOS()){
            //TODO add in windows and linux
            case "mac":
                //check for files
                return(!Toolkit.Is_Empty_File(File.separator+"ethminer.command")&&
                        !Toolkit.Is_Empty_File(File.separator+"geth.command")&&
                        !Toolkit.Is_Empty_File(File.separator+"start.command"));
            case "windows":
                //check for files
                return(!Toolkit.Is_Empty_File(File.separator+"ethminer.cmd")&&
                        !Toolkit.Is_Empty_File(File.separator+"geth.cmd")&&
                        !Toolkit.Is_Empty_File(File.separator+"start.cmd"));
            case "linux":
                //check for files
                return(!Toolkit.Is_Empty_File(File.separator+"ethminer.command")&&
                        !Toolkit.Is_Empty_File(File.separator+"geth.command")&&
                        !Toolkit.Is_Empty_File(File.separator+"start.command"));

        }
        return false;
    }
    private static boolean Geth_File_Check(){ //Checks for Geth
        File f;
        System.err.println("Checking for Geth file at" + Sys.getPath()+File.separator+"Geth");
        if(Sys.getOS().contains("win")){
            f = new File(Sys.getPath()+File.separator+"geth.exe");
        }
        else{
            f = new File(Sys.getPath()+File.separator+"geth");
        }
        return f.exists() && !f.isDirectory();
    }
    private static boolean Genesis_File_Check(){
        System.err.println("Checking for Genesis file at"+Sys.getPath()+File.separator+"genesis.json");
        File f = new File(Sys.getPath()+File.separator+"genesis.json");
        return f.exists() && !f.isDirectory();
    }
    private static boolean ethMiner_File_Check(){
        System.err.println("Checking for Ethminer file at"+Sys.getPath()+File.separator+"ethminer");
        File f;
        if(Sys.getOS().contains("win")){
            f = new File(Sys.getPath()+File.separator+"ethminer.exe");
        }
        else {
            f = new File(Sys.getPath() + File.separator + "ethminer");
        }
        return f.exists() && !f.isDirectory();
    }
    private static boolean Static_Node_Check(){
        System.err.println("Checking for Static node file at "+Sys.getPath()+File.separator+"BCL_Node"+File.separator+
                "geth"+ File.separator+"static-nodes.json");
        File f = new File(Sys.getPath()+File.separator+"BCL_Node"+File.separator+
                "geth"+ File.separator+"static-nodes.json");
        return f.exists();
    }
}
