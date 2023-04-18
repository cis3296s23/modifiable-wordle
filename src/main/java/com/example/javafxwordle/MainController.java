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

import java.io.IOException;

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("customdict-view.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("customdict-view.fxml"));


            Scene scene = new Scene(root, 500, 300);
//        TextField myButton = (TextField) loader.getNamespace().get("nameTextField");
//
//        Button button = (Button) loader.getNamespace().get("loginButton");
//        System.out.println(myButton.getText());
//
//
//        button.setOnAction(event -> {
//                System.out.println(myButton.getText());
//            stage.close();
//        });

            stage.setScene(scene);
            stage.show();

    }

    public void handleSubmit() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("customdict-view.fxml"));
//
//
//
//        TextField myButton = (TextField) loader.getNamespace().get("nameTextField");
//        Scene scene = myButton.getScene();
//        Stage stage = (Stage) scene.getWindow();
//        Button button = (Button) loader.getNamespace().get("loginButton");
//        System.out.println(myButton.getText());
//
//        System.out.println(myButton.getText());

//        button.setOnAction(event -> {
//                System.out.println(myButton.getText());
//                System.out.println("closing the stage");
////                stage.close();
//        });


        Scene scene = nameTextField.getScene();
        Stage stage = (Stage) scene.getWindow();
        System.out.println("Button clicked: " + nameTextField.getText());
        System.out.println("closing stage: ");
        stage.close();
        System.out.println("stage closed");
    }

    public void restartWithCustomDict(){

    }
    // Time Trial Mode; contributors: Abir, Ato, Kevin, Marcie
    public void toggleTimeTrial() {
        mainHelper.toggleTimeTrial(extraHBox, stopwatchIcon);
    }



}
