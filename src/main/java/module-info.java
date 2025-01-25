module com.shop.shop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.shop.shop.model to javafx.base;
    opens com.shop.shop.controller to javafx.fxml;
    opens com.shop.shop to javafx.fxml;

    exports com.shop.shop;
    exports com.shop.shop.controller;
    exports com.shop.shop.dao; // Add this line
}