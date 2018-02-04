package build_structure;

import ui.Global;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commands {
    //Mac Commands
    final static private String macStartCommand = Global.getPath() + File.separator + "start.command";
    final static private String macGethCommand = Global.getPath() + File.separator + "geth.command";
    final static private String macMineCommand = "open " + Global.getPath() + File.separator + "ethminer.command";
    //Win Commands
    final static private String winStartCommand = Global.getPath() + File.separator + "start.cmd";
    final static private String winGethCommand = Global.getPath() + File.separator + "geth.cmd";
    final static private String winMineCommand = "cmd.exe /k start " + Global.getPath() + File.separator + "ethminer.cmd";
    final static private String winKillAllGeth = "taskkill /IM geth.exe /F";
    final static private String winKillAllEthminer = "taskkill /IM ethminer.exe /F";
    Process p = null;

    public void start() {
        Runnable r = () -> {
            try {
                ProcessBuilder pb = null;
                if (Global.getOS().contains("mac")) {
                    /* Create the ProcessBuilder */
                    pb = new ProcessBuilder(macStartCommand);
                } else if (Global.getOS().contains("win")) {
                    pb = new ProcessBuilder(winStartCommand);
                }
                startProcess(pb);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        };

        new Thread(r).start();
    }

    public void geth() {
        Runnable r = () -> {
            try {
                ProcessBuilder pb = null;
                if (Global.getOS().contains("mac")) {
                    /* Create the ProcessBuilder */
                    pb = new ProcessBuilder(macGethCommand);
                } else if (Global.getOS().contains("win")) {
                    pb = new ProcessBuilder(winGethCommand);
                }
                startProcess(pb);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        };

        new Thread(r).start();
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

    public static void kill_geth_ethminer() {
        try {
            if (Global.getOS().contains("mac")) {
                Process p = Runtime.getRuntime().exec("killall geth");
                Process p2 = Runtime.getRuntime().exec("killall ethminer");
                synchronized (p) {
                    p.waitFor();
                }
                synchronized (p2) {
                    p2.waitFor();
                }
            } else if (Global.getOS().contains("win")) {
                Process p = Runtime.getRuntime().exec(winKillAllGeth);
                Process p2 = Runtime.getRuntime().exec(winKillAllEthminer);
                synchronized (p) {
                    p.waitFor();
                }
                synchronized (p2) {
                    p2.waitFor();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void startProcess(ProcessBuilder pb) throws IOException {
        pb.redirectErrorStream(true);

        /* Start the process */
        Process proc = pb.start();
        Global.getAppProcesses().add(proc);
        System.out.println("Process started !");

        /* Read the process's output */
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                proc.getInputStream()));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        /* Clean-up */
        proc.destroy();
        System.out.println("Process ended !");
        Global.getAppProcesses().add(p);
    }
}
