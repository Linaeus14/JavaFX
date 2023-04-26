package main.controller.staff;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import main.controller.pesananController;
import main.model.key;
import main.model.strip;
import main.model.data.pembelian;
import main.model.data.produk;
import main.model.data.produsen;

public class mainStaff extends pesananController {
    
    private Scene prdnScene, prdScene;

    @FXML
    private Tab tabPrdn, tabPrd;
    @FXML
    private Label stripNama, stripKode, stripStatus;
    @FXML
    private TextField tKodeP, tProdusen, tAlamat, tKontak, tKode, tNama;
    @FXML
    private TextField tHarga, tStok, keywordPrdn, keywordPrd;
    @FXML
    private Button bTambahPrdn, bUbahPrdn, bHapusPrdn, bTambahPrd, bSelsai, bLogOut;
    @FXML
    private ComboBox<String> typePrdn, typePrd, tProdusen2;
    @FXML
    private TableView<ObservableList<String>> tableProdusen, tableProduk;

    public mainStaff(strip akun) {
        super(akun);
    }

    public void initialize() {

        key.numberInput(tHarga);
        key.numberInput(tStok);

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
        tabChange(null);
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

        if (checkSelected(tableProdusen)) {

            produsen produsen = new produsen(
                    tKodeP.getText(),
                    tProdusen.getText(),
                    tAlamat.getText(),
                    tKontak.getText());

            produsen.updateData();
            clearPrdn();
            produsen.readData(tableProdusen);
        } else {
            info.showAndWait();
        }
    }

    @FXML
    void bHapusPrdnClick(Event e) {

        if (checkSelected(tableProdusen)) {
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

        if (checkSelected(tableProduk)) {

            produk produk = new produk(

                    tKode.getText(),
                    tNama.getText(),
                    tProdusen2.getValue(),
                    tHarga.getText(),
                    tStok.getText());

            produk.updateData();
            clearPrd();
            produk.readData(tableProduk);
        } else {
            info.showAndWait();
        }
    }

    @FXML
    void bHapusPrdClick(Event e) {

        if (checkSelected(tableProduk)) {
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
    void bSelsaiClick() {

        if(checkSelected(tablePembelian)) {
            ObservableList<String> selectedList = tablePembelian.getSelectionModel().getSelectedItem();
            pembelian pembelian = new pembelian();
            pembelian.updateStatus(Integer.parseInt(selectedList.get(0)));
            tabOnR();
        }
    }

    @FXML
    void tabChange(Event e) {

        if (tabPrdn.isSelected()) {
            tabOnPrdn();
        } else if(tabPrd.isSelected()) {
            tabOnPrd();
        } else {
            tabOnR();
        }
    }

    private void tabOnPrdn() {

        produsen produsen = new produsen();
        produsen.readData(tableProdusen);
        key.focus(keywordPrdn);
        ;
    }

    private void tabOnPrd() {

        produk produk = new produk();
        produk.readData(tableProduk);
        produk.fillComboBox(tProdusen2);
        key.focus(keywordPrd);
        ;
    }

    @Override
    protected void tabOnR() {

        pembelian pembelian = new pembelian();
        pembelian.readProcessed(tablePembelian);
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

    private final void openPrdn(Event e) {

        prdnStaff prdnStaff = (prdnStaff) main.App.getLoader(prdnScene).getController();
        prdnStaff.initialize();
        main.App.setScene(e, prdnScene);
    }

    private final void openPrd(Event e) {

        prdStaff prdStaff = (prdStaff) main.App.getLoader(prdScene).getController();
        prdStaff.initialize();
        main.App.setScene(e, prdScene);
    }
}