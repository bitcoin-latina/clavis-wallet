package ui.controllers;

import geth.Mine;
import init.build.Kill_Geth;
import init.web3j.Web3_Geth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.web3j.protocol.admin.Admin;
import ui.Global;
import ui.Init;
import web3j.Personal;

public class Kill_Geth_Controller {
    @FXML
    public Button mining_button;
    public Label mining_address;

    public void init(){

    }
    public void kill_geth(javafx.event.ActionEvent e){
        new Kill_Geth();
        try {
            Init.initialize();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    public void quit(javafx.event.ActionEvent e){
        System.exit(1);
    }
}
