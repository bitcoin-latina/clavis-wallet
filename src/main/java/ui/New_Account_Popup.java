package ui;

import build_structure.Commands;
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
public class New_Account_Popup {
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
                event.consume();
                //Kill All Geth
                Commands.kill_geth_ethminer();
                //Kill All Running Threads
                for (Thread t : Global.getAppThreads()) {
                    t.interrupt();
                }
                //Kill All Running Processes
                for (Process p : Global.getAppProcesses()
                        ) {
                    if (p != null)
                        p.destroyForcibly();
                }
                Platform.exit();
                //Exit All
                System.exit(0);
            }
            Init.start();
            Platform.exit();
        });
    }
}