package init.build;

import ui.Global;

import java.io.File;
import java.io.IOException;

public class Commands {
    //Mac Commands
    final static private String macStartCommand = "open " + Global.getPath() + File.separator + "start.command";
    final static private String macGethCommand = "open " + Global.getPath() + File.separator + "geth.command";
    final static private String macMineCommand = "open " + Global.getPath() + File.separator + "ethminer.command";
    //Win Commands
    final static private String winStartCommand = "cmd.exe /k start " + Global.getPath() + File.separator + "start.cmd";
    final static private String winGethCommand = "cmd.exe /k start " + Global.getPath() + File.separator + "geth.cmd";
    final static private String winMineCommand = "cmd.exe /k start " + Global.getPath() + File.separator + "ethminer.cmd";
    final static private String winKillAll = "taskkill /IM geth.exe /F";
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

    public static void kill_geth() {
        try {
            if (Global.getOS().contains("mac")) {
                Process p = Runtime.getRuntime().exec("killall geth");
                synchronized (p) {
                    p.waitFor();
                }
            } else if (Global.getOS().contains("win")) {
                Process p = Runtime.getRuntime().exec(winKillAll);
                synchronized (p) {
                    p.waitFor();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
