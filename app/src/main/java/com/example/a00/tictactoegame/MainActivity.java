package com.example.a00.tictactoegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
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
}
