import java.util.LinkedList;
import java.util.List;

import biuoop.DrawSurface;

/**
 * SpriteCollection class.
 * The class represents a collection of sprites.
 * The class has methods to add and remove sprites from the collection,
 * and to notify all sprites that time has passed and draw all sprites on a DrawSurface.
 */
public class SpriteCollection {
    //fields
    private final LinkedList<Sprite> sprites;
    private final List<Sprite> spritesToRemove;


    //constructor

    /**
     * Construct a sprite collection.
     */
    public SpriteCollection() {
        sprites = new LinkedList<>();
        spritesToRemove = new LinkedList<>();
    }


    /**
     * add a sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        // Add a sprite to the collection.
        sprites.add(s);
    }

    /**
     * remove a sprite from the collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        // Remove a sprite from the collection.
    spritesToRemove.add(s);
    }

    // call timePassed() on all sprites.

    /**
     * Notify all sprites that time has passed.
     */
    public void notifyAllTimePassed() {
        for (Sprite s : sprites) {
            s.timePassed();
        }
        // Remove sprites after iteration
        sprites.removeAll(spritesToRemove);
        spritesToRemove.clear();
    }
    // call drawOn(d) on all sprites.

    /**
     * Draw all sprites on a DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }
}
