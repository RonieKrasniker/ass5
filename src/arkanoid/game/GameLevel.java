package arkanoid.game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import arkanoid.animation.Animation;
import arkanoid.animation.AnimationRunner;
import arkanoid.levels.Constants;
import arkanoid.levels.LevelInformation;
import arkanoid.listeners.BallRemover;
import arkanoid.listeners.BlockRemover;
import arkanoid.listeners.HitListener;
import arkanoid.listeners.ScoreTrackingListener;
import arkanoid.listeners.ScoreManager;
import arkanoid.physics.Point;
import arkanoid.physics.Velocity;
import arkanoid.resources.PlaySound;
import arkanoid.sprites.*;
import arkanoid.ui.Screen;
import arkanoid.util.Counter;

/**
 * GameLevel represents a single level run.
 */
public class GameLevel implements Animation, GameContext {
    private final LevelInformation levelInformation;
    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final ScoreManager scoreManager;
    private final SpriteCollection sprites;
    private final GameEnvironment environment;
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final List<Counter> rowCounters;
    private boolean running;
    private boolean levelCleared;
    private boolean playerFailed;
    private PlaySound backgroundMusic;

    /**
     * Construct a GameLevel.
     *
     * @param levelInformation the level definition
     * @param runner the animation runner
     * @param keyboard the keyboard sensor
     * @param scoreManager the score manager
     */
    public GameLevel(LevelInformation levelInformation, AnimationRunner runner,
                     KeyboardSensor keyboard, ScoreManager scoreManager) {
        this.levelInformation = levelInformation;
        this.runner = runner;
        this.keyboard = keyboard;
        this.scoreManager = scoreManager;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter(levelInformation.numberOfBlocksToRemove());
        this.remainingBalls = new Counter(levelInformation.numberOfBalls());
        this.rowCounters = new ArrayList<>();
        this.running = false;
        this.levelCleared = false;
        this.playerFailed = false;
    }

    /**
     * Initialize the level by creating all sprites and collidables.
     */
    public void initialize() {
        // Background
        addSprite(levelInformation.getBackground());

        // Screen boundaries
        Screen screen = new Screen(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Color.BLUE);
        screen.addToGame(this);

        // Death region
        Block deathRegion = new Block(new Point(0, Constants.DEATH_REGION_Y),
                Constants.SCREEN_WIDTH, Constants.DEATH_REGION_HEIGHT, Color.BLACK);
        deathRegion.addToGame(this);

        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        deathRegion.addHitListener(ballRemover);

        // Block remover and score tracking
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        ScoreTrackingListener scoreListener = scoreManager.createListener();

        // Score indicator
        ScoreIndicator indicator = new ScoreIndicator(scoreManager);
        indicator.addToGame(this);

        // Paddle
        Paddle paddle = new Paddle(levelInformation.paddleWidth(), Constants.PADDLE_HEIGHT,
                Color.YELLOW, levelInformation.paddleSpeed());
        paddle.setKeyboard(this.keyboard);
        paddle.addToGame(this);

        // Balls
        List<Velocity> velocities = levelInformation.initialBallVelocities();
        double startY = Constants.SCREEN_HEIGHT - Constants.PADDLE_HEIGHT - 30;
        double startX = Constants.SCREEN_CENTER_X;
        for (int i = 0; i < velocities.size(); i++) {
            Ball ball = new Ball(new Point(startX + i * 15, startY),
                    Constants.DEFAULT_BALL_RADIUS, Color.WHITE, environment);
            ball.setVelocity(velocities.get(i));
            ball.addToGame(this);
        }

        // Blocks
        assignBlocks(levelInformation.blocks(), blockRemover, scoreListener);

        // Background music
        backgroundMusic = new PlaySound(true, Constants.BACKGROUND_MUSIC);
        backgroundMusic.play();
    }

    private void assignBlocks(List<Block> blocks, BlockRemover blockRemover, HitListener scoreListener) {
        Map<Integer, List<Block>> blocksByRow = new HashMap<>();
        for (Block block : blocks) {
            int rowKey = (int) Math.round(block.getCollisionRectangle().getUpperLeft().getY());
            blocksByRow.computeIfAbsent(rowKey, key -> new ArrayList<>()).add(block);
        }

        for (List<Block> rowBlocks : blocksByRow.values()) {
            Counter rowCounter = new Counter(rowBlocks.size());
            rowCounters.add(rowCounter);
            for (Block block : rowBlocks) {
                block.setRow(rowCounter);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreListener);
                block.addToGame(this);
            }
        }
    }

    /**
     * Run the level (after initialize was called).
     */
    public void run() {
        this.running = true;
        this.runner.run(this);
        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.close();
        }
    }

    /**
     * @return true if the level was cleared
     */
    public boolean isLevelCleared() {
        return levelCleared;
    }

    /**
     * @return true if the player lost all balls
     */
    public boolean isPlayerFailed() {
        return playerFailed;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        sprites.drawAllOn(d);
        sprites.notifyAllTimePassed();

        // Award points for cleared rows once
        Iterator<Counter> iterator = rowCounters.iterator();
        while (iterator.hasNext()) {
            Counter counter = iterator.next();
            if (counter.getValue() == 0) {
                scoreManager.addPoints(Constants.POINTS_PER_ROW);
                iterator.remove();
            }
        }

        if (remainingBlocks.getValue() <= 0) {
            if (!levelCleared) {
                scoreManager.addPoints(Constants.POINTS_FOR_CLEAR);
            }
            levelCleared = true;
            running = false;
        }
        if (remainingBalls.getValue() <= 0) {
            playerFailed = true;
            running = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !running;
    }

    @Override
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    @Override
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    @Override
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    @Override
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }
}
