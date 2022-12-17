package org.maven.apache.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.maven.apache.item.Item;
import org.maven.apache.mapper.ItemMapper;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.service.item.ItemServiceProvider;
import org.maven.apache.spring.SpringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class appPageController implements Initializable {
    @FXML
    private Label totalItemLabel;
    @FXML
    private Label totalRestockLabel1;
    @FXML
    private Label totalRestockLabel2;
    @FXML
    private Label totalShipmentLabel1;
    @FXML
    private Label totalShipmentLabel2;
    @FXML
    private Label usernameLabel;
    @FXML
    private ImageView userPhotoImage;
    @FXML
    private ImageView warehousePageShiftImage;
    @FXML
    private ImageView transactionPageShiftImage;
    @FXML
    private Button logOffButton1;
    @FXML
    private Button logOffButton11;
    @FXML
    private Button logOffButton12;
    /*
    * The FXML attributes under this line are only used for testing
    *
    * the variables declared under this line represents some user information passed from LogInPage
    * !!! ATTENTION: those variables need to be replaced
    * */
    @FXML
    private  Button testButton1;
    private String username = "Anthony Feng";
    private int getTotalItemNUmber(){

        return 0;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(username);

    }

}
