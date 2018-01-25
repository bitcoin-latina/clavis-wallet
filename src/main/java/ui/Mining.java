package ui;

import init.Commands;
import javafx.stage.Stage;

public class Mining {
    static int mining_instance =0;
    public void start(Stage stage) throws Exception {
        if(mining_instance==0) {
            new Commands().mine();
            mining_instance = 1;
        }
    }
}
