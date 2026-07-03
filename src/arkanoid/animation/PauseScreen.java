package arkanoid.animation;

import biuoop.DrawSurface;
import arkanoid.levels.Constants;
import java.awt.Color;

/**
 * PauseScreen class.
 * Displays a pause screen.
 */
public class PauseScreen implements Animation {
    /**
     * Construct a PauseScreen.
     */
    public PauseScreen() {
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        d.setColor(Color.WHITE);
        d.drawText(200, 300, "Paused -- Press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return false; // KeyPressStoppableAnimation handles stopping
    }
}
