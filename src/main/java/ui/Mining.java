package ui;

import init.build.Commands;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.web3j.protocol.core.methods.response.EthMining;
import ui.controllers.Mine_Controller;

public class Mining {
    public void start(Stage stage) throws Exception {
        Commands.mine();

    }
}
