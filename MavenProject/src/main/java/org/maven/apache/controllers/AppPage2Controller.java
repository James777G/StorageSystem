package org.maven.apache.controllers;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.maven.apache.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.RotationUtils;


public class AppPage2Controller implements Initializable {

	private User user = DataUtils.currentUser;

	private boolean isEnterExtend = false;

	private  boolean isEnterExtended = true;
	private boolean isArrowDown = false;

	@FXML
	private VBox vbox ;

	@FXML
	private Button testButton;

	@FXML
	private Button backButton;
	
	@FXML
	private ImageView backArrow;

	@FXML
	private ImageView extendImage;

	@FXML
	private ImageView refreshImage;

	@FXML
	private Label usernameLabel;

	@FXML
	private JFXDrawer VBoxDrawer;

	@FXML
	private void onClickExtend(){
		RotateTransition rotate;
		if (VBoxDrawer.isOpened()){
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,0, 90);
			isArrowDown = false;
		}else{
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,90, 0);
			isArrowDown = true;
		}
		rotate.play();
		if(VBoxDrawer.isOpened()){
			VBoxDrawer.close();
		}else{
			VBoxDrawer.open();
		}
	}

	@FXML
	private void onEnterExtend(){
		if(VBoxDrawer.isClosed()){
			VBoxDrawer.open();
			RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,90, 0);
			rotate.play();
		}
	}

	@FXML
	private void refreshPage(){
		RotateTransition rotate = RotationUtils.getRotationTransitionFromBy(refreshImage,500,0, RotationUtils.Direction.COUNTERCLOCKWISE,360);
		rotate.play();
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
	@FXML
	private void onBackToLoginPage() throws IOException {
		Stage stage = (Stage) backButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/logInPage.fxml"));
		Scene scene = new Scene(loader.load());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void onEnterBackArrow() {
		backArrow.setFitWidth(16);
		backArrow.setFitHeight(16);
	}
	
	@FXML
	private void onExitBackArrow() {
		backArrow.setFitWidth(13);
		backArrow.setFitHeight(13);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			vbox = FXMLLoader.load(getClass().getResource("/fxml/VBox.fxml"));
			vbox.setOnMouseExited(event -> {
				RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,0, 90);
				rotate.play();
				VBoxDrawer.close();
			});
			System.out.println("trying");
			VBoxDrawer.setSidePane(vbox);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		usernameLabel.setText(user.getName());
	}
}
