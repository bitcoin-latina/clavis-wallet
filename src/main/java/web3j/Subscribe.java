package web3j;

import ui.Global;

/**
 * Class handles all of the web3 subscription requests
 */
public class Subscribe {
    /**
     * Background process that lists blocks
     */
    public static void subscibeToBlocks(){
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
        background.setDaemon(true);
        background.start();
    }
}
