package com.example.savethebunnyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    Bitmap background, ground, rabbit; //display images in the game
    Rect rectBackground, rectGround; //define position and size of background and size images
    Context context; //to retrieve resources and perform other task
    Handler handler; //schedule tasks to be run
    final long UPDATE_MILLIS = 30; //time for each frame in game
    Runnable runnable; //update graphic
    Paint textPaint = new Paint(); //render text on screen
    Paint healthPaint = new Paint(); //render the player's health bar
    float TEXT_SIZE = 120; //size of text on screen
    int points = 0; //player score
    int life = 3; //remaining lives
    static int dWidth, dHeight; //width and height of the game's display
    Random random; //generate random numbers for certain game
    float rabbitX, rabbitY; //position of player character
    float oldX;
    float oldRabbitX; //character's position on previous frame
    ArrayList<Spike> spikes;
    ArrayList<Carrot> carrots;
    ArrayList<Explosion> explosions; //game's obstacles and other objects
    Level[] levels = {
            new Level(1, 50),
            new Level(2, 100), //representing level
            new Level(3, 300)
    };
    int currentLevelIndex = 0;
    Level currentLevel = levels[currentLevelIndex]; //representing current level in game
    int score = 0;


    public GameView(Context context) {
        super(context);
        this.context = context;

        //Load the background, ground, and rabbit Bitmap images from the app's resources
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ground = BitmapFactory.decodeResource(getResources(), R.drawable.ground);
        rabbit = BitmapFactory.decodeResource(getResources(), R.drawable.rabbit);

        //Get the device screen size
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;

        //Create Rect objects to define the positions and sizes of the background and ground
        rectBackground = new Rect(0,0,dWidth,dHeight);
        rectGround = new Rect(0, dHeight - ground.getHeight(), dWidth, dHeight);

        //Create a Handler object to manage tasks that need to be run on the UI thread
        handler = new Handler();

        //Post a delayed Runnable to the Handler's message queue to executed after a certain delay
        handler.postDelayed(new Runnable() {
            @Override

            //generate carrots and schedules the next execution
            public void run() {
                generateCarrots();
                handler.postDelayed(this, 5000);
            }
        }, 5000);


        runnable = new Runnable() {

            @Override
            public void run() {

                invalidate(); //View method that invalidates the entire view, causing it to be redrawn on the next frame
            }
        };
        //objects used for drawing text on the screen
        textPaint.setColor(Color.rgb(255, 165, 0));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.kenney_blocks));
        healthPaint.setColor(Color.GREEN);

        //initial position of the rabbit
        random = new Random();
        rabbitX = dWidth / 2 - rabbit.getWidth() / 2;
        rabbitY = dHeight - ground.getHeight() - rabbit.getHeight();
        spikes = new ArrayList<>();
        carrots = new ArrayList<>();
        explosions = new ArrayList<>();
        for (int i=0; i<3; i++){
            Spike spike = new Spike(context);
            Carrot carrot = new Carrot(context);
            spikes.add(spike);
            carrots.add(carrot);
        }
        currentLevel.getLevelNumber();
    }
    //create new Carrot object and add into 'carrots' Arraylist
    private void generateCarrots(){
        Carrot carrot = new Carrot(getContext());
        carrots.add(carrot);
    }

    @Override
    //responsible for drawing tha game screen
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background, null, rectBackground, null);
        canvas.drawBitmap(ground, null, rectGround, null);
        canvas.drawBitmap(rabbit, rabbitX, rabbitY, null);
//        canvas.drawText("Points: " + points, 50, TEXT_SIZE, textPaint);
//        canvas.drawText("Life: " + life, dWidth - 350, TEXT_SIZE, textPaint);
        //       canvas.drawText("Level: " + currentLevel, 50, TEXT_SIZE, textPaint);

        //draw all carrots on the canvas
        for (int i=0; i<carrots.size(); i++){
            canvas.drawBitmap(carrots.get(i).getCarrot(carrots.get(i).carrotFrame), carrots.get(i).carrotX, carrots.get(i).carrotY, null);
            carrots.get(i).carrotFrame++;
            if (carrots.get(i).carrotFrame > 1){
                carrots.get(i).carrotFrame = 0;
            }
            //carrot position based on its velocity, and removes carrot when it reaches the ground
            carrots.get(i).carrotY += carrots.get(i).carrotVelocity;
            if (carrots.get(i).carrotY + carrots.get(i).getCarrotHeight() >= dHeight - ground.getHeight()){
//                life++;
                points += 10; //player get 10 points
                carrots.remove(i);
                score += points;
            }
        }


        //draw all spike on the canvas
        for (int i=0; i<spikes.size(); i++){
            canvas.drawBitmap(spikes.get(i).getSpike(spikes.get(i).spikeFrame), spikes.get(i).spikeX, spikes.get(i).spikeY, null);
            spikes.get(i).spikeFrame++;
            if (spikes.get(i).spikeFrame > 2){
                spikes.get(i).spikeFrame = 0;
            }
            //velocity & resets spike position
            spikes.get(i).spikeY += spikes.get(i).spikeVelocity;
            if (spikes.get(i).spikeY + spikes.get(i).getSpikeHeight() >= dHeight - ground.getHeight()){
                points += 10;
                score += points;
                Explosion explosion = new Explosion(context);
                explosion.explosionX = spikes.get(i).spikeX;
                explosion.explosionY = spikes.get(i).spikeY;
                explosions.add(explosion);
                spikes.get(i).resetPosition();
            }
        }

        //if rabbit collides with any spikes, decrease the player's life by 1
        for (int i=0; i < spikes.size(); i++){
            if (spikes.get(i).spikeX + spikes.get(i).getSpikeWidth() >= rabbitX
                    && spikes.get(i).spikeX <= rabbitX + rabbit.getWidth()
                    && spikes.get(i).spikeY + spikes.get(i).getSpikeWidth() >= rabbitY
                    && spikes.get(i).spikeY + spikes.get(i).getSpikeWidth() <= rabbitY + rabbit.getHeight()){
                life--;
                spikes.get(i).resetPosition();
                if (score >= currentLevel.getRequiredScore()) {
                    currentLevelIndex++;
                    if (currentLevelIndex < levels.length) {
                        currentLevel = levels[currentLevelIndex];
                    }}
                //if player's life reach 0, game over
                if (life == 0){
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("points", points);
                    context.startActivity(intent);
                    ((Activity) context).finish();

//                    Intent newIntent = new Intent(context, GameOver.class);
//                    newIntent.putExtra("currentLevel", currentLevel.getLevelNumber());
//                    context.startActivity(newIntent);
                }
            }
        }

        //draw all explosions on the canvas
        for (int i=0; i<explosions.size(); i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame), explosions.get(i).explosionX,
                    explosions.get(i).explosionY, null);
            explosions.get(i).explosionFrame++;
            if (explosions.get(i).explosionFrame > 3){
                explosions.remove(i);
            }
        }

        if (life == 2){
            healthPaint.setColor(Color.YELLOW);
        } else if(life == 1){
            healthPaint.setColor(Color.RED);
        }
        //draw player's score and life on canvas
        canvas.drawRect(dWidth-200, 30, dWidth-200+60*life, 80, healthPaint);
        canvas.drawText("" + points, 20, TEXT_SIZE, textPaint);
        //creates loop that updates the game state and draws the game screen repeatedly
        handler.postDelayed(runnable, UPDATE_MILLIS);

    }

    @Override
    //whenever user interacts with the screen and receives motion
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        //checks if the touch event occurred below the current y-coordinate of the rabbit, indicating that the touch to control the rabbit.
        if (touchY >= rabbitY){
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldRabbitX = rabbitX;
            }
            //if the action is a finger moving on the screen, the code calculates the horizontal shift of the finger
            if (action == MotionEvent.ACTION_MOVE){
                float shift = oldX - touchX;
                //calculates the new x-coordinate of the rabbit
                float newRabbitX = oldRabbitX - shift;
                //if the new position is less than or equal to 0, the rabbit is moved to the leftmost edge of the screen
                if (newRabbitX <= 0)
                    rabbitX = 0;
                    //else to the right
                else if(newRabbitX >= dWidth - rabbit.getWidth())
                    rabbitX = dWidth - rabbit.getWidth();
                else
                    //or new position based on the finger shift
                    rabbitX = newRabbitX;
            }
        }
        return true;
    }


}
