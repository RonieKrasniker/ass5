package engine;

import sprites.Ball;
import sprites.Block;

/**
 * engine.ScoreTrackingListener class.
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
    // increase the score by 5 points for hitting a block
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);

    }
}
