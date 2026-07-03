//318828175 Aharon Krasniker
package arkanoid.sprites;

import biuoop.DrawSurface;
import arkanoid.game.Sprite;
import arkanoid.game.GameEnvironment;
import arkanoid.game.GameContext;
import arkanoid.physics.Point;
import arkanoid.physics.Velocity;
import arkanoid.physics.CollisionInfo;
import arkanoid.physics.Line;
import arkanoid.physics.PhysicsEngine;
import arkanoid.levels.Constants;

/**
 * Ball class.
 * The class represents a ball with a center point, radius, color and velocity.
 * The class has methods to move the ball, draw it on a DrawSurface and get its properties.
 */
public class Ball implements Sprite {
    // fields
    private Point center;
    private final int r;
    private  java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment environment;

    // constructor

    /**
     * Constructor.
     *
     * @param center      the center point of the ball
     * @param r           the radius of the ball
     * @param color       the color of the ball
     * @param environment the game environment
     */
    public Ball(Point center, int r, java.awt.Color color, GameEnvironment environment) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0); // default velocity
        this.environment = environment;
    }

    // constructor

    /**
     * Constructor.
     *
     * @param x     the x coordinate of the center point of the ball
     * @param y     the y coordinate of the center point of the ball
     * @param r     the radius of the ball
     * @param color the color of the ball
     */
    public Ball(double x, double y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
    }

    // accessors

    /**
     * Returns the x coordinate of the center point of the ball.
     *
     * @return the x coordinate of the center point of the ball
     */
    public Point getCenter() {
        return this.center;
    }

    /**
     * Returns the x coordinate of the center point of the ball.
     *
     * @return the x coordinate of the center point of the ball
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * Returns the y coordinate of the center point of the ball.
     *
     * @return the y coordinate of the center point of the ball
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * Returns the radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Returns the velocity of the ball.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Returns the color of the ball.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    //setters


    /**
     * Sets the game environment of the ball.
     *
     * @param environment the game environment of the ball
     */
    public void setGameEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param dx the change in x coordinate of the velocity
     * @param dy the change in y coordinate of the velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    //set the center of the ball

    /**
     * Sets the center point of the ball.
     *
     * @param center the new center point of the ball
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * Sets the center point of the ball by velocity.
     */

    public void moveOneStep() {
        //get nearest point from trajectory calculation
        Line trajectory = PhysicsEngine.createTrajectory(this.center, this.velocity);
        CollisionInfo collision = this.environment.getClosestCollision(trajectory);
        if (collision == null) {
            // No collision detected, move ball normally
            this.center = PhysicsEngine.applyVelocity(this.center, this.velocity);
        } else {
            Point collisionPoint = collision.collisionPoint();
            
            // Move ball to just before collision point (avoid getting stuck in wall)
            this.center = PhysicsEngine.offsetBeforeCollision(collisionPoint, this.velocity,
                    Constants.COLLISION_EPSILON);
            
            //notify the object that we collided with it and get new velocity
            this.velocity = collision.collisionObject().hit(this, collisionPoint, this.velocity);
        }
    }


// draw the ball on the given DrawSurface

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw the ball on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }


    /**
     * Returns the intersection point of the ball with a line.
     *
     * @param l1 the line to check for intersection with the ball
     * @return the intersection point of the ball with the line
     */
    public Point getIntersectionPoint(Line l1) {
        double x0 = this.center.getX() + this.velocity.getDx();
        double y0 = this.center.getY() + this.velocity.getDy();
        double x1 = l1.start().getX();
        double y1 = l1.start().getY();
        double x2 = l1.end().getX();
        double y2 = l1.end().getY();
        double radius = this.r;

        // Translate points to circle's coordinate system
        double dx = x2 - x1;
        double dy = y2 - y1;
        double fx = x1 - x0;
        double fy = y1 - y0;

        double a = dx * dx + dy * dy;
        double b = 2 * (fx * dx + fy * dy);
        double c = (fx * fx + fy * fy) - radius * radius;

        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            // No intersection
            return null;
        } else {
            // Discriminant is non-negative, compute the two intersection points
            discriminant = Math.sqrt(discriminant);

            double t1 = (-b - discriminant) / (2 * a);
            double t2 = (-b + discriminant) / (2 * a);

            boolean validT1 = t1 >= 0 && t1 <= 1;
            boolean validT2 = t2 >= 0 && t2 <= 1;

            if (validT1 && validT2) {
                // Both intersections are within the segment, return the midpoint
                double ix1 = x1 + t1 * dx;
                double iy1 = y1 + t1 * dy;
                double ix2 = x1 + t2 * dx;
                double iy2 = y1 + t2 * dy;
                return new Point((int) ((ix1 + ix2) / 2), (int) ((iy1 + iy2) / 2));
            } else if (validT1) {
                // Only t1 is within the segment, return this point
                double ix1 = x1 + t1 * dx;
                double iy1 = y1 + t1 * dy;
                return new Point((int) ix1, (int) iy1);
            } else if (validT2) {
                // Only t2 is within the segment, return this point
                double ix2 = x1 + t2 * dx;
                double iy2 = y1 + t2 * dy;
                return new Point((int) ix2, (int) iy2);
            } else {
                // No valid intersections within the segment
                return null;
            }
        }
    }

// notify the ball that time has passed

    /**
     * Notify the ball that time has passed.
     */
    public void timePassed() {
        this.moveOneStep();
    }
//add the ball to the game

    /**
     * Add the ball to a game.
     *
     * @param gameLevel the game level to add the ball to
     */
    public void addToGame(GameContext gameLevel) {
        gameLevel.addSprite(this);
    }
    // remove the ball from the game
    /**
     * Remove the ball from a game.
     *
     * @param gameLevel the game level to remove the ball from
     */
    public void removeFromGame(GameContext gameLevel) {
        gameLevel.removeSprite(this);
    }
    // set ball color
    /**
     * Set the color of the ball.
     *
     * @param c the color of the ball
     */
    public void setColor(java.awt.Color c) {
        this.color = c;
    }
}


