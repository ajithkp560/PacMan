module com.blogspot.terminalcoders {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;  // Required for some Swing components (if used)

    opens com.blogspot.terminalcoders to javafx.fxml;
    exports com.blogspot.terminalcoders;
}
