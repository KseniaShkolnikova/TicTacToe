package com.example.paraaaa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class Glavna extends AppCompatActivity {
    SharedPreferences themeSettings;
    SharedPreferences.Editor settingsEditor;
    ImageButton imageTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavna);
        Button player = findViewById(R.id.buttonplayer);
        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Glavna.this, MainActivity.class);
                startActivity(intent);
            }
        });
        Button bot = findViewById(R.id.buttonbot);
        bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Glavna.this, Gamebot.class);
                startActivity(intent);
            }
        });
        themeSettings = getSharedPreferences("SETTINGS", MODE_PRIVATE);
        settingsEditor = themeSettings.edit();

        imageTheme = findViewById(R.id.imgbtnluna);
        updateImageButton();
        imageTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (themeSettings.getBoolean("MODE_NIGHT_ON", false)) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    settingsEditor.putBoolean("MODE_NIGHT_ON", false).apply();
                    Toast.makeText(Glavna.this, "Тёмная тема отключена", Toast.LENGTH_SHORT).show();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    settingsEditor.putBoolean("MODE_NIGHT_ON", true).apply();
                    Toast.makeText(Glavna.this, "Тёмная тема включена", Toast.LENGTH_SHORT).show();
                }
                updateImageButton();
            }
        });
    }
    private void updateImageButton() {
        if (themeSettings.getBoolean("MODE_NIGHT_ON", false)) {
            imageTheme.setImageResource(R.drawable.dark);
        } else {
            imageTheme.setImageResource(R.drawable.light);
        }
    }
}