
module KontoBankowe_BD2 {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.sql;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.base;
    requires mysql.connector.java;
    requires org.kordamp.bootstrapfx.core;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.apache.commons.lang3;

    opens com.bankaccount.applicationview to javafx.fxml;

    exports com.bankaccount.applicationsource;
    exports com.bankaccount.applicationview;

}
