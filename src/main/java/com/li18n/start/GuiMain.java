package com.li18n.start;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain  extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("guiFxml/MainLayout.fxml"));
            Scene scene = new Scene(parent,600,400);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Li18n 工具");
            primaryStage.setOnCloseRequest(e->{
                System.exit(0);
            });
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
