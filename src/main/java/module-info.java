module maman13.restaurant {
    requires javafx.controls;
    requires javafx.fxml;


    opens q2 to javafx.fxml;
    exports q2;
}