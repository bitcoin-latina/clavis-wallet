package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Kill_Geth_Controller {
    @FXML
    public Button mining_button;
    public Label mining_address;

    public void init(){

    }
    public void kill_geth(javafx.event.ActionEvent e){
//        new Kill_Geth();
//        try {
//            Init.initialize();
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
    }
    public void quit(javafx.event.ActionEvent e){
        System.exit(1);
    }
}
