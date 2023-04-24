package main.controller.admin;

import javafx.event.Event;
import javafx.fxml.FXML;
import main.model.data.manager;

public class managerAdm extends inputAdm {

    @FXML
    void bTambahClick(Event event) {

        if (check()) {
            info.showAndWait();
        } else {
                manager manager = new manager(
                tUserid.getText(),
                tPass.getText(),
                tNama.getText(), 
                null,
                "manager"
            );

            if (!manager.writeData()) {
                info.setContentText("kode sudah ada!");
                info.showAndWait();
            } else {
                openMainAdm(event);
            }
        }
    }
}
