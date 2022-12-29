package org.maven.apache.utils;

import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.util.Duration;
import org.maven.apache.MyLauncher;
import org.maven.apache.controllers.LogInPageController;
import org.maven.apache.item.Item;
import org.maven.apache.search.FuzzySearch;
import org.maven.apache.service.item.ItemService;
import javafx.scene.control.Label;
import io.github.palexdev.materialfx.controls.MFXTextField;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUtils {

    private static final AtomicInteger atomicInteger01 = new AtomicInteger();

    private static final AtomicInteger atomicInteger02 = new AtomicInteger();

    private static final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);

    private static final ItemService itemService = MyLauncher.context.getBean("itemService", ItemService.class);

    public static KeyFrame generateSearchKeyFrame(JFXButton[] buttonList, MFXTextField searchField){
        return new KeyFrame(Duration.seconds(0.2), event -> {
            if (atomicInteger01.compareAndSet(0, 1)) {
                executorService.execute(() -> {
                    searchTask(buttonList, searchField);
                });
            }
        });
    }

    private static void searchTask(JFXButton[] buttonList, MFXTextField searchField){
        List<Item> searchedItems = itemService.selectByCondition(FuzzySearch.getFuzzyName(searchField.getText()), Integer.MIN_VALUE);
        List<String> collect = searchedItems.stream()
                .map(Item::getItemName)
                .toList();

        Platform.runLater(() -> {
            for(int i=0; i < buttonList.length; i++){
                buttonList[i].setText(collect.get(i));
            }
        });
        searchedItems = null;
        atomicInteger01.compareAndSet(1, 0);
    }

    public static KeyFrame generateVerificationKeyFrame(MFXTextField textField, Label label01, Label label02){
        return new KeyFrame(Duration.seconds(1), event -> {
            if (atomicInteger02.compareAndSet(0, 1)){
                executorService.execute(() -> {
                    usernameVerificationTask(textField, label01, label02);
                    System.out.println("verifying");
                });
            }
        });
    }

    private static void usernameVerificationTask(MFXTextField textField, Label label01, Label label02){
        System.out.println(textField.getText());
        if (LogInPageController.isUsernameFound(textField.getText())){
            Platform.runLater(() -> {
                label01.setText("Yes");
            });
        }else{
            Platform.runLater(() -> {
                label02.setText("No");
            });
        }
        atomicInteger02.compareAndSet(1, 0);
    }


}
