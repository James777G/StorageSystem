package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.maven.apache.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuPageController implements Initializable {

    @FXML
    private JFXButton backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void onBackToLoginPage() throws IOException {
        //timeline.stop(); // stop searching per sec
        Stage stage = (Stage) backButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/logInPage.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}

