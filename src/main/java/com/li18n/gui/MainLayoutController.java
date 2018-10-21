package com.li18n.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainLayoutController {
    @FXML
    private Button mButton;
    @FXML
    private Label mLabel;

    @FXML
    public void onButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage)mButton.getScene().getWindow();
        stage.close();
        mLabel.setText("HelloWorld");
        stage=new Stage();
         Parent root = FXMLLoader.load(Thread.currentThread().getContextClassLoader().getResource("guiFxml/MainLayout.fxml"));
         stage.setTitle("第二个窗口");
         stage.setScene(new Scene(root));
         stage.show();


    }
}
