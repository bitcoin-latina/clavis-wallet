package ui;

import init.build.Commands;
import javafx.stage.Stage;

public class Mining {
    public void start(Stage stage) throws Exception {
        new Commands().mine();

    }
}
