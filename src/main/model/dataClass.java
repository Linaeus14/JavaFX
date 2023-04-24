package main.model;

import javafx.scene.control.TableView;
import main.model.interfaceModel.interfaceData;

@SuppressWarnings("rawtypes")
public class dataClass extends dynamicTable implements interfaceData {

    protected String writeCheckQuery;
    protected String writeMainQuery;
    protected String readQuery;  
    protected String updateQuery;
    protected String deleteQuery;
    
    @Override
    public boolean writeData() {

        conn.open();
        boolean result = conn.insert(writeCheckQuery, writeMainQuery);
        conn.close();

        return result;
    }

    @Override
    public void readData(TableView table) {
        readDB(table, readQuery);
    }

    @Override
    public void updateData() {

        conn.open();
        conn.update(updateQuery);
        conn.close();
    }

    @Override
    public void deleteData() {

        conn.open();
        conn.update(deleteQuery);
        conn.close();
    }
}
