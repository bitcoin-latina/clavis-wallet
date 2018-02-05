package ui.controllers;

import javafx.scene.control.*;
import ui.Global;
import web3j.Gas;
import web3j.Personal;
import web3j.Tx;

import java.util.logging.Logger;

public class Send_Controller extends Dashboard_Controller {
    public Button send_BCL_button;
    public Label gas_limit;
    public TextField amount;
    public Slider gas_price_slider;
    public TextField to_address;
    public ComboBox address_dropdown;
    public static int count =0;
    public PasswordField password_field;
    private static final Logger LOGGER = Logger.getLogger(Send_Controller.class.getName());

    public void initialize() {
        LOGGER.addHandler(Global.getLog_fh());
        global_init();
        if(count==0)
            initialize_once();
    }

    private void initialize_once(){
        setPaneHoverEffects();
        setHoverEffects();
        setOnClick();
        setupDropdown();
        setupSlider();
        setGas_limit();
        count =1;
    }
    private void setHoverEffects() {
        //Set Hover Effects
        dashboard.setOnMouseEntered(event -> hover_side(dashboard));
        dashboard.setOnMouseExited(event -> remove_hover_side(dashboard));
        personal.setOnMouseEntered(event -> hover_side(personal));
        personal.setOnMouseExited(event -> remove_hover_side(personal));
        bcl_about.setOnMouseEntered(event -> hover_side(bcl_about));
        bcl_about.setOnMouseExited(event -> remove_hover_side(bcl_about));
        clavis_about.setOnMouseEntered(event -> hover_side(clavis_about));
        clavis_about.setOnMouseExited(event -> remove_hover_side(clavis_about));
    }

    private void setGas_limit() {
        gas_limit.setText(String.valueOf(Gas.getGasLimit(Global.getWeb3j())) + " Gas");
    }

    private void setupSlider() {
        gas_price_slider.setMin(0);
        gas_price_slider.setMax(Gas.getGasLimit(Global.getWeb3j()).doubleValue());
        gas_price_slider.setBlockIncrement(100000);
        gas_price_slider.adjustValue(.1 * (Gas.getGasLimit(Global.getWeb3j()).doubleValue()));
    }

    private double getGasPrice() {
        return gas_price_slider.getValue();
    }

    private void setupDropdown() {
        address_dropdown.getItems().clear();
        for (int i = 0; i < Global.getAccountList().size(); ++i) {
            address_dropdown.getItems().add(Global.getAccountList().get(i).getAddress());
        }
    }

    public void send_BCL() {
        if (getDropdown() == null) {
            createAlert("Please select an address from dropdown...");
        } else if (getToAddress() == null || !getToAddress().contains("0x")) {
            createAlert("Please select enter a valid to address");
        } else if (getGasPrice() == 0) {
            createAlert("Transaction will not go through");
        } else if (getAmount() == null || Double.valueOf(getAmount()) <= 0) {
            createAlert("Please set a valid amount to send");
        } else {
            Tx transaction = new Tx(getDropdown(),
                    getToAddress(),
                    Double.parseDouble(getAmount()), String.valueOf(getGasPrice()), getPass_field(), Global.getWeb3j());
            transaction.Send();
            clearValues();
        }
    }

    private String getDropdown() {
        return address_dropdown.getValue().toString();
    }

    private String getAmount() {
        return amount.getText();
    }

    private String getToAddress() {
        return to_address.getText();
    }

    private String getPass_field() {
        return password_field.getText();
    }

    private void createErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something wen't wrong... Try again");
        alert.setContentText("Try Again");
        alert.show();
    }

    private void createAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.show();
    }

    private void clearValues() {
        password_field.setText("");
        to_address.clear();
        setupSlider();
        setupDropdown();
        gas_limit.setText("");
    }
}
