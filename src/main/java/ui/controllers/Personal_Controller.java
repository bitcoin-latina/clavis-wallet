package ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ui.Global;
import web3j.Personal;
import web3j.accounts.Account;
import web3j.accounts.Accounts;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Personal_Controller extends Dashboard_Controller {
    public Button new_account_button;
    public Button unlock_button;
    public PasswordField new_account_pass;
    public TextField duration_field;
    public PasswordField unlock_account_pass;
    public ComboBox dropdown;
    public static int count = 0;
    public Button copy_address_button;
    private static final Logger LOGGER = Logger.getLogger(Personal.class.getName());

    public void initialize(){
        LOGGER.addHandler(Global.getLog_fh());
        global_init();
        set_dropdown(Global.getAccountList());
        init_once();
    }
    public void init_once(){
        if(count==0) {
            setOnClick_Personal();
            setPaneHoverEffects();
            setHoverEffects();
            setOnClick();
            count=1;
        }
    }

    private void unlock_account(){
        if(dropdown.getValue()==null){
            createAlert("No selected address from dropdown!");
            LOGGER.warning("No selected address from dropdown");
        }
        else if(unlock_account_pass.getText()==null){
            createAlert("No password entered!");
            LOGGER.warning("No password entered");
        }
        else if(duration_field.getText().isEmpty()){
            createAlert("No duration is entered!");
            LOGGER.warning("No duration entered");
        }
        else {
            boolean b = Accounts.unlock_account_time(Global.getGeth(),
                    dropdown.getValue().toString(),
                    unlock_account_pass.getText(),
                    Double.valueOf(duration_field.getText()));
            if(b){
                createAlert("Successfully Unlocked Account!");
            }
            else{
                LOGGER.warning("UNABLE TO CREATE NEW ACCOUNT");
                createErrorAlert();
            }
        }
    }

    private void createNewAccount(){
        Personal personal = new Personal(Global.getGeth(), new_account_pass.getText());
        String s = personal.createAccount();
        if(s.isEmpty()){
            LOGGER.warning("COULD NOT CREATE NEW ACCOUNT");
            createErrorAlert();
        }
        else {
            LOGGER.info("New Account Creation Successful");
            createAlert("New Account Created: " + s);
            try {
                set_dropdown(new Accounts().getAccounts());
            } catch (IOException e) {
                LOGGER.warning("COULD NOT SETUP DROPDOWWN");
                LOGGER.warning(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private void setOnClick_Personal(){
        unlock_button.setOnMouseClicked(event -> unlock_account());
        new_account_button.setOnMouseClicked(event -> createNewAccount());
    }

    private void createErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something wen't wrong... Try again");
        alert.setContentText("Try Again");
        alert.show();
    }

    private void createAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.show();
    }

    private void set_dropdown(List<Account> a){
        dropdown.getItems().clear();
        for (Account anA : a) {
            assert false;
            dropdown.getItems().add(anA.getAddress());
        }

    }
    private void setHoverEffects(){
        //Set Hover Effects
        dashboard.setOnMouseEntered(event -> hover_side(dashboard));
        dashboard.setOnMouseExited(event -> remove_hover_side(dashboard));
        bcl_about.setOnMouseEntered(event -> hover_side(bcl_about));
        bcl_about.setOnMouseExited(event -> remove_hover_side(bcl_about));
        clavis_about.setOnMouseEntered(event -> hover_side(clavis_about));
        clavis_about.setOnMouseExited(event -> remove_hover_side(clavis_about));
    }

    public void copy_address(ActionEvent actionEvent) {
        if(dropdown.getValue().toString()!=null) {
            String s = dropdown.getValue().toString();
            StringSelection selection = new StringSelection(s);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
        }
    }
}
