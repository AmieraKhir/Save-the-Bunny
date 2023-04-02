package com.example.savethebunnyapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//defining the class GameOver that extends AppCompatActivity
public class GameOver extends AppCompatActivity {

    //declaring instance variables
    TextView tvPoints;
    TextView tvHighest;

    TextView tvLevel;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;


    //Override the onCreate method of AppCompatActivity
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over); //Set the layout for this activity

        //Initialize instance variables with their respective views from the layout
        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest);
        tvLevel = findViewById(R.id.tvLevel);

        //Get the points passed from the previous activity using he intent
        int points = getIntent().getExtras().getInt("points");

        //Set the points text view with the received points
        tvPoints.setText("" + points);

        //Retrieve the saved highest score from the shared preferences
        sharedPreferences = getSharedPreferences("my_pref", 0);
        int highest = sharedPreferences.getInt("highest", 0);

        //Check if the received points are higher than the saved highest score
        //If so, update the highest score with the new points and save it in shared preferences
        if (points > highest) {
            ivNewHighest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest", highest);
            editor.commit();
        }

        // Set the highest score text view with the highest score
        tvHighest.setText("" + highest);

        // Calculate the level for the received points using the Level class method
        int currentLevel = Level.getCurrentLevel(points);

        // Set the level text view with the calculated level
        tvLevel.setText("Level " + currentLevel);
    }

    // Define the restart method that is called when the restart button is clicked
    public void restart(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish this activity to prevent the user from returning to it using the back button
    }

    // Define the exit method that is called when the exit button is clicked
    public void exit(View view) {
        finish(); // Finish this activity to exit}
    }
}