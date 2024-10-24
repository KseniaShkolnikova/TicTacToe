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

public class MainActivity extends AppCompatActivity {

    ImageButton[] buttons = new ImageButton[9];
    int[] gameBoard = new int[9];
    int currentPlayer = 1;
    boolean gameRunning = true;
    TextView vivvod;
    Button restartButton;
    private int crossWins = 0;
    private int circleWins = 0;
    private int draws = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button ex = findViewById(R.id.exit);
        ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Glavna.class);
                startActivity(intent);
            }
        });
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
        loadStatistics();

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
                openStatisticsActivity();
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
                        switchPlayer();
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
        for (int i = 0; i < 9; i += 3){
            if (gameBoard[i] != 0 && gameBoard[i] == gameBoard[i + 1] && gameBoard[i] == gameBoard[i + 2]) {
                gameOver(currentPlayer);
                return;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i] != 0 && gameBoard[i] == gameBoard[i + 3] && gameBoard[i] == gameBoard[i + 6]) {
                gameOver(currentPlayer);
                return;
            }
        }
        if (gameBoard[0] != 0 && gameBoard[0]== gameBoard[4] && gameBoard[0] == gameBoard[8]) {
            gameOver(currentPlayer);
            return;
        }
        if (gameBoard[2] != 0 && gameBoard[2] == gameBoard[4] && gameBoard[2] == gameBoard[6]) {
            gameOver(currentPlayer);
            return;
        }
        if (isBoardFull()) {
            gameOver(0);
        }
    }

    private void gameOver(int winner) {
        gameRunning = false;
        if (winner == 1) {
            vivvod.setText("Крестики победили!");
            crossWins++;
        } else if (winner == 2) {
            vivvod.setText("Нолики победили!");
            circleWins++;
        } else {
            vivvod.setText("Ничья!");
            draws++;
        }
        saveStatistics();
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 9; i++) {
            if (gameBoard[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer == 1 ? 2 : 1;
    }

    private void saveStatistics() {
        SharedPreferences preferences = getSharedPreferences("gameStats", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("crossWins", crossWins);
        editor.putInt("circleWins", circleWins);
        editor.putInt("draws", draws);
        editor.apply();
    }

    private void loadStatistics() {
        SharedPreferences preferences = getSharedPreferences("gameStats", Context.MODE_PRIVATE);
        crossWins = preferences.getInt("crossWins", 0);
        circleWins = preferences.getInt("circleWins", 0);
        draws = preferences.getInt("draws", 0);
    }

    private void openStatisticsActivity() {
        Intent intent = new Intent(this, StatisticActivity.class);
        intent.putExtra("crossWins", crossWins);
        intent.putExtra("circleWins", circleWins);
        intent.putExtra("draws", draws);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveStatistics();
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadStatistics();
    }
}