module com.example.moje_wydatki {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.moje_wydatki to javafx.fxml;
    exports com.example.moje_wydatki;
}