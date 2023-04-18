package com.example.javafxwordle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import static com.example.javafxwordle.MainApplication.dictionaryWords;
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
    public ImageView restartIcon;

    @FXML
    public TextField nameTextField;

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


    public void showCustomDict() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("CUSTOM DICTIONARY");
        Parent root = FXMLLoader.load(getClass().getResource("customdict-view.fxml"));
        Scene scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    public void handleSubmit() throws IOException {
        Scene scene = nameTextField.getScene();
        Stage stage = (Stage) scene.getWindow();
        String file = nameTextField.getText();
        System.out.println("Button clicked: " + file);
        System.out.println("closing stage: ");
        stage.close();
        System.out.println("stage closed");
        System.out.println("changing dictionary words start: ");

        changeDictionaryWords(file);

        System.out.println("changing dictionary words done: ");
    }


    // Time Trial Mode; contributors: Abir, Ato, Kevin, Marcie
    public void toggleTimeTrial() {
        mainHelper.toggleTimeTrial(extraHBox, stopwatchIcon);
    }

    public void changeDictionaryWords(String file){

        InputStream dictionary = getClass().getResourceAsStream(file);

        if(dictionary == null){
            System.out.println("invalid");
            return;
        }

        Stream<String> dictionary_lines = new BufferedReader(new InputStreamReader(dictionary)).lines();
        dictionaryWords.clear();
        dictionary_lines.forEach(dictionaryWords::add);
        System.out.println(dictionaryWords);

    }


}
