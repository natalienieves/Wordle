package edu.virginia.cs.gui;

import edu.virginia.cs.wordle.DefaultDictionaryFactory;
import edu.virginia.cs.wordle.LetterResult;
import edu.virginia.cs.wordle.WordleDictionary;
import edu.virginia.cs.wordle.WordleImplementation;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class WordleController {
    private WordleImplementation wordle;
    private DefaultDictionaryFactory defaultDictionaryFactory = new DefaultDictionaryFactory();
    @FXML
    private Label WORDLE;
    @FXML
    private Label state_of_game;


    @FXML
    TextField Grid1, Grid2, Grid3, Grid4, Grid5, Grid6, Grid7, Grid8, Grid9, Grid10, Grid11, Grid12, Grid13, Grid14, Grid15, Grid16, Grid17, Grid18, Grid19, Grid20, Grid21, Grid22, Grid23, Grid24, Grid25, Grid26, Grid27, Grid28, Grid29, Grid30;
    TextField[] textFields;

    private String guess = "";
    private int current = 0;
    private WordleDictionary newWordle;
    @FXML
    private Label ERROR;

    @FXML
    protected Button YES;

    @FXML
    protected Button NO;

    @FXML
    private Label propmtedQuestion;

    @FXML
    protected void initialize(){
        wordle = new WordleImplementation();
        newWordle = new WordleDictionary();
        textFields = new TextField[] {Grid1, Grid2, Grid3, Grid4, Grid5, Grid6, Grid7, Grid8, Grid9, Grid10, Grid11, Grid12, Grid13, Grid14, Grid15, Grid16, Grid17, Grid18, Grid19, Grid20, Grid21, Grid22, Grid23, Grid24, Grid25, Grid26, Grid27, Grid28, Grid29, Grid30};
        current = 0;
        guess = "";
    }

    @FXML
    protected void onYesButtonClick(KeyEvent e) {
        NO.setVisible(false);
        YES.setVisible(false);
        propmtedQuestion.setVisible(false);
        ERROR.setVisible(false);
        WORDLE.setText("WORDLE");
        state_of_game.setVisible(false);

        for(TextField grid : textFields){
            grid.setEditable(true);
            grid.clear();
            grid.setStyle("-fx-text-fill: black");
            grid.setStyle("-fx-background-color: #FF;");
        }
        current = 0;
        guess = "";
        initialize();
    }

    @FXML
    protected void onNoButtonClick(KeyEvent e){
        if (e.getCode().equals(KeyCode.N)) {
            System.exit(0);
        }
        if (e.getCode().equals(KeyCode.Y)) {
            onYesButtonClick(e);
        }
    }




    @FXML
    protected void enterYourGuess(KeyEvent e) {
        textFields[current].requestFocus();
        if (current % 5 == 4) {
            if (e.getCode().equals(KeyCode.ENTER)) {
                guess += textFields[current].getText();
                guess = guess.toUpperCase();
                if (!defaultDictionaryFactory.getDefaultGuessesDictionary().containsWord(guess)) {
                    ERROR.setText("ERROR NOT A WORD! PLEASE TRY AGAIN");
                    ERROR.setVisible(true);
                    ERROR.setFont(Font.font(String.valueOf(FontWeight.BOLD), 9));
                    ERROR.setAlignment(Pos.CENTER);
                    ERROR.setStyle("-fx-text-fill: red");
                    textFields[current].clear();
                    textFields[current-1].clear();
                    textFields[current-2].clear();
                    textFields[current-3].clear();
                    textFields[current-4].clear();
                    current = current -4;
                    guess = "";
                    textFields[current].requestFocus();
                    textFields[current].setEditable(true);
                    textFields[current+4].setEditable(true);
                    textFields[current+3].setEditable(true);
                    textFields[current+2].setEditable(true);
                    textFields[current+1].setEditable(true);

                }

                else {
                    ERROR.setVisible(false);
                   // System.out.print(wordle.getAnswer());
                    LetterResult[] word = wordle.submitGuess(guess);
                    ColorChangeHandler(textFields[current - 4], word[0]);
                    ColorChangeHandler(textFields[current - 3], word[1]);
                    ColorChangeHandler(textFields[current - 2], word[2]);
                    ColorChangeHandler(textFields[current - 1], word[3]);
                    ColorChangeHandler(textFields[current], word[4]);
                    if (!wordle.getAnswer().equals(guess)) {
                        guess = "";
                        current++;
                        if (current == 30 && guess != wordle.getAnswer()) {
                            textFields[current-1].setEditable(false);
                            gameOVERL();
                        } else
                            textFields[current].requestFocus();
                    }
                    if (wordle.getAnswer().equals(guess)) {
                        textFields[current].setEditable(false);
                        gameOVERW();
                    }
                }
            }
        }
        else{
            String current_text = textFields[current].getText();
            current++;
            if (current_text.length() == 1) {
                guess += current_text;
                textFields[current].requestFocus();
                textFields[current-1].setEditable(false);
            }
        }
    }

    @FXML
    protected void ColorChangeHandler(TextField text, LetterResult guess){
        if(guess == LetterResult.GRAY){
            text.setStyle("-fx-text-fill: white; "+"-fx-background-color: #808080");
            text.setFont(Font.font(String.valueOf(FontWeight.BOLD)));
        }
        else if (guess == LetterResult.GREEN) {
            text.setStyle("-fx-text-fill: white; "+"-fx-background-color: #00A300");
            text.setFont(Font.font(String.valueOf(FontWeight.BOLD)));
        }
        else if (guess == LetterResult.YELLOW) {
            text.setStyle("-fx-text-fill: white; "+"-fx-background-color:  #FEDE00");
            text.setFont(Font.font(String.valueOf(FontWeight.BOLD)));
        }
    }
    @FXML
    public void gameOVERL(){
        String ans = wordle.getAnswer();
        WORDLE.setText("GAME OVER");
        WORDLE.setStyle("-fx-text-fill: red");
        state_of_game.setText("LOSER! Correct answer was " + ans);
        state_of_game.setAlignment(Pos.CENTER);
        state_of_game.setFont(Font.font(10));
        state_of_game.setFont(Font.font(String.valueOf(FontWeight.BOLD)));
        state_of_game.setVisible(true);
        ERROR.setText("Want to play again??");
        ERROR.setAlignment(Pos.CENTER);
        propmtedQuestion.setText("Type 'y' for YES and 'n' for NO  ");
        propmtedQuestion.setFont(Font.font("Arial Black"));
        propmtedQuestion.setFont(Font.font(String.valueOf(FontWeight.BOLD)));
        propmtedQuestion.setFont(Font.font(15));
        propmtedQuestion.setAlignment(Pos.BOTTOM_CENTER);
        propmtedQuestion.setVisible(true);
        YES.setVisible(true);
        YES.setOnAction(event -> initialize());
        NO.setVisible(true);
        NO.setOnAction(event -> initialize());

    }

    @FXML
    public void gameOVERW(){
        WORDLE.setText("GAME OVER");
        state_of_game.setText("WINNER!");
        state_of_game.setStyle("-fx-text-fill: gold");
        state_of_game.setFont(Font.font(String.valueOf(FontWeight.EXTRA_BOLD)));
        state_of_game.setFont(Font.font(24));
        state_of_game.setAlignment(Pos.CENTER);
        state_of_game.setVisible(true);
        ERROR.setText("Want to play again??");
        ERROR.setAlignment(Pos.CENTER);
        propmtedQuestion.setText("Type 'y' for YES and 'n' for NO  ");
        propmtedQuestion.setFont(Font.font("Arial Black"));
        propmtedQuestion.setFont(Font.font(String.valueOf(FontWeight.BOLD)));
        propmtedQuestion.setFont(Font.font(15));
        propmtedQuestion.setAlignment(Pos.BOTTOM_CENTER);
        propmtedQuestion.setVisible(true);
        YES.setVisible(true);
        YES.requestFocus();
        YES.setOnAction(event -> initialize());
        NO.setVisible(true);
        NO.requestFocus();
        NO.setOnAction(event -> initialize());

    }
}

