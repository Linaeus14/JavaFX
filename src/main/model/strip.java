package main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class strip {

    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty kode = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public void setStrip(Label stripNama, Label stripKode, Label stripStatus) {

        try {
            namaProperty().addListener((obs, oldText, newText) -> stripNama.setText(newText));
            kodeProperty().addListener((obs, oldText, newText) -> stripKode.setText(newText));
            statusProperty().addListener((obs, oldText, newText) -> stripStatus.setText(newText));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public StringProperty kodeProperty() {
        return kode;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getNama() {
        return namaProperty().get();
    }

    public final String getKode() {
        return kodeProperty().get();
    }

    public final String getStatus() {
        return statusProperty().get();
    }

    public final void setNama(String nama) {
        namaProperty().set(nama);
    }

    public final void setKode(String kode) {
        kodeProperty().set(kode);
    }

    public final void setStatus(String status) {
        statusProperty().set(status);
    }
}