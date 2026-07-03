package arkanoid.levels;

import biuoop.DrawSurface;
import java.awt.Color;
import arkanoid.game.Sprite;

/**
 * BackgroundSprite draws a solid color background.
 */
public class BackgroundSprite implements Sprite {
    private final Color color;

    /**
     * Construct a BackgroundSprite with the given color.
     *
     * @param color the background color
     */
    public BackgroundSprite(Color color) {
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    @Override
    public void timePassed() {
        // Background does not change over time
    }
}
