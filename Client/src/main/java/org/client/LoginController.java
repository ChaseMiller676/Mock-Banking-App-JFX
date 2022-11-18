package org.client;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Objects;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label message;
    static BufferedReader clientReader;
    static PrintWriter clientWriter;
    static String serverOutput;

    static void initObjects(){
        try {
            clientReader = new BufferedReader(new InputStreamReader(Client.client.getInputStream()));
            clientWriter = new PrintWriter(Client.client.getOutputStream(), true);
        }catch(IOException error){
            error.printStackTrace();
        }
    }

    @SuppressWarnings("All")
    static String handleInput(){
        Task<String> input = new Task<>() {
            @Override
            protected String call() throws Exception {
                serverOutput = clientReader.readLine();
                return null;
            }
        };

        new Thread(input).run();
        return serverOutput;
    }

    @FXML
    @SuppressWarnings("unused")
    public void validate(ActionEvent event) throws IOException {
        clientWriter.println("login");
        clientWriter.println(usernameField.getText());
        clientWriter.println(passwordField.getText());

        switch (handleInput()){
            case "0": message.setText("Logging in..."); enterApp(); break;
            case "1": message.setText("Login info was incorrect/Account does not exit"); break;
            default: message.setText("Error");
        }
    }

    @FXML
    @SuppressWarnings("unused")
    public void createAccount(ActionEvent event){
        clientWriter.println("create");
        clientWriter.println(usernameField.getText());
        clientWriter.println(passwordField.getText());

        switch (handleInput()){
            case "0": message.setText("Account Created"); break;
            case "1": message.setText("Account Name is Taken"); break;
            default: message.setText("Error");
        }
    }

    private void enterApp() throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(LoginController.class.getResource("mainmenu.fxml")));
        Stage stage = (Stage) Client.stage.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}