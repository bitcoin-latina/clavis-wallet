package ui;

import build_structure.Commands;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Mining_Popup {
    static int mining_instance =0;
    public void start(Stage stage) throws Exception {
        if(mining_instance==0) {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getClassLoader().getResource("mining_popup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setAlwaysOnTop(true);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }
    }
}
