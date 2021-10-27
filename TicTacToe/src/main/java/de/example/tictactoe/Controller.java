package de.example.tictactoe;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnNewGame;

    @FXML
    private Label lblPlayer;

    @FXML
    private Label lblTitle;

    @FXML
    void setX(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert btn1 != null : "fx:id=\"btn1\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn2 != null : "fx:id=\"btn2\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn3 != null : "fx:id=\"btn3\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn4 != null : "fx:id=\"btn4\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn5 != null : "fx:id=\"btn5\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn6 != null : "fx:id=\"btn6\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn7 != null : "fx:id=\"btn7\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn8 != null : "fx:id=\"btn8\" was not injected: check your FXML file 'gui.fxml'.";
        assert btn9 != null : "fx:id=\"btn9\" was not injected: check your FXML file 'gui.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'gui.fxml'.";
        assert btnNewGame != null : "fx:id=\"btnNewGame\" was not injected: check your FXML file 'gui.fxml'.";
        assert lblPlayer != null : "fx:id=\"lblPlayer\" was not injected: check your FXML file 'gui.fxml'.";
        assert lblTitle != null : "fx:id=\"lblTitle\" was not injected: check your FXML file 'gui.fxml'.";

    }

}
