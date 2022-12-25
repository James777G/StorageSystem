package org.maven.apache.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import javafx.animation.RotateTransition;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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

	private Boolean isArrowDown = false;

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
	private JFXDrawer userDrawer;

	@FXML
	private void onClickExtend(){
		System.out.println("Clicked");
	}

	@FXML
	private void onEnterExtend(){
		System.out.println("Entered");
		if(!(userDrawer.isOpened())){
			userDrawer.open();
			RotateTransition rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,90, 0);
			rotate.play();
		}
		/*RotateTransition rotate;
		if (isArrowDown){
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,0, 90);
			isArrowDown = false;
		}else{
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,90, 0);
			isArrowDown = true;
		}*/

		if(userDrawer.isOpened()){
			userDrawer.close();
		}else{
			userDrawer.open();
		}
		isEnterExtend = true;
	}

	@FXML
	private void onExitExtend(){
		System.out.println("Exit");
		RotateTransition rotate;
		if (isArrowDown){
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,0, 90);
			isArrowDown = false;
		}else{
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,90, 0);
			isArrowDown = true;
		}
		rotate.play();
		if((userDrawer.isOpened()) && (DataUtils.isEnterExtended)){
			userDrawer.close();
		}else{
			userDrawer.open();
		}
		isEnterExtend = false;
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
			VBox vbox = FXMLLoader.load(getClass().getResource("/fxml/userPage.fxml"));
			System.out.println("trying");
			userDrawer.setSidePane(vbox);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		//usernameLabel.setText(user.getName());
	}
}
