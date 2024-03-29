module go.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;
    requires org.testng;
    //requires jdk.internal.opt;


    opens go.client to javafx.fxml;
    exports go.client;
    exports go.client.UI.GUI;
    opens go.client.UI.GUI to javafx.fxml;
    exports go.client.UI;
    opens go.client.UI to javafx.fxml;
    exports go.client.comandStrategies;
    opens go.client.comandStrategies to javafx.fxml;
    exports go.communications;
    exports go.game;
}