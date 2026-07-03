package arkanoid.listeners;

import arkanoid.game.GameContext;
import arkanoid.sprites.Block;
import arkanoid.sprites.Ball;
import arkanoid.util.Counter;
import arkanoid.resources.PlaySound;

/**
 * BallRemover class. This class is in charge of removing balls from the game, as well as keeping count
 * of the number of balls that remain in the game.
 */
public class BallRemover implements HitListener {
    // fields
    private GameContext gameLevel;
    private Counter remainingBalls;
    private PlaySound ballLostSound;

    // constructor
    /**
     * Construct a BallRemover object.
     *
    * @param gameLevel the game level
    * @param removedBalls the counter of removed balls
    */
    public BallRemover(GameContext gameLevel, Counter removedBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = removedBalls;
        this.ballLostSound = new PlaySound(false,"BallLost.wav");
    }

    // methods
    /**
     * This method is called whenever a ball is being hit.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that is hitting the block
     */
    // remove the ball from the game and decrease the counter of remaining balls
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
        this.ballLostSound.play();
    }

}
