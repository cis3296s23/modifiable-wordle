package com.example.javafxwordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//import java.awt.*;

public class JavaFx extends Application {

    int milliseconds=0;
    int seconds=0;
    int minutes=0;
    int hours=0;

    Label timeLabel= new Label();

    String milli= String.format("%02d", milliseconds);
    String sec= String.format ("%02d", seconds);
    String min= String.format ("%02d", minutes);
    String hr= String.format ("%02d", hours);
/*
    public void actionPerformed(ActionEvent e){
        milliseconds+= 1000;
        seconds= (milliseconds/1000)% 60;
        minutes= (milliseconds/ 60000) % 60;
        hours= (milliseconds/3600000);

        milli= String.format("%02d", milliseconds);
        sec= String.format ("%02d", seconds);
        min= String.format ("%02d", minutes);
        hr= String.format ("%02d", hours);

        timeLabel.setText(hr + ":" + min + ":" + sec);

    }
*/

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("This is just a test");
        stage.setWidth(400);
        stage.setHeight(500);

        VBox root= new VBox();
        Label label1 = new Label("This is just some random text to test if this actually shows up");
        root.getChildren().addAll(label1);

        Scene scene= new Scene(root);

        stage.setScene(scene);

        stage.show();
    }

    public static void main (String[] args){

        launch(args);
    }
    
}
