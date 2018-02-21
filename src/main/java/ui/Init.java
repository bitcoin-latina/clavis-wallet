package ui;

import build_structure.Commands;
import build_structure.Permission_Commands;
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
import web3j.accounts.Accounts;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Init extends Application {
    private static final Logger LOGGER = Logger.getLogger(Init.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Setup Logging File
        Global.setup_logger_fh();
        LOGGER.addHandler(Global.getLog_fh());
        //Setup Stage Closing
        setup_closing_policy(stage);
        LOGGER.info("*******************************");
        LOGGER.info("*******************************");
        LOGGER.info("*******************************");
        LOGGER.info("*******************************");
        LOGGER.info("It's A New Instance Of CLAVIS");
        LOGGER.info("Setting up splash screen");
        //Create Bootup Window
        Global.setStage(stage);
        //Initialize Loader
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("init.fxml"));
        Parent root = loader.load();
        Global.setLoader(loader);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
        stage.getIcons().add(new Image(Init.class.getResourceAsStream("/Images/icon.png")));
        stage.show();
        //TODO can remove if threaded
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> initialize());
        //Start the pause
        pause.play();
    }

    public static void initialize() {
        LOGGER.info("Initializing the UI");
        //Save UI Thread To Global
        Global.setUiThread(Thread.currentThread());
        //Get OS
        Utils.set_os(System.getProperty("os.name").toLowerCase());
        //Check folder Structure
        //TODO IF ADDING WINDOWS INSTALLER CAN CHECK FOR OS
        if(Global.getOS().contains("win")){
            //Initialized
            Global.setPath(Global.getPath() + File.separator + "BCL_CL");
            LOGGER.info("USER PATH "+ Global.getPath());
            Permission_Commands.permission();
            //Check for geth contents
            if(Utils.Is_Empty_Directory(Global.getPath()+File.separator+"BCL_Node"
                    +File.separator+"geth"+File.separator+"chaindata")){
                new Commands().start(); // Uninitialized
                LOGGER.info("Initialized Geth!");
            }
            else
                new Commands().geth();
        }else{
            Structure_Check.check_Structure();
        }
        //Get Web3j
        //Initialize web3j functionality
        new Setup().setupWeb3();
        //check for accounts if none create a popup
        if (!Accounts.accounts_check()) {
            LOGGER.warning("NO ACCOUNTS FOUND");
            try {
                new New_Account_Popup().start(new Stage());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE,e.getMessage(), e);
                e.printStackTrace();
            }
        } else {
            start();
        }
    }

    private static void setup_closing_policy(Stage stage){
        stage.setOnCloseRequest(event -> {
            event.consume();
            LOGGER.info("Closing Gracefully...");
            //Kill All Geth
            Commands.kill_geth_ethminer();
            //Kill All Running Threads
            for (Thread t : Global.getAppThreads()) {
                t.interrupt();
            }
            LOGGER.info("Closed App Threads...");
            //Kill All Running Processes
            for (Process p : Global.getAppProcesses()
                    ) {
                if (p != null)
                    p.destroyForcibly();
            }
            LOGGER.info("Closed App Processes...");
            //Exit The Platform
            LOGGER.info("Done");
            Platform.exit();
            //Exit All
            System.exit(0);
        });
    }

    public static void start() {
        //Subscribe Once
        Subscribe.subscibeToBlocks();
        //Update Global Class Information
        Global.update_information();
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
            LOGGER.log(Level.SEVERE,e.getMessage(), e);
            e.printStackTrace();
        }
    }

}