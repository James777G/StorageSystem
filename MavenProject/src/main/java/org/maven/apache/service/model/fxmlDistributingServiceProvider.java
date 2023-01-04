package org.maven.apache.service.model;

import javafx.scene.Node;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
public class fxmlDistributingServiceProvider implements fxmlDistributingService{

    private Node nodeForEditCargoPane;

    private Node nodeForAppPage2;

    private Node nodeForLogInPage;

    private Node nodeForMenuPage;

    private Node nodeForTransactionPage;

    private Node nodeForTransactionPage_data;

    private Node nodeForWarehousePage;

    @Override
    public void setNode(Node nodeToBeSet, Node nodeToBeLoaded) {

    }
}
