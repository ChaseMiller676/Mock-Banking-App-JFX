package org.client;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

public class MainMenuController {
    public static final ObservableList<AnchorPane> depositList = FXCollections.observableArrayList();
    private final ListChangeListener<AnchorPane> listChangeListener = new ListChangeListener<>() {
        @Override
        public void onChanged(Change c) {
            depositListContainer.getChildren().add(depositList.get(depositList.size() - 1));
        }
    };

    @FXML
    private VBox depositListContainer;

    @FXML
    void createDeposit(ActionEvent event){
        try {
            depositList.addListener(listChangeListener);
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("newdepositmenu.fxml")));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            NewDepositMenu.stage = new Stage();
            NewDepositMenu.stage.initModality(Modality.APPLICATION_MODAL);
            NewDepositMenu.stage.initOwner(Client.stage);
            NewDepositMenu.stage.setScene(scene);
            NewDepositMenu.stage.show();
        }catch (IOException error){
            error.printStackTrace();
        }
    }

    void closeDeposit(ActionEvent event){

    }

    void transferBetweenDeposits(ActionEvent event){

    }

    void transferBetweenAccounts(ActionEvent event){

    }

    @FXML
    @SuppressWarnings("unused")
    void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainMenuController.class.getResource("login.fxml")));
        Stage stage = (Stage) Client.stage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}