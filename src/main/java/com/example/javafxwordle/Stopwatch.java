package com.example.javafxwordle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Stopwatch implements ActionListener {
    
    int milliseconds=0;
    int seconds=0;
    int minutes=0;
    int hours=0;
    //int days=0;
    JLabel timeLabel= new JLabel();
    JFrame frame= new JFrame();

    JButton startButton= new JButton("Start");
    JButton resetButton= new JButton("Reset");

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

    Stopwatch(){
        timeLabel.setText(hr + ":" + min + ":" + sec);
        timeLabel.setBounds(100,100,200,100);
        timeLabel.setFont(new Font("Verdana", Font.PLAIN, 35));
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setOpaque(true);
        timeLabel.setHorizontalAlignment(JTextField.CENTER);

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
        frame.add(timeLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,420);
        frame.setLayout(null);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed (ActionEvent e){
        if(e.getSource()== startButton){
            if(started==false){
                started=true;
                startButton.setText("Stop");
                start();
            }
        
            else{
                started= false;
                startButton.setText("Start");
                stop();
            }
        }
        if(e.getSource()== resetButton){
            started=false;
            startButton.setText("Start");
            reset();
        }
    }

    void start(){
        frame.setVisible(true);
        time.start();

    }

    void stop(){
        time.stop();
        frame.setVisible(false); 
        //frame.dispose(); 
    }

    void reset(){
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
    //public static void main(String [] args){
        //Stopwatch x= new Stopwatch();
        //System.out.println("This is just some text that should print to screen.");
        //x.start();
    //}

}

