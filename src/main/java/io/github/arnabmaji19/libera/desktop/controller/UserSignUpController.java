package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class UserSignUpController {

    @FXML
    private ImageView loadingImageView;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;

    private AlertDialog alertDialog;

    @FXML
    private void signUp() {
        /*
         * Create New User account
         */

        // get all form parameters
        var firstName = firstNameTextField.getText();
        var lastName = lastNameTextField.getText();
        var email = emailTextField.getText();
        var password = passwordField.getText();
        var phone = phoneTextField.getText();
        var address = addressTextField.getText();

        // validate form parameters
        var errorMessage = Validations
                .validateFormParameters(firstName, lastName, email, password, phone, address);
        if (!errorMessage.isBlank()) {  // show error message if any
            if (alertDialog == null) alertDialog = new AlertDialog();
            alertDialog.show(errorMessage);
            return;
        }

        loadingImageView.setVisible(true);
    }
}
