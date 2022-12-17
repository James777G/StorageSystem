package org.maven.apache.controllers;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.maven.apache.MyLauncher;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.maven.apache.utils.TransitionUtils;

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

import org.maven.apache.MyLauncher;
import org.maven.apache.service.user.UserService;
import org.maven.apache.user.User;
import org.maven.apache.utils.TransitionUtils;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class LogInPageController implements Initializable {

private String signUpFullNameString;

	private String signUpEmailAddressString;

	private String signUpUserNameString;

	private String signUpPasswordString;

	UserService userService = MyLauncher.context.getBean("userService", UserService.class);

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
    private MFXTextField userNameField;

    @FXML
    private MFXTextField passwordField;

    @FXML
    private MFXGenericDialog errorDialog;

    @FXML
    private ImageView errorMessageIcon;

    @FXML
    private ImageView confirmDialogIcon;

    private static volatile List<User> userList;
    
    /////////
	@FXML
	private MFXPasswordField signUpPassword;

	@FXML
	private MFXTextField signUpFullName;

	@FXML
	private MFXTextField signUpEmailAddress;

	@FXML
	private MFXTextField signUpUserName;

	@FXML
	private Button confimButton;

	@FXML
	private Label confirmationUserName;

	@FXML
	private Label confirmationPassword;

	@FXML
	private Label confirmationEmailAddress;

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
     * Sign in button, if the correct username and its corresponding password have been used then head
     * straight to the next page. Otherwise, sign up for a new user or renter the password
     */
    @FXML
    private void onSignInAction() {
        //get the user list
        updateUserList();
        String username = userNameField.getText();
        if (!isUserNameFound(username)) {
            //if the user does not exist, show sign up alert
            errorDialog.setVisible(true);
            errorDialog.setPickOnBounds(true);
            System.out.println("++++++++++++++++++++++++++++++++++++++");
            System.out.println("User does not exist");
            System.out.println("++++++++++++++++++++++++++++++++++++++");
        } else {
            //if the user exists, check its verification (correct password)
            if (getUserPassword(username).equals(passwordField.getText())) {
                //head to the next page
                System.out.println("++++++++++++++++++++++++++++++++++++++");
                System.out.println("Congrats, you've signed in");
                System.out.println("++++++++++++++++++++++++++++++++++++++");
            } else {
                errorDialog.setVisible(true);
                errorDialog.setPickOnBounds(true);
                System.out.println("++++++++++++++++++++++++++++++++++++++");
                System.out.println("Incorrect password");
                System.out.println("++++++++++++++++++++++++++++++++++++++");
            }
        }
    }

    /**
     * Find the existence of the input username
     *
     * @param userName username from input
     * @return boolean
     */
    private boolean isUserNameFound(String userName) {
        return userList.stream()
                .anyMatch(user -> user.getUsername().equals(userName));
    }

    /**
     * get the index position of the specified user from the user list
     *
     * @param userName username from input
     * @return index position
     */
    private String getUserPassword(String userName) {
        Optional<String> result = userList.stream()
                .filter(user -> user.getUsername().equals(userName))
                .map(User::getPassword)
                .findFirst();
        return result.orElse(null);
    }

    @FXML
    private void onSignUpAction() {
        confirmDialog.setVisible(true);
        confirmDialog.setPickOnBounds(true);
    }

    @FXML
    private void onCloseConfirmDialog() {
        confirmDialog.setVisible(false);
        confirmDialog.setPickOnBounds(false);

    }
    
    //the function for button confirmation 
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
		// UserServiceProvider userServiceProvider = new UserServiceProvider();
		List<User> users = userService.selectByUsername(signUpUserNameString);
		if (users.isEmpty()) {
			return false;
		}
		return true;

	}

	public String getSignUpFullNameString() {
		signUpFullNameString = signUpFullName.getText();
		return signUpFullNameString;
	}

	public String getSignUpEmailAddressString() {
		signUpEmailAddressString = signUpEmailAddress.getText();
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
