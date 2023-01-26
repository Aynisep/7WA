/**
 * definition du module SevenWonder
 */
module com.example.SevenWonders {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.apache.log4j;
    requires org.apache.logging.log4j.core;
    requires org.apache.logging.log4j;
    requires java.desktop;


    opens fr.isep.game7wonderarch.utils to javafx.fxml;
    exports fr.isep.game7wonderarch.utils;
}