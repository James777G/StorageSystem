package org.maven.apache.utils;

import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import org.maven.apache.MyLauncher;
import org.maven.apache.item.Item;
import org.maven.apache.search.FuzzySearch;
import org.maven.apache.service.item.ItemService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchUtils {

    private static final AtomicInteger atomicInteger= new AtomicInteger();

    private static final ExecutorService executorService = MyLauncher.context.getBean("threadPoolExecutor", ExecutorService.class);
    private static final ItemService itemService = MyLauncher.context.getBean("itemService", ItemService.class);

    public static KeyFrame generateKeyFrame(MFXTableView<Item> searchTable, MFXTextField searchField){
        return new KeyFrame(Duration.seconds(0.2), event -> {
            if (atomicInteger.compareAndSet(0, 1)) {
                executorService.execute(() -> {
                    task(searchTable, searchField);
                });
            }
        });
    }
    private static void task(MFXTableView<Item> searchTable, MFXTextField searchField){
        List<Item> searchedItems = itemService.selectByCondition(FuzzySearch.getFuzzyName(searchField.getText()), Integer.MIN_VALUE);
        ObservableList<Item> itemInfo = FXCollections.observableArrayList();
        itemInfo.addAll(searchedItems);
        Platform.runLater(() -> {
            searchTable.setItems(itemInfo);
        });
        atomicInteger.compareAndSet(1, 0);
    }
}
