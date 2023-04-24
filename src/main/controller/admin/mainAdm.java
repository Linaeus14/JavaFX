package main.controller.admin;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import main.model.strip;
import main.model.data.manager;
import main.model.data.staff;

public class mainAdm {

    private Scene beforeScene, staffAdmScene, managerAdmScene;
    private strip akun;
    Alert info = new Alert(AlertType.INFORMATION), alert = new Alert(AlertType.CONFIRMATION);

    @FXML
    private Button bHapusMan, bHapusStaff, bLogOut, bTambahMan, bTambahStaff, bUbahMan, bUbahStaff;
    @FXML
    private TextField keywordMan, keywordStaff, tNamaMan, tNamaStaff, tPassMan, tPassStaff, tUseridMan, tUseridStaff;
    @FXML
    private Label stripKode, stripNama, stripStatus;
    @FXML
    private Tab tabAdmin;
    @FXML
    private TableView<ObservableList<String>> tableMan, tableStaff;
    @FXML
    private ComboBox<String> typeMan, typeStaff;


    public mainAdm(strip akun) {
        this.akun = akun;
    }

    public void initialize() {

        alert.setTitle("Hapus");
        alert.setContentText("Yakin ingin hapus?");
        info.setTitle("Informasi");
        info.setContentText("Mohon pilih terlebih dahulu!");

        typeStaff.getItems().removeAll(typeStaff.getItems());
        typeStaff.getItems().addAll("Nama", "Userid");
        typeStaff.getSelectionModel().select("nama");

        typeMan.getItems().removeAll(typeMan.getItems());
        typeMan.getItems().addAll("Nama", "Userid");
        typeMan.getSelectionModel().select("Nama");

        akun.setStrip(stripNama, stripKode, stripStatus);

        tabOnStaff();
        tabOnManager();
    }

    @FXML
    void tableStaffSelect(MouseEvent event) {
        
        ObservableList<String> selectedList = tableStaff.getSelectionModel().getSelectedItem();
        tUseridStaff.setText(selectedList.get(0));
        tPassStaff.setText(selectedList.get(1));
        tNamaStaff.setText(selectedList.get(2));
    }
    
    @FXML
    void bTambahStaffClick(MouseEvent event) {
        openStaff(event);
    }

    @FXML
    void bUbahStaffClick(MouseEvent event) {

        staff staff = new staff(
            tUseridStaff.getText(), 
            tPassStaff.getText(), 
            tNamaStaff.getText(), 
            null, 
            "staff"
        );

        staff.updateData();
        clearStaff();
        staff.readData(tableStaff);
    }

    @FXML
    void bHapusStaffClick(MouseEvent event) {

        if (tableStaff.getSelectionModel().getSelectedItem() != null) {
            ObservableList<String> selectedList = tableStaff.getSelectionModel().getSelectedItem();
            staff staff = new staff(selectedList.get(0));

            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    staff.deleteData();
                    clearStaff();
                    staff.readData(tableStaff);
                }
            });
        } else {
            info.showAndWait();
        }
    }

    @FXML
    void keywordStaffChanged(KeyEvent event) {

        staff staff = new staff();
        staff.searchData(tableStaff, keywordStaff, typeStaff.getValue());
    }

    @FXML
    void tableManSelect(MouseEvent event) {

        ObservableList<String> selectedList = tableMan.getSelectionModel().getSelectedItem();
        tUseridMan.setText(selectedList.get(0));
        tPassMan.setText(selectedList.get(1));
        tNamaMan.setText(selectedList.get(2));
    }

    @FXML
    void bTambahManClick(MouseEvent event) {
        openMan(event);
    }

    @FXML
    void bUbahManClick(MouseEvent event) {

        manager manager = new manager(
            tUseridMan.getText(), 
            tPassMan.getText(), 
            tNamaMan.getText(), 
            null, 
            "manager"
        );

        manager.updateData();
        clearMan();
        manager.readData(tableMan);
    }

    @FXML
    void bHapusManClick(MouseEvent event) {
        
        if (tableMan.getSelectionModel().getSelectedItem() != null) {
            ObservableList<String> selectedList = tableMan.getSelectionModel().getSelectedItem();
            manager manager = new manager(selectedList.get(0));

            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    manager.deleteData();
                    clearMan();
                    manager.readData(tableMan);
                }
            });
        } else {
            info.showAndWait();
        }
    }

    @FXML
    void keywordManChanged(KeyEvent event) {

        manager manager = new manager();
        manager.searchData(tableMan, keywordMan, typeMan.getValue());
    }

    @FXML
    void tabChange(Event event) {

        if (tabAdmin.isSelected()) {
            tabOnStaff();
        } else {
            tabOnManager();
        }
    }

    @FXML
    void bLogOutClick(MouseEvent event) {

        akun.setNama(null);
        akun.setKode(null);
        akun.setStatus(null);
        openAuth(event);
    }

    private void tabOnStaff() {

        staff staff = new staff();
        staff.readData(tableStaff);
        Platform.runLater(()->keywordStaff.requestFocus());
    }

    private void tabOnManager() {

        manager manager = new manager();
        manager.readData(tableMan);
        Platform.runLater(()->keywordMan.requestFocus());
    }

    private void clearStaff() {

        tUseridStaff.clear();
        tNamaStaff.clear();
        tPassStaff.clear();
    }

    private void clearMan() {

        tUseridMan.clear();
        tNamaMan.clear();
        tPassMan.clear();
    }

    public void setTransition(Scene beforeScene, Scene staffAdmScene, Scene managerAdmScene) {

        this.beforeScene = beforeScene;
        this.staffAdmScene = staffAdmScene;
        this.managerAdmScene = managerAdmScene;
    }

    private final void openAuth(Event e) {

        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(beforeScene);
    }
    
    private final void openStaff(Event e) {

        inputAdm inputAdm = (inputAdm) main.App.getLoader(staffAdmScene).getController();
        inputAdm.initialize();
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(staffAdmScene);
    }

    private final void openMan(Event e) {

        inputAdm inputAdm = (inputAdm) main.App.getLoader(managerAdmScene).getController();
        inputAdm.initialize();
        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(managerAdmScene);
    }
}