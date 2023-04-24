package main.controller.staff;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.model.data.produsen;

public class prdnStaff {

    private Scene beforeScene;
    Alert info = new Alert(AlertType.INFORMATION);

    @FXML
    private TextField tKodePrdn, tProdusen, tAlamat, tKontak;
    @FXML
    private Button prdnBack, prdnTambah;

    public void initialize() {

        info.setTitle("Informasi");
        clear();
        Platform.runLater(()->tKodePrdn.requestFocus());
    }

    @FXML
    void prdnTambahClick(Event e) {

        if (check()) {
            info.showAndWait();
        } else {
            produsen produsen = new produsen(
                tKodePrdn.getText(),
                tProdusen.getText(),
                tAlamat.getText(), 
                tKontak.getText()
            );

            if (!produsen.writeData()) {
                info.setContentText("kode sudah ada!");
                info.showAndWait();
            } else {
                openMainStaff(e);
            }
        }
    }

    @FXML
    void prdnBackClick(Event e) {
        openMainStaff(e);
    }

    @FXML
    void tKodePrdnEnter(KeyEvent ke) {
        enterPress(ke, tProdusen);
    }
    @FXML
    void tProdusenEnter(KeyEvent ke) {
        enterPress(ke, tAlamat);
    }
    @FXML
    void tAlamatEnter(KeyEvent ke) {
        enterPress(ke, tKontak);
    }
    @FXML
    void tKontakEnter(KeyEvent ke) {
        enterPress(ke);
    }

    private Boolean check() {

        if (tKodePrdn.getText() == null || tKodePrdn.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi kode!");
            Platform.runLater(()->tKodePrdn.requestFocus());
            return true;

        } else if (tProdusen.getText() == null || tProdusen.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi nama produsen!");
            Platform.runLater(()->tProdusen.requestFocus());
            return true;

        } else if (tAlamat.getText() == null || tAlamat.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi alamat produsen!");
            Platform.runLater(()->tAlamat.requestFocus());
            return true;

        } else if (tKontak.getText() == null || tKontak.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi kontak produsen!");
            Platform.runLater(()->tKontak.requestFocus());
            return true;

        } else {
            return false;
        }
    }

    private void clear() {
        
        tKodePrdn.clear();
        tProdusen.clear();
        tAlamat.clear();
        tKontak.clear();
    }

    private void enterPress(KeyEvent ke) {
        
        if (ke.getCode().equals(KeyCode.ENTER)) {
            prdnTambahClick(ke);
        }
    }
    private void enterPress(KeyEvent ke, TextField textfield) {

        if (ke.getCode().equals(KeyCode.ENTER)) {
            Platform.runLater(()->textfield.requestFocus());
        }
    }

    public void setTransition(Scene beforeScene) {
        this.beforeScene = beforeScene;
    }

    private final void openMainStaff(Event e) {

        mainStaff staffMain = (mainStaff) main.App.getLoader(beforeScene).getController();
        staffMain.initialize();
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(beforeScene);
    }
}