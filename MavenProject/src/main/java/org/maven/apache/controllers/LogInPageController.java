package org.maven.apache.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.maven.apache.utils.TransitionUtils;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class LogInPageController implements Initializable {

	private String signUpFullNameString;

	private String signUpEmailAddressString;

	private String signUpUserNameString;

	private String signUpPasswordString;

	@FXML
	private ImageView exitButton;

	@FXML
	private AnchorPane signUpPane;

	@FXML
	private AnchorPane signInPane;

	@FXML
	private ImageView exitButton2;

	@FXML
	private Label labelOnSignUp;

	@FXML
	private MFXGenericDialog confirmDialog;

	@FXML
	private Label labelOnSignIn;

	@FXML
	private Line lineOnSignIn;

	@FXML
	private Line lineOnSignUp;

	@FXML
	private ImageView imageOnStorage;

	@FXML
	private Line lineOnForgotPassword;

	@FXML
	private Label labelOnForgotPassword;

	@FXML
	private MFXGenericDialog errorDialog;

	@FXML
	private ImageView errorMessageIcon;

	@FXML
	private ImageView confirmDialogIcon;
/////////
	@FXML
	private MFXPasswordField signUpPassword;

	@FXML
	private MFXTextField signUpFullName;

	@FXML
	private MFXTextField singUpEmailAddress;

	@FXML
	private MFXTextField signUpUserName;

	@FXML
	private Label confirmationUserName;

	@FXML
	private Label confirmationPassword;

	@FXML
	private Label confirmationEmailAddress;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		signInPane.setOpacity(1);
		signUpPane.setOpacity(0);
		signUpPane.setVisible(false);
		signUpPane.setPickOnBounds(false);
		errorDialog.setVisible(false);
		errorDialog.setOpacity(1);
		errorDialog.setHeaderIcon(errorMessageIcon);
		errorDialog.setPickOnBounds(false);
		confirmDialog.setVisible(false);
		confirmDialog.setOpacity(1);
		confirmDialog.setHeaderIcon(confirmDialogIcon);
		confirmDialog.setPickOnBounds(false);
		labelOnSignUp.setCursor(Cursor.HAND);
		labelOnSignIn.setCursor(Cursor.HAND);
		lineOnSignIn.setCursor(Cursor.HAND);
		lineOnSignUp.setCursor(Cursor.HAND);
		labelOnForgotPassword.setCursor(Cursor.HAND);
		lineOnForgotPassword.setCursor(Cursor.HAND);
		lineOnSignIn.setVisible(false);
		lineOnForgotPassword.setVisible(false);
		lineOnSignUp.setVisible(false);
		FadeTransition fadeTransition = TransitionUtils.getFadeTransition(imageOnStorage, 3000, 0, 1);
		fadeTransition.play();

	}

	@FXML
	private void onExit() {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void onExit2() {
		Platform.exit();
		System.exit(0);
	}

	@FXML
	private void onEnterExitButton() {
		exitButton.setScaleX(1.2);
		exitButton.setScaleY(1.2);
	}

	@FXML
	private void onEnterExitButton2() {
		exitButton2.setScaleX(1.2);
		exitButton2.setScaleY(1.2);
	}

	@FXML
	private void onLeaveExitButton2() {
		exitButton2.setScaleX(1);
		exitButton2.setScaleY(1);
	}

	@FXML
	private void onPressExitButton2() {
		exitButton2.setScaleX(0.8);
		exitButton2.setScaleY(0.8);
	}

	@FXML
	private void onReleaseExitButton2() {
		exitButton2.setScaleX(1);
		exitButton2.setScaleY(1);
	}

	@FXML
	private void onLeaveExitButton() {
		exitButton.setScaleX(1);
		exitButton.setScaleY(1);
	}

	@FXML
	private void onPressExitButton() {
		exitButton.setScaleX(0.8);
		exitButton.setScaleY(0.8);
	}

	@FXML
	private void onReleaseExitButton() {
		exitButton.setScaleX(1);
		exitButton.setScaleY(1);
	}

	@FXML
	private void onSignUp() {
		setVisibility(signUpPane, signInPane);
	}

	@FXML
	private void onSignIn() {
		setVisibility(signInPane, signUpPane);

	}

	private void setVisibility(AnchorPane signInPane, AnchorPane signUpPane) {
		signInPane.setVisible(true);
		signInPane.setPickOnBounds(true);
		signUpPane.setPickOnBounds(false);
		FadeTransition fadeTransition = TransitionUtils.getFadeTransition(signUpPane, 300, 1, 0);
		FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(signInPane, 300, 0, 1);
		fadeTransition.play();
		fadeTransition.setOnFinished(event -> {
			fadeTransition1.play();
			signUpPane.setVisible(false);
		});
	}

	@FXML
	private void onEnterForgotPassword() {
		lineOnForgotPassword.setVisible(true);
	}

	@FXML
	private void onExitForgotPassword() {
		lineOnForgotPassword.setVisible(false);
	}

	@FXML
	private void onEnterLabelSignIn() {
		lineOnSignIn.setVisible(true);
	}

	@FXML
	private void onExitLabelSignIn() {
		lineOnSignIn.setVisible(false);
	}

	@FXML
	private void onEnterLabelSignUp() {
		lineOnSignUp.setVisible(true);
	}

	@FXML
	private void onExitLabelSignUp() {
		lineOnSignUp.setVisible(false);
	}

	@FXML
	private void onEnterLineOnForgotPassword() {
		lineOnForgotPassword.setVisible(true);
	}

	@FXML
	private void onExitLineOnForgotPassword() {
		lineOnForgotPassword.setVisible(false);
	}

	@FXML
	private void onCloseErrorDialog() {
		errorDialog.setVisible(false);
		errorDialog.setPickOnBounds(false);
	}

	@FXML
	private void onEnterImage() {
		Bloom bloom = new Bloom(0.2);
		imageOnStorage.setEffect(bloom);
	}

	@FXML
	private void onSignInAction() {
		errorDialog.setVisible(true);
		errorDialog.setPickOnBounds(true);
	}

	@FXML
	private void onExitImage() {
		imageOnStorage.setEffect(null);
	}

	@FXML
	private void onSignUpAction() {

		confirmationUserName.setText("Username: " + getSignUpUserNameString());
		confirmationPassword.setText("Password: " + getSignUpPasswordString());
		confirmationEmailAddress.setText("Email Address: " + getSignUpEmailAddressString());

		confirmDialog.setVisible(true);
		confirmDialog.setPickOnBounds(true);

	}

	@FXML
	private void onCloseConfirmDialog() {
		confirmDialog.setVisible(false);
		confirmDialog.setPickOnBounds(false);
	}

	public String getSignUpFullNameString() {
		signUpFullNameString = signUpFullName.getText();
		return signUpFullNameString;
	}

	public String getSignUpEmailAddressString() {
		signUpEmailAddressString = singUpEmailAddress.getText();
		return signUpEmailAddressString;
	}

	public String getSignUpUserNameString() {
		signUpUserNameString = signUpUserName.getText();
		return signUpUserNameString;
	}

	public String getSignUpPasswordString() {
		signUpPasswordString = signUpPassword.getText();
		return signUpPasswordString;
	}

}
