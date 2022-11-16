package org.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class Client extends Application {

    public static Socket client;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        try{
            client = new Socket("localhost", 4999);
            LoginController.initObjects();
            launch();
            client.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}