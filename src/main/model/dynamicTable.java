package main.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import main.model.data.keranjang;

public class dynamicTable {
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected final void readDB(TableView table, String query) {
    //This code will trigger type safety warning due to how the cellValueFactory works
        
        ObservableList<ObservableList<?>> data;
        data = FXCollections.observableArrayList();

        conn.open();
        ResultSet rs = conn.select(query);

        try {
            table.getItems().clear();
            table.getColumns().clear();
            //Clear the table first

            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                // We are using non property style for making dynamic table

                final int j = i;
                Callback cb = new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                };
                //This is a cellValueFactory to be generated and set to each column

                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(cb);
                table.getColumns().addAll(col);
                //Set each column header in the database to the table
            }
            //Set table header

            while (rs.next()) {
                // Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    // Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
                //Add the row to a temporary observableList to be listed
            }
            //Set the whole data into an observableList

            table.setItems(data);
            //Finaly add the data to the table
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }

    public final void readKeranjang(TableView<keranjang> table, ObservableList<keranjang> listkeranjang) {

        table.getItems().clear();
        table.getColumns().clear();

        TableColumn<keranjang, String> nameCol = new TableColumn<>("Barang");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<keranjang, Integer> valueCol = new TableColumn<>("Jumlah");
        valueCol.setCellValueFactory(cellData -> cellData.getValue().valueProperty().asObject());
        TableColumn<keranjang, Integer> subTotalCol = new TableColumn<>("Total");
        subTotalCol.setCellValueFactory(cellData -> cellData.getValue().subTotalProperty().asObject());

        table.getColumns().add(nameCol);
        table.getColumns().add(valueCol);
        table.getColumns().add(subTotalCol);

        table.setItems(listkeranjang);
    }
}
