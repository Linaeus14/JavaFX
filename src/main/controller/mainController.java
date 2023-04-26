package main.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import main.model.strip;
import javafx.scene.control.TableView;

public class mainController {
    
    protected Scene beforeScene;
    protected strip akun;
    protected Alert info = new Alert(AlertType.INFORMATION), alert = new Alert(AlertType.CONFIRMATION);

    public mainController(strip akun) {
        this.akun = akun;
    }

    @FXML
    void bLogOutClick(Event e) {

        akun.setNama(null);
        akun.setKode(null);
        akun.setStatus(null);
        openBefore(e);
    }

    protected boolean checkSelected(TableView<?> table) {

        if (table.getSelectionModel().getSelectedItem() != null) {
            return true;
        } else {
            return false;
        }
    }

    protected final void openBefore(Event e) {

        auth auth = (auth) main.App.getLoader(beforeScene).getController();
        auth.initialize();
        main.App.setScene(e, beforeScene);
    }
}
