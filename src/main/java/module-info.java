module com.example.moje_wydatki {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.j;

    opens com.example.moje_wydatki to javafx.fxml;
    exports com.example.moje_wydatki;
}