package org.maven.apache.controllers;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import jakarta.mail.MessagingException;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import org.maven.apache.utils.*;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

public class LogInPageController implements Initializable {

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
    private JFXButton confirmButton;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton sendVerificationCodeButton;

    @FXML
    private ProgressIndicator loadIndicator;

    @FXML
    private MFXProgressSpinner confirmSpinner;

    private String signUpUserNameString;

    private String verificationCode;

    private int time;

    private static volatile List<User> userList;

    private final ExecutorService threadPoolExecutor = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private final UserService userService = MyLauncher.context.getBean("userService", UserService.class);

    private final MailService mailService = MyLauncher.context.getBean("mailService", MailService.class);

    private final Timeline usernameTimeline = new Timeline();

    private final Timeline passwordTimeline = new Timeline();

    public static boolean isCounting = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.updateUserList();
        signUpPane.setVisible(false);
        errorDialog.setVisible(false);
        errorDialog.setHeaderIcon(errorMessageIcon);
        confirmDialog.setVisible(false);
        verificationDialog.setVisible(false);
        sendVerificationCodeButton.setDisable(true);
        labelOnSignUp.setCursor(Cursor.HAND);
        labelOnSignIn.setCursor(Cursor.HAND);
        labelOnForgotPassword.setCursor(Cursor.HAND);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(imageOnStorage, 3000, 0, 1);
        fadeTransition.play();
        blockPane.setVisible(false);
        usernameCross.setVisible(false);
        confirmSpinner.setVisible(false);
        usernameCheck.setVisible(false);
        passwordCross.setVisible(false);
        passwordCheck.setVisible(false);
        loadIndicator.setStyle(" -fx-progress-color: black;");
        loadIndicator.setVisible(false);
        loginButton.setDisable(false);
        setFieldStatus(false);
        /****/
        fastLoginButton.setDisable(false);  // TEST ONLY
        /****/
    }

    /**
     * 执行异步操作 查询数据库 并留下缓存
     */
    public void updateUserList() {
        UserService userService = MyLauncher.context.getBean("userService", UserService.class);
        userList = userService.selectAll();
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
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton, 500, 1.2);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onEnterExitButton2() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton2, 500, 1.2);
        scaleTransition = ScaleUtils.addEaseOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onLeaveExitButton2() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton2, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onPressExitButton2() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton2, 500, 0.8);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onReleaseExitButton2() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton2, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onLeaveExitButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onPressExitButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton, 500, 0.8);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
    }

    @FXML
    private void onReleaseExitButton() {
        ScaleTransition scaleTransition = ScaleUtils.getScaleTransitionToXY(exitButton, 500, 1);
        scaleTransition = ScaleUtils.addEaseInOutTranslateInterpolator(scaleTransition);
        scaleTransition.play();
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
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(signUpPane, 300, 1, 0);
        FadeTransition fadeTransition1 = TransitionUtils.getFadeTransition(signInPane, 300, 0, 1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            signInPane.setVisible(true);
            signInPane.setOpacity(0);
            signUpPane.setVisible(false);
            fadeTransition1.play();
        });
    }


    private void callErrorDialog() {
        errorDialog.setVisible(true);
        blockPane.setVisible(true);
    }

    @FXML
    private void onCloseErrorDialog() {
        errorDialog.setVisible(false);
        blockPane.setVisible(false);
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
        threadPoolExecutor.execute(() -> {
            Platform.runLater(() -> {
                loginButton.setDisable(true);
                loadIndicator.setVisible(true);
                setFieldStatus(true);
            });
            try {
                // get the user list
                updateUserList();
                String username = userNameField.getText();
                User currentUser = getUser(username);
                Platform.runLater(() -> {
                    if (!isUsernameFound(username)) {
                        Platform.runLater(() -> {
                            loginButton.setDisable(false);
                            loadIndicator.setVisible(false);
                            setFieldStatus(false);
                        });
                        // if the user does not exist, show alert
                        callErrorDialog();
                    } else {
                        // if the user exists, check its verification (correct password)
                        if (currentUser.getPassword().equals(passwordField.getText())) {
                            // show progress indicator and disable signing button
                            loginButton.setDisable(true);
                            loadIndicator.setVisible(true);
                            // head to the app page (appPage2) in the background
                            threadPoolExecutor.execute(() -> {
                                DataUtils.currentUser = currentUser;
                                Stage stage = (Stage) loginButton.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/appPage2.fxml"));
                                Scene scene = null;
                                try {
                                    scene = new Scene(loader.load());
                                } catch (IOException e) {
                                    Platform.runLater(() -> {
                                        loginButton.setDisable(false);
                                        loadIndicator.setVisible(false);
                                        setFieldStatus(false);
                                    });
                                }
                                Scene finalScene = scene;
                                Platform.runLater(() -> {
                                    stage.setScene(finalScene);
                                    stage.show();
                                });
                            });
                        } else {
                            Platform.runLater(() -> {
                                loginButton.setDisable(false);
                                loadIndicator.setVisible(false);
                                setFieldStatus(false);
                            });
                            // incorrect username or password
                            callErrorDialog();
                        }
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    loginButton.setDisable(false);
                    loadIndicator.setVisible(false);
                    setFieldStatus(false);
                });
            }

        });
    }

    /**
     * Find the existence of the input username
     *
     * @param userName username from input
     * @return boolean
     */
    public static boolean isUsernameFound(String userName) {
        if (!userList.isEmpty() && userList != null) {
            return userList.stream().anyMatch(user -> user.getUsername().equals(userName));
        } else {
            return false;
        }
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
        verificationDialog.setOpacity(0);
        verificationDialog.setVisible(true);
        blockPane.setVisible(true);
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(verificationDialog,300,0,1);
        fadeTransition.setOnFinished(event -> {
            KeyFrame usernameKeyFrame = ThreadUtils.generateUsernameVerificationKeyFrame(verificationUsername, usernameCheck, usernameCross, usernameNotificationLabel);
            usernameTimeline.getKeyFrames().add(usernameKeyFrame);
            usernameTimeline.setCycleCount(Timeline.INDEFINITE);
            usernameTimeline.playFromStart();
            // initialize password verification per sec
            KeyFrame passwordKeyFrame = ThreadUtils.generatePasswordVerificationKeyFrame(newPasswordField, passwordCheck, passwordCross, newPasswordNotificationLabel, sendVerificationCodeButton);
            passwordTimeline.getKeyFrames().add(passwordKeyFrame);
            passwordTimeline.setCycleCount(Timeline.INDEFINITE);
            passwordTimeline.playFromStart();
            // initialize username verification per sec
            resetPasswordButton.setDisable(true);
        });
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(verificationDialog,300,-137.5,0);
        translateTransition = TranslateUtils.addEaseOutTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
    }

    /**
     * generate a random 6-bit number and send to the email
     */
    @FXML
    private void onSendVerificationCode() throws MessagingException {
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
            countToOneMinute(sendVerificationCodeButton);
        }
    }

    /**
     * resending email would be available in 60sec
     */
    private void countToOneMinute(JFXButton button) {
        isCounting = true;
        Timeline countTimeline = new Timeline();
        time = 60;
        passwordTimeline.stop();
        usernameTimeline.stop();
        countTimeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), event -> {
            time--;
            Task<Void> countTask = new Task<>() {
                @Override
                protected Void call() {
                    if (time == 0) {
                        Platform.runLater(() -> countLabel.setText(""));
                        isCounting = false;
                        countTimeline.stop();
                        button.setDisable(true);
                        passwordTimeline.playFromStart();
                        usernameTimeline.playFromStart();
                    } else {
                        Platform.runLater(() -> countLabel.setText(time + " "));
                    }
                    return null;
                }
            };
            Thread countThread = new Thread(countTask);
            countThread.start();
        });
        countTimeline.getKeyFrames().add(keyFrame);
        countTimeline.playFromStart();
    }

    /**
     * close the verification dialog
     */
    @FXML
    private void onCloseVerificationDialog() {
        FadeTransition fadeTransition = TransitionUtils.getFadeTransition(verificationDialog,300,1,0);
        fadeTransition.setOnFinished(event -> {
            verificationDialog.setVisible(false);
            notificationLabel.setText("");
            blockPane.setVisible(false);
            verificationUsername.clear();
            newPasswordField.clear();
            verificationCodeField.clear();
            usernameTimeline.stop();
            passwordTimeline.stop();
        });
        TranslateTransition translateTransition = TranslateUtils.getTranslateTransitionFromToY(verificationDialog,300,0,-137.5);
        translateTransition = TranslateUtils.addEaseInTranslateInterpolator(translateTransition);
        fadeTransition.play();
        translateTransition.play();
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
        blockPane.setVisible(true);
    }

    @FXML
    private void onCloseConfirmDialog() {
        confirmDialog.setVisible(false);
        blockPane.setVisible(false);
    }

    // the function for button confirmation in order to add a new user to database
    @FXML
    private void onConfirmationButton() {
        confirmButton.setVisible(false);
        confirmSpinner.setVisible(true);
        threadPoolExecutor.execute(() -> {
            if (checkExist()) {
                Platform.runLater(() -> {
                    Alert existAlert = new Alert(AlertType.WARNING);
                    existAlert.setTitle("Warning");
                    existAlert.setHeaderText("The username already exist");
                    existAlert.setContentText("Please change a user name");
                    existAlert.showAndWait();
                    confirmButton.setVisible(true);
                    confirmSpinner.setVisible(false);
                });
            } else {
                User userSignUp = new User();
                userSignUp.setName(getSignUpFullNameString());
                userSignUp.setEmailAddress(getSignUpEmailAddressString());
                userSignUp.setUsername(getSignUpUserNameString());
                userSignUp.setPassword(getSignUpPasswordString());
                userService.add(userSignUp);
                Platform.runLater(() -> {
                    confirmButton.setVisible(true);
                    confirmSpinner.setVisible(false);
                    // if user sign up successfully then would go back to the sign in scene
                    onCloseConfirmDialog();
                    setVisibility(signInPane, signUpPane);
                });
            }
        });

    }

    // the function for check whether the user already has an account based on
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

    /**
     * all clickable ndoes are disabled during a successful login
     *
     * @param isLoggingIn becomes true if logging in
     */
    private void setFieldStatus(boolean isLoggingIn) {
        if (isLoggingIn) {
            userNameField.setDisable(true);
            passwordField.setDisable(true);
            labelOnForgotPassword.setDisable(true);
            labelOnSignUp.setDisable(true);
        } else {
            userNameField.setDisable(false);
            passwordField.setDisable(false);
            labelOnForgotPassword.setDisable(false);
            labelOnSignUp.setDisable(false);
        }
    }

    /**
     * TEST ONLY
     */
    @FXML
    private Button fastLoginButton;

    /**
     * TEST ONLY
     * login as piper by clicking a button
     */
    @FXML
    private void onFastLogin() {
        userNameField.setText("Piper");
        passwordField.setText("sir");
        fastLoginButton.setDisable(true);
        onSignInAction();
    }

}
