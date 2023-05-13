module proyecto2.Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.logging;
    
    
    opens proyecto2.Main;
    opens proyecto2.util;
    opens proyecto2.Controller;
    exports proyecto2.Main;
}
