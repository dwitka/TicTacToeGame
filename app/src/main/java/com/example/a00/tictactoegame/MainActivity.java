package com.example.a00.tictactoegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    static String x;
    static String o;
    static String[] List;
    public static ArrayList<Button> buttonList;
    static Random random;
    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview = (TextView) findViewById(R.id.text_view1);

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
        int tag = Integer.parseInt(String.valueOf(button.getTag()));
        List[tag] = x;
        button.setBackgroundResource(R.drawable.x_icon);
        button.setAlpha(1.0f);
        computersTurn();
    }


    public void computersTurn(){
        textview.setText(R.string.computers_turn);
        random = new Random();
        int rand = random.nextInt(9);
        Button button = buttonList.get(rand);
        if (button.getAlpha() == 0.25f) {
            button.setBackgroundResource(R.drawable.o_icon);
            button.setAlpha(1.0f);
            List[rand] = o;
        } else {
            computersTurn();
        }
        textview.setText(R.string.your_turn);
    }


    public static String retrieve(int index) {
        if (List[index] == null) {
            return " ";
        }
        return List[index];
    }
}
