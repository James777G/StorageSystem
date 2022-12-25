package org.maven.apache.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.maven.apache.utils.DataUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class userPageController implements Initializable {

    @FXML
    private VBox vbox;
    @FXML
    private void enterExtendedPage(){
        DataUtils.isEnterExtended = true;
    }

    @FXML
    private void exitExtendedPage(){
        DataUtils.isEnterExtended = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
