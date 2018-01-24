package init.build;

import java.io.IOException;

/**
 * Initializes Web3_Geth with BCL genesis file
 *
 */
public class Kill_Geth {
    public Kill_Geth() {
                try {
                    Process p = Runtime.getRuntime().exec("killall geth");
                    synchronized (p) {
                        p.waitFor();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Killing Geth...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
