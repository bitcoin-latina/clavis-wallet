package geth;

import ui.Global;

import java.io.File;
import java.io.IOException;

public class Mine {
    /**
     * Starts Geth with Mining Enabled
     *
     */
    public static void start_mining(){
        Thread background = new Thread();
        background.setDaemon(true);
        Global.getAppThreads().add(background);
        background = new Thread(() -> {
            File file = new File(Global.getPath()+File.separator+"geth");
            if (! file.exists()) {
                System.err.println("Geth File Not Found Terminating");
                System.exit(1);
            }
            try {
                System.out.println("Starting Mining...");
                //Starting Web3_Geth with flags --syncmode fast --identity Bitcoin Latina
                Process p = Runtime.getRuntime().exec(Global.getPath()+File.separator+"geth --datadir "+
                        Global.getPath()+File.separator+"BCL_NODE " +
                        "--mine");
                synchronized (p) {
                    p.wait();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        background.start();
        try {
            Thread.sleep(10000);
            Global.update_information();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void stop_mining(){
        Thread background = new Thread();
        background.setDaemon(true);
        Global.getAppThreads().add(background);
        background = new Thread(() -> {
            File file = new File(Global.getPath()+File.separator+"geth");
            if (! file.exists()) {
                System.err.println("Geth File Not Found Terminating");
                System.exit(1);
            }
            try {
                System.out.println("Starting Mining...");
                //Starting Web3_Geth with flags --syncmode fast --identity Bitcoin Latina
                Process p = Runtime.getRuntime().exec(Global.getPath()+File.separator+"geth --datadir "+
                        Global.getPath()+File.separator+"BCL_NODE ");
                synchronized (p) {
                    p.wait();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        background.start();
        try {
            Thread.sleep(10000);
            Global.update_information();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
