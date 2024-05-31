package com.example.moje_wydatki;

import connect_db.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;



public class BudgetController implements Initializable {



    private ApplicationController Main;

    public void setMain(ApplicationController main) {
        this.Main = main;
    }

    @FXML
    private LineChart<String, Number> barChart;

    @FXML
    private Label Amount_wallet;

    @FXML
    private Label error_label;

    @FXML
    private TextField Amount;

    @FXML
    private Label status_label;

    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    //Pobranie id_user z sesji
    SessionController session = SessionController.getInstance();
    int userId = session.getUserId();

    public void initialize(URL url, ResourceBundle rb) {



        //Pobranie id_user z sesji
        SessionController session = SessionController.getInstance();
        int userId = session.getUserId();

        //Tworzeni połączenie za bazą danych
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();

        //Tworzenie zapytania SQL
        String query = "SELECT amount, date FROM income_money WHERE id_user = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        // Tworzenie serii danych
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Suma pieniędzy w PLN");

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            double sumAmount = getTotalAmountForUser(userId);
            Amount_wallet.setText("Twój budżet to: " + sumAmount);

            while (resultSet.next()) {
                double amount = resultSet.getDouble("amount");
                LocalDate date = LocalDate.parse(resultSet.getString("date"), formatter);

                //Wyciąganie miesiąca z daty w bazie danych
                String month = date.getMonth().toString();
                series.getData().add(new XYChart.Data<>(month, amount));

            }

            barChart.getData().add(series);

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void SaveAmountOnClick(ActionEvent event) {

        String amount = Amount.getText();

        //Tworzenie połączenia z bazą danych
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connection = connectionClass.getConnection();
        if(amount.isEmpty()){
            status_label.setText("Wpisz kwotę");
            return;
        }
        double UserAmount;

        try{
            UserAmount = Double.parseDouble(amount);
        }
        catch (NumberFormatException e){
            status_label.setText("Wprowadź poprawną kwotę");
            return ;
        }

        String query = "INSERT INTO income_money (id_user, amount, date) VALUES (?, ?, CURDATE())";

        try{ PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            statement.setDouble(2, UserAmount);
            statement.executeUpdate();
            status_label.setText("Poprawnie wprowadzono kwotę do bazy danych!");

        }
        catch (Exception e){
            e.printStackTrace();
            status_label.setText("Bląd zapisu!");
        }

    }

    // Metoda, która oblicza łączną kwotę dla danego użytkownika
    public double getTotalAmountForUser(int userId) {

        double totalAmount = 0;

        //Tworzenie zapytania SQL
        String query2 = "SELECT SUM(amount) AS total_amount FROM income_money WHERE id_user = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query2);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {

                totalAmount = resultSet.getDouble("total_amount");

            }



            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalAmount;
    }

    public void budgetOnClick(MouseEvent event) {


        try {
            Main.switchToBudgetScene("application-view-budget.fxml");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void MyWalletOnClick(MouseEvent event) {


        Main = new ApplicationController();


        try {
            Main.switchToMyWalletScene("application-view-mywallet.fxml");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}



















