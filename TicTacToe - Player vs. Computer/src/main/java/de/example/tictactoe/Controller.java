package de.example.tictactoe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    // Variablen
    private final ArrayList<Button> buttons = new ArrayList<>();
    private boolean win;
    private Timeline timeline;
    private int counter = 0;

    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnClose, btnNewGame;

    @FXML
    private Label lblPlayer;

    @FXML
    private Label lblTitle;

    /**
     * App beenden.
     */
    @FXML
    void appClose() {
        System.exit(0);
    }

    /**
     * Neues Spiel starten und Werte zurücksetzen.
     * Beginner wird zufällig ermittelt.
     */
    @FXML
    void newGame() {

        // Zähler zurücksetzen.
        counter = 0;

        btnNewGame.setDisable(true);

        // ArrayList durchlaufen und entsprechende Werte setzen.
        for (Button button : buttons) {
            button.setText("");
            button.setDisable(false);
            button.setTextFill(Color.BLACK);
        }

        // Variable zurücksetzen.
        win = false;

        timeline = null;

        // Zufallszahl generieren.
        int random = ThreadLocalRandom.current().nextInt(1, 3);

        switch (random) {
            case 1 -> lblPlayer.setText("Spieler ist am Zug!");
            case 2 -> {
                lblPlayer.setText("Computer ist am Zug!");
                setComputer();
            }
        }

        try {
            Thread.sleep(600);
            btnNewGame.setDisable(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            if (button.getText().equals("X")) button.setDisable(true);
        }

        counter ++;
        lblPlayer.setText("Computer ist am Zug!");

        // Methodenaufruf.
        win();

        if (!win) setComputer();
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

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), ev -> {

            timeline.stop();

            // Zufallszahl generieren.
            int random = ThreadLocalRandom.current().nextInt(1, 10);

            // Einen Button aus der ArraysList 'buttons' durch den Index zuweisen.
            Button button = buttons.get(random -1);

            if (!button.isDisabled()) {
                button.setText("O");
                button.setDisable(true);
                counter ++;
                lblPlayer.setText("Spieler ist am Zug!");
                win();

            } else {
                setComputer();
            }
        }));
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void win() {

        if (counter == 9) lblPlayer.setText("Das Spiel ist vorbei!");

        if (btn1.getText().equals("X") && btn2.getText().equals("X") && btn3.getText().equals("X")) end("Spieler", 1,2,3);
        if (btn4.getText().equals("X") && btn5.getText().equals("X") && btn6.getText().equals("X")) end("Spieler", 4,5,6);
        if (btn7.getText().equals("X") && btn8.getText().equals("X") && btn9.getText().equals("X")) end("Spieler", 7,8,9);

        if (btn1.getText().equals("X") && btn4.getText().equals("X") && btn7.getText().equals("X")) end("Spieler", 1,4,7);
        if (btn2.getText().equals("X") && btn5.getText().equals("X") && btn8.getText().equals("X")) end("Spieler", 2,5,8);
        if (btn3.getText().equals("X") && btn6.getText().equals("X") && btn9.getText().equals("X")) end("Spieler", 3,6,9);

        if (btn1.getText().equals("X") && btn5.getText().equals("X") && btn9.getText().equals("X")) end("Spieler", 1,5,9);
        if (btn3.getText().equals("X") && btn5.getText().equals("X") && btn7.getText().equals("X")) end("Spieler", 3,5,7);

        if (btn1.getText().equals("O") && btn2.getText().equals("O") && btn3.getText().equals("O")) end("Computer", 1,2,3);
        if (btn4.getText().equals("O") && btn5.getText().equals("O") && btn6.getText().equals("O")) end("Computer", 4,5,6);
        if (btn7.getText().equals("O") && btn8.getText().equals("O") && btn9.getText().equals("O")) end("Computer", 7,8,9);

        if (btn1.getText().equals("O") && btn4.getText().equals("O") && btn7.getText().equals("O")) end("Computer", 1,4,7);
        if (btn2.getText().equals("O") && btn5.getText().equals("O") && btn8.getText().equals("O")) end("Computer", 2,5,8);
        if (btn3.getText().equals("O") && btn6.getText().equals("O") && btn9.getText().equals("O")) end("Computer", 3,6,9);

        if (btn1.getText().equals("O") && btn5.getText().equals("O") && btn9.getText().equals("O")) end("Computer", 1,5,9);
        if (btn3.getText().equals("O") && btn5.getText().equals("O") && btn7.getText().equals("O")) end("Computer", 3,5,7);
    }

    void end (String winner, int b1, int b2, int b3) {

        // Durchlaufen der ArraysList.
        for (Button button : buttons) {
            button.setDisable(true);
        }

        // Ausgabe des Gewinners über das Label-Element.
        lblPlayer.setText(winner + " hat gewonnen!");

        // Variable auf 'true' (Gewinn).
        win = true;

        // Schriftfarbe der Gewinn-Buttons ändern.
        for (int i = -1; i <= buttons.size(); i++) {
            if (i == b1 || i == b2 || i == b3) {
                Button button = buttons.get(i -1);
                button.setTextFill(Color.rgb(0, 255, 26));
            }
        }
    }
}