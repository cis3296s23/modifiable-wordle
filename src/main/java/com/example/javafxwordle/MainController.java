package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class MainController {

    private final MainHelper mainHelper = MainHelper.getInstance();

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    @FXML
    public GridPane keyboardRow2;
    @FXML
    public GridPane keyboardRow3;
    @FXML
    public ImageView helpIcon;
    @FXML
    public ImageView stopwatchIcon;
    @FXML
    public HBox titleHBox;
    @FXML
    public HBox extraHBox;
    @FXML
    public ImageView restartIcon;

    public void createUI() {
        createGrid();
        createKeyboard();
        createTitleHBox();
        createExtraHBox();
    }

    public void createGrid() {
        mainHelper.createGrid(gridPane);
    }

    public void createKeyboard() {
        mainHelper.createKeyboard(keyboardRow1, keyboardRow2, keyboardRow3);
    }

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        mainHelper.onKeyPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3, keyEvent);
    }

    public void getRandomWord() {
        mainHelper.getRandomWord();
    }

    public void showHelp() {
        HelpWindow.display();
    }

    public void createTitleHBox() {
        mainHelper.createTitleHBox(titleHBox);
    }

    public void createExtraHBox() {
        mainHelper.createExtraHBox(extraHBox);
    }

    public void restart() {
        mainHelper.restart(restartIcon, gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
    }

    // Time Trial Mode; contributors: Abir, Ato, Kevin, Marcie
    public void toggleTimeTrial() {
        mainHelper.toggleTimeTrial(extraHBox, stopwatchIcon);
    }

    //@FXML
    //public void onEnterPressed(){
        //mainHelper.onEnterPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
    //}

}
