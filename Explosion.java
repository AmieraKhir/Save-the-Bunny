package com.example.savethebunnyapp;

// import android packages to enable the use of Bitmap in the game
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {

    // public attributes
    public Bitmap explosion[] = new Bitmap[4];
    public int explosionFrame = 0;
    public int explosionX, explosionY;

    // constructor class to initialise values
    public Explosion(Context context){

        // to display 4 different kinds of explosion styles
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode0);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode1);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode2);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode3);
    }

    // function getExplosion that gets explosionFrame as parameter and returns different kinds of explosion styles
    public Bitmap getExplosion(int explosionFrame){

        return explosion[explosionFrame];
}
}