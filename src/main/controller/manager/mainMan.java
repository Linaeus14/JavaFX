package main.controller.manager;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import main.controller.pesananController;
import main.model.strip;
import main.model.data.pembelian;

public class mainMan extends pesananController {
    
    @FXML
    private TextField tTotal;
    
    public void initialize() {
        
        akun.setStrip(stripNama, stripKode, stripStatus);
        tabOnR();
    }

    public mainMan(strip akun) {
        super(akun);
    }
    
    @Override
    protected void tabOnR() {

        pembelian pembelian = new pembelian();
        pembelian.readDone(tablePembelian);

        int total = 0;
            for (int i = 0;i < tablePembelian.getItems().size();i++) {
                total += Integer.parseInt(tablePembelian.getItems().get(i).get(1));
            }
        tTotal.setText(Integer.toString(total));
    }

    public void setTransition(Scene beforeScene) {
        this.beforeScene = beforeScene;
    }
}