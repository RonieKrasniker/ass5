import biuoop.DrawSurface;

/**
 * Paddle class.
 * The class represents a paddle with a rectangle and color.
 * The class has methods to move the paddle, draw it on a DrawSurface and get its properties.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private final Rectangle rectangle;
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
        this.rectangle = new Rectangle(new Point(400 - (double) width / 2, 600 - height), width, height);
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
     * Move the paddle left.
     */
    public void moveLeft() {
        this.rectangle.setUpperLeft(this.rectangle.getUpperLeft().getX() - speed, this.rectangle.getUpperLeft().getY());
    }

    /**
     * Move the paddle right.
     */
    public void moveRight() {
        this.rectangle.setUpperLeft(this.rectangle.getUpperLeft().getX() + speed, this.rectangle.getUpperLeft().getY());
    }

    // Sprite

    /**
     * Notify the paddle that time has passed.
     */
    public void timePassed() {

        if (keyboard.isPressed(biuoop.KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }

        if (keyboard.isPressed(biuoop.KeyboardSensor.RIGHT_KEY)) {

            moveRight();
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
            this.rectangle.setUpperLeft(800 - this.rectangle.getWidth(), this.rectangle.getUpperLeft().getY());
        }
        if (this.rectangle.getUpperLeft().getX() >= 800) {
            //place the paddle at the left edge of the screen
            this.rectangle.setUpperLeft(0, this.rectangle.getUpperLeft().getY());
        }
        //draw the paddle
        rectangle.drawOn(d);
        /*if the paddle is out of bounds, draw extra paddle on the other side of the screen
        in accordance with the paddle's part that is out of bounds*/
        if (this.rectangle.getUpperLeft().getX() < 0) {
            /*create a new rectangle named "extra" replica of current paddle
             and then set its upper left point to the right edge of the screen
             */
            Rectangle extra = new Rectangle(new Point(800 + this.rectangle.getUpperLeft().getX(),
                    this.rectangle.getUpperLeft().getY()), this.rectangle.getWidth(), this.rectangle.getHeight());
            //set the color of the extra paddle to the color of the current paddle
            extra.setColor(this.color);
            //draw the extra paddle
            extra.drawOn(d);
        }
        if (this.rectangle.getUpperLeft().getX() + this.rectangle.getWidth() > 800) {
            /* create a new rectangle named "extra" replica of current paddle
             and then set its upper left point to the left edge of the screen */
            Rectangle extra = new Rectangle(new Point(this.rectangle.getUpperLeft().getX() - 800,
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
     * @param collisionPoint  the point of collision
     * @param currentVelocity the current velocity
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
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
     * @param g the game to add the paddle to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
