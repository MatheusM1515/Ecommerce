module com.example.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.ecommerce.controller to javafx.fxml;
    opens com.example.ecommerce to javafx.fxml;

    exports com.example.ecommerce;
}
