package com.example.javafxwordle;

import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

import static com.example.javafxwordle.MainApplication.dictionaryWords;
import static com.example.javafxwordle.MainApplication.winningWords;

/**
 * Communicates with MainController for all main functionality of the Wordle game.
 * Most if not all implementations of new features either are present or communicate with MainHelper.
 *
 */
public class MainHelper  {
    /**
     *
     */
    private static MainHelper INSTANCE = null;

    private final String[] firstRowLetters = {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"};
    private final String[] secondRowLetters = {"A", "S", "D", "F", "G", "H", "J", "K", "L"};
    private final String[] thirdRowLetters = {"↵", "Z", "X", "C", "V", "B", "N", "M", "←"};

    protected int CURRENT_ROW = 1;
    protected int CURRENT_COLUMN = 1;
    protected final int MAX_COLUMN = 5;
    protected final int MAX_ROW = 6;
    protected String winningWord;
    protected int attempts= 5;

    // New Variables for Game Modes Implementations
    protected boolean timeTrialEnabled= false;
    protected boolean allChars= false;
    protected boolean limitGuess= false;
    protected boolean normal= true;

    protected boolean menuOpen = false;

    private final Label stopwatchLabel = new Label("0");
    protected Label gameModeLabel= new Label("Game Mode: Normal");
    protected Label numAttempts= new Label("Number of Attempts Left: " + attempts);

    private final HashMap<Integer, String> map = new HashMap<>();
    private final ArrayList<String> incorrectLetters = new ArrayList<>();
    private final ArrayList<String> validLetters = new ArrayList<>();
    private final ArrayList<String> wordLibrary = new ArrayList<>();
    ArrayList wordLib = (ArrayList)winningWords.clone();
    boolean checkForReset = false;

    protected final Timeline stopwatch = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        int seconds = Integer.parseInt(stopwatchLabel.getText()) + 1;
        stopwatchLabel.setText(String.valueOf(seconds));
    }));

    protected MainHelper() {
    }

    /**
     *
     * @return
     */
    public static MainHelper getInstance() {
        if (INSTANCE == null){
            INSTANCE = new MainHelper();
        }
        
        return INSTANCE;
    }

    /**
     * Displays the "Wordle" Title above the game
     * @param titleHBox
     */
    public void createTitleHBox(HBox titleHBox) {
        ArrayList<Label> titleLetters = new ArrayList<>();
        for (String letter : new String[]{"W", "O", "R", "D", "L", "E"}) {
            Label label = new Label(letter);
            if (letter.equals("W") || letter.equals("L"))
                label.getStyleClass().setAll("correct-letter-example");
            else if (letter.equals("R"))
                label.getStyleClass().setAll("present-letter-example");
            else
                label.getStyleClass().setAll("wrong-letter-example");
            titleLetters.add(label);
        }
        for (Label label : titleLetters)
            titleHBox.getChildren().add(label);
    }

    /**
     *
     * @param extraHBox
     */
    public void createExtraHBox(HBox extraHBox) {
        stopwatchLabel.setFont(Font.font("Cambria", 30));
        extraHBox.getChildren().add(stopwatchLabel);
    }

    /**
     *
     * @param extraVBox
     */
    public void createExtraVBox(VBox extraVBox){
        gameModeLabel.setFont(Font.font("Cambria", 30));
        numAttempts.setFont(Font.font("Cambria", 20));
        extraVBox.getChildren().add(gameModeLabel);
    }

    /**
     *
     * @param gridPane
     */
    public void createGrid(GridPane gridPane) {
        for (int i = 1; i <= MAX_ROW; i++) {
            for (int j = 1; j <= MAX_COLUMN; j++) {
                Label label = new Label();
                label.getStyleClass().add("default-tile");
                gridPane.add(label, j, i);
            }
        }
    }

    /**
     *
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    public void createKeyboard(GridPane keyboardRow1, GridPane keyboardRow2, GridPane keyboardRow3) {
        for (int i = 0; i < firstRowLetters.length; i++) {
            Label label = new Label();
            label.getStyleClass().add("keyboardTile");
            label.setText(firstRowLetters[i]);
            keyboardRow1.add(label, i, 1);
        }
        for (int i = 0; i < secondRowLetters.length; i++) {
            Label label = new Label();
            label.getStyleClass().add("keyboardTile");
            label.setText(secondRowLetters[i]);
            keyboardRow2.add(label, i, 2);
        }
        for (int i = 0; i < thirdRowLetters.length; i++) {
            Label label = new Label();
            if (i == 0 || i == thirdRowLetters.length - 1)
                label.getStyleClass().add("keyboardTileSymbol");
            else
                label.getStyleClass().add("keyboardTile");
            label.setText(thirdRowLetters[i]);
            keyboardRow3.add(label, i, 3);
        }
    }

    /**
     *
     * @param gridPane
     * @param searchRow
     * @param searchColumn
     * @param input
     */
    private void setLabelText(GridPane gridPane, int searchRow, int searchColumn, String input) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null)
            label.setText(input.toUpperCase());
    }

    /**
     *
     * @param gridPane
     * @param searchRow
     * @param searchColumn
     * @return
     */
    private Label getLabel(GridPane gridPane, int searchRow, int searchColumn) {
        for (Node child : gridPane.getChildren()) {
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            int row = r == null ? 0 : r;
            int column = c == null ? 0 : c;
            if (row == searchRow && column == searchColumn && (child instanceof Label))
                return (Label) child;
        }
        return null;
    }

    /**
     *
     * @param gridPane
     * @param letter
     * @return
     */
    private Label getLabel(GridPane gridPane, String letter) {
        Label label;
        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label) {
                label = (Label) child;
                if (letter.equalsIgnoreCase(label.getText()))
                    return label;
            }
        }
        return null;
    }

    /**
     *
     * @param gridPane
     * @param searchRow
     * @param searchColumn
     * @return
     */
    private String getLabelText(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null)
            return label.getText().toLowerCase();
        return null;
    }

    /**
     *
     * @param gridPane
     * @param searchRow
     * @param searchColumn
     * @param styleclass
     */
    private void setLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn, String styleclass) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().add(styleclass);
        }
    }

    /**
     *
     * @param gridPane
     * @param searchRow
     * @param searchColumn
     */
    private void clearLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    /*
        Original source code had problems with duplicate letters showing up as yellow despite there only
        being one of the letter in the winningWord.

        BEFORE:
            Example: winningWord = ANIME
                     guess = SALSA
            Both the A in the 2nd and 5th position would highlight yellow.

        AFTER:
            Example: winningWord = ANIME
                     guess = SALSA
            Only the first A in the 2nd position will highlight yellow.

        contributors: Marcie Grayson

        ------------------------------------------------

        This method is also where the bulk of Practice Mode is implemented. We add eligible words to a
        map.

        contributors: Abir Islam
    */

    /**
     *
     * @param gridPane
     * @param searchRow
     */
    public void updateRowColors(GridPane gridPane, int searchRow) {
        // Using HashMaps to resolve bugs
        HashMap <String, Integer> checkDups = new HashMap<>();

        int count=1;
        for(int i=0; i< winningWord.length(); i++){
            for(int j=0; j<winningWord.length(); j++){
                if((i!=j) && (winningWord.charAt(i)== winningWord.charAt(j))){
                    count++;
                }
            }
            if(!checkDups.containsKey((String.valueOf(winningWord.charAt(i))).toLowerCase())){
                checkDups.put(String.valueOf(winningWord.charAt(i)).toLowerCase(), count);
            }
            count=1;
        }

        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(gridPane, searchRow, i);
            if (label != null) {
                String currentCharacter = String.valueOf(label.getText().charAt(0)).toLowerCase();
                boolean isCorrectLetter = String.valueOf(winningWord.charAt(i - 1)).toLowerCase().equals(currentCharacter);

                if (isCorrectLetter) {
                    checkDups.put(currentCharacter, (checkDups.get(currentCharacter) - 1));
                }
               
            }
        }

        if (checkForReset) {
            wordLib = (ArrayList)winningWords.clone();
            checkForReset = false;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Possible Guesses");
        alert.setHeaderText("Practice Mode Possible Guesses");
        StringBuilder bigString = new StringBuilder();

        InputStream winning_words = getClass().getResourceAsStream("winning-words.txt");
        assert winning_words != null;
        Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
        winning_words_lines.forEach(wordLibrary::add);

        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(gridPane, searchRow, i);
            String styleClass;
            if (label != null) {
                String currentCharacter = String.valueOf(label.getText().charAt(0)).toLowerCase();
                boolean isCorrectLetter = String.valueOf(winningWord.charAt(i - 1)).toLowerCase().equals(currentCharacter);
                boolean isPresentLetter = winningWord.contains(currentCharacter) && checkDups.get(currentCharacter) > 0;

                if (isCorrectLetter) {
                    styleClass = "correct-letter";
                    map.put(i, currentCharacter);
                    for (String word : winningWords) {
                        String newWord = String.valueOf(word.charAt(i - 1));
                        if (!newWord.equals(currentCharacter)) {
                            wordLib.remove(word);
                        }
                    }
                } else if (isPresentLetter) {
                    styleClass = "present-letter";
                    checkDups.put(currentCharacter, (checkDups.get(currentCharacter) - 1));
                    validLetters.add(currentCharacter);
                    for (String word : winningWords)
                        if (!word.contains(currentCharacter)) {
                            wordLib.remove(word);
                        }
                } else {
                    styleClass = "wrong-letter";
                    incorrectLetters.add(currentCharacter);
                    for (String word : winningWords) {
                        if (word.contains(currentCharacter)) {
                            wordLib.remove(word);
                        }
                    }
                }

                transit(label, styleClass);
            }
        }

        int size = wordLib.size();
        if (size > 4) {
            for (int j = 0; j < 5; j++) {
                bigString.append(wordLib.get(j));
                bigString.append("\n");
            }
        } else {
            for (Object s : wordLib) {
                bigString.append(s);
                bigString.append("\n");
            }
        }

        alert.setContentText(bigString.toString());
        alert.show();
    }

    /**
     *
     * @param label
     * @param styleClass
     */
    private void transit(Label label, String styleClass ){
        FadeTransition firstFadeTransition = new FadeTransition(Duration.millis(300), label);
        firstFadeTransition.setFromValue(1);
        firstFadeTransition.setToValue(0.2);
        firstFadeTransition.setOnFinished(e -> {
            label.getStyleClass().clear();
            label.getStyleClass().setAll(styleClass);
        });

        FadeTransition secondFadeTransition = new FadeTransition(Duration.millis(300), label);
        secondFadeTransition.setFromValue(0.2);
        secondFadeTransition.setToValue(1);

        new SequentialTransition(firstFadeTransition, secondFadeTransition).play();
    }

    /**
     *
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    protected void updateKeyboardColors(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2, GridPane keyboardRow3) {
        String currentWord = getWordFromCurrentRow(gridPane).toLowerCase();
        HashSet<String> checkDups = new HashSet<>();
   
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label keyboardLabel = new Label();
            String styleClass;
            String currentCharacter = String.valueOf(currentWord.charAt(i - 1));
            String winningCharacter = String.valueOf(winningWord.charAt(i - 1));

            if (contains(firstRowLetters, currentCharacter))
                keyboardLabel = getLabel(keyboardRow1, currentCharacter);
            else if (contains(secondRowLetters, currentCharacter))
                keyboardLabel = getLabel(keyboardRow2, currentCharacter);
            else if (contains(thirdRowLetters, currentCharacter))
                keyboardLabel = getLabel(keyboardRow3, currentCharacter);

            if (currentCharacter.equals(winningCharacter)){
                styleClass = "keyboardCorrectColor";  
                checkDups.add(currentCharacter); 
            } else if (winningWord.contains("" + currentCharacter) && !(checkDups.contains(currentCharacter))){
                styleClass = "keyboardPresentColor";
            } else {
                styleClass = "keyboardWrongColor";
                if(checkDups.contains(currentCharacter)){
                    styleClass = "keyboardCorrectColor"; 
                }
            }

            if (keyboardLabel != null) {
                keyboardLabel.getStyleClass().clear();
                keyboardLabel.getStyleClass().add(styleClass);
            }

        }
    }

    /**
     *
     * @param gridPane
     * @return
     */
    protected String getWordFromCurrentRow(GridPane gridPane) {
        StringBuilder input = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++)
            input.append(getLabelText(gridPane, CURRENT_ROW, j));
        return input.toString();
    }

    /**
     *
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     * @param keyEvent
     */
    public void onKeyPressed(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,
                             GridPane keyboardRow3, KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            onBackspacePressed(gridPane);
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterPressed(gridPane, keyEvent);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterPressed(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
        }
    }

    /**
     *
     * @param gridPane
     */
    private void onBackspacePressed(GridPane gridPane) {
        if ((CURRENT_COLUMN == MAX_COLUMN || CURRENT_COLUMN == 1)
                && !Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, "");
            clearLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "default-tile");
        } else if ((CURRENT_COLUMN > 1 && CURRENT_COLUMN < MAX_COLUMN)
                || (CURRENT_COLUMN == MAX_COLUMN && Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), ""))) {
            CURRENT_COLUMN--;
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, "");
            clearLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "default-tile");
        }
    }

    /**
     *
     * @param gridPane
     * @param keyEvent
     */
    private void onLetterPressed(GridPane gridPane, KeyEvent keyEvent) {
        // this is to make it so that when the user types a letter but the row is full
        // it doesn't change the last letter instead
        if (Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, keyEvent.getText());
            Label label = getLabel(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            ScaleTransition firstScaleTransition = new ScaleTransition(Duration.millis(100), label);
            firstScaleTransition.fromXProperty().setValue(1);
            firstScaleTransition.toXProperty().setValue(1.1);
            firstScaleTransition.fromYProperty().setValue(1);
            firstScaleTransition.toYProperty().setValue(1.1);
            ScaleTransition secondScaleTransition = new ScaleTransition(Duration.millis(100), label);
            secondScaleTransition.fromXProperty().setValue(1.1);
            secondScaleTransition.toXProperty().setValue(1);
            secondScaleTransition.fromYProperty().setValue(1.1);
            secondScaleTransition.toYProperty().setValue(1);
            new SequentialTransition(firstScaleTransition, secondScaleTransition).play();

            setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "tile-with-letter");
            if (CURRENT_COLUMN < MAX_COLUMN)
                CURRENT_COLUMN++;
        }
    }

    /**
     *
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    void onEnterPressed(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,
                              GridPane keyboardRow3) {
        if (normal && (!allChars) && (!limitGuess)){
            normalMode(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
        }
        else if((!normal) && allChars && (!limitGuess)){
            allCharsMode(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
        }
        else if((!normal) && (!allChars) && limitGuess){
            limitedGuessesMode(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
        }
            
    }

    /**
     * Picks from a random string within winningWords to make the word to solve during the game of Wordle.
     * Customizable using custom library feature
     */
    public void getRandomWord() {
        winningWord = winningWords.get(new Random().nextInt(winningWords.size()));
        //winningWord = "debug";
        System.out.println("THIS IS FOR DEBUGGING PURPOSES: " + winningWord);
    }

    /**
     * checks both winningWords and dictionaryWords to see if guess is a valid word in the dictionary
     * @param guess
     * @return
     */
    public boolean isValidGuess(String guess) {
        return binarySearch(winningWords, guess) || binarySearch(dictionaryWords, guess);
    }

    /**
     * Wordle game is reset, with all text cleared, called by restart()
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    public void resetGame(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,
                          GridPane keyboardRow3) {
        getRandomWord();
        Label label;
        for (Node child : gridPane.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.setText("");
                label.getStyleClass().add("default-tile");
            }

        for (Node child : keyboardRow1.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.getStyleClass().add("keyboardTile");
            }

        for (Node child : keyboardRow2.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.getStyleClass().add("keyboardTile");
            }

        for (Node child : keyboardRow3.getChildren())
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.getStyleClass().add("keyboardTile");
            }

        CURRENT_COLUMN = 1;
        CURRENT_ROW = 1;
        checkForReset = true;
    }

    /**
     * Resets stopwatch timer to 0, animates the reset button when pressed, and calls for resetGame
     * @param restartIcon
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    public void restart(ImageView restartIcon, GridPane gridPane, GridPane keyboardRow1,
                        GridPane keyboardRow2, GridPane keyboardRow3) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), restartIcon);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setOnFinished(ae -> resetGame(gridPane, keyboardRow1, keyboardRow2, keyboardRow3));
        rotateTransition.play();
        stopwatchLabel.setText("0");
        stopwatch.pause();
    }

    /**
     * Checks if string is in specified list using binary search
     * @param list
     * @param string
     * @return
     */
    private boolean binarySearch(ArrayList<String> list, String string) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = string.compareTo(list.get(mid));
            if (comparison == 0) return true;
            if (comparison > 0) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }

    /**
     *
     * @param array
     * @param letter
     * @return
     */
    private boolean contains(String[] array, String letter) {
        for (String string : array)
            if (string.equalsIgnoreCase(letter))
                return true;
        return false;
    }

    /*
        Time Trial Mode : helper methods

        Time Trial Mode starts a timer when the user enters their first VALID guess. Until a
        user finishes a Wordle game, successfully or otherwise, the elapsed game time will
        be shown on the board.

        You can see more implementation details in onEnterPressed().

        contributors: Abir, Ato, Kevin, Marcie
    */

    /**
     *
     * @param extraHBox
     * @param stopwatchIcon
     */
    public void toggleTimeTrial(HBox extraHBox, ImageView stopwatchIcon) {
        if(timeTrialEnabled) {
            extraHBox.setVisible(false);
            extraHBox.setManaged(false);
            stopwatchLabel.setText("0");
            stopwatch.pause();
            timeTrialEnabled = false;
            System.out.println("THIS IS FOR DEBUGGING PURPOSES: Time Trial Mode disabled.");
        } else {
            extraHBox.setVisible(true);
            extraHBox.setManaged(true);
            timeTrialEnabled = true;
            System.out.println("THIS IS FOR DEBUGGING PURPOSES: Time Trial Mode enabled.");
        }
    }

    /*
        Custom Dictionary : helper methods

        You can specify a filepath to a .txt file or the name of a preset .txt file to change
        what words can be selected as possible winning words.

        contributors: Ato
    */

    /**
     *
     */
    public void showCustomDict()  {
        try{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("CUSTOM DICTIONARY");
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("customdict-view.fxml")));
            Scene scene = new Scene(root, 500, 300);
            stage.setScene(scene);
            stage.show();
        }catch(IOException e){
            System.out.println("IO Exception from showCustomDict()");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param nameTextField
     */
    public void handleCustomDictSubmit(TextField nameTextField){
        Scene scene = nameTextField.getScene();
        Stage stage = (Stage) scene.getWindow();
        String file = nameTextField.getText();
        stage.close();
        changeDictionaryWords(nameTextField, file);
    }

    /**
     *
     * @param nameTextField
     * @param path
     */
    private void changeDictionaryWords(TextField nameTextField, String path) {
        InputStream dictionary = getClass().getResourceAsStream(path);

        if(dictionary == null){
            try{
                File file = new File(path);
                if(!file.exists()){
                    System.out.println("File does not exist");
                    Scene scene = nameTextField.getScene();
                    Stage stage = (Stage) scene.getWindow();
                    Toast.makeText(stage, "INVALID FILE");
                    return;
                }
                else{
                    dictionary = new FileInputStream(file);  // Possible issue with dictionary here, causing it not to change in practice?
                }
            }catch(FileNotFoundException e){
                System.out.println("FileNotFoundException from changeDictionaryWords()");
                e.printStackTrace();
            }
        }
        assert dictionary != null;
        Stream<String> dictionary_lines = new BufferedReader(new InputStreamReader(dictionary)).lines();
        winningWords.clear();
        dictionary_lines.forEach(winningWords::add);
        Scene scene = nameTextField.getScene();
        Stage stage = (Stage) scene.getWindow();

        Toast.makeText(stage, "DICTIONARY WORDS CHANGED");
        System.out.println("THIS IS FOR DEBUGGING PURPOSES: Dictionary words changed successfully.");
    }

    /*
        Difficulty Modes : helper methods

        All Characters Accepted mode makes the game easier as you do not have enter a 
        valid word.

        Limited Guesses Mode accepts invalid words, but will consume a guess without providing
        any hints.

        contributors: Marcie
    */

    /**
     *
     * @param extraVBox
     */
    public void toggleAllChars(VBox extraVBox) {
        if(allChars) {
            allChars= false;
            limitGuess= false;
            normal=true;
            System.out.println("THIS IS FOR DEBUGGING PURPOSES: All Characters Mode disabled.");
            gameModeLabel.setText("Game Mode: Normal");
        }
        else {
            allChars=true;
            limitGuess=false;
            normal=false;
            System.out.println("THIS IS FOR DEBUGGING PURPOSES: All Characters Mode enabled.");
            gameModeLabel.setText("Game Mode: All Characters");
        }
        extraVBox.getChildren().remove(numAttempts);
    }

    /**
     *
     * @param extraVBox
     */
    public void toggleLimitedGuesses(VBox extraVBox) {
        if(limitGuess) {
            allChars= false;
            limitGuess= false;
            normal=true;
            System.out.println("THIS IS FOR DEBUGGING PURPOSES: Limited Guesses Mode disabled.");
            gameModeLabel.setText("Game Mode: Normal");
            extraVBox.getChildren().remove(numAttempts);
        } 
        else {
            limitGuess=true;
            allChars=false;
            normal=false;
            System.out.println("THIS IS FOR DEBUGGING PURPOSES: Limited Guesses Mode enabled.");
            gameModeLabel.setText("Game Mode: Limited Guesses");
            extraVBox.getChildren().add(numAttempts);
            attempts=5;
        }
    }

    /**
     *
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    public void normalMode(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,
                            GridPane keyboardRow3){

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
                resetGame(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                ScoreWindow.resetGame.set(false);
            }
            if (ScoreWindow.quitApplication.get())
                MainApplication.quit();
            }
    }

    /**
     *
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    public void allCharsMode(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,
                            GridPane keyboardRow3){

        if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
        
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winningWord)) {
                updateRowColors(gridPane, CURRENT_ROW);
                updateKeyboardColors(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);

                if(timeTrialEnabled && CURRENT_ROW != 0) {
                    stopwatch.pause();
                }
                ScoreWindow.display(true, winningWord);
            } else if (guess.length()==winningWord.length()) {
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
            
            } 
            else {
                MainApplication.showToast();
            }
            if (ScoreWindow.resetGame.get()) {
                resetGame(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                ScoreWindow.resetGame.set(false);
            }
            if (ScoreWindow.quitApplication.get())
                MainApplication.quit();
            }
    }

    /**
     *
     * @param gridPane
     * @param keyboardRow1
     * @param keyboardRow2
     * @param keyboardRow3
     */
    public void limitedGuessesMode(GridPane gridPane, GridPane keyboardRow1, GridPane keyboardRow2,
                            GridPane keyboardRow3){

        if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
            if(attempts==0){
                ScoreWindow.display(false, winningWord);
            }
        
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winningWord)) {
                updateRowColors(gridPane, CURRENT_ROW);
                updateKeyboardColors(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                attempts= 5;
                numAttempts.setText("Number of Attempts Left: " + attempts);

                if(timeTrialEnabled && CURRENT_ROW != 0) {
                    stopwatch.pause();
                }
                ScoreWindow.display(true, winningWord);
            } else if (isValidGuess(guess)) {
                updateRowColors(gridPane, CURRENT_ROW);
                updateKeyboardColors(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                attempts=5;
                numAttempts.setText("Number of Attempts Left: " + attempts);

                // if this our last guess
                if (CURRENT_ROW == MAX_ROW) {
                    if(timeTrialEnabled || attempts==0) {
                        stopwatch.pause();
                    }
                    ScoreWindow.display(false, winningWord);
                    if (ScoreWindow.resetGame.get()) {
                        attempts = 5;
                        numAttempts.setText("Number of Attempts Left: " + attempts);
                        resetGame(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                    }
                }

                if(timeTrialEnabled) {
                    stopwatch.setCycleCount(Animation.INDEFINITE);
                    stopwatch.play();
                }

                CURRENT_ROW++;
                CURRENT_COLUMN = 1;
            
            } 
            else {
                MainApplication.showToast();
                //Not sure if we what to count an attempt everything enter is pressed or guess is an 5 char string
                if (guess.length()==winningWord.length()){
                    attempts--;
                    numAttempts.setText("Number of Attempts Left: " + attempts);
                }
                System.out.println("The number of attempts: " + attempts);
                if(attempts==0){
                    ScoreWindow.display(false, winningWord);
                }
            }
            if (ScoreWindow.resetGame.get()) {
                attempts=5;
                numAttempts.setText("Number of Attempts Left: " + attempts);
                resetGame(gridPane, keyboardRow1, keyboardRow2, keyboardRow3);
                ScoreWindow.resetGame.set(false);
            }
            if (ScoreWindow.quitApplication.get())
                MainApplication.quit();
            }
    }

    // TESTING

    /**
     *
     * @param menuVBox
     */
    public void showMenu(VBox menuVBox) {
        if(menuOpen) {
            menuVBox.setVisible(false);
            menuVBox.setManaged(false);
            menuOpen = false;
        } else {
            menuVBox.setVisible(true);
            menuVBox.setManaged(true);
            menuOpen = true;
        }

    }
    
}
