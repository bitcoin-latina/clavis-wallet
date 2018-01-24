package init.build;

import ui.Global;
import utils.Sys;

import java.io.File;
import java.io.IOException;

public class Commands {
    public static void start(){
        try {
            Process p = null;
            if (Sys.getOS().contains("mac")) {
                p = Runtime.getRuntime().exec("open " + Sys.getPath() + File.separator + "start.command");
            }
            if (Sys.getOS().contains("win")) {
                System.out.println("start " +Sys.getPath()+ File.separator+"start.cmd");
                p = Runtime.getRuntime().exec("cmd.exe /k start "+Sys.getPath()+ File.separator+"start.cmd");
            }
            if (Sys.getOS().contains("lin")) {
                p = Runtime.getRuntime().exec("bash "+ Sys.getPath()+ File.separator+"start.sh");
            }
            Global.getAppProcesses().add(p);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static void geth(){
        System.out.println("Starting geth... A new window should appear");
        try {
            Process p = null;
            switch (Sys.getOS()){
                case "mac":
                    p = Runtime.getRuntime().exec("open "+ Sys.getPath()+ File.separator+"geth.command");
                    break;
                case "windows":
                    p = Runtime.getRuntime().exec("cmd.exe /k start "+Sys.getPath()+ File.separator+"geth.cmd");
                    break;
                case "linux":
                    p = Runtime.getRuntime().exec("bash "+ Sys.getPath()+ File.separator+"geth.sh");
                    break;
                    default:
                        System.out.println("OS not recognized");
                        System.exit(1);
            }
            Global.getAppProcesses().add(p);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void mine(){
        System.out.println("Starting ethminer... A new window should appear");
        try {
            Process p = null;
            switch (Sys.getOS()){
                case "mac":
                    p = Runtime.getRuntime().exec("open "+ Sys.getPath()+ File.separator+"ethminer.command");
                    break;
                case "windows":
                    p = Runtime.getRuntime().exec("cmd.exe /k start "+Sys.getPath()+ File.separator+"ethminer.cmd");
                    break;
                case "linux":
                    p = Runtime.getRuntime().exec("bash "+ Sys.getPath()+ File.separator+"ethminer.sh");
                    break;
                default:
                    System.out.println("OS not recognized");
                    System.exit(1);
            }
            Global.getAppProcesses().add(p);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
