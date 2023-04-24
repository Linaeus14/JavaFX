package main.model.data;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.model.dataClass;

@SuppressWarnings("rawtypes")
public class produsen extends dataClass {

    private String kode;
    private String produsen;
    private String alamat;
    private String kontak;

    public produsen() {
        setQuery();
    }

    public produsen(String kode) {

        this.kode = kode;
        setQuery();
    }
        
    public produsen(String kode, String produsen, String alamat, String kontak) {
        
        this.kode = kode;
        this.produsen = produsen;
        this.alamat = alamat;
        this.kontak = kontak;
        setQuery();
    }

    public void searchData(TableView table, TextField text, String type) {

        String query = "SELECT * FROM produsen WHERE " + type + " like '%" + text.getText() + "%'";
        readDB(table, query);
    }

    private void setQuery() {

        this.writeCheckQuery = "SELECT * FROM produsen WHERE kodep = '" + this.kode + "'";
        this.writeMainQuery = "INSERT INTO produsen(kodep, produsen, alamat, kontak) value('" + this.kode + "', '" + this.produsen + "', '" + this.alamat + "', '" + this.kontak + "')";
        this.readQuery = "SELECT * FROM produsen";
        this.updateQuery = "UPDATE produsen SET produsen = '" + this.produsen + "', alamat = '" + this.alamat + "', kontak = '" + this.kontak + "' WHERE kodep = '" + this.kode + "'";
        this.deleteQuery = "DELETE FROM produsen WHERE kodep = '" + this.kode + "'";
    }

    public String getKode() {
        return this.kode;
    }

    public void setKode(String kode) {

        this.kode = kode;
        setQuery();
    }

    public String getProdusen() {
        return this.produsen;
    }

    public void setProdusen(String produsen) {

        this.produsen = produsen;
        setQuery();
    }

    public String getAlamat() {
        return this.alamat;
    }

    public void setAlamat(String alamat) {

        this.alamat = alamat;
        setQuery();
    }

    public String getKontak() {
        return this.kontak;
    }

    public void setKontak(String kontak) {

        this.kontak = kontak;
        setQuery();
    }
}
