package main.model.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.model.conn;
import main.model.dataClass;

@SuppressWarnings("rawtypes")
public class produk extends dataClass {

    private String kode;
    private String nama;
    private String kodep;
    private String harga;
    private String stok;

    public produk() {
        setQuery();
    }

    public produk(String kode) {

        this.kode = kode;
        setQuery();
    }

    public produk(String kode, String nama, String kodep, String harga, String stok) {

        this.kode = kode;
        this.nama = nama;
        this.kodep = kodep;
        this.harga = harga;
        this.stok = stok;
        setQuery();
    }

    public void searchData(TableView table, TextField text, String type) {

        String query = "SELECT b.kode, b.nama, p.produsen, b.harga, b.stok FROM barang b JOIN produsen p ON b.kodeP = p.kodeP " + "WHERE " + type + " like '%" + text.getText() + "%'";
        readDB(table, query);
    }

    public void fillComboBox(ComboBox<String> comboBox) {

        String query = "SELECT produsen FROM produsen";
        
        conn.open();
        ResultSet rs = conn.select(query);
        try {
            comboBox.getItems().removeAll(comboBox.getItems());
            while (rs.next()) {
                comboBox.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }

    public String produsenToKodeP(String produsen) {

        String result = null;

        String query = "SELECT kodep FROM produsen WHERE produsen ='" + produsen + "'";

        conn.open();
        ResultSet rs = conn.select(query);
        try {
            rs.next();
            result = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();

        return result;
    }

    private void setQuery() {

        if(this.harga != null & this.stok != null){
            this.updateQuery = "UPDATE barang SET nama = '" + this.nama + "', kodep = '" + produsenToKodeP(this.kodep) + "', harga = '" + Integer.parseInt(this.harga) + "', stok = '" + Integer.parseInt(this.stok) + "' WHERE kode = '" + this.kode + "'";
            this.writeMainQuery = "INSERT INTO barang(kode, nama, kodep, harga, stok) value('" + this.kode + "', '" + this.nama + "', '" + produsenToKodeP(this.kodep) + "', '" + Integer.parseInt(this.harga) + "', '" + Integer.parseInt(this.stok) + "')";
        }
        this.writeCheckQuery = "SELECT * FROM barang WHERE kode = '" + this.kode + "'";
        this.readQuery = "SELECT b.kode, b.nama, p.produsen, b.harga, b.stok FROM barang b JOIN produsen p ON b.kodeP = p.kodeP";
        this.deleteQuery = "DELETE FROM barang WHERE kode = '" + this.kode + "'";
    }

    public String getKode() {
        return this.kode;
    }

    public void setKode(String kode) {

        this.kode = kode;
        setQuery();
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {

        this.nama = nama;
        setQuery();
    }

    public String getkodep() {
        return this.kodep;
    }

    public void setkodep(String kodep) {

        this.kodep = kodep;
        setQuery();
    }

    public String getharga() {
        return this.harga;
    }

    public void setharga(String harga) {

        this.harga = harga;
        setQuery();
    }

    public String getStok() {
        return this.stok;
    }

    public void setStok(String stok) {

        this.stok = stok;
        setQuery();
    }
}
