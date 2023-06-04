module proyecto2.Main {
requires java.base;
requires java.compiler;
requires java.desktop;
requires java.instrument;
requires java.logging;
requires java.management;
requires java.naming;
requires java.persistence;
requires java.sql;
requires java.xml;
requires javafx.base;
requires javafx.controls;
requires javafx.fxml;
requires javafx.graphics;
requires jdk.unsupported;
requires jdk.xml.dom;
requires org.eclipse.persistence.moxy;
    opens proyecto2.Model;
    opens proyecto2.Main;
    opens proyecto2.util;
    opens proyecto2.Controller;
    exports proyecto2.Main;
}
