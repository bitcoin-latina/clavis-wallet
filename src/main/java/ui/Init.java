package ui;

import de.codecentric.centerdevice.MenuToolkit;
import init.Structure_Check;
import init.build.Commands;
import init.build.Setup_Web3;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import ui.controllers.*;
import utils.Verify_OS;
import web3j.Subscribe;

import java.io.IOException;
import java.util.List;

public class Init extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                System.out.println("Closing Gracefully...");
                Commands.kill_geth();
                List<Thread> appThreads = Global.getAppThreads();
                List<Process>appProcesses = Global.getAppProcesses();
                for (Thread t:appThreads
                     ) {
                    t.interrupt();
                    System.out.println("Killing Thread");
                }
                for (Process p:appProcesses
                     ) {
                    if(p!=null)
                        p.destroyForcibly();
                    System.out.println("Killing Process");
                }
                Runtime.getRuntime();
                Platform.exit();
                System.exit(0);
            }
        });
        //Create Bootup Window
        Global.setStage(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("init.fxml"));
        Parent root = loader.load();
//        if(System.getProperty("os.name").toLowerCase().contains("mac")) {
//            setupMenu();
//        }
        Global.setLoader(loader);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setAlwaysOnTop(false);
        stage.getIcons().add(new Image(Init.class.getResourceAsStream("/Images/icon.png")));
        stage.show();

        //TODO Fix this
        //Pause For 2 seconds to show window before loading geth
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event ->
        {
            try {
                //Move on to initialize
//                new Kill_Geth_Popup().start(new Stage());
                initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Start the pause
        pause.play();
    }

    public static void initialize() throws Exception {
        //Check for structure
        //Get OS
        Verify_OS.set_os();
        //Check folder Structure
        Structure_Check.check_Structure();
        //Get Web3j
        System.out.println("Structure check complete");
        //Initialize web3j functionality
        new Setup_Web3().setupWeb3();
        System.out.println("Got IPC Files");
        //Update Global Class Information
        Global.update_information();
        //Subscribe Once
        Subscribe.subscibeToBlocks();

        //Check for accounts
        if (Global.getAccountList().isEmpty()) {
            //New Account Popup
            new New_Account().start(new Stage());
            Global.update_information();
        }
        //Fire Up Dashboard
        toAnotherPage("dashboard.fxml");
    }

    public static void toAnotherPage(String page_name) {
        //To Another Page
        try {
            System.out.println("To Another Page Called");
            FXMLLoader loader = new FXMLLoader(Init.class.getClassLoader().getResource(page_name));
            Parent root = null;
            root = loader.load();
            Scene new_scene= new Scene(root);
            Global.getStage().setScene(new_scene);
            Global.setLoader(loader);
            //Custom data for different pages
            switch (page_name){
                case "personal_page.fxml":
                    Personal_Controller.count=0;
                    Personal_Controller p = loader.getController();
                    p.initialize();
                    break;
                case "aboutBCL.fxml":
                    AboutBCL_Controller a = loader.getController();
                    a.initialize();
                    break;
                case "send_page.fxml":
                    Send_Controller.count=0;
                    Send_Controller s = loader.getController();
                    s.initialize();
                    break;
                case "aboutClavis.fxml":
                    AboutClavis_Controller c = loader.getController();
                    c.initialize();
                    break;
                case "dashboard.fxml":
                    Dashboard_Controller.count=0;
                    Dashboard_Controller d = loader.getController();
                    d.initialize();
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInitLabel(String label) {
        //TODO remove
    }

    private void setupMenu(){
        // Get the toolkit
        MenuToolkit tk = MenuToolkit.toolkit();

        // Create the default Application menu
        Menu defaultApplicationMenu = tk.createDefaultApplicationMenu("CLAVIS Wallet");

        // Update the existing Application menu
        tk.setApplicationMenu(defaultApplicationMenu);

        // Since we now have a reference to the menu, we can rename items
        defaultApplicationMenu.getItems().get(1).setText("Hide all the otters");
        // Create a new menu bar
        MenuBar bar = new MenuBar();

        // Add the default application menu
        bar.getMenus().add(tk.createDefaultApplicationMenu("Clavis Wallet"));

        // Add some more Menus...

        // Use the menu bar for all stages including new ones
        tk.setGlobalMenuBar(bar);
    }

}
