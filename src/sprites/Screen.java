package sprites;

import geometry.Point;

/**
 * A screen is a block that is not removed when hit by a ball.
 */
public class Screen extends Block {

    // constructor

    /**
     * Construct a screen from a given width, height and color.
     *
     * @param width  the width of the screen
     * @param height the height of the screen
     * @param color  the color of the screen
     */
    public Screen(int width, int height, java.awt.Color color) {
        super(new Point(0, 0), width, height, color);

    }

    // methods
    //make sure that the screen wont be removed
    /**
     * Return the rectangle of the screen.
     *
     * @return the rectangle of the screen
     */
    @Override
    public boolean ballColorMatch(Ball ball) {

        return true;
    }
}
