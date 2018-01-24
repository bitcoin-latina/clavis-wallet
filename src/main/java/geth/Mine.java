package geth;

import ui.Global;
<<<<<<< HEAD
=======
import utils.Sys;
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b

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
<<<<<<< HEAD
            File file = new File(Global.getPath()+File.separator+"geth");
=======
            File file = new File(Sys.getPath()+File.separator+"geth");
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b
            if (! file.exists()) {
                System.err.println("Geth File Not Found Terminating");
                System.exit(1);
            }
            try {
                System.out.println("Starting Mining...");
                //Starting Web3_Geth with flags --syncmode fast --identity Bitcoin Latina
<<<<<<< HEAD
                Process p = Runtime.getRuntime().exec(Global.getPath()+File.separator+"geth --datadir "+
                        Global.getPath()+File.separator+"BCL_NODE " +
=======
                Process p = Runtime.getRuntime().exec(Sys.getPath()+File.separator+"geth --datadir "+
                        Sys.getPath()+File.separator+"BCL_NODE " +
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b
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
<<<<<<< HEAD
            File file = new File(Global.getPath()+File.separator+"geth");
=======
            File file = new File(Sys.getPath()+File.separator+"geth");
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b
            if (! file.exists()) {
                System.err.println("Geth File Not Found Terminating");
                System.exit(1);
            }
            try {
                System.out.println("Starting Mining...");
                //Starting Web3_Geth with flags --syncmode fast --identity Bitcoin Latina
<<<<<<< HEAD
                Process p = Runtime.getRuntime().exec(Global.getPath()+File.separator+"geth --datadir "+
                        Global.getPath()+File.separator+"BCL_NODE ");
=======
                Process p = Runtime.getRuntime().exec(Sys.getPath()+File.separator+"geth --datadir "+
                        Sys.getPath()+File.separator+"BCL_NODE ");
>>>>>>> fc09b114b78a75556ff3c0ec919ba79c48dd5d8b
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
