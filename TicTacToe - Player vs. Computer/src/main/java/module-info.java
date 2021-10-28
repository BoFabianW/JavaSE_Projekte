module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.example.tictactoe to javafx.fxml;
    exports de.example.tictactoe;
}