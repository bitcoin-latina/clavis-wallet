package ui;

import build_structure.Commands;
import build_structure.Structure_Check;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.controllers.*;
import utils.Utils;
import web3j.Setup;
import web3j.Subscribe;

import java.io.IOException;
import java.util.List;

public class Init extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(event -> {//If closing wallet...
            event.consume();
            System.out.println("Closing Gracefully...");
            Commands.kill_geth();
            List<Thread> appThreads = Global.getAppThreads();
            List<Process> appProcesses = Global.getAppProcesses();
            for (Thread t : appThreads
                    ) {
                t.interrupt();
                System.out.println("Killing Thread");
            }
            for (Process p : appProcesses
                    ) {
                if (p != null)
                    p.destroyForcibly();
                System.out.println("Killing Process");
            }
            Runtime.getRuntime();
            Platform.exit();
            System.exit(0);
        });

        //Create Bootup Window
        Global.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("init.fxml"));
        Parent root = loader.load();
        Global.setLoader(loader);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
        stage.getIcons().add(new Image(Init.class.getResourceAsStream("/Images/icon.png")));
        stage.show();
        //Allows Splash Screen To Show Before Loading Geth
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            initialize();
        });
        //Start the pause
        pause.play();
    }

    public static void initialize() {
        //Get OS
        Utils.set_os(System.getProperty("os.name").toLowerCase());
        //Check folder Structure
        Structure_Check.check_Structure();
        //Get Web3j
        System.out.println("Structure check complete");
        //Initialize web3j functionality
        new Setup().setupWeb3();
        //Update Global Class Information
        Global.update_information();
        //Subscribe Once
        Subscribe.subscibeToBlocks();
        //Fire Up Dashboard
        toAnotherPage("dashboard.fxml");
    }

    public static void toAnotherPage(String page_name) {
        try {
            FXMLLoader loader = new FXMLLoader(Init.class.getClassLoader().getResource(page_name));
            Parent root = loader.load();
            Global.getStage().setScene(new Scene(root));
            Global.setLoader(loader);
            //Custom data for different pages
            switch (page_name) {
                case "personal_page.fxml":
                    Personal_Controller.count = 0;
                    Personal_Controller p = loader.getController();
                    p.initialize();
                    break;
                case "aboutBCL.fxml":
                    AboutBCL_Controller a = loader.getController();
                    a.initialize();
                    break;
                case "send_page.fxml":
                    Send_Controller.count = 0;
                    Send_Controller s = loader.getController();
                    s.initialize();
                    break;
                case "aboutClavis.fxml":
                    AboutClavis_Controller c = loader.getController();
                    c.initialize();
                    break;
                case "dashboard.fxml":
                    Dashboard_Controller.count = 0;
                    Dashboard_Controller d = loader.getController();
                    d.initialize();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
