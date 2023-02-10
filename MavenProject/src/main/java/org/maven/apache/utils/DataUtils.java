package org.maven.apache.utils;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.controllers.AppPage2Controller;
import org.maven.apache.controllers.NewTransactionPageController;
import org.maven.apache.controllers.WarehouseController;
import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;
import org.maven.apache.transaction.Transaction;
import org.maven.apache.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataUtils {

    public static WarehouseController warehouseController;

    public static AppPage2Controller.ButtonSelected buttonSelected;

    public static AppPage2Controller appPage2Controller;

    public static NewTransactionPageController transactionPageController;

    public static Pagination transactionPagination;

    public static User currentUser;

    public static Pagination pagination;

    public static AnchorPane publicSettingBlockPane;

    public static AnchorPane publicTransactionBlockPane;

    public static MFXGenericDialog publicSettingsDialog;

}
