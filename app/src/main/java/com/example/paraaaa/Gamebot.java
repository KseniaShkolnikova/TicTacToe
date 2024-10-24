package com.example.paraaaa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Gamebot extends AppCompatActivity {

    ImageButton[] buttons = new ImageButton[9];
    int[] gameBoard = new int[9];
    int currentPlayer = 1;
    boolean gameRunning = true;
    TextView vivvod;
    Button restartButton;
    private int crossWinsbot = 0;
    private int circleWinsbot = 0;
    private int drawsBot = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamebot);

        restartButton = findViewById(R.id.restartButton);
        vivvod = findViewById(R.id.vivvod);
        buttons[0] = findViewById(R.id._0);
        buttons[1] = findViewById(R.id._1);
        buttons[2] = findViewById(R.id._2);
        buttons[3] = findViewById(R.id._3);
        buttons[4] = findViewById(R.id._4);
        buttons[5] = findViewById(R.id._5);
        buttons[6] = findViewById(R.id._6);
        buttons[7] = findViewById(R.id._7);
        buttons[8] = findViewById(R.id._8);
        loadStatisticsBot();

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        Button statisticsButton = findViewById(R.id.statisticsButton);
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStatisticsActivityBot();
            }
        });

        Button exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gamebot.this, Glavna.class);
                startActivity(intent);
            }
        });

        for (int i = 0; i < 9; i++) {
            final int buttonIndex = i;
            buttons[buttonIndex].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (gameRunning && gameBoard[buttonIndex] == 0) {
                        makeMove(buttonIndex);
                        checkWin();
                        if (gameRunning) {
                            botTurn();
                            checkWin();
                        }
                    }
                }
            });
        }
    }

    private void resetGame() {
        gameRunning = true;
        currentPlayer = 1;
        for (int i = 0; i < 9; i++) {
            gameBoard[i] = 0;
            buttons[i].setImageResource(0);
        }
        vivvod.setText("");
    }

    private void makeMove(int buttonIndex) {
        gameBoard[buttonIndex] = currentPlayer;
        Drawable image = currentPlayer == 1 ? getResources().getDrawable(R.drawable.cross) : getResources().getDrawable(R.drawable.circle);
        buttons[buttonIndex].setImageDrawable(image);
    }

    private void checkWin() {
        for (int i = 0; i < 9; i += 3) {
            if (gameBoard[i] != 0 && gameBoard[i] == gameBoard[i + 1] && gameBoard[i] == gameBoard[i + 2]) {
                gameOver(gameBoard[i]);
                return;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i] != 0 && gameBoard[i] == gameBoard[i + 3] && gameBoard[i] == gameBoard[i + 6]) {
                gameOver(gameBoard[i]);
                return;
            }
        }
        if (gameBoard[0] != 0 && gameBoard[0] == gameBoard[4] && gameBoard[0] == gameBoard[8]) {
            gameOver(gameBoard[0]);
            return;
        }
        if (gameBoard[2] != 0 && gameBoard[2] == gameBoard[4] && gameBoard[2] == gameBoard[6]) {
            gameOver(gameBoard[2]);
            return;
        }
        if (isBoardFull()) {
            gameOver(0);
        }
    }
    private void gameOver(int winner) {
        gameRunning = false;
        if (winner == 1) {
            vivvod.setText("Вы победили!");
            crossWinsbot++;
        } else if (winner == 2) {
            vivvod.setText("Бот победил!");
            circleWinsbot++;
        } else {
            vivvod.setText("Ничья!");
            drawsBot++;
        }
        saveStatisticsBot();
    }
    private boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            if (gameBoard[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private void botTurn() {
        Random random = new Random();
        int botMove;
        do {
            botMove = random.nextInt(9);
        } while (gameBoard[botMove] != 0);
        gameBoard[botMove] = 2;
        buttons[botMove].setImageResource(R.drawable.circle);
        currentPlayer = 1;
    }

    private void saveStatisticsBot() {
        SharedPreferences preferences = getSharedPreferences("gameStatsBot", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("crossWinsbot", crossWinsbot);
        editor.putInt("circleWinsbot", circleWinsbot);
        editor.putInt("drawsBot", drawsBot);
        editor.apply();
    }

    private void loadStatisticsBot() {
        SharedPreferences preferences = getSharedPreferences("gameStatsBot", Context.MODE_PRIVATE);
        crossWinsbot = preferences.getInt("crossWinsbot", 0);
        circleWinsbot = preferences.getInt("circleWinsbot", 0);
        drawsBot = preferences.getInt("drawsBot", 0);
    }

    private void openStatisticsActivityBot() {
        Intent intent = new Intent(this, StatisticActivityBot.class);
        intent.putExtra("crossWinsbot", crossWinsbot);
        intent.putExtra("circleWinsbot", circleWinsbot);
        intent.putExtra("draws", drawsBot);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveStatisticsBot();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStatisticsBot();
    }
}
