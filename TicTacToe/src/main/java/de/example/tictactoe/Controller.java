package de.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    // Variablen
    private final ArrayList<Button> buttons = new ArrayList<>();
    private boolean win;

    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnClose, btnNewGame;

    @FXML
    private Label lblPlayer;

    @FXML
    private Label lblTitle;

    @FXML
    void appClose() {
        System.exit(0);
    }

    @FXML
    void newGame() {
        for (Button button : buttons) {
            button.setText("");
            button.setDisable(false);
        }

        // Variable zurücksetzen.
        win = false;

        int random = ThreadLocalRandom.current().nextInt(1, 3);

        switch (random) {
            case 1 -> lblPlayer.setText("Spieler ist am Zug!");
            case 2 -> {
                lblPlayer.setText("Computer ist am Zug!");
                setComputer();
            }
        }
    }

    @FXML
    void setX(ActionEvent event) {

        Button btn = (Button) event.getSource();
        switch (btn.getId()) {

            case "btn1" -> btn1.setText("X");
            case "btn2" -> btn2.setText("X");
            case "btn3" -> btn3.setText("X");
            case "btn4" -> btn4.setText("X");
            case "btn5" -> btn5.setText("X");
            case "btn6" -> btn6.setText("X");
            case "btn7" -> btn7.setText("X");
            case "btn8" -> btn8.setText("X");
            case "btn9" -> btn9.setText("X");
        }

        for (Button button : buttons) {
            if (button.getText().equals("X") || button.getText().equals("O")) button.setDisable(true);
        }

        lblPlayer.setText("Computer ist am Zug!");

        if (!win) setComputer();

        // Methodenaufruf.
        win();
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

        // Buttons der ArrayList hinzufügen.
        buttons.add(0, btn1);
        buttons.add(1, btn2);
        buttons.add(2, btn3);
        buttons.add(3, btn4);
        buttons.add(4, btn5);
        buttons.add(5, btn6);
        buttons.add(6, btn7);
        buttons.add(7, btn8);
        buttons.add(8, btn9);
    }

    /*
     Der Computer ist am Zug.
     */
    void setComputer () {

        // Zufallszahl generieren.
        int random = ThreadLocalRandom.current().nextInt(1, 10);

        // Einen Button aus der ArraysList 'buttons' durch den Index zuweisen.
        Button button = buttons.get(random -1);

        if (!button.isDisabled()) {
            button.setText("O");
            button.setDisable(true);
        } else {
            setComputer();
        }

        lblPlayer.setText("Spieler ist am Zug!");
    }

    void win() {

        if (btn1.getText().equals("X") && btn2.getText().equals("X") && btn3.getText().equals("X")) end("Spieler");
        if (btn4.getText().equals("X") && btn5.getText().equals("X") && btn6.getText().equals("X")) end("Spieler");
        if (btn7.getText().equals("X") && btn8.getText().equals("X") && btn9.getText().equals("X")) end("Spieler");

        if (btn1.getText().equals("X") && btn4.getText().equals("X") && btn7.getText().equals("X")) end("Spieler");
        if (btn2.getText().equals("X") && btn5.getText().equals("X") && btn8.getText().equals("X")) end("Spieler");
        if (btn3.getText().equals("X") && btn6.getText().equals("X") && btn9.getText().equals("X")) end("Spieler");

        if (btn1.getText().equals("X") && btn5.getText().equals("X") && btn9.getText().equals("X")) end("Spieler");
        if (btn3.getText().equals("X") && btn5.getText().equals("X") && btn7.getText().equals("X")) end("Spieler");

        if (btn1.getText().equals("O") && btn2.getText().equals("O") && btn3.getText().equals("O")) end("Computer");
        if (btn4.getText().equals("O") && btn5.getText().equals("O") && btn6.getText().equals("O")) end("Computer");
        if (btn7.getText().equals("O") && btn8.getText().equals("O") && btn9.getText().equals("O")) end("Computer");

        if (btn1.getText().equals("O") && btn4.getText().equals("O") && btn7.getText().equals("O")) end("Computer");
        if (btn2.getText().equals("O") && btn5.getText().equals("O") && btn8.getText().equals("O")) end("Computer");
        if (btn3.getText().equals("O") && btn6.getText().equals("O") && btn9.getText().equals("O")) end("Computer");

        if (btn1.getText().equals("O") && btn5.getText().equals("O") && btn9.getText().equals("O")) end("Computer");
        if (btn3.getText().equals("O") && btn5.getText().equals("O") && btn7.getText().equals("O")) end("Computer");
    }

    void end (String winner) {

        // Durchlaufen der ArraysList.
        for (Button button : buttons) {
            button.setDisable(true);
        }

        // Ausgabe des Gewinners über das Label-Element.
        lblPlayer.setText(winner + " hat gewonnen!");

        // Variable auf 'true' (Gewinn).
        win = true;
    }
}
