package arkanoid.sprites;

import biuoop.DrawSurface;
import java.awt.Color;
import arkanoid.game.Sprite;
import arkanoid.game.GameContext;
import arkanoid.listeners.ScoreManager;
import arkanoid.levels.Constants;

/**
 * ScoreIndicator class.
 * Displays the current score on the screen.
 */
public class ScoreIndicator implements Sprite {
    private final ScoreManager scoreManager;

    /**
     * Construct a ScoreIndicator.
     *
     * @param scoreManager the score manager to display
     */
    public ScoreIndicator(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(Constants.SCORE_TEXT_X, Constants.SCORE_TEXT_Y, 
            "Score: " + scoreManager.getScoreValue(), Constants.SCORE_TEXT_SIZE);
    }

    @Override
    public void timePassed() {
        // Score indicator doesn't need to update on time passed
    }

    /**
     * Add this score indicator to the game.
     *
     * @param gameLevel the game level to add to
     */
    public void addToGame(GameContext gameLevel) {
        gameLevel.addSprite(this);
    }
}
