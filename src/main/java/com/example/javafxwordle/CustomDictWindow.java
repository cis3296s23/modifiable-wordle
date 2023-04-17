package com.example.javafxwordle;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CustomDictWindow {


    public void display() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("CUSTOM DICTIONARY");


        Parent root = FXMLLoader.load(getClass().getResource("customdict-view.fxml"));


        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.showAndWait();
    }
}
