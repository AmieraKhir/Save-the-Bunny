package com.example.savethebunnyapp;

// Represents a level in a game with a level number and a required score to advance to the next level.
class Level {
    private final int levelNumber;
    private final int requiredScore;

    // Constructs a new Level with the given level number and required score.
    public Level(int levelNumber, int requiredScore) {
        this.levelNumber = levelNumber;
        this.requiredScore = requiredScore;
    }

    // Returns the level number of this level.
    public int getLevelNumber() {
        return levelNumber;
    }

    // Returns the required score of this level.
    public int getRequiredScore() {
        return requiredScore;
    }

    // Returns the current level based on the given player score.
    public static int getCurrentLevel(int playerScore) {
        Level level1 = new Level(1, 50);
        Level level2 = new Level(2, 700);
        Level level3 = new Level(3, 1000);

        if (playerScore >= level3.getRequiredScore()) {
            // The player has reached level 3
            return level3.getLevelNumber();
        } else if (playerScore >= level2.getRequiredScore()) {
            // The player has reached level 2
            return level2.getLevelNumber();
        } else {
            // The player has reached level 1 or is still on level 1
            return level1.getLevelNumber();
        }
    }
}
