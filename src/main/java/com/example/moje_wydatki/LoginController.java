package com.example.moje_wydatki;

import connect_db.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class LoginController {

    @FXML
    private Button exit_button;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;


    public void LoginButtonOnAction(ActionEvent event)
    {
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        String loginFromUser = usernameTextField.getText();
        String passwordFromUser = passwordField.getText();
        String verifyPassword = "SELECT `password` FROM `Login_db` WHERE `name` = ?";
        String password = null;

        try {
            if(!loginFromUser.isEmpty () || !passwordFromUser.isEmpty()) {


                PreparedStatement statement = connection.prepareStatement(verifyPassword);
                statement.setString(1, loginFromUser);
                ResultSet queryOutput = statement.executeQuery();

                if (queryOutput.next()) {
                    password = queryOutput.getString("password");

                    if (password.equals(passwordFromUser)) {

                        loginMessageLabel.setText("Yasss");
                    } else {
                        loginMessageLabel.setText("Niepoprawne hasło");
                    }

                } else {
                    loginMessageLabel.setText("Nie znaleziono użytkownika");
                }


                // Zamykamy zasoby
                queryOutput.close();
                statement.close();
                connection.close();
            }
            else {
                loginMessageLabel.setText("Wpisz login i hasło");
            }
        } catch (Exception e) {

            // Wyświetlenie komunikatu o błędzie
            loginMessageLabel.setText("Błąd: " + e.getMessage());
        }

    }


    public void exit_buttonOnAction(ActionEvent e) {
        Stage stage = (Stage) exit_button.getScene().getWindow();
        stage.close();
    }


}