package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainMenuController {

    @FXML
    private VBox depositListContainer;

    @FXML
    public void createDeposit(ActionEvent event){
        depositListContainer.getChildren().add(insertDeposit("Checking", "Test Deposit"));
    }

    public void removeDeposit(ActionEvent event){

    }

    public void transferBetweenDeposits(ActionEvent event){

    }

    public void transferBetweenAccounts(ActionEvent event){

    }

    @FXML
    public static AnchorPane insertDeposit(String depositType, String depositName){
        double balance = 0.00F;
        Label depositInfo = new Label();
        depositInfo.setText(String.format("Deposit: %s\nType: %s\nBalance: $%.2f", depositName, depositType, balance));
        Button removeButton = new Button("Delete");
        AnchorPane depositContainer = new AnchorPane(depositInfo, removeButton);
        depositContainer.setMinHeight(50.0);
        depositContainer.setMaxHeight(60.0);
        depositContainer.setMaxWidth(Double.MAX_VALUE);
        depositContainer.setBackground(Background.fill(Color.LIGHTGREY));

        AnchorPane.setLeftAnchor(depositInfo, 0.0);
        AnchorPane.setTopAnchor(depositInfo, 0.0);
        AnchorPane.setBottomAnchor(depositInfo, 0.0);
        AnchorPane.setRightAnchor(depositInfo, 50.0);

        AnchorPane.setRightAnchor(removeButton, 17.0);
        AnchorPane.setTopAnchor(removeButton, 0.0);
        AnchorPane.setBottomAnchor(removeButton, 0.0);

        removeButton.setMaxWidth(Double.MAX_VALUE);

        return depositContainer;
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainMenuController.class.getResource("login.fxml")));
        Stage stage = (Stage) Client.stage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
