package ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import web3j.accounts.Accounts;

/**
 * If no accounts created: Popup
 */
public class New_Account {
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().
                getClassLoader().getResource("new_account_popup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();

        stage.setOnCloseRequest(event -> {//If closing popup
            event.consume();
            if(!Accounts.accounts_check()){
                System.err.println("Popup Closed Prematurely");
                Platform.exit();
                System.exit(0);
            }
            Init.start();
            Platform.exit();
        });
    }
}