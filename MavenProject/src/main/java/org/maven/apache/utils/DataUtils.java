package org.maven.apache.utils;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.scene.layout.AnchorPane;
import org.maven.apache.item.Item;
import org.maven.apache.user.User;

import java.util.ArrayList;
import java.util.List;

public class DataUtils {

    public static User currentUser;

    public static AnchorPane publicDataPane;

    public static AnchorPane publicSettingBlockPane;

    public static AnchorPane publicTransactionBlockPane;

    public static AnchorPane editCargoPane;

    public static MFXGenericDialog publicSettingsDialog;

    public static List<List<Item>> publicCachedWarehouseTableData = new ArrayList<>();

}
