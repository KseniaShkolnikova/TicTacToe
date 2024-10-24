package com.example.paraaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticActivityBot extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticbot);
        Button exit = findViewById(R.id.buttonexit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivityBot.this, Gamebot.class);
                startActivity(intent);
            }
        });
        TextView crossWinsView = findViewById(R.id.crossWinsbot);
        TextView circleWinsView = findViewById(R.id.circleWinsbot);
        TextView drawsView = findViewById(R.id.drawsbot);

        Intent intent = getIntent();
        int crossWins = intent.getIntExtra("crossWinsbot", 0);
        int circleWins = intent.getIntExtra("circleWinsbot", 0);
        int draws = intent.getIntExtra("draws", 0);

        crossWinsView.setText("Выигрыши крестика: " + crossWins);
        circleWinsView.setText("Выигрыши нолика: " + circleWins);
        drawsView.setText("Ничьи: " + draws);
    }
}