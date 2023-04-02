// package name
package com.example.savethebunnyapp;

// imports context class
import android.content.Context;
// imports bitmap class
import android.graphics.Bitmap;
// imports BitmapFactory class
import android.graphics.BitmapFactory;

// import class Random
import java.util.Random;


public class Spike {
    //object declarations
    Bitmap spike[] = new Bitmap[3];
    int spikeFrame = 0;
    int spikeX, spikeY, spikeVelocity;
    Random random;

    // to initialize spike bitmap array and load 3 bitmap images
    public Spike(Context context){
        spike[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike0);
        spike[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike1);
        spike[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.spike2);
        random = new Random();
        resetPosition();
    }

    // returns bitmap object from the spike array
    public Bitmap getSpike(int spikeFrame){

        return spike[spikeFrame];
    }

    // returns width of the first bitmap object
    public int getSpikeWidth(){

        return spike[0].getWidth();
    }

    // returns height of the first bitmap object
    public int getSpikeHeight(){

        return spike[0].getHeight();
    }

    // set a new random position, x and y coordinates and spikeVelocity for spike object
    public void resetPosition() {
        spikeX = random.nextInt(GameView.dWidth - getSpikeWidth());
        spikeY = -200 + random.nextInt(600) * -1;
        spikeVelocity = 35 + random.nextInt(16);
}
}