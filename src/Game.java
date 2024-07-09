import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import java.awt.Color;
import java.util.List;

/**
 * Game class.
 * The class represents a game with a collection of sprites and a game environment.
 * The class has methods to add collidables and sprites to the game, initialize the game and run the game.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Counter remainingBalls;
    private Counter remainingBlocks;
    private Counter score;
    private List<Counter> rowCounters;
    private PlaySound backgroundMusic;

    //add collidable to the game environment

    /**
     * Add a collidable to the game environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    //add sprite to the sprites collection

    /**
     * Add a sprite to the game.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    // and add them to the game.

    /**
     * Initialize the game.
     */
    public void initialize() {
        //initialize the row counters
        rowCounters = new java.util.ArrayList<>();
        //create the gui
        this.gui = new GUI("Arkanoid", 800, 600);
        //creat the screen as a block
        Screen screen = new Screen(800, 600, Color.BLUE);
        //create a death region as a block
        Block deathRegion = new Block(new Point(0, 599), 800, 1, Color.BLACK);
        //ball remover
        remainingBalls = new Counter(3);
        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        deathRegion.addHitListener(ballRemover);
        //create a counter for blocks
        remainingBlocks = new Counter(63);
        //create block remover
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        // creat a counter for the score
        score = new Counter(0);
        //creat a score tracking listener
        HitListener scoreTrackingListener = new ScoreTrackingListener(score);
        //initialize the game environment
        this.environment = new GameEnvironment();
        //initialize the sprites collection
        this.sprites = new SpriteCollection();
        screen.addToGame(this);
        deathRegion.addToGame(this);
        //initialize the paddle
        Paddle paddle = new Paddle(200, 20, Color.YELLOW, 5);
        paddle.setKeyboard(gui.getKeyboardSensor());
        paddle.addToGame(this);
        //initialize the balls
        for (int i = 0; i < 3; i++) {
            Ball ball = new Ball(new Point((400 + i * 25) % 600, (400 - i * 25) % 800), 5, Color.WHITE, environment);
            ball.setVelocity(2 * Math.pow(-1, i), 2);
            //add the ball to the game
            ball.addToGame(this);
        }


        /* initialize the blocks by pattern 6 descending rows, first has 12 block, second 11 and so on
        each row has a different color */
        //enum the selected colors for the rows
        Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.GRAY, Color.YELLOW, Color.PINK};
        for (int i = 0; i < 6; i++) {
            //add row counter
            rowCounters.add(new Counter(12 - i));
            for (int j = 0; j < 12 - i; j++) {
                //make each row a different color noticbley different from the previous row
                Block block = new Block(new Point(800 - 50 - j * 50, 100 + i * 25), 50, 25, colors[i]);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreTrackingListener);
                block.setRow(rowCounters.get(i));
                block.addToGame(this);
            }
        }

        //initialize the background music
        backgroundMusic = new PlaySound(true, "backgroundMusic.wav");
        backgroundMusic.play();

    }

    // Run the game -- start the animation loop

    /**
     * Run the game.
     */
    public void run() {
        //initialize the sleeper
        Sleeper sleeper = new Sleeper();

        //initialize the game
        initialize();
        //set paddle keyboard sensor

        //initialize the frames per second and milliseconds per frame
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            long startTime = System.currentTimeMillis(); // timing
            //no balls left
            if (remainingBalls.getValue() == 0 || remainingBlocks.getValue() == 0) {
                DrawSurface d = gui.getDrawSurface();
                backgroundMusic.stop();
                backgroundMusic.close();
                //create a new screen with the message
                Screen screen = new Screen(800, 600, Color.RED);
                //draw screen
                screen.drawOn(d);
                gui.show(d);
                DrawSurface e = gui.getDrawSurface();
                //play game over sound
                if (remainingBalls.getValue() == 0) {
                    PlaySound gameOverSound = new PlaySound(false, "GameOver.wav");
                    gameOverSound.play();
                    //wait for 2 seconds
                    sleeper.sleepFor(2000);

                    screen.drawOn(e);

                    //write a message - game over
                    e.drawText(300, 300, "Game Over", 32);
                } else {
                    PlaySound youWonSound = new PlaySound(true, "YouWon.wav");
                    youWonSound.play();
                    //wait for 2 seconds
                    sleeper.sleepFor(2000);
                    //write a message - you won
                    d.drawText(300, 300, "You Won!", 32);
                }
                //draw the score
                e.drawText(300, 350, "Your score is: " + score.getStrValue(), 32);
                gui.show(e);
                //wait for 2 seconds
                sleeper.sleepFor(2000);
                gui.close();
                break;
            }
            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            // print the score on the top of the screen
            d.drawText(400, 20, "Score:", 16);
            // if row is cleared add 100 points
            //temp counter to store the row counter
            Counter tempCounter = null;
            for (Counter rowCounter : rowCounters) {
                if (rowCounter.getValue() == 0) {
                    score.increase(100);
                    //store the row counter to remove it later
                    tempCounter = rowCounter;
                }
            }
            //remove the row counter
            if (tempCounter != null) {
                rowCounters.remove(tempCounter);
            }
            // print the current score on the top of the screen
            d.drawText(450, 20, score.getStrValue(), 16);
            // check if all the blocks are cleared and add 100 points
            if (remainingBlocks.getValue() == 0) {
                score.increase(100);
            }
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
    //  removeCollidable method

    /**
     * Remove a collidable from the game environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
    // removeSprite method

    /**
     * Remove a sprite from the game.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    // unwinnable scenario: more than one block of the same color remains but all balls are the same color
    // the game will never end because the balls will never pop the blocks
    // the game will be stuck in an infinite loop

}
