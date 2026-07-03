package arkanoid.animation;

import biuoop.DrawSurface;
import arkanoid.levels.Constants;
import java.awt.Color;

/**
 * EndScreen class.
 * Displays game over or win screen.
 */
public class EndScreen implements Animation {
    private final boolean won;
    private final int score;

    /**
     * Construct an EndScreen.
     *
     * @param won true if player won, false if game over
     * @param score the final score
     */
    public EndScreen(boolean won, int score) {
        this.won = won;
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(won ? Color.GREEN : Color.RED);
        d.fillRectangle(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        d.setColor(Color.WHITE);
        
        if (won) {
            d.drawText(250, 250, "You Won!", 64);
        } else {
            d.drawText(220, 250, "Game Over", 64);
        }
        
        d.drawText(220, 350, "Your score is: " + score, 32);
        d.drawText(180, 450, "Press space to exit", 24);
    }

    @Override
    public boolean shouldStop() {
        return false; // KeyPressStoppableAnimation handles stopping
    }
}
