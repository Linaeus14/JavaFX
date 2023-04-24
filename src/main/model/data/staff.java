package main.model.data;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.model.abstractModel.akun;

@SuppressWarnings("rawtypes")
public class staff extends akun {
    
    public staff() {

        super();
        setQuery();
    }

    public staff(String userid) {
        
        super(userid);
        setQuery();
    }

    public staff(String userid, String pass, String nama, String email, String status) {

        super(userid, pass, nama, email, status);
        setQuery();
    }

    public void searchData(TableView table, TextField text, String type) {

        String query = "SELECT userid, pass, nama FROM akun WHERE " + type + " like '%" + text.getText() + "%' AND status = 'staff'";
        readDB(table, query);
    }

    private void setQuery() {

        this.writeCheckQuery = "SELECT * FROM akun WHERE userid = '" + this.userid + "'";
        this.writeMainQuery = "INSERT INTO akun(userid, pass, nama, email, status) value('" + this.userid + "', '" + this.pass + "', '" + this.nama + "', '" + this.email + "', '" + this.status + "')";
        this.readQuery = "SELECT userid, pass, nama FROM akun WHERE status = 'staff'";
        this.updateQuery = "UPDATE akun SET pass = '" + this.pass + "', nama = '" + this.nama + "', email = '" + this.email + "', status = '" + this.status + "'  WHERE userid = '" + this.userid + "'";
        this.deleteQuery = "DELETE FROM akun WHERE userid = '" + this.userid + "'";
    }

    public void setUserid(String userid) {

        this.userid = userid;
        setQuery();
    }

    public void setPass(String pass) {

        this.pass = pass;
        setQuery();
    }

    public void setNama(String nama) {

        this.nama = nama;
        setQuery();
    }

    public void setEmail(String email) {

        this.email = email;
        setQuery();
    }

    public void setStatus(String status) {

        this.status = status;
        setQuery();
    }
}
