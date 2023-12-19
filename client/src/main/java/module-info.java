module go.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens go.client to javafx.fxml;
    exports go.client;
}