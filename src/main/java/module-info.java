module com.example.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.ecommerce to javafx.fxml;
    opens com.example.ecommerce.controller to javafx.fxml;
    opens com.example.ecommerce.model to javafx.base;

    exports com.example.ecommerce;
    exports com.example.ecommerce.controller;
}
