package org.maven.apache.utils;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.maven.apache.MyLauncher;
import org.maven.apache.controllers.LogInPageController;
import org.maven.apache.item.Item;
import org.maven.apache.search.FuzzySearch;
import org.maven.apache.service.item.ItemService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUtils {

    private static final AtomicInteger atomicInteger01 = new AtomicInteger();

    private static final AtomicInteger atomicInteger02 = new AtomicInteger();

    private static final AtomicInteger atomicInteger03 = new AtomicInteger();

    private static final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private static final ItemService itemService = MyLauncher.context.getBean("itemService", ItemService.class);

    private static boolean isUsernameOk = false;

    private static boolean isPasswordOk = false;

    /**
     * generate the timeline for searching frequently
     *
     * @param buttonList
     * @param searchField
     * @return
     */
    public static KeyFrame generateSearchKeyFrame(JFXButton[] buttonList, TextField searchField) {
        return new KeyFrame(Duration.seconds(0.2), event -> {
            if (atomicInteger01.compareAndSet(0, 1)) {
                executorService.execute(() -> {
                    searchTask(buttonList, searchField);
                });
            }
        });
    }

    /**
     * define the searching task
     *
     * @param buttonList
     * @param searchField
     */
    private static void searchTask(JFXButton[] buttonList, TextField searchField) {
        List<Item> searchedItems = itemService.selectByCondition(FuzzySearch.getFuzzyName(searchField.getText()), Integer.MIN_VALUE);
        List<String> collect = searchedItems.stream()
                .map(Item::getItemName)
                .toList();

        Platform.runLater(() -> {
            for (int i = 0; i < buttonList.length; i++) {
                buttonList[i].setText(collect.get(i));
            }
        });
        searchedItems = null;
        atomicInteger01.compareAndSet(1, 0);
    }

    /**
     * generate the timeline for verifying username frequently
     *
     * @param textField
     * @param check
     * @param cross
     * @return
     */
    public static KeyFrame generateUsernameVerificationKeyFrame(MFXTextField textField, ImageView check, ImageView cross, Label label){
        return new KeyFrame(Duration.seconds(0.5), event -> {
            if (atomicInteger02.compareAndSet(0, 1)){
                executorService.execute(() -> {
                    usernameVerificationTask(textField, check, cross, label);
                });
            }
        });
    }

    /**
     * define the task for verifying username
     *
     * @param textField
     * @param check
     * @param cross
     */
    private static void usernameVerificationTask(MFXTextField textField, ImageView check, ImageView cross, Label label){
        if (LogInPageController.isUsernameFound(textField.getText())){
            Platform.runLater(() -> {
                // if user exists
                isUsernameOk = true;
                check.setVisible(true);
                cross.setVisible(false);
                label.setText("");
            });
        }else{
            Platform.runLater(() -> {
                // if user does not exist
                isUsernameOk = false;
                check.setVisible(false);
                cross.setVisible(true);
                label.setText("User does not exist");
            });
        }
        atomicInteger02.compareAndSet(1, 0);
    }

    /**
     * generate the timeline for verifying password frequently
     *
     * @param textField
     * @param check
     * @param cross
     * @return
     */
    public static KeyFrame generatePasswordVerificationKeyFrame(MFXTextField textField, ImageView check, ImageView cross, Label label, JFXButton button){
        return new KeyFrame(Duration.seconds(0.5), event -> {
            if (atomicInteger03.compareAndSet(0, 1)){
                executorService.execute(() -> {
                    passwordVerificationTask(textField, check, cross, label, button);
                });
            }
        });
    }

    /**
     * define the task for verifying password
     *
     * @param textField
     * @param check
     * @param cross
     */
    private static void passwordVerificationTask(MFXTextField textField, ImageView check, ImageView cross, Label label, JFXButton button){
        if (textField.getText().length() > 5){
            Platform.runLater(() -> {
                // at least six characters
                isPasswordOk = true;
                check.setVisible(true);
                cross.setVisible(false);
                label.setText("");
            });
        }else{
           Platform.runLater(() -> {
                // less than six characters
                isPasswordOk = false;
                check.setVisible(false);
                cross.setVisible(true);
                label.setText("At least six characters");
            });
        }
        // set the button sending email available
        // set the button sending email disable
        button.setDisable(!isUsernameOk || !isPasswordOk || LogInPageController.isCounting);
        atomicInteger03.compareAndSet(1, 0);
    }

}