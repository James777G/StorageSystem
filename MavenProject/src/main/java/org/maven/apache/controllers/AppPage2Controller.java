package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.RotationUtils;
import org.maven.apache.utils.ScaleUtils;
import org.maven.apache.utils.ThreadUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppPage2Controller implements Initializable {

    @FXML
    private JFXButton warehouseButton;

    @FXML
    private JFXButton messageButton;

    @FXML
    private JFXButton staffButton;

    @FXML
    private JFXButton transactionButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView refreshImage;

    @FXML
    private ImageView extendArrow;

    @FXML
    private MFXTextField searchField;

    @FXML
    private VBox searchTable;

    @FXML
    private JFXButton buttonOne;

    @FXML
    private JFXButton buttonTwo;

    @FXML
    private JFXButton buttonThree;

    @FXML
    private JFXButton buttonFour;

    @FXML
    private JFXButton buttonFive;

    @FXML
    private JFXDrawer VBoxDrawer;

    @FXML
    private AnchorPane appPagePane;

	@FXML
	private Label cargoNameLabel01;

	@FXML
	private Label cargoNameLabel02;

	@FXML
	private Label cargoNameLabel03;

	@FXML
	private Label cargoNameLabel04;

	@FXML
	private Label cargoAmountLabel01;

	@FXML
	private Label cargoAmountLabel02;

	@FXML
	private Label cargoAmountLabel03;

	@FXML
	private Label cargoAmountLabel04;

	@FXML
	private Label staffNameLabel01;

	@FXML
	private Label staffNameLabel02;

	@FXML
	private Label staffNameLabel03;

	@FXML
	private Label staffNameLabel04;

    @FXML
    private StackPane stackPane;

	//pass the user from login page
    private final User user = DataUtils.currentUser;

    private boolean isTriangleRotating = false;

    private final JFXButton[] buttonList = new JFXButton[5];

    private boolean isRotating = false;

    private final Timeline timeline = new Timeline();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(user.getName());
        warehouseButton.setOpacity(0);
        staffButton.setOpacity(0);
        transactionButton.setOpacity(0);
        messageButton.setOpacity(0);
        searchTable.setPickOnBounds(false);
        searchTable.setOpacity(0);
        setButtonList();
        setTransactionPane();
        stackPane.setPickOnBounds(false);
        stackPane.setOpacity(0);
        stackPane.setVisible(false);
        // initialize search per sec when search field is chosen
        searchField.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                searchOnBackgroundPerSec();
                searchTable.setOpacity(1);
                searchTable.setPickOnBounds(true);
            }else{
                timeline.stop();
                searchTable.setOpacity(0);
                searchTable.setPickOnBounds(false);
            }
        });
        // load the menu VBox to drawer
        setDrawer();
    }

    @FXML
    private void onClickSearch(){
        searchOnBackgroundPerSec();
        searchTable.setOpacity(1);
        searchTable.setPickOnBounds(true);
    }

    @FXML
    private void onClickSearchBar(){
        searchOnBackgroundPerSec();
        searchTable.setOpacity(1);
        searchTable.setPickOnBounds(true);
    }

    private void setTransactionPane(){
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/transactionPage.fxml")));
            stackPane.getChildren().add(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setDrawer(){
        try {
            VBox vbox = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/menuPage.fxml")));
            vbox.setOnMouseExited(event -> {
                RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,-90, 0);
                rotate.setOnFinished(event1 -> isTriangleRotating = false);
                if(!isTriangleRotating){
                    isTriangleRotating = true;
                    rotate.play();
                }
                VBoxDrawer.close();
            });
            VBoxDrawer.setSidePane(vbox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onEnterAppPage(){
        appPagePane.setOpacity(1);
        appPagePane.setPickOnBounds(true);
        appPagePane.setVisible(true);
        stackPane.setOpacity(1);
        stackPane.setPickOnBounds(false);
        stackPane.setVisible(false);
    }
    private void setButtonList(){
        buttonOne.setAlignment(Pos.CENTER_LEFT);
        buttonTwo.setAlignment(Pos.CENTER_LEFT);
        buttonThree.setAlignment(Pos.CENTER_LEFT);
        buttonFour.setAlignment(Pos.CENTER_LEFT);
        buttonFive.setAlignment(Pos.CENTER_LEFT);
        buttonList[0] = buttonOne;
        buttonList[1] = buttonTwo;
        buttonList[2] = buttonThree;
        buttonList[3] = buttonFour;
        buttonList[4] = buttonFive;
    }

    @FXML
    private void onClickExtend(){
        RotateTransition rotate;
        if (VBoxDrawer.isOpened()){
            rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,-90, 0);
        }else{
            rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,0, -90);
        }
        rotate.setOnFinished(event -> isTriangleRotating = false);
        if(!isTriangleRotating){
            isTriangleRotating = true;
            rotate.play();
        }
        if(VBoxDrawer.isOpened()){
            VBoxDrawer.close();
        }else{
            VBoxDrawer.open();
        }
    }

    @FXML
    private void onClickAppPagePane(){
        timeline.stop();
        searchTable.setOpacity(0);
        searchTable.setPickOnBounds(false);
    }

    @FXML
    private void onEnterExtend(){
        if(VBoxDrawer.isClosed()){
            VBoxDrawer.open();
            RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendArrow,300,0, -90);
            rotate.setOnFinished(event -> isTriangleRotating = false);
            if(!isTriangleRotating){
                isTriangleRotating = true;
                rotate.play();
            }

        }
    }

	/**
	 * perform fuzzy search and show the list of relevant cargos in background per
	 * sec
	 */
	private void searchOnBackgroundPerSec() {
		KeyFrame keyFrame = ThreadUtils.generateSearchKeyFrame(buttonList, searchField);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyFrame);
		timeline.playFromStart();
	}

	@FXML
	private void enterWarehouseButton() {
		warehouseButton.setOpacity(1);
	}

	@FXML
	private void exitWarehouseButton() {
		warehouseButton.setOpacity(0);
	}

	@FXML
	private void enterStaffButton() {
		staffButton.setOpacity(1);
	}

	@FXML
	private void exitStaffButton() {
		staffButton.setOpacity(0);
	}

	@FXML
	private void enterTransactionButton() {
		transactionButton.setOpacity(1);
	}

	@FXML
	private void exitTransactionButton() {
		transactionButton.setOpacity(0);
	}

	@FXML
	private void enterMessageButton() {
		messageButton.setOpacity(1);
	}

	@FXML
	private void exitMessageButton() {
		messageButton.setOpacity(0);
	}

	@FXML
	private void refreshPage() {
		RotateTransition rotate = RotationUtils.getRotationTransitionFromBy(refreshImage, 500, 0,
				RotationUtils.Direction.COUNTERCLOCKWISE, 360);
        rotate.setOnFinished(event -> isRotating = false);
        if(!isRotating){
            isRotating = true;
            rotate.play();
        }
	}

	@FXML
	private void enterRefreshImage() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(refreshImage,200,1.2);
        scaleTransition.play();
	}

	@FXML
	private void exitRefreshImage() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionBy(refreshImage,200,1);
        scaleTransition.play();
	}

    @FXML
    private void onTransactionPage() {
        appPagePane.setPickOnBounds(false);
        appPagePane.setOpacity(0);
        appPagePane.setVisible(false);
        stackPane.setPickOnBounds(true);
        stackPane.setOpacity(1);
        stackPane.setVisible(true);
    }
}
