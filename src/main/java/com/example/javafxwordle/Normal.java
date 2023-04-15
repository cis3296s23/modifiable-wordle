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

public class Normal extends MainHelper implements GameMode{
    //private static MainHelper INSTANCE = null;

    //private Object gridPane;
    public GridPane gridPane;
    public GridPane keyboardRow1;
    public GridPane keyboardRow2;
    public GridPane keyboardRow3;
    public int CURRENT_ROW;
    public int CURRENT_COLUMN;

    MainHelper INSTANCE= null;

    public Normal(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2, GridPane keyboardRow3){
        super();
        this.gridPane= gridPane;
        this.keyboardRow1= keyboardRow1;
        this.keyboardRow2= keyboardRow2;
        this.keyboardRow3=keyboardRow3;
        //CURRENT_ROW= super.getCURRENT_ROW();
        //CURRENT_COLUMN= super.getCURRENT_COLUMN();

        //System.out.println(CURRENT_ROW);
        //System.out.println(CURRENT_COLUMN);

        
        //this.INSTANCE= INSTANCE;
        //System.out.println("You are in Normal Mode");
        //getInstance();
        //MainHelper.getInstance();
        //this.gridPane= super.gridPane;
        
    }


    //@Override
    public void afterEnterPressed (){
        System.out.println("This is in the method afterEnterPressed. Should run after enter is pressed");
        System.out.println("The value of current_row, max_row, current_column, and max_column " + CURRENT_ROW + MAX_ROW + CURRENT_COLUMN + MAX_COLUMN);
        System.out.println(super.getCURRENT_ROW());
        System.out.println(super.getCURRENT_COLUMN());

        String guess = getWordFromCurrentRow(gridPane).toLowerCase();

        if(isValidGuess(guess)) {
            updateRowColors(gridPane, CURRENT_ROW);
            updateKeyboardColors(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
        }
    //public void afterEnterPressed(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2, GridPane keyboardRow3){
    //public void onEnterPressed(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,GridPane keyboardRow3){
            //throws new UnsupportedOperationException("Unimplemented method 'afterEnterPressed'"){ 
        
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'afterEnterPressed'");
/*
        if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winningWord)) {
                updateRowColors(gridPane, CURRENT_ROW);
                updateKeyboardColors(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);

                if(timeTrialEnabled && CURRENT_ROW != 0) {
                    stopwatch.pause();
                }

                ScoreWindow.display(true, winningWord);
            } else if (isValidGuess(guess)) {
                updateRowColors(gridPane, CURRENT_ROW);
                updateKeyboardColors(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);

                // if this our last guess
                if (CURRENT_ROW == MAX_ROW) {
                    if(timeTrialEnabled) {
                        stopwatch.pause();
                    }
                    ScoreWindow.display(false, winningWord);
                    if (ScoreWindow.resetGame.get())
                        resetGame(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                }

                if(timeTrialEnabled) {
                    stopwatch.setCycleCount(Animation.INDEFINITE);
                    stopwatch.play();
                }

                CURRENT_ROW++;
                CURRENT_COLUMN = 1;
            } else {
                MainApplication.showToast();
            }
            if (ScoreWindow.resetGame.get()) {
                System.out.println("This line should only print when the game is reset");
                resetGame(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                ScoreWindow.resetGame.set(false);
            }
            if (ScoreWindow.quitApplication.get())
                MainApplication.quit();
        }
*/
    }  

}



