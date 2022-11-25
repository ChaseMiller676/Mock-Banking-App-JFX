package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class NewDepositMenu{

    @FXML
    private TextField nameField;
    @FXML
    private TextField typeField;

    public static Stage stage;
    @FXML
    @SuppressWarnings("unused")
    void confirm(ActionEvent event) {
        double balance = 0.00F;
        Label depositInfo = new Label();
        depositInfo.setText(String.format("Deposit: %s\nType: %s\nBalance: $%.2f", nameField.getText(), typeField.getText(), balance));
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
        MainMenuController.depositList.add(depositContainer);
        stage.close();
    }

    @FXML
    @SuppressWarnings("unused")
    void cancel(ActionEvent event){
        stage.close();
    }
}