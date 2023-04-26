package main.controller.admin;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import main.model.key;

class inputAdm {

    Scene beforeScene;
    Alert info = new Alert(AlertType.INFORMATION);

    @FXML
    protected Button bBack, bTambah;
    @FXML
    protected TextField tNama, tPass, tUserid;
    @FXML
    protected Label label;

    public void initialize() {

        clear();
        info.setTitle("Informasi");
        Platform.runLater(() -> tUserid.requestFocus());
    }

    @FXML
    void bBackClick(MouseEvent event) {
        openMainAdm(event);
    }

    @FXML
    void bTambahClick(Event event) {
    }

    @FXML
    void tUseridEnter(KeyEvent event) {
        key.enterPress(event, tPass);
    }

    @FXML
    void tPassEnter(KeyEvent event) {
        key.enterPress(event, tNama);
    }

    @FXML
    void tNamaEnter(KeyEvent ke) {

        if (ke.getCode().equals(KeyCode.ENTER)) {
            bTambahClick(ke);
        }
    }

    protected Boolean check() {

        if (key.empty(tUserid)) {
            info.setContentText("Mohon isi kode!");
            key.focus(tUserid);
            return true;

        } else if (key.empty(tPass)) {
            info.setContentText("Mohon isi password!");
            key.focus(tPass);
            return true;

        } else if (key.empty(tNama)) {
            info.setContentText("Mohon isi nama!");
            key.focus(tNama);
            return true;

        } else {
            return false;
        }
    }

    protected void clear() {

        tUserid.clear();
        tPass.clear();
        tNama.clear();
    }

    public void setTransition(Scene beforeScene) {
        this.beforeScene = beforeScene;
    }

    protected final void openMainAdm(Event e) {

        mainAdm admMain = (mainAdm) main.App.getLoader(beforeScene).getController();
        admMain.initialize();
        main.App.setScene(e, beforeScene);
    }
}
