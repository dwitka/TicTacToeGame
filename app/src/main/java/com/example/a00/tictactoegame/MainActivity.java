package com.example.a00.tictactoegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
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

        textview = (TextView) findViewById(R.id.text_view1);
        block_number = 0;
        List = new String[9];
        x = "x";
        o = "o";
        buttonList = new ArrayList<>();
        addButtonsToList();
    }


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


    public void checkGameState(){
        boolean winner = checkForWinner();
        if (winner){
            Button playButton = (Button)findViewById(R.id.button10);
            playButton.setVisibility(View.VISIBLE);
        }else{
            boolean full = isBoardFull();
            if (full){
                Button playButton = (Button)findViewById(R.id.button10);
                playButton.setVisibility(View.VISIBLE);
            }else {
                computersTurn();
            }
        }
    }


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
            Button playButton = (Button) findViewById(R.id.button10);
            playButton.setVisibility(View.VISIBLE);
        }
        block_number++;
    }


    public static String retrieve(int index) {
        if (List[index] == null) {
            return " ";
        }
        return List[index];
    }


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


    public boolean isBoardFull() {
        for (int i=0; i < 9; i++) {
            if (List[i] == null) {
                return false;
            }
        }
        Toast.makeText(this, "It's a Draw!", Toast.LENGTH_LONG).show();
        return true;
    }


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
}

