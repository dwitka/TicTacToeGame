package com.example.a00.tictactoegame;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    static String x;
    static String o;
    static String[] List;
    static Random random;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List = new String[9];
        x = "x";
        o = "o";

    }


    @Override
    public void onClick(View view) {
        int buttonId = view.getId();
        Button button = (Button)findViewById(buttonId);
        Toast.makeText(context, String.valueOf(buttonId), Toast.LENGTH_LONG).show();
        //List[buttonId] = x;
        button.setBackgroundResource(R.drawable.x_icon);
        button.setAlpha(1.0f);
        computerMoves(context, buttonId);
    }

    public static void computerMoves(Context context,int buttonId){
        for (int index=0; index<9; index++ ){

        }

    }

}
