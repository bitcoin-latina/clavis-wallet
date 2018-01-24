package ui.controllers;

import init.web3j.Web3_Geth;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.web3j.protocol.admin.Admin;
import web3j.Personal;

public class New_Account_Controller{
    @FXML
    private PasswordField password_input;

    private String getPassword_input() {
        return password_input.getText();
    }

    public void button_press(ActionEvent e){
        //Initialize web3j functionality
        Admin geth = new Web3_Geth().get_web3();
        //Personal
        Personal personal = new Personal(geth, getPassword_input());
        personal.createAccount();

        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
