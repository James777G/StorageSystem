package org.maven.apache.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
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
	private AnchorPane extendPane;

	@FXML
	private JFXDrawer userDrawer;

	@FXML
	private void onClickExtend(){
		System.out.println("Clicked");
		RotateTransition rotate;
		/*Rectangle2D boxBounds = new Rectangle2D(100, 100, 170, 200);
		Rectangle clipRect = new Rectangle();
		clipRect.setWidth(boxBounds.getWidth());
		slidePane.setClip(clipRect);*/
		if (isArrowDown){
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,0, 90);
			/* Animation for scroll up.
			Timeline timelineUp = new Timeline();
			timelineUp.setCycleCount(1);
			timelineUp.setAutoReverse(true);
			final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
			final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight());
			final KeyValue kvUp3 = new KeyValue(slidePane.translateYProperty(), -boxBounds.getHeight());
			final KeyFrame kfUp = new KeyFrame(Duration.millis(2000), kvUp1, kvUp2, kvUp3);
			timelineUp.getKeyFrames().add(kfUp);*/
			isArrowDown = false;
		}else{
			//EventHandler onFinished = new EventHandler() {
			//	public void handle(ActionEvent t) {
			//		timelineBounce.play();
			//	}
			//};
			rotate = RotationUtils.getRotationTransitionFromTo(extendImage,300,90, 0);
			/*Timeline timelineDown = new Timeline();
			timelineDown.setCycleCount(1);
			timelineDown.setAutoReverse(true);
			final KeyValue kvDwn1 = new KeyValue(clipRect.heightProperty(), boxBounds.getHeight());
			final KeyValue kvDwn2 = new KeyValue(clipRect.translateYProperty(), 0);
			final KeyValue kvDwn3 = new KeyValue(slidePane.translateYProperty(), 0);
			final KeyFrame kfDwn = new KeyFrame(Duration.millis(200), kvDwn1, kvDwn2, kvDwn3);
			timelineDown.getKeyFrames().add(kfDwn);*/
			isArrowDown = true;
		}
		rotate.play();
		if(userDrawer.isOpened()){
			userDrawer.close();
		}else{
			userDrawer.open();
		}
	}

	@FXML
	private void onEnterExtend() {
		System.out.println("Entered");
		/*Rectangle2D boxBounds = new Rectangle2D(100, 100, 170, 200);
		Rectangle clipRect = new Rectangle();
		clipRect.setWidth(boxBounds.getWidth());
		slidePane.setClip(clipRect);
		/* Animation for scroll up.
		Timeline timelineUp = new Timeline();
		timelineUp.setCycleCount(1);
		timelineUp.setAutoReverse(true);
		final KeyValue kvUp1 = new KeyValue(clipRect.heightProperty(), 0);
		final KeyValue kvUp2 = new KeyValue(clipRect.translateYProperty(), boxBounds.getHeight());
		final KeyValue kvUp3 = new KeyValue(slidePane.translateYProperty(), -boxBounds.getHeight());
		final KeyFrame kfUp = new KeyFrame(Duration.millis(200), kvUp1, kvUp2, kvUp3);
		timelineUp.getKeyFrames().add(kfUp);*/
	}

	@FXML
	private void onExitExtend(){
		System.out.println("Exit");
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
