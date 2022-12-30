package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.Bloom;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.maven.apache.App;
import org.maven.apache.MyLauncher;
import org.maven.apache.service.mail.MailService;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.maven.apache.utils.DataUtils;
import org.maven.apache.utils.ThreadUtils;
import org.maven.apache.utils.TransitionUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class LogInPageController implements Initializable {

	private String signUpUserNameString;

	private String verificationCode;

	private int time;

	private static volatile List<User> userList;

	private final UserService userService = MyLauncher.context.getBean("userService", UserService.class);

	private final MailService mailService = MyLauncher.context.getBean("mailService", MailService.class);

	private final Timeline usernameTimeline = new Timeline();

	private final Timeline passwordTimeline = new Timeline();

	public static boolean isCounting = false;

	@FXML
	private ImageView exitButton2;

	@FXML
	private ImageView errorMessageIcon;

	@FXML
	private ImageView exitButton;

	@FXML
	private ImageView imageOnStorage;

	@FXML
	private ImageView usernameCross;

	@FXML
	private ImageView usernameCheck;

	@FXML
	private ImageView passwordCross;

	@FXML
	private ImageView passwordCheck;

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
	private Label usernameNotificationLabel;

	@FXML
	private Label newPasswordNotificationLabel;

	@FXML
	private Label countLabel;

	@FXML
	private AnchorPane signUpPane;

	@FXML
	private AnchorPane signInPane;

	@FXML
	private AnchorPane lineOnSignIn;

	@FXML
	private AnchorPane lineOnSignUp;

	@FXML
	private AnchorPane lineOnForgotPassword;

	@FXML
	private AnchorPane blockPane;

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
	private JFXButton resetPasswordButton;

	@FXML
	private JFXButton loginButton;

	@FXML
	private JFXButton sendVerificationCodeButton;

	@FXML
	private ProgressIndicator loadIndicator;

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
		confirmDialog.setPickOnBounds(false);
		verificationDialog.setVisible(false);
		verificationDialog.setOpacity(1);
		verificationDialog.setPickOnBounds(false);
		sendVerificationCodeButton.setDisable(true);
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
		blockPane.setVisible(false);
		blockPane.setPickOnBounds(false);
		usernameCross.setVisible(false);
		usernameCheck.setVisible(false);
		passwordCross.setVisible(false);
		passwordCheck.setVisible(false);
		loadIndicator.setStyle(" -fx-progress-color: black;");
		loadIndicator.setVisible(false);
		loginButton.setDisable(false);
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
	private void onEnterSignUp() {
		lineOnSignUp.setVisible(true);
	}

	@FXML
	private void onExitSignUp() {
		lineOnSignUp.setVisible(false);
	}

	@FXML
	private void onCloseErrorDialog() {
		errorDialog.setVisible(false);
		errorDialog.setPickOnBounds(false);
		blockPane.setVisible(false);
		blockPane.setPickOnBounds(false);
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
	 * 
	 * @throws IOException
	 */
	@FXML
	private void onSignInAction(){
		// get the user list
		loginButton.setDisable(true);
		updateUserList(); // can be changed to comment
		String username = userNameField.getText();
		User currentUser = getUser(username);
		if (!isUsernameFound(username)) {
			// if the user does not exist, show alert
			errorDialog.setVisible(true);
			errorDialog.setPickOnBounds(true);
			blockPane.setVisible(true);
			blockPane.setPickOnBounds(true);
		} else {
			// if the user exists, check its verification (correct password)
			if (currentUser.getPassword().equals(passwordField.getText())) {
				// show progress indicator
				loadIndicator.setVisible(true);
				// head to the app page (appPage2) in the background
				ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
				threadPoolExecutor.execute(() -> {
					DataUtils.currentUser = currentUser;
					Stage stage = (Stage) loginButton.getScene().getWindow();
					FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/appPage2.fxml"));
					final Scene scene;
					try {
						scene = new Scene(loader.load());
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					Platform.runLater(() -> {
						stage.setScene(scene);
						stage.show();
					});
				});
			} else {
				// incorrect username or password
				errorDialog.setVisible(true);
				errorDialog.setPickOnBounds(true);
				blockPane.setVisible(true);
				blockPane.setPickOnBounds(true);
			}
		}
	}

	/**
	 * Find the existence of the input username
	 *
	 * @param userName username from input
	 * @return boolean
	 */
	public static boolean isUsernameFound(String userName) {
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
		blockPane.setVisible(true);
		blockPane.setPickOnBounds(true);
		// initialize username verification per sec
		KeyFrame usernameKeyFrame = ThreadUtils.generateUsernameVerificationKeyFrame(verificationUsername, usernameCheck, usernameCross, usernameNotificationLabel);
		usernameTimeline.getKeyFrames().add(usernameKeyFrame);
		usernameTimeline.setCycleCount(Timeline.INDEFINITE);
		usernameTimeline.playFromStart();
		// initialize password verification per sec
		KeyFrame passwordKeyFrame = ThreadUtils.generatePasswordVerificationKeyFrame(newPasswordField, passwordCheck, passwordCross, newPasswordNotificationLabel, sendVerificationCodeButton);
		passwordTimeline.getKeyFrames().add(passwordKeyFrame);
		passwordTimeline.setCycleCount(Timeline.INDEFINITE);
		passwordTimeline.playFromStart();
	}

	/**
	 * generate a random 6-bit number and send to the email
	 */
	@FXML
	private void onSendVerificationCode() {
		String inputUsername = verificationUsername.getText();
		int newPasswordLength = newPasswordField.getText().length();
		if (isUsernameFound(inputUsername) && newPasswordLength > 5) {
			// if username exists and password length is at least six
			String emailAddress = getUser(inputUsername).getEmailAddress();
			Random rnd = new Random();
			int randNumber = rnd.nextInt(999999);
			verificationCode = String.format("%06d", randNumber);
			mailService.sendEmail(emailAddress, verificationCode);
			resetPasswordButton.setDisable(false);
			notificationLabel.setVisible(true);
			notificationLabel.setText("Email has been sent");
			// verification code can be resent in 60 seconds
			sendVerificationCodeButton.setDisable(true);
			countToOneMinute(sendVerificationCodeButton, 60);
		}
	}

	/**
	 * resending email would be available in 60sec
	 *
	 */
	private void countToOneMinute(JFXButton button, int seconds){
		isCounting = true;
		Timeline countTimeline = new Timeline();
		time = seconds;
		passwordTimeline.stop();
		usernameTimeline.stop();
		countTimeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				time--;
				Task<Void> countTask = new Task<>(){
					@Override
					protected Void call() {
						if (time == 0){
							Platform.runLater(() -> {
								countLabel.setText("");
							});
							isCounting = false;
							countTimeline.stop();
							button.setDisable(true);
							passwordTimeline.playFromStart();
							usernameTimeline.playFromStart();
						}else{
							Platform.runLater(() -> {
								countLabel.setText(time + " ");
							});
						}
						return null;
					}
				};
				Thread countThread = new Thread(countTask);
				countThread.start();
			}
		});
		countTimeline.getKeyFrames().add(keyFrame);
		countTimeline.playFromStart();
	}

	/**
	 * close the verification dialog
	 */
	@FXML
	private void onCloseVerificationDialog() {
		verificationDialog.setVisible(false);
		verificationDialog.setPickOnBounds(false);
		notificationLabel.setText("");
		blockPane.setVisible(false);
		blockPane.setPickOnBounds(false);
		verificationUsername.clear();
		newPasswordField.clear();
		verificationCodeField.clear();
		usernameTimeline.stop();
		passwordTimeline.stop();
	}

	/**
	 * assign the new password to the user
	 */
	@FXML
	private void onResetPassword() {
		String newPassword = newPasswordField.getText();
		String inputVerificationCode = verificationCodeField.getText();
		if (inputVerificationCode.equals(verificationCode) && inputVerificationCode.length() > 5) {
			// if the verification code is matched and new password length is at least 6
			// characters
			User userToBeResetPassword = getUser(verificationUsername.getText());
			userToBeResetPassword.setPassword(newPassword);
			userService.update(userToBeResetPassword);
			notificationLabel.setText("Your password has been reset");
		} else {
			// if the verification code is not matched
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
		blockPane.setVisible(true);
		blockPane.setPickOnBounds(true);
	}

	@FXML
	private void onCloseConfirmDialog() {
		confirmDialog.setVisible(false);
		confirmDialog.setPickOnBounds(false);
		blockPane.setVisible(false);
		blockPane.setPickOnBounds(false);
	}

	// the function for button confirmation in order to add a new user to database
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
