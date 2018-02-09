package build_structure;

import ui.Global;
import ui.Init;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Logger;

public class Commands {
    /**
     * Used To Execute Sys Commands
     */
    private static final Logger LOGGER = Logger.getLogger(Commands.class.getName());
    //Mac Commands
    final static private String macStartCommand = Global.getPath() + File.separator + "start.command";
    final static private String macGethCommand = Global.getPath() + File.separator + "geth.command";
    final static private String macMineCommand = "open " + Global.getPath() + File.separator + "ethminer.command";
    //Win Commands
    final static private String winStartCommand = "\""+Global.getPath() + File.separator + "start.cmd"+"\"";
    final static private String winGethCommand = "\""+Global.getPath() + File.separator + "geth.cmd"+"\"";
    final static private String winMineCommand = "cmd.exe /k start " + "\""+Global.getPath() +
            File.separator + "ethminer.cmd"+"\"";
    final static private String winKillAllGeth = "taskkill /IM geth.exe /F";
    final static private String winKillAllEthminer = "taskkill /IM ethminer.exe /F";
    Process p = null;

    public void start() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Running Start Command File");
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
                LOGGER.warning("UNABLE TO RUN START COMMAND \n\n"+ Arrays.toString(e.getStackTrace()));
                System.exit(1);
            }
        };
        LOGGER.info("Reordering Thread Priorities For Geth");
        reorder_threads(r);
    }
    private static void reorder_threads(Runnable r){
        //Set Ui Thread Lower Priority Until Geth is Synced
        Global.getUiThread().setPriority(Thread.MIN_PRIORITY);
        Global.setGethThread(new Thread(r));
        Global.getGethThread().setPriority(Thread.MAX_PRIORITY);
        Global.getGethThread().start();
    }
    public void geth() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Running Geth Command File");
        Runnable r = () -> {
            try {
                LOGGER.info("OS is "+Global.getOS());
                ProcessBuilder pb = null;
                if (Global.getOS().contains("mac")) {
                    /* Create the ProcessBuilder */
                    pb = new ProcessBuilder(macGethCommand);
                } else if (Global.getOS().contains("win")) {
                    LOGGER.info(winGethCommand);
                    pb = new ProcessBuilder(winGethCommand);
                }
                startProcess(pb);
            } catch (IOException e) {
                LOGGER.warning("UNABLE TO RUN GETH COMMAND \n\n"+ Arrays.toString(e.getStackTrace()));
                System.exit(1);
            }
        };
        //Set Ui Thread Lower Priority Until Geth is Synced
        reorder_threads(r);
    }

    public void mine() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Running Mining_Popup Command File");
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
            LOGGER.warning("UNABLE TO RUN MINING COMMAND " + Arrays.toString(e.getStackTrace()));
            System.exit(1);
        }
    }

    public static void kill_geth_ethminer() {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Running Mining_Popup Command File");
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
            LOGGER.warning("Unable to Kill Ethminer/Geth" + Arrays.toString(e.getStackTrace()));
            System.exit(1);
        } catch (InterruptedException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }

    private void startProcess(ProcessBuilder pb) throws IOException {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Process Builder Started");
        pb.redirectErrorStream(true);
        /* Start the process */
        Process proc = pb.start();
        Global.getAppProcesses().add(proc);
        /* Read the process's output */
        String line;
        BufferedReader in = new BufferedReader(new InputStreamReader(
                proc.getInputStream()));
        while ((line = in.readLine()) != null) {
            LOGGER.info("GETH: " + line);
        }
        /* Clean-up */
        proc.destroy();
        Global.getAppProcesses().add(p);
    }
}
