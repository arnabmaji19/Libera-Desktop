package io.github.arnabmaji19.libera.desktop.controller;

import io.github.arnabmaji19.libera.desktop.datasource.UserRequest;
import io.github.arnabmaji19.libera.desktop.util.AlertDialog;
import io.github.arnabmaji19.libera.desktop.util.Validations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class IssueBooksController implements Initializable {

    @FXML
    private TextField emailTextField;
    @FXML
    private ImageView fetchUserLoadingAnimation;
    @FXML
    private VBox userDetailsForm;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private VBox addHoldingForm;
    @FXML
    private TextField holdingNumberTextField;
    @FXML
    private ListView<Integer> addedHoldingsListView;
    @FXML
    private ImageView checkoutLoadingAnimation;

    private AlertDialog alertDialog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.alertDialog = new AlertDialog();
    }

    @FXML
    private void addHoldingToList() {
        /*
         * Add a holding to the cart
         */
        var holdingNumber = holdingNumberTextField.getText();

        if (!Validations.isNumber(holdingNumber)) {
            alertDialog.show("Holding Number must be an Integer!");
            return;
        }
        addedHoldingsListView.getItems().add(Integer.parseInt(holdingNumber));
        holdingNumberTextField.clear();
    }

    @FXML
    private void fetchUser() {
        /*
         * Fetch user by email and show user details in the form
         */

        var email = emailTextField.getText();
        if (email.isBlank()) {
            alertDialog.show("Email can't be Empty!");
            return;
        }

        fetchUserLoadingAnimation.setVisible(true);  // show the loading animation

        // Make http request to fetch use by email
        UserRequest
                .getInstance()
                .getByEmail(email)
                .thenAcceptAsync(user -> Platform.runLater(() -> {

                    fetchUserLoadingAnimation.setVisible(false);  // hide the loading image
                    // if user is not found show error message
                    if (user == null) {
                        alertDialog.show("Email not registered with us!");
                        return;
                    }

                    // otherwise user fields in the form
                    firstNameTextField.setText(user.getFirstName());
                    lastNameTextField.setText(user.getLastName());
                    phoneTextField.setText(user.getPhone());
                    addressTextField.setText(user.getAddress());

                    userDetailsForm.setVisible(true);  // show user details form
                    addHoldingForm.setVisible(true);  // show add holding form


                }));


    }
}
