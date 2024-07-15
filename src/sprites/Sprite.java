package sprites;

import biuoop.DrawSurface;

//In computer graphics and games, a sprites.Sprite is a game object that can be drawn to the screen (and which is
//not just a background image).
//Sprites can be drawn on the screen, and can be notified that time has passed (so that they know to change
//their position / shape / appearance / etc).
// In our design, all of the game objects (sprites.Ball, sprites.Block, sprites.Paddle, ...) are
//Sprites -- they will implement the sprites.Sprite interface
/**
 * Interface for all sprites in the game.
 */
public interface Sprite {
    /**
     * Draw the sprite to the screen.
     * @param d the DrawSurface to draw on
     */
    void drawOn(DrawSurface d);

    /**
     * Notify the sprite that time has passed.
     */
    void timePassed();

}
