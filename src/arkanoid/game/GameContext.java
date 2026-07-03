package arkanoid.game;

/**
 * Interface combining SpriteAdder and CollidableAdder for game management.
 * Used to decouple sprites/collidables from the concrete game implementation.
 */
public interface GameContext extends SpriteAdder, CollidableAdder {
    /**
     * Remove a sprite from the collection.
     *
     * @param s the sprite to remove
     */
    void removeSprite(Sprite s);
    
    /**
     * Remove a collidable from the collection.
     *
     * @param c the collidable to remove
     */
    void removeCollidable(Collidable c);
}
