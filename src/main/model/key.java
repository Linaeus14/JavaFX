package main.model;

import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class key {
    
    public static void focus(TextField textField) {
        Platform.runLater(() -> textField.requestFocus());
    }
    
    public static void focus(PasswordField passwordField) {
        Platform.runLater(() -> passwordField.requestFocus());
    }

    public static void enterPress(KeyEvent ke, TextField textField) {

        if (ke.getCode().equals(KeyCode.ENTER)) {
           focus(textField);;
        }
    }

    public static void enterPress(KeyEvent ke, PasswordField passwordField) {

        if (ke.getCode().equals(KeyCode.ENTER)) {
            focus(passwordField);;
        }
    }

    public static void numberInput(TextField textField) {
       
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static boolean empty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }
}
