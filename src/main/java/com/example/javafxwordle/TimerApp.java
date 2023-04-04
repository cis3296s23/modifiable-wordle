package com.example.javafxwordle;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TimerApp extends JFrame {
    private Timer timer;
    private JLabel label;
    private int counter;

    public TimerApp() {
        super("Stop Watch");
        this.counter = 0;
        label = new JLabel("Elapsed Time: 0 seconds", JLabel.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        getContentPane().add(label, BorderLayout.CENTER);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startClock();
            }
        });
        getContentPane().add(startButton, BorderLayout.NORTH);

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopClock();
            }
        });
        getContentPane().add(stopButton, BorderLayout.SOUTH);

        setSize(300, 200);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void stopClock() {
        if (timer != null) {
            timer.stop();
            timer = null;
        }
    }

    private void startClock() {
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                counter += 1;
                label.setText("Elapsed Time: " + counter + " seconds");

            }
        });
        timer.start();
    }


    public static void main(String[] args) {
        new TimerApp();
    }
}
