package build_structure;

import ui.Global;
import utils.Utils;

import java.io.File;

/**
 * Structure check:
 * 1) BCL Directory
 * 2) Geth within BCL
 * 3) BCL_Node Directory within BCL
 * 4) genesis.json
 */

public class Structure_Check {

    public static void check_Structure() {
        //Check for BCL Folder
        if (!BCL_Directory_Check()) {
            Global.setState(0);
            new Build().BCLFolder();
        }

        //Set Path To BCL_CL Folder

        Global.setPath(Global.getPath() + File.separator + "BCL_CL");

        //Check for Files Inside of BCL_CL Folder 1 by 1
        if (!gethCheck() || !genesisCheck() || !ethminerCheck() ||
                !commandCheck() || !staticNodeCheck()) {
            Global.setState(0);
            new Build().binaries();
            new Build().commands();
            Permission_Commands.permission();
        }

        if (Global.getState() == 0) {
            //Uninitialized
            System.out.println("Start Command Issued");
            new Commands().start();
        } else {
            //Initialized
            new Commands().geth();
        }

    }

    private static boolean BCL_Directory_Check() {
        //Checks for BCL Dir
        File f = new File(Global.getPath() + File.separator + "BCL_CL");
        return f.exists() && f.isDirectory();
    }

    private static boolean commandCheck() {
        //Checks for Command Files
        if (Global.getOS().contains("mac")) {
            return (!Utils.Is_Empty_File(File.separator + "ethminer.command") &&
                    !Utils.Is_Empty_File(File.separator + "geth.command") &&
                    !Utils.Is_Empty_File(File.separator + "start.command"));
        } else if (Global.getOS().contains("win")) {
            return (!Utils.Is_Empty_File(File.separator + "ethminer.cmd") &&
                    !Utils.Is_Empty_File(File.separator + "geth.cmd") &&
                    !Utils.Is_Empty_File(File.separator + "start.cmd"));
        }
        return false;
    }

    private static boolean gethCheck() {
        //Checks for Geth
        File f;
        if (Global.getOS().contains("win")) {
            f = new File(Global.getPath() + File.separator + "geth.exe");
        } else {
            f = new File(Global.getPath() + File.separator + "geth");
        }
        return f.exists() && !f.isDirectory();
    }

    private static boolean genesisCheck() {
        //Checks for Genesis File
        File f = new File(Global.getPath() + File.separator + "genesis.json");
        return f.exists() && !f.isDirectory();
    }

    private static boolean ethminerCheck() {
        //Checks for Ethminer File
        File f;
        if (Global.getOS().contains("win")) {
            f = new File(Global.getPath() + File.separator + "ethminer.exe");
        } else {
            f = new File(Global.getPath() + File.separator + "ethminer");
        }
        return f.exists() && !f.isDirectory();
    }

    private static boolean staticNodeCheck() {
        //Checks for Static Node File
        File f = new File(Global.getPath() + File.separator + "BCL_Node" + File.separator +
                "geth" + File.separator + "static-nodes.json");
        return f.exists();
    }
}
