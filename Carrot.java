package com.example.savethebunnyapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Carrot {
    Bitmap carrot[] = new Bitmap[2]; // array to hold carrot images
    int carrotFrame = 0; // current frame of the carrot image
    int carrotX, carrotY, carrotVelocity; // carrot position and velocity
    Random random; // object to generate random numbers

    public Carrot(Context context){ // constructor
        carrot[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.carrot); // load first carrot image
        carrot[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.carrot); // load second carrot image

        random = new Random(); // initialize random number generator
        resetPosition(); // set initial position and velocity of the carrot
    }

    public Bitmap getCarrot(int carrotFrame){ // get the carrot image at the specified frame
        return carrot[carrotFrame];
    }

    public int getCarrotWidth(){ // get the width of the carrot image
        return carrot[0].getWidth();
    }

    public int getCarrotHeight(){ // get the height of the carrot image
        return carrot[0].getHeight();
    }

    public void resetPosition() { // reset the position and velocity of the carrot
        carrotX = random.nextInt(GameView.dWidth - getCarrotWidth()); // set the X-coordinate of the carrot to a random value within the screen width
        carrotY = -200 + random.nextInt(600) * -1; // set the Y-coordinate of the carrot to a random value above the screen
        carrotVelocity = 35 + random.nextInt(16); // set the velocity of the carrot to a random value between 35 and 50
    }
}

