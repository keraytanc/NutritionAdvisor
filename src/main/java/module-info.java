module keray {
    requires javafx.controls;
    requires java.net.http;
    requires com.google.gson;
    exports keray;
    //Adding elements to the searchbox required opening package to javafx.base
    opens keray.domain to javafx.base;

}