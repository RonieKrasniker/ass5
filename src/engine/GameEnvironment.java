package engine;
/**
 * engine.GameEnvironment class
 * This class is used to create the game environment.
 * During the game, there are going to be many objects a sprites.Ball can collide with.
 * The engine.GameEnvironment class is Java collection of such things.
 * The ball will know the game environment, and will use it to check for collisions and direct its movement.
 */


import geometry.Collidable;
import geometry.CollisionInfo;
import geometry.Line;
import geometry.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * engine.GameEnvironment class
 * This class is used to create the game environment.
 * During the game, there are going to be many objects a sprites.Ball can collide with.
 * The engine.GameEnvironment class is Java collection of such things.
 * The ball will know the game environment, and will use it to check for collisions and direct its movement.
 */
public class GameEnvironment {
    //fields
    private final LinkedList<Collidable> collidables;

    // constructor
    // create a new game environment with an empty collection of collidable objects

    /**
     * Construct a game environment.
     */
    public GameEnvironment() {
        this.collidables = new java.util.LinkedList<Collidable>();
    }
    // create a new game environment with a given collection of collidable objects

    /**
     * Construct a game environment from a given collection of collidable objects.
     *
     * @param collidables the collection of collidable objects
     */
    public GameEnvironment(LinkedList<Collidable> collidables) {
        this.collidables = collidables;
    }

    // add the given collidable to the environment.

    /**
     * Add a collidable to the game environment.
     *
     * @param c the collidable to add
     */
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }
    //get the collidables collection
    /**
     * Get the collidables collection.
     *
     * @return the collidables collection
     */
    public List<Collidable> getCollidables() {
        return this.collidables;
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.

    /**
     * Get the closest collision to a given trajectory.
     *
     * @param trajectory the trajectory to check
     * @return the closest collision to the trajectory
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //initialize the closest collision info
        CollisionInfo closestCollision = null;
        //initialize the closest distance
        double closestDistance = Double.MAX_VALUE;
        //loop through the collidables
        for (Collidable c : this.collidables) {
            //get the collision point
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            //if there is a collision point
            if (collisionPoint != null) {
                //calculate the distance
                double distance = trajectory.start().distance(collisionPoint);
                //if the distance is smaller than the closest distance
                if (distance < closestDistance) {
                    //update the closest distance
                    closestDistance = distance;
                    //update the closest collision info
                    closestCollision = new CollisionInfo(collisionPoint, c);
                }
            }
        }
        //return the closest collision info
        return closestCollision;

    }
    // removeCollidable method
    // remove the given collidable from the environment.
    /**
     * Remove a collidable from the game environment.
     *
     * @param c the collidable to remove
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }
}
