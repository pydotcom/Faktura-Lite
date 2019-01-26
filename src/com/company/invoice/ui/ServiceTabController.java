package com.company.invoice.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class ServiceTabController {

    @FXML
    private BorderPane serviceBorderPane;

    public void initialize() {
        System.out.println("Test service");
    }

    @FXML
    public void showNewServiceDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(serviceBorderPane.getScene().getWindow());
        dialog.setTitle("Towar/Usługa");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("serviceDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch(IOException e) {
            System.out.println("Couldn't load the dialog");
            e.getStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            ServiceController serviceController = fxmlLoader.getController();
            //TODO here we need add code take values from ServiceController and sava them in db
            System.out.println("Service or ware saved in database");
        }
    }

}
