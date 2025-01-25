module com.shop.shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.shop.shop.model to javafx.base, javafx.fxml;
    opens com.shop.shop.controller to javafx.fxml;
    opens com.shop.shop to javafx.fxml;
    opens com.shop.shop.dao to javafx.fxml; // Add if DAO uses FXML

    exports com.shop.shop;
    exports com.shop.shop.controller;
    exports com.shop.shop.dao;
    exports com.shop.shop.model; // Add this line
}