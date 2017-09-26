package com.example.a00.tictactoegame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.R.attr.drawable;
import static android.R.attr.id;
import static android.R.attr.mipMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    static String x;
    static String o;
    static String[] List;

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
        button.setAlpha(1.0f);
        button.setBackgroundResource(R.drawable.X_icon);
        computerMoves();
    }

    public static void computerMoves(){

    }

}
