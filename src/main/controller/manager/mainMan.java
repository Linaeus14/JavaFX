package main.controller.manager;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.strip;

public class mainMan {

    private Scene beforeScene;
    private strip akun;

    @FXML
    private Label stripNama;
    @FXML
    private Label stripKode;
    @FXML
    private Label stripStatus;

    public mainMan(strip akun) {
        this.akun = akun;
    }

    public void initialize() {

        try {
            akun.namaProperty().addListener((obs, oldText, newText) -> stripNama.setText(newText));
            akun.kodeProperty().addListener((obs, oldText, newText) -> stripKode.setText(newText));
            akun.statusProperty().addListener((obs, oldText, newText) -> stripStatus.setText(newText));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void setTransition(Scene beforeScene) {
        this.beforeScene = beforeScene;
    }

    public void openAuth(Event e) {
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(beforeScene);
    }
}