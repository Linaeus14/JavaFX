package main.controller;

import java.sql.Statement;
import java.sql.ResultSet;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import main.model.strip;
import main.model.conn;

public class auth {

    private Scene mainStaffScene, mainAdminScene;
    private final strip akun;

    @FXML
    private Tab logTab, regTab;
    @FXML
    private TextField tUserLog,  tUserReg, tEmailReg;
    @FXML
    private PasswordField tPassLog, tPassReg;
    @FXML
    private Button bLog,  bReg;

    public auth(strip akun) {
        this.akun = akun;
    }

    public void initialize() {}

    @FXML
    void authTab(Event e) {
        foucs();
    } 

    @FXML
    void bLogClick(Event e) {
        validate(e);
    }

    @FXML
    void bRegClick(Event e) {

    }

    @FXML
    void tUserLogEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            Platform.runLater(()->tPassLog.requestFocus());
        }
    }
    @FXML
    void tPassLogEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            validate(ke);;
        }
    }

    public strip getAkun() {
        return akun;
    }
    
    private void validate(Event e) {
        try {

            conn.open();
            Statement stmt = conn.getCon().createStatement();
            ResultSet rs = stmt.executeQuery("select * from akun where userid = '" + tUserLog.getText() +"' and pass = '" + tPassLog.getText() + "'");

            if ( rs.next() == true) {

                final String nama = rs.getString("nama");
                final String userid = rs.getString("userid");
                final String status = rs.getString("status");
                conn.close();

                akun.setNama(nama);
                akun.setKode(userid);
                akun.setStatus(status);
                
                if(status.equals("admin")) {
                    tPassLog.clear();
                    openMainAdmin(e);
                }
                else if(status.equals("manager")) {
                    
                }
                else if(status.equals("staff")) {
                    tPassLog.clear();
                    openMainStaff(e);
                }
                else{
             
                }
            }
            else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Informasi");
                alert.setContentText("username atau password salah!");
                alert.showAndWait();
                foucs();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void foucs() {
        if (logTab.isSelected()) {
            Platform.runLater(()->tUserLog.requestFocus());
        }   else {
            Platform.runLater(()->tUserReg.requestFocus());
        }
    }

    public void setTransition(Scene mainStaffScene, Scene mainAdminScene) {

        this.mainStaffScene = mainStaffScene;
        this.mainAdminScene = mainAdminScene;
    }
    
    private final void openMainStaff(Event e) {

        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(mainStaffScene);
    }

    private final void openMainAdmin(Event e) {
        
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(mainAdminScene);
    }
}