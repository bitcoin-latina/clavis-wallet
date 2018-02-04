package ui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.*;
import web3j.accounts.Account;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

public class Dashboard_Controller extends Controller {
    @FXML
    public Label BCL_Monitor_Text;
    @FXML
    public Label Other_Accounts;
    @FXML
    public Label Main_Account_Balance;
    @FXML
    public Label Main_Account_Address;
    @FXML
    public Label dashboard;
    @FXML
    public Label newest_block_data;
    @FXML
    public Label newest_block;
    @FXML
    public Label clavis_about;
    @FXML
    public Label bcl_about;
    @FXML
    public Pane mine_pane;
    @FXML
    public Pane send_pane;
    @FXML
    public Pane chain_pane;
    @FXML
    public Pane personal_pane;
    @FXML
    public Label balance;
    @FXML
    public Label syncing_data;
    @FXML
    public Label syncing;
    @FXML
    public Label wallet_status;
    @FXML
    public Label wallet_status_data;
    @FXML
    public Label personal;
    @FXML
    public Label chain_label;
    @FXML
    public Label send_label;
    @FXML
    public Label mine_label;
    @FXML
    public Label about_bcl_sidebar;

    public static int count= 0;

    @Override
    protected void global_init(){
        setTotalBalance(Global.getTotal_balance());
        setupSidebar();
        setupMonitor(Global.getBCL_monitor());
    }
    @Override
    public void initialize(){
        setMainAccount(Global.getMain_account());
        setOtherAccounts(Global.getAccountList());
        global_init();
        init_once();
    }
    public void init_once(){
        if(count==0) {
            setHoverEffects();
            setOnClick();
        }
        count=1;
    }
    private void setupSidebar(){
        syncing_data.setText(Global.getSyncing());
        wallet_status_data.setText(Global.getWallet_status());
        newest_block_data.setText(Global.getNewest_block());
    }
    private void setMainAccount(Account a){
        Main_Account_Address.setText(a.getAddress());
        //NEW
        Main_Account_Address.setOnMouseClicked(mouseEvent -> doubleClickCopy(mouseEvent));
        Main_Account_Balance.setText(a.getBalance());
    }

    private void setTotalBalance(String s){
        balance.setText(String.format("%,.2f", Double.valueOf(s)));
    }
    @FXML
    private void copy_accounts(){

    }
    private void setOtherAccounts(List<Account> list){
        if(list.size()<2){
            Other_Accounts.setText("There are no other accounts to display at this time...\n" +
                    "To create more go to the Personal Section");
        }
        else{
            StringBuilder sb = new StringBuilder("");
            for(int i = 1; i<list.size()&&i<5; ++i){
                sb.append("Address: ").
                        append(list.get(i).getAddress()).
                        append("\n").append("Balance:  ").
                        append(list.get(i).getBalance()).
                        append("\n\n");
            }
            Other_Accounts.setText(sb.toString());
            Other_Accounts.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    //Create a list of current accounts
                    if(Global.getAccountList().size()<2){
                        //Do Nothing
                    }
                    else {
                        try {
                            new Copy_Popup().start(new Stage());
                            new Copy_Popup_Controller().initialize();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
        }
    }

    private void doubleClickCopy(MouseEvent mouseEvent){
        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
            if(mouseEvent.getClickCount() == 2){
                String s = Main_Account_Address.getText();
                if(s!=null) {
                    StringSelection selection = new StringSelection(s.toString());
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);
                    createAlert("Address Copied!");
                }
            }
        }
    }

    private void setupMonitor(String s){
        BCL_Monitor_Text.setText(new StringBuilder(s).reverse().toString());
    }

    protected void hover_side(Label l){
        l.setStyle("-fx-background-color: #5B5B5B;");
        l.setCursor(Cursor.HAND);
    }

    protected void remove_hover_side(Label l){
        l.setStyle("-fx-background-color: ");
        l.setCursor(Cursor.DEFAULT);
    }

    protected void hover(Pane id){
        id.setStyle("-fx-background-color: white;");
        id.setOpacity(.3);
        id.setCursor(Cursor.HAND);
    }

    protected void remove_hover(Pane id){
        id.setStyle("-fx-background-color: ");
        id.setOpacity(1.00);
        id.setCursor(Cursor.DEFAULT);
    }
    protected void setPaneHoverEffects(){
        //Set PaneHover Effects
        chain_pane.setOnMouseEntered(event -> hover(chain_pane));
        chain_pane.setOnMouseExited(event -> remove_hover(chain_pane));
        mine_pane.setOnMouseEntered(event -> hover(mine_pane));
        mine_pane.setOnMouseExited(event -> remove_hover(mine_pane));
        personal_pane.setOnMouseEntered(event -> hover(personal_pane));
        personal_pane.setOnMouseExited(event -> remove_hover(personal_pane));
        send_pane.setOnMouseEntered(event -> hover(send_pane));
        send_pane.setOnMouseExited(event -> remove_hover(send_pane));
    }
    private void setHoverEffects(){
        //Set Hover Effects
        setPaneHoverEffects();
        personal.setOnMouseEntered(event -> hover_side(personal));
        personal.setOnMouseExited(event -> remove_hover_side(personal));
        bcl_about.setOnMouseEntered(event -> hover_side(bcl_about));
        bcl_about.setOnMouseExited(event -> remove_hover_side(bcl_about));
        clavis_about.setOnMouseEntered(event -> hover_side(clavis_about));
        clavis_about.setOnMouseExited(event -> remove_hover_side(clavis_about));
    }
    protected void update_ui(){
        Global.update_information();
        Controller c = Global.getLoader().getController();
        c.initialize();
    }
    protected void setOnClick(){
        personal_pane.setOnMouseClicked(event -> Init.toAnotherPage("personal_page.fxml"));
        personal.setOnMouseClicked(event -> Init.toAnotherPage("personal_page.fxml"));
        send_pane.setOnMouseClicked(event -> Init.toAnotherPage("send_page.fxml"));
        chain_pane.setOnMouseClicked(event -> update_ui());
        mine_pane.setOnMouseClicked(event -> mining_popup());
        dashboard.setOnMouseClicked(event -> Init.toAnotherPage("dashboard.fxml"));
        clavis_about.setOnMouseClicked(event -> Init.toAnotherPage("aboutClavis.fxml"));
        bcl_about.setOnMouseClicked(event -> Init.toAnotherPage("aboutBCL.fxml"));
    }

    private void mining_popup(){
        try {
            new Mining().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TODO make a global static function
    private void createAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.show();
    }
}
