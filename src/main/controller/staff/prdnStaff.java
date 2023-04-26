package main.controller.staff;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.model.key;
import main.model.data.produsen;

public class prdnStaff  {

    private Scene beforeScene;
    Alert info = new Alert(AlertType.INFORMATION);

    @FXML
    private TextField tKodePrdn, tProdusen, tAlamat, tKontak;
    @FXML
    private Button prdnBack, prdnTambah;

    public void initialize() {

        info.setTitle("Informasi");
        clear();
        key.focus(tKodePrdn);
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
                info.setContentText("kode atau nama sudah ada!");
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
        key.enterPress(ke, tProdusen);
    }
    @FXML
    void tProdusenEnter(KeyEvent ke) {
        key.enterPress(ke, tAlamat);
    }
    @FXML
    void tAlamatEnter(KeyEvent ke) {
        key.enterPress(ke, tKontak);
    }
    @FXML
    void tKontakEnter(KeyEvent ke) {

        if (ke.getCode().equals(KeyCode.ENTER)) {
            prdnTambahClick(ke);
        }
    }

    private Boolean check() {

        if (key.empty(tKodePrdn)) {
            info.setContentText("Mohon isi kode!");
            key.focus(tKodePrdn);
            return true;

        } else if (key.empty(tProdusen)) {
            info.setContentText("Mohon isi nama produsen!");
            key.focus(tProdusen);
            return true;

        } else if (key.empty(tAlamat)) {
            info.setContentText("Mohon isi alamat produsen!");
            key.focus(tAlamat);
            return true;

        } else if (key.empty(tKontak)) {
            info.setContentText("Mohon isi kontak produsen!");
            key.focus(tKontak);
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

    public void setTransition(Scene beforeScene) {
        this.beforeScene = beforeScene;
    }

    private final void openMainStaff(Event e) {

        mainStaff staffMain = (mainStaff) main.App.getLoader(beforeScene).getController();
        staffMain.initialize();
        main.App.setScene(e, beforeScene);
    }
}