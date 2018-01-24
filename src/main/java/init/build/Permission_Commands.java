package init.build;

import ui.Global;
import utils.Sys;

import java.io.File;
import java.io.IOException;

public class Permission_Commands {
    public static void permission() {
        System.out.println("Permissioning Commands");
        //TODO OS DEPENDENT
        try {
            if (Sys.getOS().contains("mac")) {
                Process p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "ethminer.command");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "ethminer");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "geth.command");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "geth");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "start.command");
                synchronized (p) {
                    p.waitFor();
                }
                Global.getAppProcesses().add(p);
            }
            else if(Sys.getOS().contains("win")){//Should grant permissions for whole folder
                Process p = Runtime.getRuntime().exec("icacls " +
                        Sys.getPath() + "/grant Users:F");
                synchronized (p) {
                    p.waitFor();
                }
            }
            else if(Sys.getOS().contains("lin")){
                Process p = Runtime.getRuntime().exec("chmod +x " +
                        Sys.getPath() + File.separator + "ethminer.sh");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "ethminer");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "geth");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "geth.sh");
                synchronized (p) {
                    p.waitFor();
                }
                p = Runtime.getRuntime().exec("chmod 777 " +
                        Sys.getPath() + File.separator + "start.sh");
                synchronized (p) {
                    p.waitFor();
                }
                Global.getAppProcesses().add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
