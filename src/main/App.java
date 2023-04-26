package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.controller.auth;
import main.controller.admin.mainAdm;
import main.controller.admin.managerAdm;
import main.controller.admin.staffAdm;
import main.controller.pembeli.mainPem;
import main.controller.staff.mainStaff;
import main.controller.staff.prdnStaff;
import main.controller.staff.prdStaff;
import main.model.strip;

public class App extends Application {

    //Controller Factory
    private strip model = new strip();
    private final Callback<Class<?>, Object> controllerFactory = type -> {
        if (type == auth.class) {
            return new auth(model);
        } else if (type == mainAdm.class) {
            return new mainAdm(model);
        } else if (type == staffAdm.class) {
            return new staffAdm();
        } else if (type == managerAdm.class) {
            return new managerAdm();
        } else if (type == mainStaff.class) {
            return new mainStaff(model);
        } else if (type == prdnStaff.class) {
            return new prdnStaff();
        } else if (type == prdStaff.class) {
            return new prdStaff();
        } else if (type == mainPem.class) {
            return new mainPem(model);
        } else {
            try {
                return type.getDeclaredConstructor().newInstance() ; // default behavior - invoke no-arg construtor
            } catch (Exception exc) {
                System.err.println("Could not create controller for "+type.getName());
                throw new RuntimeException(exc);
            }
        }
    };

    @Override
    public void start(Stage primaryStage) throws Exception{

        // SCENE

        //Autentikasi
        Scene authScene = buildScene("view/Auth.fxml");

        //Admin
        Scene admMainScene = buildScene("view/admin/mainAdm.fxml");
        Scene staffAdmScene = buildScene("view/admin/staffAdm.fxml");
        Scene managerAdmScene = buildScene("view/admin/managerAdm.fxml");

        //Staff
        Scene staffMainScene = buildScene("view/staff/mainStaff.fxml");
        Scene prdnScene = buildScene("view/staff/prdnStaff.fxml");
        Scene prdScene = buildScene("view/staff/prdStaff.fxml");

        //Pembeli
        Scene pemMainScene = buildScene("view/pembeli/mainPem.fxml");
      
        // TRANSISI

        //Autentikasi
        auth auth = (auth) getLoader(authScene).getController();
        auth.setTransition(staffMainScene, admMainScene, pemMainScene);

        //Admin
        mainAdm admMain = (mainAdm) getLoader(admMainScene).getController();
        admMain.setTransition(authScene, staffAdmScene, managerAdmScene);

        staffAdm admStaff = (staffAdm) getLoader(staffAdmScene).getController();
        admStaff.setTransition(admMainScene);

        managerAdm admMan = (managerAdm) getLoader(managerAdmScene).getController();
        admMan.setTransition(admMainScene);
        
        //Staff
        mainStaff staffMain = (mainStaff) getLoader(staffMainScene).getController();
        staffMain.setTransition(authScene, prdnScene, prdScene);

        prdnStaff staffPrdn = (prdnStaff) getLoader(prdnScene).getController();
        staffPrdn.setTransition(staffMainScene);

        prdStaff staffPrd = (prdStaff) getLoader(prdScene).getController();
        staffPrd.setTransition(staffMainScene);

        //Pembeli
        mainPem pemMain = (mainPem) getLoader(pemMainScene).getController();
        pemMain.setTransition(authScene);

        primaryStage.setScene(authScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private final Scene buildScene(String path) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        loader.setControllerFactory(controllerFactory);
        Parent pane = loader.load();
        Scene scene = new Scene(pane);
        scene.setUserData(loader);
        return scene;
    }

    public final static FXMLLoader getLoader(Scene scene) {
        
        FXMLLoader loader = (FXMLLoader) scene.getUserData();
        return loader;
    }

    public final static void setScene(Event e, Scene scene) {

        Stage primaryStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        primaryStage.setScene(scene);
    }
}