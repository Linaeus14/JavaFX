package main.controller.pembeli;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import main.controller.pesananController;
import main.model.key;
import main.model.strip;
import main.model.data.keranjang;
import main.model.data.pembelian;
import main.model.data.produk;

public class mainPem extends pesananController {

    ObservableList<keranjang> listKeranjang = FXCollections.observableArrayList();
    int pilihanKeranjang;

    @FXML
    private Button bKonfirmasi, bPlus, bMinus, bTambah, bX;
    @FXML
    private TextField keywordPrd, tValue;
    @FXML
    private Tab tabPem;
    @FXML
    private TableView<ObservableList<String>> tableProduk;
    @FXML
    private TableView<keranjang> tableKeranjang;
    @FXML
    private ComboBox<String> typePrd;

    public mainPem(strip akun) {
        super(akun);
    }

    public void initialize() {

        key.numberInput(tValue);

        info.setTitle("Informasi");

        alert.setTitle("Konfirmasi");
        alert.setContentText("Konfirmasi Pembelian?");

        typePrd.getItems().removeAll(typePrd.getItems());
        typePrd.getItems().addAll("Kode", "Nama", "Produsen");
        typePrd.getSelectionModel().select("Nama");

        akun.setStrip(stripNama, stripKode, stripStatus);
        tabPem(null);
    }

    @FXML
    void bTambahClick(MouseEvent event) {

        if (checkSelected(tableProduk)) {

            ObservableList<String> selectedList = tableProduk.getSelectionModel().getSelectedItem();
            String nama = selectedList.get(1);
            int harga = Integer.parseInt(selectedList.get(3));
            int stok = Integer.parseInt(selectedList.get(4));

            Integer value;
            if (key.empty(tValue)) {
                value = 1;
            } else if(Integer.parseInt(tValue.getText()) > stok || Integer.parseInt(tValue.getText()) == 0) {
                info.setContentText("Jumlah salah, Cek Stok!");
                info.showAndWait();
                key.focus(tValue);
                return;
            } else {
                value = Integer.parseInt(tValue.getText());
            }

            if (stok != 0) {
                keranjang keranjang = new keranjang(nama, value, harga, stok);
                if (!listKeranjang.contains(keranjang)) {
                    listKeranjang.add(keranjang);
                } 
            }
            else {
                info.setContentText("Stok Kosong!");
                info.showAndWait();
            }
            tValue.clear();
        } else {
            info.showAndWait();
        }
    }

    @FXML
    void bPlusClick(MouseEvent event) {

        if(checkSelected(tableKeranjang)) {
            tableKeranjang.getItems().get(pilihanKeranjang).increment();
        }
        else {
            info.showAndWait();
        }
    }

    @FXML
    void bMinusClick(MouseEvent event) {

        if (checkSelected(tableKeranjang)) {
            tableKeranjang.getItems().get(pilihanKeranjang).decrement();
        }
        else {
            info.showAndWait();
        }
    }

    @FXML
    void bXClick(MouseEvent event) {

        if (checkSelected(tableKeranjang)) {
            tableKeranjang.getItems().remove(pilihanKeranjang);
        }
        else {
            info.showAndWait();
        }
    }

    @FXML
    void bKonfirmasiClick(MouseEvent event) {

        addPem();
        tabOnPem();
    }

    @FXML
    void keywordPrdChanged(KeyEvent event) {

        produk produk = new produk();
        produk.searchData(tableProduk, keywordPrd, typePrd.getValue());
    }

    @FXML
    void tabPem(Event event) {

        if (tabPem.isSelected()) {
            tabOnPem();
        } else {
            tabOnR();
        }
    }

    @FXML
    void tableKeranjangSelect(MouseEvent event) {
        pilihanKeranjang = tableKeranjang.getSelectionModel().getSelectedIndex();
    }

    private void addPem() {

        if (!tableKeranjang.getItems().isEmpty()) {
            info.setContentText("Penambahan gagal!");
            int total = 0;
            for (int i = 0;i < tableKeranjang.getItems().size();i++) {
                total += tableKeranjang.getItems().get(i).getSubTotal();
            }
            pembelian pembelian = new pembelian(0, akun.getKode(), total);
            int nextId = pembelian.getNextIdPembelian();
            if(!pembelian.writeData()) {
                info.showAndWait();
            };
        
            for (int i = 0;i < tableKeranjang.getItems().size();i++) {
                produk produk = new produk();
                keranjang keranjang = new keranjang();
                String nowKode = keranjang.barangToKode(tableKeranjang.getItems().get(i).getName());
                int nowStok = tableKeranjang.getItems().get(i).getStok() - tableKeranjang.getItems().get(i).getValue();
                if (nowStok < 0) {
                    info.showAndWait();
                    return;
                }

                tableKeranjang.getItems().get(i).setIdPembelian(nextId);
                if(tableKeranjang.getItems().get(i).writeData()) {
                    produk.updateStok(nowKode, nowStok);
                }
                else {
                    info.showAndWait();
                    return;
                };
            }

            info.setContentText("Penambahan berhasil!");
            info.showAndWait();
            tableKeranjang.getItems().clear();
        }
    }

    private void tabOnPem() {

        produk produk = new produk();
        produk.readData(tableProduk);
        keranjang keranjang = new keranjang();
        keranjang.readKeranjang(tableKeranjang, listKeranjang);
        info.setContentText("Mohon pilih terlebih dahulu!");
    }

    public void setTransition(Scene beforeScene) {
        this.beforeScene = beforeScene;
    }
}