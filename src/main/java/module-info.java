module com.mm.constructioncompany {
    /*
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.mm.constructioncompany to javafx.fxml, javafx.base;
    exports com.mm.constructioncompany;
    exports com.mm.constructioncompany.controller;
    opens com.mm.constructioncompany.controller to javafx.fxml;

    opens com.mm.constructioncompany.model to javafx.fxml;


     */
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.web;
    requires java.sql;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    //requires validatorfx;
    //requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;

    opens com.mm.constructioncompany to javafx.fxml;
    exports com.mm.constructioncompany;
    exports com.mm.constructioncompany.controller;
    opens com.mm.constructioncompany.controller to javafx.fxml;
    opens com.mm.constructioncompany.model to javafx.base;


}