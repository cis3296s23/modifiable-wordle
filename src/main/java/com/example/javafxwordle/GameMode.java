package com.example.javafxwordle;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.*;

import static com.example.javafxwordle.MainApplication.dictionaryWords;
import static com.example.javafxwordle.MainApplication.winningWords;

//private int CURRENT_ROW;
//private int CURRENT_COLUMN = 1;
//private final int MAX_COLUMN = 5;
//private final int MAX_ROW = 6;



public interface GameMode {
    
    //public void afterEnterPressed(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,GridPane keyboardRow3);
    public void afterEnterPressed();

    //public void onEnterPressed(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,GridPane keyboardRow3);

}



