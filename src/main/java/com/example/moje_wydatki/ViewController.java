package com.example.moje_wydatki;

import connect_db.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.fxml.FXML;


public class ViewController {

        @FXML
        private Label mywallet;

        @FXML
        private Label welcome;

        @FXML
        private Label budget;

        private int userId;
        private Connection connection;
        private SessionController session;
        private ApplicationController Main;


        @FXML
        public void initialize() {
                session = SessionController.getInstance();
                userId = session.getUserId();

                ConnectionClass connectionClass = new ConnectionClass();
                connection = connectionClass.getConnection();

                Main = new ApplicationController();

                String query = "SELECT name FROM Login_db WHERE id_user = ?";
                try {
                        PreparedStatement statement = connection.prepareStatement(query);
                        statement.setInt(1, userId);
                        try(ResultSet name = statement.executeQuery()){
                                if(name.next()){
                                        String user = name.getString("name");
                                        welcome.setText("Witaj ponownie " + user + "!");
                                }
                        }


                }
                catch (Exception e){
                        e.printStackTrace();
                }

                mywallet.setOnMouseClicked(this::handleLabelClickMyWallet);
                budget.setOnMouseClicked(this::handleLabelClickBudget);

        }
        public void handleLabelClickMyWallet(MouseEvent event) {
                try {
                        Main.switchToMainScene("application-view-mywallet.fxml");
                } catch (IOException e) {
                        e.printStackTrace();

                }
        }

        public void handleLabelClickBudget(MouseEvent event){
                try {
                        Main.switchToMainScene("application-view-budget.fxml");
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }





}
