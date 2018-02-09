package ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import ui.Global;
import web3j.accounts.Account;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Copy_Popup_Controller extends Dashboard_Controller{

    public Button copy_text;
    public ComboBox copy_address_dropdown;
    public void initialize(){
        for(int i =1; i<Global.getAccountList().size();++i){
            copy_address_dropdown.getItems().add(Global.getAccountList().get(i).getAddress());
        }
    }

    public void copy_address(ActionEvent actionEvent) {
        if(copy_address_dropdown.getValue().toString()!=null) {
            String s = copy_address_dropdown.getValue().toString();
            StringSelection selection = new StringSelection(s);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            Stage stage = (Stage) copy_address_dropdown.getScene().getWindow();
            stage.close();
        }
    }
}
