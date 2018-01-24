package init.build;

import ui.Global;

import java.io.File;
import java.io.IOException;

public class Permission_Commands {
    final static String macPerm = "chmod -R 777 " + System.getProperty("user.home") + File.separator + "BCL_CL";
    final static String winPerm = "icacls " + Global.getPath() + "/grant Users:F";

    public static void permission() {
        try {
            if (Global.getOS().contains("mac")) {
                Process p = Runtime.getRuntime().exec(macPerm);
                synchronized (p) {
                    p.waitFor();
                }
                Global.getAppProcesses().add(p);
            } else if (Global.getOS().contains("win")) {//Should grant permissions for whole folder
                Process p = Runtime.getRuntime().exec(winPerm);
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
