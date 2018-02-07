package web3j;

import ui.Global;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class Mine {
    /**
     * Starts Geth with Mining_Popup Enabled
     *
     */
    private static final Logger LOGGER = Logger.getLogger(Mine.class.getName());
    public static void start_mining(){
        LOGGER.addHandler(Global.getLog_fh());
        Thread background = new Thread();
        background.setDaemon(true);
        Global.getAppThreads().add(background);
        background = new Thread(() -> {
            File file = new File(Global.getPath()+File.separator+"geth");
            if (! file.exists()) {
                LOGGER.warning("GETH FILE NOT FOUND TERMINATING");
                System.exit(1);
            }
            try {
                //Starting Web3_Geth with flags --syncmode fast --identity Bitcoin Latina
                Process p = Runtime.getRuntime().exec(Global.getPath()+File.separator+"geth --datadir "+
                        Global.getPath()+File.separator+"BCL_NODE " +
                        "--mine");
                synchronized (p) {
                    p.wait();
                }
            } catch (IOException e) {
                LOGGER.warning(Arrays.toString(e.getStackTrace()));
                System.exit(1);
            } catch (InterruptedException e) {
                LOGGER.warning(Arrays.toString(e.getStackTrace()));
            }
        });
        background.start();
        //TODO Why Sleep?
        try {
            Thread.sleep(10000);
            Global.update_information();
        } catch (InterruptedException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }

    public static void stop_mining(){
        Thread background = new Thread();
        background.setDaemon(true);
        Global.getAppThreads().add(background);
        background = new Thread(() -> {
            File file = new File(Global.getPath()+File.separator+"geth");
            if (! file.exists()) {
                LOGGER.warning("Geth File Not Found Terminating");
                System.exit(1);
            }
            try {
                //Starting Web3_Geth with flags --syncmode fast --identity Bitcoin Latina
                Process p = Runtime.getRuntime().exec(Global.getPath()+File.separator+"geth --datadir "+
                        Global.getPath()+File.separator+"BCL_NODE ");
                synchronized (p) {
                    p.wait();
                }
            } catch (IOException e) {
                LOGGER.warning(Arrays.toString(e.getStackTrace()));
                System.exit(1);
            } catch (InterruptedException e) {
                LOGGER.warning(Arrays.toString(e.getStackTrace()));
            }
        });
        background.start();
        try {
            Thread.sleep(10000);
            Global.update_information();
        } catch (InterruptedException e) {
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
        }
    }
}
