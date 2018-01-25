package ui.controllers;

import web3j.Mine;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ui.Global;

public class Mine_Controller{
    @FXML
    public Button mining_button;
    public Label mining_address;

    public void init(){
        mining_address.setText("{ "+Global.getMain_account().getAddress()+" }");

    }
    public void letsMine(javafx.event.ActionEvent e){
        Mine.start_mining();

        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
    public void letsStop(javafx.event.ActionEvent e){
        Mine.stop_mining();
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
