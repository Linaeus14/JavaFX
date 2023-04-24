package main.controller.staff;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import main.model.strip;
import main.model.data.produk;
import main.model.data.produsen;

public class mainStaff {

    private Scene beforeScene, prdnScene, prdScene;
    private strip akun;
    Alert info = new Alert(AlertType.INFORMATION), alert = new Alert(AlertType.CONFIRMATION);

    @FXML
    private Tab tabPrdn;
    @FXML
    private Label stripNama, stripKode, stripStatus;
    @FXML
    private TextField tKodeP, tProdusen, tAlamat, tKontak, tKode, tNama;
    @FXML
    private TextField tHarga, tStok, keywordPrdn, keywordPrd;
    @FXML
    private Button bTambahPrdn, bUbahPrdn, bHapusPrdn, bTambahPrd, bLogOut;
    @FXML
    private ComboBox<String> typePrdn,  typePrd, tProdusen2;
    @FXML
    private TableView<ObservableList<String>> tableProdusen, tableProduk;

    public mainStaff(strip akun) {
        this.akun = akun;
    }

    public void initialize() {

        alert.setTitle("Hapus");
        alert.setContentText("Yakin ingin hapus?");
        info.setTitle("Informasi");
        info.setContentText("Mohon pilih terlebih dahulu!");

        typePrdn.getItems().removeAll(typePrdn.getItems());
        typePrdn.getItems().addAll("KodeP", "Produsen");
        typePrdn.getSelectionModel().select("KodeP");

        typePrd.getItems().removeAll(typePrd.getItems());
        typePrd.getItems().addAll("Kode", "Nama", "Produsen");
        typePrd.getSelectionModel().select("Kode");

        akun.setStrip(stripNama, stripKode, stripStatus);

        tabOnPrd();
        tabOnPrdn();
    }

    @FXML
    void tableProdusenSelect() {

        ObservableList<String> selectedList = tableProdusen.getSelectionModel().getSelectedItem();
        tKodeP.setText(selectedList.get(0));
        tProdusen.setText(selectedList.get(1));
        tAlamat.setText(selectedList.get(2));
        tKontak.setText(selectedList.get(3));
    }

    @FXML
    void keywordPrdnChanged() {

        produsen produsen = new produsen();
        produsen.searchData(tableProdusen, keywordPrdn, typePrdn.getValue());
    }

    @FXML
    void bTambahPrdnClick(Event e) {
        openPrdn(e);
    }

    @FXML
    void bUbahPrdnClick(Event e) {

        produsen produsen = new produsen(
            tKodeP.getText(),
            tProdusen.getText(),
            tAlamat.getText(),
            tKontak.getText()
        );
    
        produsen.updateData();
        clearPrdn();
        produsen.readData(tableProdusen);
    }

    @FXML
    void bHapusPrdnClick(Event e) {

        if (tableProdusen.getSelectionModel().getSelectedItem() != null) {
            ObservableList<String> selectedList = tableProdusen.getSelectionModel().getSelectedItem();
            produsen produsen = new produsen(selectedList.get(0));

            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    produsen.deleteData();
                    clearPrdn();
                    produsen.readData(tableProdusen);
                }
            });
        } else {
            info.showAndWait();
        }
    }

    @FXML
    void tableProdukSelect() {

        ObservableList<String> selectedList = tableProduk.getSelectionModel().getSelectedItem();
        tKode.setText(selectedList.get(0));
        tNama.setText(selectedList.get(1));
        tProdusen2.setValue(selectedList.get(2));
        tHarga.setText(selectedList.get(3));
        tStok.setText(selectedList.get(4));
    }

    @FXML
    void keywordPrdChanged() {

        produk produk = new produk();
        produk.searchData(tableProduk, keywordPrd, typePrd.getValue());
    }

    @FXML
    void bTambahPrdClick(Event e) {
        openPrd(e);
    }

    @FXML
    void bUbahPrdClick(Event e) {

        produk produk = new produk(

            tKode.getText(), 
            tNama.getText(), 
            tProdusen2.getValue(), 
            tHarga.getText(), 
            tStok.getText()
        );

        produk.updateData();
        clearPrd();
        produk.readData(tableProduk);
    }

    @FXML
    void bHapusPrdClick(Event e) {

        if (tableProduk.getSelectionModel().getSelectedItem() != null) {
            ObservableList<String> selectedList = tableProduk.getSelectionModel().getSelectedItem();
            produk produk = new produk(selectedList.get(0));

            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    produk.deleteData();
                    clearPrd();
                    produk.readData(tableProduk);
                }
            });
        } else {
            info.showAndWait();
        }
    }

    @FXML
    void bLogOutClick(Event e) {
        
        akun.setNama(null);
        akun.setKode(null);
        akun.setStatus(null);
        openAuth(e);
    }

    @FXML
    void tabChange(Event e) {
        
        if (tabPrdn.isSelected()) {
            tabOnPrdn();
        }   else {
            tabOnPrd();
        }
    }

    private void tabOnPrdn() {

        produsen produsen = new produsen();
        produsen.readData(tableProdusen);
        Platform.runLater(()->keywordPrdn.requestFocus());
    }

    private void tabOnPrd() {

        produk produk = new produk();
        produk.readData(tableProduk);
        produk.fillComboBox(tProdusen2);
        Platform.runLater(()->keywordPrd.requestFocus());
    }

    private void clearPrdn() {
        tKodeP.clear();
        tProdusen.clear();
        tAlamat.clear();
        tKontak.clear();
    }

    private void clearPrd() {
        tKode.clear();
        tProdusen2.getSelectionModel().select(0);
        tNama.clear();
        tHarga.clear();
        tStok.clear();
    }

    public void setTransition(Scene beforeScene, Scene prdnScene, Scene prdScene) {
        this.beforeScene = beforeScene;
        this.prdnScene = prdnScene;
        this.prdScene = prdScene;
    }

    private final void openAuth(Event e) {

        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(beforeScene);
    }

    private final void openPrdn(Event e) {

        prdnStaff prdnStaff = (prdnStaff) main.App.getLoader(prdnScene).getController();
        prdnStaff.initialize();
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(prdnScene);
    }

    private final void openPrd(Event e) {

        prdStaff prdStaff = (prdStaff) main.App.getLoader(prdScene).getController();
        prdStaff.initialize();
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(prdScene);
    }
}