package arkanoid.listeners;

import arkanoid.sprites.Block;
import arkanoid.sprites.Ball;
import arkanoid.util.Counter;
import arkanoid.levels.Constants;

/**
 * ScoreTrackingListener class.
 */
public class ScoreTrackingListener implements HitListener {
    // fields
    private Counter currentScore;

    // constructor
    /**
     * Construct a score tracking listener.
     *
     * @param scoreCounter the counter of the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    // methods
    /**
     * This method is called whenever a hit occurs.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that is hitting the block
     */
    // increase the score by points per hit for hitting a block
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(Constants.POINTS_PER_HIT);
    }
}
