package init.build;

import ui.Global;

import java.io.File;
import java.io.IOException;

public class Commands {
    //Mac Commands
    final private String macStartCommand = "open " + Global.getPath() + File.separator + "start.command";
    final private String macGethCommand = "open " + Global.getPath() + File.separator + "geth.command";
    final private String macMineCommand = "open " + Global.getPath() + File.separator + "ethminer.command";
    //Win Commands
    final private String winStartCommand = "cmd.exe /k start " + Global.getPath() + File.separator + "start.cmd";
    final private String winGethCommand = "cmd.exe /k start " + Global.getPath() + File.separator + "geth.cmd";
    final private String winMineCommand = "cmd.exe /k start " + Global.getPath() + File.separator + "ethminer.cmd";

    Process p = null;

    public void start() {
        try {
            if (Global.getOS().contains("mac")) {
                p = Runtime.getRuntime().exec(macStartCommand);
            } else if (Global.getOS().contains("win")) {
                p = Runtime.getRuntime().exec(winStartCommand);
            }
            Global.getAppProcesses().add(p);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void geth() {
        try {
            if (Global.getOS().contains("mac")) {
                p = Runtime.getRuntime().exec(macGethCommand);
            } else if (Global.getOS().contains("win")) {
                p = Runtime.getRuntime().exec(winGethCommand);
            }
            Global.getAppProcesses().add(p);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void mine() {
        try {
            switch (Global.getOS()) {
                case "mac":
                    p = Runtime.getRuntime().exec(macMineCommand);
                    break;
                case "windows":
                    p = Runtime.getRuntime().exec(winMineCommand);
                    break;
            }
            Global.getAppProcesses().add(p);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
