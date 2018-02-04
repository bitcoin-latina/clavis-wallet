package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Copy_Popup {

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Copy_Popup.class.
                getClassLoader().getResource("copy.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
}
