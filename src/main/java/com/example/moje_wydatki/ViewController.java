package com.example.moje_wydatki;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;


public class ViewController {

        @FXML
        private Label mywallet;


        ApplicationController Main   = new ApplicationController();
        @FXML
        public void initialize() {

                mywallet.setOnMouseClicked(this::handleLabelClick);
        }
        private void handleLabelClick(MouseEvent event)
        {
        try{
            Main.switchToMainScene("application-view-mywallet.fxml");
        }
        catch(IOException e){
            e.printStackTrace();

        }

        }
}
