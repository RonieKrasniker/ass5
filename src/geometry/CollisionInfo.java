package geometry;

/**
 * geometry.CollisionInfo class.
 * The geometry.CollisionInfo class will be used to store information about a collision that is going to occur.
 * It will have two methods:
 * 1. collisionPoint - Return the point at which the collision occurs.
 * 2. collisionObject - Return the collidable object involved in the collision.
 */
public class CollisionInfo {
    // fields
    private final Point collisionPoint;
    private final Collidable collisionObject;
    // constructor
    // Construct a collision info object from a given collision point and a collidable object.

    /**
     * Construct a collision info object from a given collision point and a collidable object.
     *
     * @param collisionPoint  the point at which the collision occurs
     * @param collisionObject the collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    // the point at which the collision occurs.

    /**
     * Return the point at which the collision occurs.
     *
     * @return the point at which the collision occurs
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }


    // the collidable object involved in the collision.

    /**
     * Return the collidable object involved in the collision.
     *
     * @return the collidable object involved in the collision
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }

    // get the line of the collision

    /**
     * Return the line of the collision.
     *
     * @return the line of the collision
     */
    public Line collisionLine() {
        //find the line of the collision from the collision object
        for (Line line : this.collisionObject.getCollisionRectangle().getLines()) {
            //if the point is on the line - change the velocity according to the line's angle
            if (line.isOnLine(collisionPoint)) {
                return line;
            }
        }
        //should not get here
        return null;
    }
}
