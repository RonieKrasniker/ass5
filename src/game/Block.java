import biuoop.DrawSurface;

/**
 * Block class.
 * The class represents a block with a rectangle and color.
 * The class has methods to draw the block on a DrawSurface and get its properties.
 * a block is a rectangle with color
  */
public class Block implements Collidable, Sprite {

    //fields
    private final Rectangle rect;
    private final java.awt.Color color;

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
     * @return the new velocity expected after the hit
     */
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
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
}