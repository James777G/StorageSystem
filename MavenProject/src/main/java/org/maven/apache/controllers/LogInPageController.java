package org.maven.apache.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import org.maven.apache.MyLauncher;
import org.maven.apache.service.mail.MailService;
import org.maven.apache.service.mail.MailServiceProvider;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.TransitionUtils;

import com.jfoenix.controls.JFXButton;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class LogInPageController implements Initializable {

	private String signUpUserNameString;

	private String verificationCode;

	private static volatile List<User> userList;

	private final UserService userService = MyLauncher.context.getBean("userService", UserService.class);

	private final MailService mailServiceProvider = MyLauncher.context.getBean("mailService", MailService.class);

	@FXML
	private AnchorPane signUpPane;

	@FXML
	private AnchorPane signInPane;

	@FXML
	private ImageView exitButton2;

	@FXML
	private ImageView errorMessageIcon;

	@FXML
	private ImageView confirmDialogIcon;

	@FXML
	private ImageView verificationDialogIcon;

	@FXML
	private ImageView exitButton;

	@FXML
	private ImageView imageOnStorage;
	
	@FXML
	private Label notificationLabel;

	@FXML
	private Label labelOnSignUp;

	@FXML
	private Label confirmationUserName;

	@FXML
	private Label confirmationPassword;

	@FXML
	private Label confirmationEmailAddress;

	@FXML
	private Label labelOnSignIn;

	@FXML
	private Label labelOnForgotPassword;

	@FXML
	private Line lineOnSignIn;

	@FXML
	private Line lineOnSignUp;

	@FXML
	private Line lineOnForgotPassword;

	@FXML
	private MFXTextField newPasswordField;

	@FXML
	private MFXTextField userNameField;

	@FXML
	private MFXTextField passwordField;

	@FXML
	private MFXTextField signUpFullName;

	@FXML
	private MFXTextField signUpEmailAddress;

	@FXML
	private MFXTextField verificationCodeField;

	@FXML
	private MFXTextField signUpUserName;

	@FXML
	private MFXTextField verificationUsername;

	@FXML
	private MFXGenericDialog errorDialog;

	@FXML
	private MFXGenericDialog verificationDialog;

	@FXML
	private MFXGenericDialog confirmDialog;

	@FXML
	private MFXPasswordField signUpPassword;

	@FXML
	private Button confimButton;

	@FXML
	private JFXButton resetPasswordButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.updateUserList();
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
		// confirmDialog.setHeaderIcon(confirmDialogIcon);
		confirmDialog.setPickOnBounds(false);
		verificationDialog.setVisible(false);
		verificationDialog.setOpacity(1);
		// verificationDialog.setHeaderIcon(verificationDialogIcon);
		verificationDialog.setPickOnBounds(false);
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
		resetPasswordButton.setDisable(true);
		notificationLabel.setVisible(false);
	}

	/**
	 * 执行异步操作 查询数据库 并留下缓存
	 */
	public void updateUserList() {
		UserService userService = MyLauncher.context.getBean("userService", UserService.class);
		ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
		threadPoolExecutor.execute(() -> userList = userService.selectAll());
	}

	@FXML
	private void onExit() {
		Platform.exit();
		ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
		threadPoolExecutor.shutdown();
		System.exit(0);
	}

	@FXML
	private void onExit2() {
		Platform.exit();
		ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
		threadPoolExecutor.shutdown();
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
	private void onExitImage() {
		imageOnStorage.setEffect(null);
	}

	/**
	 * Sign in button, if the correct username and its corresponding password have
	 * been used then head straight to the next page. Otherwise, sign up for a new
	 * user or renter the password
	 */
	@FXML
	private void onSignInAction() {
		// get the user list
		updateUserList(); // can be changed to comment
		String username = userNameField.getText();
		User currentUser = getUser(username);
		if (!isUsernameFound(username)) {
			// if the user does not exist, show sign up alert
			errorDialog.setVisible(true);
			errorDialog.setPickOnBounds(true);
		} else {
			// if the user exists, check its verification (correct password)
			if (currentUser.getPassword().equals(passwordField.getText())) {
				// head to the next page
				DataUtils.currentUser = currentUser;
				System.out.println("++++++++++++++++++++++++++++++++++++++");
				System.out.println("Congrats, you've signed in");
				System.out.println("++++++++++++++++++++++++++++++++++++++");
			} else {
				errorDialog.setVisible(true);
				errorDialog.setPickOnBounds(true);
			}
		}
	}

	/**
	 * Find the existence of the input username
	 *
	 * @param userName username from input
	 * @return boolean
	 */
	private boolean isUsernameFound(String userName) {
		return userList.stream().anyMatch(user -> user.getUsername().equals(userName));
	}

	/**
	 * get the user by searching by username
	 *
	 * @param userName username from input
	 * @return index position
	 */
	private User getUser(String userName) {
		Optional<User> result = userList.stream().filter(user -> user.getUsername().equals(userName)).findFirst();
		return result.orElse(null);
	}

	@FXML
	private void onForgetPassword() {
		verificationDialog.setVisible(true);
		verificationDialog.setPickOnBounds(true);
	}

	/**
	 * generate a random 6-bit number and send to the email
	 */
	@FXML
	private void onSendVerificationCode() {
		String inputUsername = verificationUsername.getText();
		if (isUsernameFound(inputUsername)) {
			// if the username exists
			String emailAddress = getUser(inputUsername).getEmailAddress();
			Random rnd = new Random();
			int randNumber = rnd.nextInt(999999);
			verificationCode = String.format("%06d", randNumber);
			mailServiceProvider.sendEmail(emailAddress, verificationCode);
			resetPasswordButton.setDisable(false);
			notificationLabel.setVisible(true);
			notificationLabel.setText("Email has been sent");
		}else {
			// if the username does not exist
			notificationLabel.setVisible(true);
			notificationLabel.setText("User does not exist");
		}
		
	}

	/**
	 * close the verification dialog
	 */
	@FXML
	private void onCloseVerificationDialog() {
		verificationDialog.setVisible(false);
		verificationDialog.setPickOnBounds(false);
		notificationLabel.setText("");
	}

	/**
	 * assign the new password to the user
	 */
	@FXML
	private void onResetPassword() {
		String newPassword = newPasswordField.getText();
		String inputVerificationCode = verificationCodeField.getText();
		if (inputVerificationCode.equals(verificationCode) && inputVerificationCode.length() > 5) {
			// if the verification code is matched and new password length is at least 6 characters
			User userToBeResetPassword = getUser(verificationUsername.getText());
			userToBeResetPassword.setPassword(newPassword);
			userService.update(userToBeResetPassword);
			notificationLabel.setVisible(true);
			notificationLabel.setText("Your password has been reset");
		} else {
			// if the verification code is not matched
			notificationLabel.setVisible(true);
			notificationLabel.setText("Incorrect verification code");
		}
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

	// the function for button confirmation
	@FXML
	private void onConfirmationButton(ActionEvent event) {
		if (checkExist()) {
			Alert existAlert = new Alert(AlertType.WARNING);
			existAlert.setTitle("Warning");
			existAlert.setHeaderText("The username already exist");
			existAlert.setContentText("Please change a user name");
			existAlert.showAndWait();

		} else {
			User userSignUp = new User();
			userSignUp.setName(getSignUpFullNameString());
			userSignUp.setEmailAddress(getSignUpEmailAddressString());
			userSignUp.setUsername(getSignUpUserNameString());
			userSignUp.setPassword(getSignUpPasswordString());
			userService.add(userSignUp);
			// if user sign up successfully then would go back to the sign in scene
			onCloseConfirmDialog();
			setVisibility(signInPane, signUpPane);

		}
	}

	// the fucntion for check whether the user already has an account based on
	// username
	private boolean checkExist() {
		List<User> users = userService.selectByUsername(signUpUserNameString);
		return !users.isEmpty();

	}

	public String getSignUpFullNameString() {
		return signUpFullName.getText();
	}

	public String getSignUpEmailAddressString() {
		return signUpEmailAddress.getText();
	}

	public String getSignUpUserNameString() {
		signUpUserNameString = signUpUserName.getText();
		return signUpUserNameString;
	}

	public String getSignUpPasswordString() {
		return signUpPassword.getText();
	}

}
