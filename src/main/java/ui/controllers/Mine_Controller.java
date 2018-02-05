package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ui.Global;
import web3j.Mine;

import java.util.logging.Logger;

public class Mine_Controller{
    @FXML
    public Button mining_button;
    public Label mining_address;
    private static final Logger LOGGER = Logger.getLogger(Mine_Controller.class.getName());
    public void init(){
        mining_address.setText("{ "+ Global.getMain_account().getAddress()+" }");
    }
    public void letsMine(javafx.event.ActionEvent e){
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Mining_Popup Button Clicked");
        Mine.start_mining();
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
}
