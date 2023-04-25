package com.example.javafxwordle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import javafx.scene.layout.VBox;

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
    public  ImageView addFileIcon;
    @FXML
    public ImageView helpIcon;
    @FXML
    public ImageView stopwatchIcon;
    @FXML
    public HBox titleHBox;
    @FXML
    public HBox extraHBox;
    @FXML
    public VBox extraVBox;
    @FXML
    public ImageView restartIcon;
    @FXML
    public ImageView alphaIcon;
    @FXML
    public ImageView betaIcon;

    @FXML
    public TextField nameTextField;

    public void createUI() {
        createGrid();
        createKeyboard();
        createTitleHBox();
        createExtraHBox();
        createExtraVBox();
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

    private void createExtraVBox() {
        mainHelper.createExtraVBox(extraVBox);
    }


    public void restart() {
        mainHelper.restart(restartIcon, gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
    }

    // Time Trial Mode; contributors: Abir, Ato, Kevin, Marcie
    public void toggleTimeTrial() {
        mainHelper.toggleTimeTrial(extraHBox, stopwatchIcon);
    }

    // Custom Dictonaries; contributors: Ato
    public void handleCustomDictSubmit()  { mainHelper.handleCustomDictSubmit(nameTextField); }

    public void showCustomDict() { mainHelper.showCustomDict(); }
    
    // All Characters Accepted Mode; contributors: Marcie
    public void toggleAllChars(){
        mainHelper.toggleAllChars(extraVBox);
    }

    // Limited Guesses Mode; contributors: Marcie
    public void toggleLimitedGuesses(){
        mainHelper.toggleLimitedGuesses(extraVBox);
    }

    /*
     // Possibly no longer needed based on our implementation of other features.
     @FXML
     public void onEnterPressed(){
         mainHelper.onEnterPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
     }
    */

}
