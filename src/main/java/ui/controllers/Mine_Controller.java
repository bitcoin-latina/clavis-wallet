package ui.controllers;

import build_structure.Commands;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mining.Github_API_Parser;
import ui.Global;
import utils.Download;
import utils.Utils;
import web3j.Mine;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Mine_Controller{
    @FXML
    public Button mining_button;
    public Label mining_address;
    private static final Logger LOGGER = Logger.getLogger(Mine_Controller.class.getName());
    private static final String ETHMINER = "ethminer";
    public Button cancel_button;

    public void init(){
        mining_address.setText("{ "+ Global.getMain_account().getAddress()+" }");
    }
    public void letsMine(javafx.event.ActionEvent e){
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Mining_Popup Button Clicked");
        //Start File Check
        if(!Utils.fileCheck(ETHMINER)){
            //Download Ethminer
            try {
                Download.ethminer();
                new Commands().mine();
            } catch (IOException e1) {
                for (StackTraceElement s:e1.getStackTrace()) {
                    LOGGER.warning(s.toString());
                }
                createAlert("Could not download ethminer...\n Check Internet Connection and logs");
            }

        }
        else {
            new Commands().mine();
        }
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void cancel(javafx.event.ActionEvent e){
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    //TODO Implement Stop Mining_Popup Functionality
    public void letsStop(javafx.event.ActionEvent e){
        Mine.stop_mining();
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    private void createAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.show();
    }
}
