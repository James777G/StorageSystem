package org.maven.apache.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.maven.apache.MyLauncher;
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
    private String getTotalItemNUmber(){
        // According to the itemtable,
        // sum = 3160 (10+20+30+50+100+40+45+45+150+150+200+70+100+500+1000+30+500+30+50+40=3160)
        int sum=0;
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        ItemService itemService = context.getBean("itemService", ItemService.class);
        List<Item> items = itemService.selectAll();
        for (Item item : items) {
            sum += item.getUnit();
        }
        return String.valueOf(sum);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(username);
        totalItemLabel.setText(this.getTotalItemNUmber());
    }

}
