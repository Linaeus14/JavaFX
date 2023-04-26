package main.model.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableView;
import main.model.conn;
import main.model.dataClass;

public class pembelian extends dataClass {

    private final IntegerProperty idPembelian = new SimpleIntegerProperty();
    private final StringProperty idAkun = new SimpleStringProperty();
    private final IntegerProperty total = new SimpleIntegerProperty();

    public pembelian() {}

    public pembelian(int idPembelian, String idAkun, int total) {

        setIdPembelian(idPembelian);
        setIdAkun(idAkun);
        setTotal(total);
    }

    public int getNextIdPembelian() {

        String query = "SELECT AUTO_INCREMENT " +
        "FROM information_schema.TABLES " +
        "WHERE TABLE_SCHEMA = database() " +
        "AND TABLE_NAME = 'pembelian'";

        conn.open();
        ResultSet rs =  conn.select(query);
        try {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
        return 0;
    }

    public void readProcessed(TableView<?> table) {

        this.readQuery = "SELECT idPembelian, total FROM pembelian WHERE status = 'diproses'";
        readData(table);
    }

    public void readDone(TableView<?> table) {

        this.readQuery = "SELECT idPembelian, total FROM pembelian WHERE status = 'selsai'";
        readData(table);
    }

    public void updateStatus(int idPembelian) {

        this.updateQuery = "UPDATE pembelian SET status = 'selsai' WHERE idPembelian = '" + idPembelian + "'";
        updateData();
    }

    private void setQuery() {

        this.writeCheckQuery = "SELECT * FROM pembelian WHERE idPembelian = '" + getIdPembelian() + "'";
        this.writeMainQuery = "INSERT INTO pembelian(idPembelian, userid, total, status) value('" + getIdPembelian() + "', '" + getIdAkun() + "', '" + getTotal() + "', 'diproses')";
        this.readQuery = "SELECT idPembelian, total, status FROM pembelian WHERE userid = '" + getIdAkun() + "'";
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

    public final StringProperty idAkunProperty() {
        return this.idAkun;
    }

    public final String getIdAkun() {
        return this.idAkunProperty().get();
    }

    public final void setIdAkun(final String idAkun) {

        this.idAkunProperty().set(idAkun);
        setQuery();
    }

    public final IntegerProperty totalProperty() {
        return this.total;
    }

    public final int getTotal() {
        return this.totalProperty().get();
    }

    public final void setTotal(final int total) {

        this.totalProperty().set(total);
        setQuery();
    }
}
