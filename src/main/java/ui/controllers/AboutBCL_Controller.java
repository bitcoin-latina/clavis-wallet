package ui.controllers;

public class AboutBCL_Controller extends Dashboard_Controller {
    public void initialize(){
        global_init();
        setPaneHoverEffects();
        setHoverEffects();
        setOnClick();
    }
    private void setHoverEffects(){
        //Set Hover Effects
        dashboard.setOnMouseEntered(event -> hover_side(dashboard));
        dashboard.setOnMouseExited(event -> remove_hover_side(dashboard));
        personal.setOnMouseEntered(event -> hover_side(personal));
        personal.setOnMouseExited(event -> remove_hover_side(personal));
        clavis_about.setOnMouseEntered(event -> hover_side(clavis_about));
        clavis_about.setOnMouseExited(event -> remove_hover_side(clavis_about));
    }
}
