package org.maven.apache.utils;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.controllers.WarehouseController;
import org.maven.apache.item.Item;
import org.maven.apache.staff.Staff;
import org.maven.apache.user.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataUtils {

    public static WarehouseController warehouseController;

    public static User currentUser;

    public static Pagination pagination;

    public static AnchorPane publicDataPane;

    public static AnchorPane publicSettingBlockPane;

    public static AnchorPane publicTransactionBlockPane;

    public static AnchorPane editCargoPane;

    public static AnchorPane editMessagePane;

    public static MFXGenericDialog publicSettingsDialog;

//    public static List<List<Item>> publicCachedWarehouseTableData = new ArrayList<>();


}
