package com.example.paraaaa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        Button exit = findViewById(R.id.buttonexit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        int crossWins = getIntent().getIntExtra("crossWins", 0);
        int circleWins = getIntent().getIntExtra("circleWins", 0);
        int draws = getIntent().getIntExtra("draws", 0);

        TextView crossWinsTextView = findViewById(R.id.crossWins);
        TextView circleWinsTextView = findViewById(R.id.circleWins);
        TextView drawsTextView = findViewById(R.id.draws);

        crossWinsTextView.setText("Крестики: " + crossWins);
        circleWinsTextView.setText("Нолики: " + circleWins);
        drawsTextView.setText("Ничьи: " + draws);

    }
}