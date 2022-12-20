package org.maven.apache.controllers;

import io.github.palexdev.materialfx.effects.ripple.MFXCircleRippleGenerator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.maven.apache.MyLauncher;
import org.maven.apache.item.Item;
import org.maven.apache.service.item.ItemService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.RotationUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class appPageController implements Initializable {
    User user = DataUtils.currentUser;
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
    private ImageView refreshImage;
    @FXML
    private Button logOffButton1;
    @FXML
    private Button logOffButton11;
    @FXML
    private Button logOffButton12;
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private Pane warehouseButton;

    @FXML
    private NumberAxis numberAxis;

    @FXML
    private CategoryAxis categoryAxis;
    /*
    * The FXML attributes under this line are only used for testing
    *
    * the variables declared under this line represents some user information passed from LogInPage
    * !!! ATTENTION: those variables need to be replaced
    * */
    String refreshName="James Gong";
    int refreshItemNumber = 1314;
    @FXML
    private  Button testButton1;

    @FXML
    private void refreshPage(){

        RotateTransition rotate = RotationUtils.getRotationTransitionFromBy(refreshImage,500,0, RotationUtils.Direction.COUNTERCLOCKWISE,360);
        rotate.play();
        usernameLabel.setText(refreshName);//Test only
        //usernameLabel.setText(user.getName());
        totalItemLabel.setText(String.valueOf(refreshItemNumber)); //Test only
        // totalItemLabel.setText(String.valueOf(this.getTotalItemNumber()));
    }
    @FXML
    private void enterRefreshImage(){
        refreshImage.setScaleX(1.2);
        refreshImage.setScaleY(1.2);
    }
    @FXML
    private void exitRefreshImage(){
        refreshImage.setScaleX(1);
        refreshImage.setScaleY(1);
    }
    private int getTotalItemNumber(){
        // According to the itemtable,
        // sum = 3160 (10+20+30+50+100+40+45+45+150+150+200+70+100+500+1000+30+500+30+50+40=3160)
        int sum=0;
        ItemService itemService = MyLauncher.context.getBean("itemService", ItemService.class);
        List<Item> items = itemService.selectAll();
        for (Item item : items) {
            sum += item.getUnit();
        }
        return sum;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String name = "Anthony Feng";
        usernameLabel.setText(name);//Test only
        /* This is the code in the final version but the current user in the Utils class has not been set
         * so set up string name instead.
         *
         *usernameLabel.setText(user.getName());
         */
        totalItemLabel.setText(String.valueOf(this.getTotalItemNumber()));

        XYChart.Series S = new XYChart.Series<>();
        S.setName("Color01");

        S.getData().add(new XYChart.Data<>("1", 8));
        S.getData().add(new XYChart.Data<>("2", 4));
        S.getData().add(new XYChart.Data<>("3", 16));
        S.getData().add(new XYChart.Data<>("4", 32));
        S.getData().add(new XYChart.Data<>("5", 50));
        S.getData().add(new XYChart.Data<>("6", 2));
        S.getData().add(new XYChart.Data<>("7", 17));


        XYChart.Series S1 = new XYChart.Series<>();
        S1.setName("Color02");

        S1.getData().add(new XYChart.Data<>("1", 18));
        S1.getData().add(new XYChart.Data<>("2", 34));
        S1.getData().add(new XYChart.Data<>("3", 1));
        S1.getData().add(new XYChart.Data<>("4", 2));
        S1.getData().add(new XYChart.Data<>("5", 20));
        S1.getData().add(new XYChart.Data<>("6", 29));
        S1.getData().add(new XYChart.Data<>("7", 14));

        XYChart.Series S2 = new XYChart.Series<>();
        S2.setName("Color03");

        S2.getData().add(new XYChart.Data<>("1", 58));
        S2.getData().add(new XYChart.Data<>("2", 42));
        S2.getData().add(new XYChart.Data<>("3", 6));
        S2.getData().add(new XYChart.Data<>("4", 39));
        S2.getData().add(new XYChart.Data<>("5", 50));
        S2.getData().add(new XYChart.Data<>("6", 12));
        S2.getData().add(new XYChart.Data<>("7", 17));

        lineChart.getData().addAll(S, S1, S2);
    }

}
