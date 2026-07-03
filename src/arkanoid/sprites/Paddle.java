package arkanoid.sprites;

import biuoop.DrawSurface;
import arkanoid.game.Sprite;
import arkanoid.game.Collidable;
import arkanoid.game.GameContext;
import arkanoid.physics.Point;
import arkanoid.physics.Line;
import arkanoid.physics.Rectangle;
import arkanoid.physics.Velocity;
import arkanoid.levels.Constants;

/**
 * Paddle class.
 * The class represents a paddle with a rectangle and color.
 * The class has methods to move the paddle, draw it on a DrawSurface and get its properties.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle;  // Not final since we need to recreate it for movement
    private final java.awt.Color color;
    private final int speed;


    //constructor

    /**
     * Construct a paddle from a given width, height, color and speed.
     *
     * @param width  the width of the paddle
     * @param height the height of the paddle
     * @param color  the color of the paddle
     * @param speed  the speed of the paddle
     */
    public Paddle(int width, int height, java.awt.Color color, int speed) {
        this.color = color;
        this.speed = speed;
        //start the paddle at the middle of the screen
        this.rectangle = new Rectangle(new Point(Constants.SCREEN_CENTER_X - (double) width / 2, 
            Constants.SCREEN_HEIGHT - height), width, height);
        //set rectangle color
        this.rectangle.setColor(color);
    }

    //setters

    /**
     * Set the keyboard sensor of the paddle.
     *
     * @param k the keyboard sensor to set
     */
    public void setKeyboard(biuoop.KeyboardSensor k) {
        this.keyboard = k;
    }

    /**
     * Move the paddle left by creating a new rectangle.
     */
    public void moveLeft() {
        Point currentPos = this.rectangle.getUpperLeft();
        this.rectangle = new Rectangle(
            new Point(currentPos.getX() - speed, currentPos.getY()),
            this.rectangle.getWidth(),
            this.rectangle.getHeight()
        );
        this.rectangle.setColor(this.color);
    }

    /**
     * Move the paddle right by creating a new rectangle.
     */
    public void moveRight() {
        Point currentPos = this.rectangle.getUpperLeft();
        this.rectangle = new Rectangle(
            new Point(currentPos.getX() + speed, currentPos.getY()),
            this.rectangle.getWidth(),
            this.rectangle.getHeight()
        );
        this.rectangle.setColor(this.color);
    }

    // Sprite

    /**
     * Notify the paddle that time has passed.
     * Check for keyboard input and move the paddle accordingly.
     */
    public void timePassed() {
        if (this.keyboard != null) {
            if (this.keyboard.isPressed(biuoop.KeyboardSensor.LEFT_KEY)) {
                this.moveLeft();
            }
            if (this.keyboard.isPressed(biuoop.KeyboardSensor.RIGHT_KEY)) {
                this.moveRight();
            }
        }
    }

    /**
     * Draw the paddle on a DrawSurface.
     *
     * @param d the DrawSurface to draw on
     */
    public void drawOn(DrawSurface d) {
        //reset position if paddle is fully out of bounds
        if (this.rectangle.getUpperLeft().getX() <= -this.rectangle.getWidth()) {
            //place the paddle at the right edge of the screen
            this.rectangle = new Rectangle(
                new Point(Constants.SCREEN_WIDTH - this.rectangle.getWidth(), this.rectangle.getUpperLeft().getY()),
                this.rectangle.getWidth(),
                this.rectangle.getHeight()
            );
            this.rectangle.setColor(this.color);
        }
        if (this.rectangle.getUpperLeft().getX() >= Constants.SCREEN_WIDTH) {
            //place the paddle at the left edge of the screen
            this.rectangle = new Rectangle(
                new Point(0, this.rectangle.getUpperLeft().getY()),
                this.rectangle.getWidth(),
                this.rectangle.getHeight()
            );
            this.rectangle.setColor(this.color);
        }
        //draw the paddle
        rectangle.drawOn(d);
        /*if the paddle is out of bounds, draw extra paddle on the other side of the screen
        in accordance with the paddle's part that is out of bounds*/
        if (this.rectangle.getUpperLeft().getX() < 0) {
            /*create a new rectangle named "extra" replica of current paddle
             and then set its upper left point to the right edge of the screen
             */
            Rectangle extra = new Rectangle(new Point(Constants.SCREEN_WIDTH + this.rectangle.getUpperLeft().getX(),
                    this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
            //set the color of the extra paddle to the color of the current paddle
            extra.setColor(this.color);
            //draw the extra paddle
            extra.drawOn(d);
        }
        if (this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() > Constants.SCREEN_WIDTH) {
            /* create a new rectangle named "extra" replica of current paddle
             and then set its upper left point to the left edge of the screen */
            Rectangle extra = new Rectangle(new Point(this.rectangle.getUpperLeft().getX() - Constants.SCREEN_WIDTH,
                    this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
            //set the color of the extra paddle to the color of the current paddle
            extra.setColor(this.color);
            //draw the extra paddle
            extra.drawOn(d);
        }
    }


    // Collidable
    /**
     * Return the collision rectangle of the paddle.
     *
     * @return the collision rectangle of the paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param hitter          the ball that hit the block
     * @param collisionPoint  the point of collision
     * @param currentVelocity the current velocity
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //initialize the new velocity
        Velocity newVelocity = currentVelocity;
        //loop through the lines of the rectangle and check if the collision point is on one of them
        for (Line line : this.rectangle.getLines()) {
            //if the point is on the line - change the velocity according to the line's angle
            if (line.isOnLine(collisionPoint)) {

                //split the paddle to five zones, each zone will return a different angle
                int location = (int) (collisionPoint.getX() - this.rectangle.getUpperLeft().getX());

                if (location > 1 && location < this.rectangle.getWidth() / 5) {
                    newVelocity = Velocity.fromAngleAndSpeed(210, currentVelocity.getSpeed());
                } else if (location >= this.rectangle.getWidth() / 5 && location < 2 * this.rectangle.getWidth() / 5) {
                    newVelocity = Velocity.fromAngleAndSpeed(240, currentVelocity.getSpeed());
                } else if (location >= 2 * this.rectangle.getWidth() / 5
                        && location < 3 * this.rectangle.getWidth() / 5) {
                    newVelocity = new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
                } else if (location >= 3 * this.rectangle.getWidth() / 5
                        && location < 4 * this.rectangle.getWidth() / 5) {
                    newVelocity = Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
                } else if (location >= 4 * this.rectangle.getWidth() / 5 && location < this.rectangle.getWidth() - 1) {
                    newVelocity = Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
                } else {
                    newVelocity = currentVelocity.adjustVelocity(line);
                }

                break;
            }
        }
        return newVelocity;
    }

    // Add this paddle to the game.
    /**
     * Add the paddle to a game.
     *
     * @param gameLevel the game level to add the paddle to
     */
    public void addToGame(GameContext gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
    }
}
