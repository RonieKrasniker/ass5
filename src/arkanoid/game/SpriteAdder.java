package arkanoid.game;

/**
 * Interface for objects that can have sprites added to them.
 * Used to break circular dependencies between game objects and the Game class.
 */
public interface SpriteAdder {
    /**
     * Add a sprite to the collection.
     *
     * @param s the sprite to add
     */
    void addSprite(Sprite s);
}
