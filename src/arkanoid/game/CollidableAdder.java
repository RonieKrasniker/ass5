package arkanoid.game;

/**
 * Interface for objects that can have collidables added to them.
 * Used to break circular dependencies between game objects and the Game class.
 */
public interface CollidableAdder {
    /**
     * Add a collidable to the collection.
     *
     * @param c the collidable to add
     */
    void addCollidable(Collidable c);
}
