package main.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import main.model.strip;
import main.model.data.keranjang;
import main.model.data.pembelian;

public class pesananController extends mainController {

    @FXML
    protected TableView<ObservableList<String>> tablePembelian, tablePesanan;

    public pesananController(strip akun) {
        super(akun);
    }
    
    @FXML
    void tablePembelianSelect(MouseEvent event) {

        ObservableList<String> selectedList = tablePembelian.getSelectionModel().getSelectedItem();
        keranjang keranjang = new keranjang();
        keranjang.setIdPembelian(Integer.parseInt(selectedList.get(0)));
        keranjang.readData(tablePesanan);
    }

    protected void tabOnR() {

        pembelian pembelian = new pembelian();
        pembelian.setIdAkun(akun.getKode());
        pembelian.readData(tablePembelian);
    }
}
