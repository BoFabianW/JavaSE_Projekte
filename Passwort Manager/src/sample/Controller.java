package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.util.Optional;
import java.util.Scanner;

public class Controller {

    String selectFile = null;

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView imgKey;

    @FXML
    private Button buttonCancelEintrag, buttonCancelPW, buttonAddList, buttonAbbruch, buttonDeleteList, buttonOpen, deleteList, addList, buttonClose, buttonSave;

    @FXML
    private Hyperlink hLink;

    @FXML
    private TextField txtLabel, txtUsername, txtPassword, txtURL, txtTresor, txtNeueListe;

    @FXML
    private TextArea txtInfo;

    @FXML
    private ListView<String> lstInhalt;

    @FXML
    private PasswordField pwfPasswort, pwfPasswortW, txtPWeingabe;

    @FXML
    private Label lblTresor, lblPasswort, lblPasswortW, lblAnzahl, lblError, lblPWeingabe, lblChange, lblInfo, lblNeueListe;

    @FXML
    private ComboBox<String> comboList;

    /**
     * Diese Methode setzt einen Link beim Auslesen des Inhalts aus einer Datei.
     * @throws IOException
     */
    @FXML
    void addLink() throws IOException {

        Desktop desk = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        assert desk != null;
        desk.browse(URI.create(txtURL.getText()));
    }

    /**
     * Diese Methode setzt die Elemente auf sichtbar/unsichtbar und setzt den Fokus auf den Button zum hinzufuegen einer Liste
     */
    @FXML
    void abbruchAddListe() {

        lblNeueListe.setText("Passwort-Liste.");
        lblNeueListe.setTextFill(javafx.scene.paint.Color.web("#000000"));
        comboList.setVisible(true);
        txtNeueListe.setVisible(false);
        deleteList.setVisible(true);
        addList.setText("+");
        addList.setPrefWidth(34);
        buttonAbbruch.setVisible(false);
        addList.requestFocus();
        comboList();
    }

    /**
     * Diese Methode fuegt eine neue Datei in der Passwortliste hinzu.
     * @throws IOException kann beim oeffnen der Datei geworfen werden.
     */
    @FXML
    void addInhalt() throws IOException {

        deleteText();

        txtLabel.setDisable(true);
        txtUsername.setDisable(true);
        txtPassword.setDisable(true);
        txtURL.setDisable(true);
        txtInfo.setDisable(true);

        hLink.setDisable(true);

        lblChange.setText("");

        if (buttonAddList.getText().equals("+")) {

            buttonOpen.setDisable(true);
            addList.setDisable(true);
            comboList.setDisable(true);
            buttonDeleteList.setDisable(true);
            lstInhalt.setVisible(false);
            comboList.setDisable(true);
            addList.setDisable(true);
            lblTresor.setVisible(true);
            lblPasswort.setVisible(true);
            lblPasswortW.setVisible(true);
            txtTresor.setVisible(true);
            pwfPasswort.setVisible(true);
            pwfPasswortW.setVisible(true);
            buttonCancelEintrag.setVisible(true);
            buttonAddList.setText("OK");
            lblError.setVisible(true);
            txtTresor.requestFocus();

        } else {

            File newFile = new File(System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\" + txtTresor.getText() + ".pwm");

            if (newFile.exists()) {

                lblError.setText("Eintrag exisiert bereits!");

            } else {

                if (pwfPasswort.getText().equals(pwfPasswortW.getText())) {

                    if (pwfPasswort.getText().length() < 5) {

                        lblError.setText("Passwort zu kurz! Min. 5 Zeichen.");
                    } else {

                        if (newFile.createNewFile()) {

                            PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));

                            write.println(PwmCrypto.codieren(pwfPasswort.getText(), txtTresor.getText()));

                            selectFile = txtTresor.getText();

                            for (int i = 0 ; i < 6; i++) {
                                write.println("leer");
                            }
                            write.close();
                        }

                        txtLabel.setDisable(false);
                        txtUsername.setDisable(false);
                        txtURL.setDisable(false);
                        txtPassword.setDisable(false);
                        txtInfo.setDisable(false);

                        comboList();

                        lstInhalt.getSelectionModel().select(txtTresor.getText());

                        lblChange.setText("Bitte Einträge vervollständigen!");

                        txtLabel.requestFocus();

                        lstInhalt.setDisable(true);

                        cancelEintrag();

                        addList.setDisable(true);
                        deleteList.setDisable(true);
                        comboList.setDisable(true);
                        buttonSave.setDisable(false);
                        buttonAddList.setDisable(true);
                    }

                } else {

                    lblError.setText("Passwörter stimmen nicht überein!");
                }
            }
        }

        deleteList.setDisable(true);

        // Eintrag in ListView selektieren.
        lstInhalt.getSelectionModel().select(selectFile);
        selectFile = null;
    }

    /**
     * Diese Methode blendet die Elemente zum Anlegen einer neuen Liste ein und aus
     * und legt eine neue Liste(Ordner auf der Festplatte) an.
     */
    @FXML
    void addList() {

        lstInhalt.getSelectionModel().select(-1);

        deleteText();

        txtLabel.setDisable(true);
        txtUsername.setDisable(true);
        txtPassword.setDisable(true);
        txtURL.setDisable(true);
        txtInfo.setDisable(true);

        lblChange.setText("");

        if (addList.getText().equals("+")) {

            lblNeueListe.setText("Neue Passwort-Liste anlegen.");
            comboList.setVisible(false);
            txtNeueListe.setVisible(true);
            deleteList.setVisible(false);
            addList.setText("Speichern");
            addList.setPrefWidth(78);
            buttonAbbruch.setVisible(true);
            txtNeueListe.requestFocus();

        } else {

            File newDir = new File(System.getProperty("user.dir") + "\\Savelist\\" + txtNeueListe.getText() + "\\");

            if (isClear(txtNeueListe.getText())) {

                if (txtNeueListe.getText() == null) {

                    lblNeueListe.setText("Bitte einen Listennamen eingeben!");
                    lblNeueListe.setTextFill(javafx.scene.paint.Color.web("#ff0000"));

                } else {

                    if (newDir.exists()) {

                        lblNeueListe.setText("Liste exisitiert bereits!");
                        lblNeueListe.setTextFill(javafx.scene.paint.Color.web("#ff0000"));

                    } else {

                        comboList.getItems().add(txtNeueListe.getText());
                        comboList.getSelectionModel().select(txtNeueListe.getText());
                        abbruchAddListe();

                        txtNeueListe.setText("");

                        try {
                            if (newDir.mkdir()) {
                                System.out.println("Verzeichnis erstellt.");
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        buttonAddList.setDisable(false);
                        deleteList.setDisable(false);

                        // Leeren des ListView-Elements
                        lstInhalt.getItems().clear();

                        comboList();
                    }
                }
            } else {

                lblNeueListe.setText("Ungültige Zeichen!");
                lblNeueListe.setTextFill(javafx.scene.paint.Color.web("#ff0000"));
            }
        }
    }

    /**
     * Diese Methode setzt die entsprechenden Visible und Disable -Werte der Elemente
     */
    @FXML
    void cancelEintrag() {

        lstInhalt.setVisible(true);
        comboList.setDisable(false);
        addList.setDisable(false);
        lblTresor.setVisible(false);
        lblPasswort.setVisible(false);
        lblPasswortW.setVisible(false);
        txtTresor.setVisible(false);
        pwfPasswort.setVisible(false);
        pwfPasswortW.setVisible(false);
        buttonCancelEintrag.setVisible(false);
        buttonAddList.setText("+");
        buttonOpen.setDisable(true);
        buttonDeleteList.setDisable(true);
        txtTresor.setText("");
        pwfPasswort.setText("");
        pwfPasswortW.setText("");
        lblError.setVisible(false);
        comboList();
    }

    /**
     * Diese Methode schliesst das Programm
     */
    @FXML
    void close() {

        System.exit(0);
    }

    /**
     * Diese Methode setzt den Text des Buttons und Labels.
     * Entsprechende Elemente werden unsichtbar.
     */
    @FXML
    void cancelPW() {

        buttonOpen.setText("Öffnen");
        buttonCancelPW.setVisible(false);
        txtPWeingabe.setVisible(false);
        lblPWeingabe.setVisible(false);
        txtPWeingabe.setText("");
        lblPWeingabe.setText("Passwort :");
        buttonAddList.setDisable(false);
        buttonDeleteList.setDisable(false);
        buttonOpen.requestFocus();
    }

    /**
     * Diese Methode uberprueft die Groesse der Combolist-Items und gibt die Anzahl der Items ueber ein Label aus.
     */
    @FXML
    void comboList() {

        if (comboList.getItems().size() == 0) {
            lblAnzahl.setText("");
            imgKey.setVisible(false);

        } else {

            File anzahl = new File(System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\");

            try {
                lblAnzahl.setText("Anzahl der Einträge in Passwort-Liste '" + comboList.getSelectionModel().getSelectedItem() + "' : " + anzahl.list().length);
                imgKey.setVisible(true);
            } catch (Exception ignored) {}
        }

        inhaltLaden(comboList.getSelectionModel().getSelectedItem());

        if (comboList.getSelectionModel().getSelectedIndex() > -1) {

            buttonAddList.setDisable(false);
            deleteList.setDisable(false);

        } else {

            buttonAddList.setDisable(true);
            deleteList.setDisable(true);
        }
    }

    /**
     * Diese Methode oeffnet die zu aendernde Datei und liest das Passwort aus.
     * Der String mit dem Passwort wird beim Aufruf der Methode 'datenSchreiben' uebergeben.
     * @throws IOException kann beim oeffnen der Datei geworfen werden.
     */
    @FXML
    void change() throws IOException {

        String pw;

        Scanner pwScan = new Scanner(new File(System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\" + lstInhalt.getSelectionModel().getSelectedItem() + ".pwm"));
        pw = pwScan.nextLine();
        pwScan.close();

        txtLabel.setDisable(true);
        txtUsername.setDisable(true);
        txtURL.setDisable(true);
        txtPassword.setDisable(true);
        txtInfo.setDisable(true);
        lblChange.setText("");

        datenSchreiben(pw);

        deleteText();

        hLink.setDisable(true);
        buttonAddList.setDisable(false);
        addList.setDisable(false);
        comboList.setDisable(false);
        deleteList.setDisable(false);
        buttonDeleteList.setDisable(false);
        buttonOpen.setDisable(false);
        buttonSave.setDisable(true);
        lstInhalt.setDisable(false);
    }

    /**
     * Diese Methode loescht eine ausgewaehlte Datei aus der Passwortliste.
     * Entsprechende Elemente werden deaktiviert.
     */
    @FXML
    void deleteInhalt() {

        File selectFile = new File(System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\" + lstInhalt.getSelectionModel().getSelectedItem() + ".pwm");

        try {
            if (selectFile.delete()) {
                System.out.println("Datei gelöscht.");
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        lstInhalt.getItems().remove(lstInhalt.getSelectionModel().getSelectedIndex());

        comboList();

        buttonDeleteList.setDisable(true);
        buttonOpen.setDisable(true);
        buttonSave.setDisable(true);

        txtLabel.setDisable(true);
        txtUsername.setDisable(true);
        txtURL.setDisable(true);
        txtPassword.setDisable(true);
        txtInfo.setDisable(true);

        hLink.setDisable(true);

        buttonAddList.requestFocus();
    }

    /**
     * Diese Methode loescht eine ausgewaehlte Passwortliste
     */
    @FXML
    void deleteList() {

        Alert del = new Alert(Alert.AlertType.CONFIRMATION);
        del.setTitle("Liste löschen");
        del.setHeaderText("Soll die Liste wirklich gelöscht werden?");

        del.setX(Main.pos.getX() + 220);
        del.setY(Main.pos.getY() + 200);

        Optional<ButtonType> option = del.showAndWait();

        if (option.isPresent()) {
            if (option.get() == ButtonType.OK) {
                deleteOk();
            }
        }
    }

    /**
     * Diese Methode leert bei Auswahl einer Datei zum oeffenen die Textfelder
     * und deaktiviert diese. Sollte in der Liste nichts ausgewaehlt sein, werden entsprechende Buttons deaktiviert.
     */
    @FXML
    void openList() {

        deleteText();

        txtLabel.setDisable(true);
        txtUsername.setDisable(true);
        txtPassword.setDisable(true);
        txtURL.setDisable(true);
        txtInfo.setDisable(true);

        hLink.setDisable(true);

        if (lstInhalt.getSelectionModel().getSelectedIndex() != -1) {

            buttonOpen.setDisable(false);
            buttonDeleteList.setDisable(false);
        }
    }

    /**
     * Diese Methode ueberprueft das eingegebene Passwort zum anzeigen des Inhalts einer Datei auf gueltigkeit
     * und ruft die Methode zum auslesen der Dateien auf.
     * @throws IOException kann beim oeffnen der Datei geworfen werden.
     */
    @FXML
    void pwEingabe() throws IOException {

        if (buttonOpen.getText().equals("Öffnen")) {

            lblPWeingabe.setVisible(true);
            txtPWeingabe.setVisible(true);
            txtPWeingabe.requestFocus();
            lblPWeingabe.setTextFill(javafx.scene.paint.Color.web("#000000"));

            buttonAddList.setDisable(true);
            buttonDeleteList.setDisable(true);
            buttonCancelPW.setVisible(true);

            buttonOpen.setText("OK");

        } else {

            String pw;

            Scanner pwScan = new Scanner(new File(System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\" + lstInhalt.getSelectionModel().getSelectedItem() + ".pwm"));
            pw = pwScan.nextLine();
            pwScan.close();

            if (pw.equals(PwmCrypto.codieren(txtPWeingabe.getText(), lstInhalt.getSelectionModel().getSelectedItem()))) {

                datenLesen();

                lblChange.setText("Einträge können jetzt aktualisiert werden.");

                txtPWeingabe.setText("");

                cancelPW();

                buttonOpen.setDisable(true);
                buttonDeleteList.setDisable(true);
                buttonAddList.setDisable(true);
                addList.setDisable(true);
                comboList.setDisable(true);
                deleteList.setDisable(true);
                lstInhalt.setDisable(true);

            } else {

                lblPWeingabe.setText("Falsches Passwort!");
                lblPWeingabe.setTextFill(Color.web("#ff0000"));
            }
        }
    }

    @FXML
    void info() {

        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setHeaderText("    Passwort-Manager V.1.0\n" +
                           "    ---------------------------------------------\n" +
                           "    Copyright © 2020 Fabian Werner\n    ");
        info.setTitle("Info");
        info.setX(Main.pos.getX() + 220);
        info.setY(Main.pos.getY() + 200);
        info.showAndWait();
    }

    @FXML
    void initialize() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblNeueListe != null : "fx:id=\"lblNeueListe\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonAddList != null : "fx:id=\"buttonAddList\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonDeleteList != null : "fx:id=\"buttonDeleteList\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtLabel != null : "fx:id=\"txtLabel\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtUsername != null : "fx:id=\"txtUsername\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtPassword != null : "fx:id=\"txtPassword\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtURL != null : "fx:id=\"txtURL\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtInfo != null : "fx:id=\"txtInfo\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lstInhalt != null : "fx:id=\"lstInhalt\" was not injected: check your FXML file 'viewController.fxml'.";
        assert comboList != null : "fx:id=\"comboList\" was not injected: check your FXML file 'viewController.fxml'.";
        assert deleteList != null : "fx:id=\"deleteList\" was not injected: check your FXML file 'viewController.fxml'.";
        assert addList != null : "fx:id=\"addList\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonSave != null : "fx:id=\"buttonSave\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtNeueListe != null : "fx:id=\"txtNeueListe\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonAbbruch != null : "fx:id=\"buttonAbbruch\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtTresor != null : "fx:id=\"txtTresor\" was not injected: check your FXML file 'viewController.fxml'.";
        assert pwfPasswort != null : "fx:id=\"pwfPasswort\" was not injected: check your FXML file 'viewController.fxml'.";
        assert pwfPasswortW != null : "fx:id=\"pwfPasswortW\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblTresor != null : "fx:id=\"lblTresor\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblPasswort != null : "fx:id=\"lblPasswort\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblPasswortW != null : "fx:id=\"lblPasswortW\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonCancelEintrag != null : "fx:id=\"buttonCancelEintrag\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblError != null : "fx:id=\"lblError\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonOpen != null : "fx:id=\"buttonOpen\" was not injected: check your FXML file 'viewController.fxml'.";
        assert txtPWeingabe != null : "fx:id=\"txtPWeingabe\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonCancelPW != null : "fx:id=\"buttonCancelPW\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblChange != null : "fx:id=\"lblChange\" was not injected: check your FXML file 'viewController.fxml'.";
        assert hLink != null : "fx:id=\"hLink\" was not injected: check your FXML file 'viewController.fxml'.";
        assert buttonClose != null : "fx:id=\"buttonClose\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblPWeingabe != null : "fx:id=\"lblPWeingabe\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblAnzahl != null : "fx:id=\"lblAnzahl\" was not injected: check your FXML file 'viewController.fxml'.";
        assert imgKey != null : "fx:id=\"imgKey\" was not injected: check your FXML file 'viewController.fxml'.";
        assert lblInfo != null : "fx:id=\"lblInfo\" was not injected: check your FXML file 'viewController.fxml'.";

        addList.requestFocus();
        startOrdnerAnlegen();
        listenLaden();
    }

    /**
     * Diese Methode erstellt beim Start ein neues Verzeichnis - falls nicht vorhanden.
     */
    void startOrdnerAnlegen() {

        File newDir = new File(System.getProperty("user.dir") + "\\Savelist\\");

        if (!newDir.exists()) {
            if (newDir.mkdir()) {
                System.out.println("Verzeichnis angelegt.");
            } else {
                System.out.println("Verzeichnis konnte nicht erstellt werden.");
            }
        }
    }

    /**
     * Diese Methode laed den Inhalt einer Passwortliste.
     * @param ordner Uebergabe des zu oeffnenden Ordners als String
     */
    void inhaltLaden(String ordner) {

        lstInhalt.getItems().clear();

        File newDir = new File(System.getProperty("user.dir") + "\\Savelist\\" + ordner + "\\");

        File[] dateien = newDir.listFiles();

        try {

            for (int i = 0; i < (dateien != null ? dateien.length : 0); i++) {

                String ausgabe = dateien[i].getAbsoluteFile().toString();
                ausgabe = ausgabe.substring(ausgabe.lastIndexOf("\\") + 1, ausgabe.lastIndexOf("."));

                lstInhalt.getItems().add(ausgabe);
                buttonDeleteList.setDisable(true);
            }

        } catch(Exception ignored) {
        }
    }

    /**
     * Diese Methode laed die existierenden Passwortlisten in die ComboBox
     */
    void listenLaden() {

        File loadDir = new File(System.getProperty("user.dir") + "\\Savelist\\");

        File[] listDir = loadDir.listFiles();

        assert listDir != null;
        for (File file : listDir) {
            if (file.isDirectory()) {
                comboList.getItems().add(file.toString().substring(file.toString().lastIndexOf("\\") + 1));
                inhaltLaden(comboList.getSelectionModel().getSelectedItem());
            }
        }
    }

    /**
     * Diese Methode liest die Dateien der Passwortliste aus und uebergibt die Eintraege in die Elemente
     * @throws FileNotFoundException kann beim oeffnen der Dateien geworfen werden.
     */
    void datenLesen() throws FileNotFoundException {

        txtLabel.setDisable(false);
        txtUsername.setDisable(false);
        txtURL.setDisable(false);
        txtPassword.setDisable(false);
        txtInfo.setDisable(false);

        buttonSave.setDisable(false);

        Scanner lesen = new Scanner(new File(System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\" + lstInhalt.getSelectionModel().getSelectedItem() + ".pwm"));

        for (int i = 0; i < 7; i++){

            if (lesen.hasNextLine()) {

                String add = lesen.nextLine();

                if (i == 1) {
                    if (!add.equals("leer")) txtLabel.setText(PwmCrypto.decodieren(add, lstInhalt.getSelectionModel().getSelectedItem()));
                }
                if (i == 2) {
                    if (!add.equals("leer")) txtUsername.setText(PwmCrypto.decodieren(add, lstInhalt.getSelectionModel().getSelectedItem()));
                }
                if (i == 3) {
                    if (!add.equals("leer")) txtPassword.setText(PwmCrypto.decodieren(add, lstInhalt.getSelectionModel().getSelectedItem()));
                }
                if (i == 4) {
                    if (!add.equals("leer")){
                        txtURL.setText(PwmCrypto.decodieren(add, lstInhalt.getSelectionModel().getSelectedItem()));
                        hLink.setDisable(false);
                    }
                }
                if (i == 5) {
                    if (!add.equals("leer")) txtInfo.setText(PwmCrypto.decodieren(add, lstInhalt.getSelectionModel().getSelectedItem()));
                }
            }
        }

        buttonSave.requestFocus();
        lesen.close();
    }

    /**
     * Diese Methode schreibt die Werte aus den Elementen für die Eingabe der ListenDaten in eine Datei.
     * @param pw Uebergabe des ermittelten Paswortes.
     * @throws IOException kann beim oeffnen der Datei geworfen werden.
     */
    void datenSchreiben(String pw) throws IOException {

        File newFile = new File(System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\" + lstInhalt.getSelectionModel().getSelectedItem() + ".pwm");

        PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));

        write.println(pw);

        if (txtLabel.getText().isEmpty()) {
            write.println("leer");
        } else{
            write.println(PwmCrypto.codieren(txtLabel.getText(), lstInhalt.getSelectionModel().getSelectedItem()));
        }


        if (txtUsername.getText().isEmpty()) {
            write.println("leer");
        } else{
            write.println(PwmCrypto.codieren(txtUsername.getText(), lstInhalt.getSelectionModel().getSelectedItem()));
        }


        if (txtPassword.getText().isEmpty()) {
            write.println("leer");
        } else{
            write.println(PwmCrypto.codieren(txtPassword.getText(), lstInhalt.getSelectionModel().getSelectedItem()));
        }


        if (txtURL.getText().isEmpty()) {
            write.println("leer");
        } else{
            write.println(PwmCrypto.codieren(txtURL.getText(), lstInhalt.getSelectionModel().getSelectedItem()));
        }


        if (txtInfo.getText().isEmpty()) {
            write.println("leer");
        } else{
            write.println(PwmCrypto.codieren(txtInfo.getText(), lstInhalt.getSelectionModel().getSelectedItem()));
        }

        write.close();
    }

    /**
     * Diese Methode ueberprueft die Eingabe für eine neue Liste auf gueltigkeit.
     * @param str übergibt einen String
     * @return gibt einen boolean zurück
     */
    boolean isClear(String str) {

        String reg = "[a-zA-Z0-9]";
        boolean regexClear = true;

        char[] zeichen;

        // String in ein Char-Array speichern.
        zeichen = str.toCharArray();

        for (char c : zeichen) {

            // Char aus dem Char-Array in String speichern.
            String s = String.valueOf(c);

            regexClear = s.matches(reg);

            if (!regexClear) return false;
        }

        return regexClear;
    }

    void deleteText(){

        txtLabel.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtURL.setText("");
        txtInfo.setText("");
        lblChange.setText("");
    }

    void deleteOk() {

        deleteText();

        String deleteFile = System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\";

        File newDir = new File(deleteFile);

        String[] dateien = newDir.list();

        // Alle Dateien aus Verzeichnissen müssen vor dem Löschen des eigentlichen Ordners gelöscht werden.
        assert dateien != null;
        for (String s : dateien) {

            String delete = System.getProperty("user.dir") + "\\Savelist\\" + comboList.getSelectionModel().getSelectedItem() + "\\" + s;
            File del = new File(delete);
            if (del.delete()) System.out.println("Verzeichnis wurde gelöscht.");
        }

        // Löschen des Ordners (Verzeichnis).
        if (newDir.delete()) {
            System.out.println("Verzeichnis gelöscht.");
        } else {
            System.out.println("Verzeichnis konnte nicht gelöscht werden.");
        }

        comboList.getItems().remove(comboList.getSelectionModel().getSelectedItem());

        // Leeren des ListView-Elements
        lstInhalt.getItems().clear();

        comboList();

        if (comboList.getSelectionModel().getSelectedIndex() == -1) {

            buttonDeleteList.setDisable(true);
            deleteList.setDisable(true);
            buttonAddList.setDisable(true);

            txtLabel.setDisable(true);
            txtUsername.setDisable(true);
            txtURL.setDisable(true);
            txtPassword.setDisable(true);
            txtInfo.setDisable(true);

            hLink.setDisable(true);

            buttonSave.setDisable(true);
            buttonOpen.setDisable(true);

            lblAnzahl.setText("");
            imgKey.setVisible(false);

        } else {

            deleteList.setDisable(false);
            buttonAddList.setDisable(false);
        }
    }
}

