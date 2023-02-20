package org.maven.apache.utils;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.controllers.AppPage2Controller;
import org.maven.apache.controllers.MessagePageController;
import org.maven.apache.controllers.NewTransactionPageController;
import org.maven.apache.controllers.StaffController;
import org.maven.apache.controllers.WarehouseController;
import org.maven.apache.user.User;
import org.springframework.stereotype.Component;

@Component
public class DataUtils {

    public static WarehouseController warehouseController;

    public static MessagePageController messageController;

    public static AppPage2Controller.ButtonSelected buttonSelected;

    public static AppPage2Controller appPage2Controller;

    public static NewTransactionPageController transactionPageController;

    public static StaffController staffController;

    public static Pagination transactionPagination;

    public static Pagination cargoPagination;

    public static Pagination pagination;

    public static AnchorPane publicSettingBlockPane;

    public static AnchorPane publicTransactionBlockPane;

    public static User currentUser;

    public static MFXGenericDialog publicSettingsDialog;

    public static Label totalPriceLabel;

}
