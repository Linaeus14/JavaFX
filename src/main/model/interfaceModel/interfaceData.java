package main.model.interfaceModel;

import javafx.scene.control.TableView;

public interface interfaceData {

    public boolean writeData();

    @SuppressWarnings("rawtypes")
    public void readData(TableView table);

    public void updateData();

    public void deleteData();
}
