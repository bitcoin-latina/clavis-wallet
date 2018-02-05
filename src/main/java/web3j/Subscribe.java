package web3j;

import ui.Global;

import java.util.logging.Logger;

/**
 * Class handles all of the web3 subscription requests
 */
public class Subscribe {
    private static final Logger LOGGER = Logger.getLogger(Subscribe.class.getName());
    public static void subscibeToBlocks(){
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Subscribing to Blocks in Background Thread");
        Thread background = new Thread(() -> {
            while (true){
                Blocks blocks = new Blocks();
                blocks.subscribe();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Global.getAppThreads().add(background);
        Global.setUpdateThread(background);
        background.setPriority(Thread.MIN_PRIORITY);
        background.setDaemon(true);
        background.start();
    }
}
