package ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.web3j.protocol.admin.Admin;
import ui.Global;
import ui.Init;
import web3j.Personal;

public class New_Account_Controller{
    @FXML
    private PasswordField password_input;

    private String getPassword_input() {
        return password_input.getText();
    }

    public void button_press(ActionEvent e){
        //Initialize web3j functionality
        Admin geth = Global.getGeth();
        //Personal
        Personal personal = new Personal(geth, getPassword_input());
        personal.createAccount();

        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        System.out.println("Account Sucessfully Created");
        Init.start();
    }
}
