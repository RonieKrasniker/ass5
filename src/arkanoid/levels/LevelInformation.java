package arkanoid.levels;

import arkanoid.physics.Velocity;
import arkanoid.game.Sprite;
import arkanoid.sprites.Block;

/**
 * LevelInformation defines the data required to create a level.
 */
public interface LevelInformation {
    /**
     * @return the number of balls in the level
     */
    int numberOfBalls();

    /**
     * @return list of initial velocities for the balls
     */
    java.util.List<Velocity> initialBallVelocities();

    /**
     * @return the paddle speed
     */
    int paddleSpeed();

    /**
     * @return the paddle width
     */
    int paddleWidth();

    /**
     * @return the level name
     */
    String levelName();

    /**
     * @return the background sprite of the level
     */
    Sprite getBackground();

    /**
     * @return the blocks in the level
     */
    java.util.List<Block> blocks();

    /**
     * @return the number of blocks that must be removed to clear the level
     */
    int numberOfBlocksToRemove();
}
