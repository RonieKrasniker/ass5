package sprites;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import geometry.Collidable;
import geometry.Line;
import engine.Game;
import engine.HitListener;
import engine.HitNotifier;


import java.util.List;
import java.util.ArrayList;

/**
 * sprites.Block class.
 * The class represents a block with a rectangle and color.
 * The class has methods to draw the block on a DrawSurface and get its properties.
 * a block is a rectangle with color
 */
public class Block implements Collidable, Sprite, HitNotifier {

    //fields
    private final Rectangle rect;
    private final java.awt.Color color;
    private List<HitListener> hitListeners;

    //constructor

    /**
     * Construct a block from a given rectangle and color.
     *
     * @param rect  the rectangle of the block
     * @param color the color of the block
     */
    public Block(Rectangle rect, java.awt.Color color) {
        this.rect = rect;
        this.color = color;
        this.rect.setColor(color);
        this.hitListeners = new ArrayList<HitListener>();
    }

    /**
     * Construct a block from a given upper left point, width, height and color.
     *
     * @param upperLeft the upper left point of the block
     * @param width     the width of the block
     * @param height    the height of the block
     * @param color     the color of the block
     */
    public Block(Point upperLeft, double width, double height, java.awt.Color color) {
        this.rect = new Rectangle(upperLeft, width, height);
        this.color = color;
        this.rect.setColor(color);
        this.hitListeners = new ArrayList<HitListener>();
    }

    //accessors

    /**
     * Return the rectangle of the block.
     *
     * @return the rectangle of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    //methods

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint  the point of collision
     * @param currentVelocity the current velocity
     * @param hitter          the ball that hit the block
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //initialize the new velocity
        Velocity newVelocity = currentVelocity;
        //loop through the lines of the rectangle and check if the collision point is on one of them
        for (Line line : this.rect.getLines()) {
            //if the point is on the line - change the velocity according to the line's angle
            if (line.isOnLine(collisionPoint)) {
                //if the line is found us trigonometry to calculate the new velocity
                newVelocity = currentVelocity.adjustVelocity(line);
                break;
            }
        }
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
        }
        return newVelocity;
    }
    //draw the block on the draw surface

    /**
     * Draw the block on a given DrawSurface.
     *
     * @param surface the DrawSurface to draw the block on
     */
    public void drawOn(DrawSurface surface) {
        //draw the rectangle
        this.getCollisionRectangle().drawOn(surface);
    }
    //add the block to the game

    /**
     * Add the block to a game.
     *
     * @param g the game to add the block to
     */
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * notify the block that time has passed.
     */
    public void timePassed() {
        //empty method
    }
    // check if the ball and the block had the same color with this methods ballColorMatch(sprites.Ball
    //ball)

    /**
     * Check if the ball and the block have the same color.
     *
     * @param ball the ball to check if it has the same color as the block
     * @return true if the ball and the block have the same color, false otherwise
     */
    public boolean ballColorMatch(Ball ball) {

        return this.color.equals(ball.getColor());
    }
    //  removeFromGame(engine.Game game) method that removes the block from the game

    /**
     * Remove the block from a game.
     *
     * @param game the game to remove the block from
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    // addHitListener method that adds a engine.HitListener to the block

    /**
     * Add a engine.HitListener to the block.
     *
     * @param hl the engine.HitListener to add
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }
    // removeHitListener method that removes a engine.HitListener from the block

    /**
     * Remove a engine.HitListener from the block.
     *
     * @param hl the engine.HitListener to remove
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
// notifyHit method that notifies all the HitListeners that the block has been hit

    /**
     * Notify all the HitListeners that the block has been hit.
     *
     * @param hitter the ball that hit the block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
        //set the color of the ball to the color of the block
        hitter.setColor(this.color);
    }
}