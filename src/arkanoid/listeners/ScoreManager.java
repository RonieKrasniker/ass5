package arkanoid.listeners;

import arkanoid.util.Counter;

/**
 * ScoreManager class.
 * Manages the score tracking for the game.
 */
public class ScoreManager {
    private final Counter score;

    /**
     * Construct a ScoreManager.
     */
    public ScoreManager() {
        this.score = new Counter(0);
    }

    /**
     * Get the score counter.
     *
     * @return the score counter
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * Get the current score value.
     *
     * @return the current score value
     */
    public int getScoreValue() {
        return this.score.getValue();
    }

    /**
     * Add points to the score.
     *
     * @param points the points to add
     */
    public void addPoints(int points) {
        this.score.increase(points);
    }

    /**
     * Create a score tracking listener.
     *
     * @return a new ScoreTrackingListener
     */
    public ScoreTrackingListener createListener() {
        return new ScoreTrackingListener(this.score);
    }
}
