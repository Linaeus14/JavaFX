package main.controller.admin;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.data.manager;

public class inputAdm {

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
        Platform.runLater(()->tUserid.requestFocus());
    }

    @FXML
    void bBackClick(MouseEvent event) {
        openMainAdm(event);
    }

    @FXML
    void bTambahClick(Event event) {

        if (check()) {
            info.showAndWait();
        } else {
                manager manager = new manager(
                tUserid.getText(),
                tPass.getText(),
                tNama.getText(), 
                null,
                "manager"
            );

            if (!manager.writeData()) {
                info.setContentText("kode sudah ada!");
                info.showAndWait();
            } else {
                openMainAdm(event);
            }
        }
    }

    @FXML
    void tUseridEnter(KeyEvent event) {
        enterPress(event, tPass);
    }

    @FXML
    void tPassEnter(KeyEvent event) {
        enterPress(event, tNama);
    }

    @FXML
    void tNamaEnter(KeyEvent event) {
        enterPress(event);
    }

    protected Boolean check() {

        if (tUserid.getText() == null || tUserid.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi kode!");
            Platform.runLater(()->tUserid.requestFocus());
            return true;

        } else if (tPass.getText() == null || tPass.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi nama produsen!");
            Platform.runLater(()->tPass.requestFocus());
            return true;

        } else if (tNama.getText() == null || tNama.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi alamat produsen!");
            Platform.runLater(()->tNama.requestFocus());
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

    protected void enterPress(KeyEvent ke) {
        
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bTambahClick(ke);
        }
    }
    protected void enterPress(KeyEvent ke, TextField textfield) {

        if (ke.getCode().equals(KeyCode.ENTER)) {
            Platform.runLater(()->textfield.requestFocus());
        }
    }

    public void setTransition(Scene beforeScene) {
        this.beforeScene = beforeScene;
    }

    protected final void openMainAdm(Event e) {

        mainAdm admMain = (mainAdm) main.App.getLoader(beforeScene).getController();
        admMain.initialize();
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(beforeScene);
    }
}
