package main.model.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.model.conn;
import main.model.dataClass;

public class keranjang extends dataClass {

    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty value = new SimpleIntegerProperty();
    private final IntegerProperty price = new SimpleIntegerProperty();
    private final IntegerProperty subTotal = new SimpleIntegerProperty();
    private final IntegerProperty stok = new SimpleIntegerProperty();
    private final IntegerProperty idPembelian = new SimpleIntegerProperty();

    public keranjang() {}

    public keranjang(String name, int value, int price, int stok) {

        setName(name);
        setValue(value);
        setPrice(price);
        setSubTotal(value * price);
        setStok(stok);
    }

    public String barangToKode(String barang) {

        String result = null;

        String query = "SELECT kode FROM barang WHERE nama ='" + barang + "'";

        conn.open();
        ResultSet rs = conn.select(query);
        try {
            rs.next();
            result = rs.getString(1);
        } catch (SQLException e) {}
        conn.close();

        return result;
    }

    private void setQuery() {

        this.writeCheckQuery = "SELECT * FROM pesanan WHERE idPesanan = ''";
        this.writeMainQuery = "INSERT INTO pesanan(idPesanan, idPembelian, kodeBarang, jumlah, subtotal) value('0', '" + getIdPembelian() + "', '" + barangToKode(getName()) + "', '" + getValue() + "', '" + getSubTotal()+ "')";
        this.readQuery = "SELECT b.nama, p.jumlah, p.subtotal FROM pesanan p JOIN barang b ON p.kodeBarang = b.kode WHERE p.idPembelian = '" + getIdPembelian() + "'";
    }

    public final StringProperty nameProperty() {
        return this.name;
    }

    public final String getName() {
        return this.nameProperty().get();
    }

    public final void setName(final String name) {

        this.nameProperty().set(name);
        setQuery();
    }

    public final IntegerProperty valueProperty() {
        return this.value;
    }

    public final int getValue() {
        return this.valueProperty().get();
    }

    public final void setValue(final int value) {

        this.valueProperty().set(value);
        setQuery();
    }

    public final IntegerProperty priceProperty() {
        return this.price;
    }

    public final int getPrice() {
        return this.priceProperty().get();
    }


    public final void setPrice(final int price) {

        this.priceProperty().set(price);
        setQuery();
    }

    public final IntegerProperty subTotalProperty() {
        return this.subTotal;
    }

    public final int getSubTotal() {
        return this.subTotalProperty().get();
    }

    public final void setSubTotal(final int subTotal) {

        this.subTotalProperty().set(subTotal);
        setQuery();
    }

    public final IntegerProperty stokProperty() {
        return this.stok;
    }

    public final int getStok() {
        return this.stokProperty().get();
    }

    public final void setStok(final int stok) {

        this.stokProperty().set(stok);
        setQuery();
    }

    public final IntegerProperty idPembelianProperty() {
        return this.idPembelian;
    }

    public final int getIdPembelian() {
        return this.idPembelianProperty().get();
    }

    public final void setIdPembelian(final int idPembelian) {

        this.idPembelianProperty().set(idPembelian);
        setQuery();
    }

    public void increment() {

        if (!(getValue() == getStok())) {
            setValue(getValue() + 1);
            setSubTotal(getValue() * getPrice());
        }
    }

    public void decrement() {
        if (getValue() > 1) {
            setValue(getValue() - 1);
            setSubTotal(getValue() * getPrice());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != keranjang.class) {
            return false ;
        }
        keranjang other = (keranjang) o ;
        return Objects.equals(getName(), other.getName())
                && getValue() == other.getValue()
                && getPrice() == other.getPrice()
                && getSubTotal() == other.getSubTotal()
                && getStok() == other.getStok();
    }
}
