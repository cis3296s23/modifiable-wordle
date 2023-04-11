package com.example.javafxwordle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
public class version extends Application implements ActionListener {
    
    int milliseconds=0;
    int seconds=0;
    int minutes=0;
    int hours=0;
    //int days=0;
    Label timeLabel= new Label();
    Frame frame= new Frame();

    VBox root= new VBox();

    Button startButton= new Button("Start");
    Button resetButton= new Button("Reset");

    boolean started=false;

    String milli= String.format("%02d", milliseconds);
    String sec= String.format ("%02d", seconds);
    String min= String.format ("%02d", minutes);
    String hr= String.format ("%02d", hours);

    Timer time= new Timer (1000, new ActionListener(){

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


    });

    version(){
        timeLabel.setText(hr + ":" + min + ":" + sec);
        //timeLabel.setBounds(100,100,200,100);
        //timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        //timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        //timeLabel.setOpaque(true);
        //timeLabel.setHorizontalAlignment(JTextField.CENTER);

        startButton.setBounds(100,200,100,50);
        startButton.setFont(new Font ("Ink Free", Font.PLAIN, 20));
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        resetButton.setBounds(200,200,100,50);
        resetButton.setFont(new Font ("Ink Free", Font.PLAIN, 20));
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        //frame.add(startButton);
        //frame.add(resetButton);
        //frame.add(timeLabel);

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(420,420);
        //frame.setLayout(null);
        //frame.setVisible(true);
    }

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


    @Override
    public void actionPerformed (ActionEvent e){
        if(e.getSource()== startButton){
            if(started==false){
                started=true;
                //startButton.setText("Stop");
                startTime();
            }
        
            else{
                started= false;
                //startButton.setText("Start");
                stopTime();
            }
        }
        if(e.getSource()== resetButton){
            started=false;
            //startButton.setText("Start");
            resetTime();
        }
    }

    void startTime(){
        time.start();

    }

    void stopTime(){
        time.stop();
    }

    void resetTime(){
        time.stop();

        milliseconds=0;
        seconds=0;
        minutes=0;
        hours=0;
        //int days=0;

        milli= String.format("%02d", milliseconds);
        sec= String.format ("%02d", seconds);
        min= String.format ("%02d", minutes);
        hr= String.format ("%02d", hours);

        timeLabel.setText(hr + ":" + min + ":" + sec);


    }
    public static void main(String [] args){
        //Stopwatch x= new Stopwatch();
        //version y= new version();
        //Stage stage= new Stage();
        //y.start(stage);
        launch(args);
    }

}
