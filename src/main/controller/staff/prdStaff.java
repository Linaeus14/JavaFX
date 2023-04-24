package main.controller.staff;


import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.data.produk;

public class prdStaff {

    private Scene beforeScene;
    Alert info = new Alert(AlertType.INFORMATION);

    @FXML
    private Button bPrdBack, bPrdTambah;
    @FXML
    private TextField tHarga, tKode, tNama, tStok;
    @FXML
    private ComboBox<String> tProdusen;

    public void initialize() {

        produk produk = new produk();
        clear();
        produk.fillComboBox(tProdusen);
        tProdusen.getSelectionModel().select(0);
        Platform.runLater(()->tKode.requestFocus());
    }
    
    @FXML
    void bPrdTambahClick(Event event) {

        if (check()) {
            info.showAndWait();
        } else {   
            produk produk = new produk(
                tKode.getText(), 
                tNama.getText(), 
                tProdusen.getValue(), 
                tHarga.getText(), 
                tStok.getText()
            );

            if (!produk.writeData()) {
                info.setContentText("kode sudah ada!");
                info.showAndWait();
            } else {
                openMainStaff(event);
            }
        }
    }
    
    @FXML
    void bPrdBackClick(MouseEvent event) {
        openMainStaff(event);
    }

    @FXML
    void tKodeEnter(KeyEvent event) {
        enterPress(event, tNama);
    }

    @FXML
    void tNamaEnter(KeyEvent event) {
        enterPress(event, tHarga);
    }

    @FXML
    void tHargaEnter(KeyEvent event) {
        enterPress(event, tStok);
    }

    @FXML
    void tStokEnter(KeyEvent event) {
        enterPress(event);
    }

    private Boolean check() {

        if (tKode.getText() == null || tKode.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi kode!");
            Platform.runLater(()->tKode.requestFocus());
            return true;

        } else if (tNama.getText() == null || tNama.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi nama barang!");
            Platform.runLater(()->tNama.requestFocus());
            return true;

        } else if (tHarga.getText() == null || tHarga.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi harga barang!");
            Platform.runLater(()->tHarga.requestFocus());
            return true;

        } else if (tStok.getText() == null || tStok.getText().trim().isEmpty()) {
            info.setContentText("Mohon isi stok barang!");
            Platform.runLater(()->tStok.requestFocus());
            return true;

        } else {
            return false;
        }
    }

    private void clear() {

        tKode.clear();
        tNama.clear();
        tProdusen.getSelectionModel().select(0);
        tHarga.clear();
        tStok.clear();
    }

    private void enterPress(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            bPrdTambahClick(ke);
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

    public void openMainStaff(Event e) {

        mainStaff staffMain = (mainStaff) main.App.getLoader(beforeScene).getController();
        staffMain.initialize();
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(beforeScene);
    }
}