package com.premus.a00.tictactoegame;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/* Class Declaration */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * Class Fields
     * List holds an abstract version of the current game state
     * buttonList is an array of game button objects
     */
    static String x;
    static String o;
    static String[] List;
    public static ArrayList<Button> buttonList;
    static Random random;
    static int block_number;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /* set field values */
        textview = (TextView) findViewById(R.id.text_view1);
        block_number = 0;
        List = new String[9];
        x = "x";
        o = "o";
        buttonList = new ArrayList<>();
        addButtonsToList();
    }

    /**
     * Populate the buttonList with game button objects
     * (these are the graphic x's and o's)
     */
    public void addButtonsToList() {
        Button button1 = (Button)findViewById(R.id.button1);
        buttonList.add(0, button1);
        Button button2 = (Button)findViewById(R.id.button2);
        buttonList.add(1, button2);
        Button button3 = (Button)findViewById(R.id.button3);
        buttonList.add(2, button3);
        Button button4 = (Button)findViewById(R.id.button4);
        buttonList.add(3, button4);
        Button button5 = (Button)findViewById(R.id.button5);
        buttonList.add(4, button5);
        Button button6 = (Button)findViewById(R.id.button6);
        buttonList.add(5, button6);
        Button button7 = (Button)findViewById(R.id.button7);
        buttonList.add(6, button7);
        Button button8 = (Button)findViewById(R.id.button8);
        buttonList.add(7, button8);
        Button button9 = (Button)findViewById(R.id.button9);
        buttonList.add(8, button9);
    }

    /**
     * on click set button to x, make 100% visible, make un-clickable
     * @param view this view represents the button clicked
     */
    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        Button button = (Button)findViewById(buttonId);
        List[buttonList.indexOf(button)] = x;
        button.setBackgroundResource(R.drawable.x_icon);
        button.setAlpha(1.0f);
        button.setClickable(false);
        checkGameState();
    }

    /**
     * After player's turn game is checked for winner
     */
    public void checkGameState(){
        boolean winner = checkForWinner();
        if (winner){
            setButtonToVisible();
            setAllUnClickable();
        }else{
            boolean full = isBoardFull();
            if (full){
                setButtonToVisible();
                setAllUnClickable();
            }else {
                computersTurn();
            }
        }
    }

    /**
     * computer moves after player moves, on computers second move
     * it tries to block a potential win by player
     */
    public void computersTurn(){
        if (block_number == 1) {
            int index = block();
            if (index == 100){
                computersPick();
            }else {
                Button button = buttonList.get(index);
                button.setBackgroundResource(R.drawable.o_icon);
                button.setAlpha(1.0f);
                button.setClickable(false);
            }
        }else {
            computersPick();
        }
        boolean winner = checkForCompWinner();
        if (winner) {
            setButtonToVisible();
            setAllUnClickable();
        }
        block_number++;
    }

    /**
     * This method applies only to the 'play again' button.
     * Button becomes visible on game completion.
     */
    public void setButtonToVisible(){
        Button playButton = (Button) findViewById(R.id.button10);
        playButton.setVisibility(View.VISIBLE);
    }

    /**
     * Method contributes to the UX, is called on game
     * completion to preserve final game state.
     */
    public void setAllUnClickable() {
        for (int index = 0; index < 9; index++) {
            Button button = buttonList.get(index);
            button.setClickable(false);
        }
    }

    /**
     * @param index integer of the index to be retrieved
     * @return retrieve item at index either 'x', 'o', or ' '.
     */
    public static String retrieve(int index) {
        if (List[index] == null) {
            return " ";
        }
        return List[index];
    }

    /**
     * @return a boolean stating whether player has won
     */
    public boolean checkForWinner() {
        String[] gameLines = getGameLines();

        for (int i=0; i < 8; i++) {
            if (gameLines[i].equals("xxx")) {
                Toast.makeText(this, "You win!", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    /**
     * @return a boolean stating whether the computer has won
     */
    public boolean checkForCompWinner() {
        String[] gameLines = getGameLines();

        for (int i=0; i < 8; i++) {
            if (gameLines[i].equals("ooo")) {
                Toast.makeText(this, "Computer wins!", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    /**
     * Method is called on computers 2nd turn to block a players potential win
     * @return index of square that computer will block or 100
     * (100 signifies that there is no block move)
     */
    public int block() {
        String[] gameLines = getGameLines();
        int index;
        index = blockEnd(gameLines);
        if (index != 100) {
            return index;
        }
        index = blockMiddle(gameLines);
        if (index != 100) {
            return index;
        }
        index = blockBeginning(gameLines);
        if (index != 100) {
            return index;
        }
        return 100;
    }

    /**
     * Check if game board is saturated
     * @return boolean
     */
    public boolean isBoardFull() {
        for (int i=0; i < 9; i++) {
            if (List[i] == null) {
                return false;
            }
        }
        Toast.makeText(this, "It's a Draw!", Toast.LENGTH_LONG).show();
        return true;
    }

    /**
     * Method picks a square at random for the computer to play
     */
    public void computersPick(){
        random = new Random();
        int rand = random.nextInt(9);
        Button button = buttonList.get(rand);
        if (button.getAlpha() == 0.25f) {
            button.setBackgroundResource(R.drawable.o_icon);
            button.setAlpha(1.0f);
            button.setClickable(false);
            List[rand] = o;
        } else {
            computersPick();
        }
    }

    /**
     * @return a list of all the current lines that exist on the game board
     * (a line is any sequence of three squares ex. 'ooo', 'xx ', 'oxo', ' o ', etc.)
     */
    public String[] getGameLines(){
        String line0 = retrieve(0) + retrieve(1) + retrieve(2);
        String line1 = retrieve(3) + retrieve(4) + retrieve(5);
        String line2 = retrieve(6) + retrieve(7) + retrieve(8);
        String line3 = retrieve(0) + retrieve(3) + retrieve(6);
        String line4 = retrieve(1) + retrieve(4) + retrieve(7);
        String line5 = retrieve(2) + retrieve(5) + retrieve(8);
        String line6 = retrieve(0) + retrieve(4) + retrieve(8);
        String line7 = retrieve(2) + retrieve(4) + retrieve(6);

        String[] gameLines = {line0,line1,line2,line3,line4,line5,line6,line7};
        return gameLines;
    }

    /**
     * Method blocks a players potential win at end of line 'xx '
     * @param gameLines current representation of game board as list of lines
     * @return the index of game square to block
     */
    public int blockEnd(String[] gameLines){
        for (int i=0; i < 8; i++) {
            if (gameLines[i].equals("xx ")) {
                if (i == 0) {
                    List[2] = o;
                    return 2;
                }
                if (i == 1) {
                    List[5] = o;
                    return 5;
                }
                if (i == 2 || i == 5 || i == 6) {
                    List[8] = o;
                    return 8;
                }
                if (i == 3 || i == 7) {
                    List[6] = o;
                    return 6;
                }
                if (i == 4) {
                    List[7] = o;
                    return 7;
                }
            }
        }
        return 100;
    }

    /**
     * Method blocks a players potential win at middle of line  'x x'
     * @param gameLines current representation of game board as list of lines
     * @return the index of game square to block
     */
    public int blockMiddle(String[] gameLines){
        for (int i=0; i < 8; i++) {
            if (gameLines[i].equals("x x")) {
                if (i == 0) {
                    List[1] = o;
                    return 1;
                }
                if (i == 1 || i == 4 || i == 6 || i == 7) {
                    List[4] = o;
                    return 4;
                }
                if (i == 2) {
                    List[7] = o;
                    return 7;
                }
                if (i == 3) {
                    List[3] = o;
                    return 3;
                }
                if (i == 5) {
                    List[5] = o;
                    return 5;
                }
            }
        }
        return 100;
    }

    /**
     * Method blocks a players potential win at beginning of line  ' xx'
     * @param gameLines current representation of game board as list of lines
     * @return the index of game square to block
     */
    public int blockBeginning(String[] gameLines){
        for (int i=0; i < 8; i++) {
            if (gameLines[i].equals(" xx")) {
                if (i==0 || i ==3|| i==6) {
                    List[0] = o;
                    return 0;
                }
                if (i==1) {
                    List[3] = o;
                    return 3;
                }
                if (i==2) {
                    List[6] = o;
                    return 6;
                }
                if (i==4) {
                    List[1] = o;
                    return 1;
                }
                if (i==5 || i==7) {
                    List[2] = o;
                    return 2;
                }
            }
        }
        return 100;
    }

    /**
     * on click method clears squares and resets the game
     * @param view current view of game
     */
    public void playAgain(View view){
        for (int index =0;index <9; index++) {
            List[index] = null;
            Button button = buttonList.get(index);
            button.setAlpha(.25f);
            button.setClickable(true);
            if (index%2 == 0) {
                button.setBackgroundResource(R.drawable.x_icon);
            }else {
                button.setBackgroundResource(R.drawable.o_icon);
            }
        }
        String[] gameLines = getGameLines();
        for (int index =0;index <8; index++){
            gameLines[index] = null;
        }
        Button playButton = (Button)findViewById(R.id.button10);
        playButton.setVisibility(View.INVISIBLE);
        block_number = 0;
    }

    /**
     * Saves State on activity transition, screen rotation, and shutdown
     * @param outState Bundle in which to place your saved state.
     * @param outPersistentState State which will be saved across reboots.
     */
    @Override
    public void onSaveInstanceState(Bundle outState,
                                    PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    /**
     * Retrieves State following activity transition, screen rotation, and reboot
     * @param savedInstanceState Bundle of data saved from previous activity
     * @param persistentState State that is retrieved after reboot
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState,
                                       PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }
}

