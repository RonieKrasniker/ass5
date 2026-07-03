package arkanoid.levels;

/**
 * Constants class.
 * This class contains all the constants used in the game.
 */
public final class Constants {
    // Screen dimensions
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;
    public static final int SCREEN_CENTER_X = SCREEN_WIDTH / 2;
    public static final int SCREEN_CENTER_Y = SCREEN_HEIGHT / 2;
    
    // Ball constants
    public static final int DEFAULT_BALL_RADIUS = 5;
    public static final int INITIAL_BALL_COUNT = 3;
    public static final int BALL_INITIAL_SPEED = 3;
    
    // Block constants
    public static final int BLOCK_WIDTH = 50;
    public static final int BLOCK_HEIGHT = 25;
    public static final int INITIAL_BLOCK_COUNT = 57;
    public static final int MAX_ROWS = 6;
    public static final int MAX_BLOCKS_PER_ROW = 12;
    public static final int FIRST_ROW_Y = 100;
    
    // Paddle constants
    public static final int PADDLE_WIDTH = 200;
    public static final int PADDLE_HEIGHT = 20;
    public static final int PADDLE_SPEED = 5;
    
    // Score constants
    public static final int POINTS_PER_HIT = 5;
    public static final int POINTS_PER_ROW = 100;
    public static final int POINTS_FOR_CLEAR = 100;
    
    // Game constants
    public static final int FRAMES_PER_SECOND = 60;
    public static final int DEATH_REGION_Y = 599;
    public static final int DEATH_REGION_HEIGHT = 1;
    public static final double COLLISION_EPSILON = 0.001; // Small distance from collision point
    
    // UI constants
    public static final int SCORE_TEXT_X = 400;
    public static final int SCORE_TEXT_Y = 20;
    public static final int SCORE_VALUE_X = 450;
    public static final int SCORE_TEXT_SIZE = 16;
    public static final int MESSAGE_X = 300;
    public static final int MESSAGE_Y = 300;
    public static final int MESSAGE_SIZE = 32;
    public static final int SCORE_MESSAGE_Y = 350;
    public static final int END_SCREEN_WAIT_MS = 8000;
    public static final int SOUND_DELAY_MS = 2000;
    
    // File paths
    public static final String BACKGROUND_MUSIC = "backgroundMusic.wav";
    public static final String GAME_OVER_SOUND = "GameOver.wav";
    public static final String YOU_WON_SOUND = "YouWon.wav";
    
    // Window title
    public static final String WINDOW_TITLE = "Arkanoid";
    
    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Cannot instantiate Constants class");
    }
}
