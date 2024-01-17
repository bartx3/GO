module go.go {
    requires javafx.controls;
    requires javafx.fxml;


    opens go.client to javafx.fxml;
    exports go.client;
    exports go.client.UI.GUI;
    opens go.client.UI.GUI to javafx.fxml;
}