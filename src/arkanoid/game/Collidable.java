package arkanoid.game;

import arkanoid.physics.Rectangle;
import arkanoid.physics.Velocity;
import arkanoid.physics.Point;
import arkanoid.sprites.Ball;

/**
 * The Collidable interface will be used by things that can be collided with.
 * It will have two methods:
 * 1. getCollisionRectangle - Return the "collision shape" of the object.
 * 2. hit - Notify the object that we collided with it at collisionPoint with
 * a given velocity. The return is the new velocity expected after the hit
 * (based on the force the object inflicted on us).
 */
public interface Collidable {
    // Return the "collision shape" of the object.
    /**
     * Return the "collision shape" of the object.
     *
     * @return the "collision shape" of the object
     */
    Rectangle getCollisionRectangle();
    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).
    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter the ball that hit the block
     * @param collisionPoint the point of collision
     * @param currentVelocity the current velocity
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}