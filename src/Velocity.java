//318828175 Aharon Krasniker

// Velocity specifies the change in position on the `x` and the `y` axes.

/**
 * This class represents the velocity of an object in the game.
 * The velocity is represented by the change in position on the `x` and the `y` axes.
 */

public class Velocity {
    // fields

    private double dx;
    private double dy;

    // constructor
    // Velocity specifies the change in position on the `x` and the `y` axes.

    /**
     * Construct a velocity from a given dx and dy values.
     *
     * @param dx the change in position on the x-axis
     * @param dy the change in position on the y-axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // instances
    // Take an angle and a speed and return the dx and dy values of the velocity

    /**
     * Construct a velocity from a given angle and speed.
     *
     * @param angle the angle of the velocity
     * @param speed the speed of the velocity
     * @return the velocity object
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.cos(Math.toRadians(angle)) * speed;
        double dy = Math.sin(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    // accessors
    // Return the dx value of the velocity

    /**
     * Get the change in position on the x-axis.
     *
     * @return the change in position on the x-axis
     */
    public double getDx() {
        return this.dx;
    }

    // Return the dy value of the velocity

    /**
     * Get the change in position on the y-axis.
     *
     * @return the change in position on the y-axis
     */
    public double getDy() {
        return this.dy;
    }

    // Return the speed of the velocity

    /**
     * Get the speed of the velocity.
     *
     * @return the speed of the velocity
     */
    public double getSpeed() {
        return Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    }

    // Return the angle of the velocity

    /**
     * Get the angle of the velocity.
     *
     * @return the angle of the velocity
     */
    public double getAngle() {
        return Math.toDegrees(Math.atan2(this.dy, this.dx));
    }

    //setters
    // set the dx value of the velocity

    /**
     * Set the change in position on the x-axis.
     *
     * @param dx the change in position on the x-axis
     */
    public void setDx(double dx) {
        this.dx = dx;
    }

    // set the dy value of the velocity

    /**
     * Set the change in position on the y-axis.
     *
     * @param dy the change in position on the y-axis
     */
    public void setDy(double dy) {
        this.dy = dy;
    }

    // set the speed of the velocity

    /**
     * Set the speed of the velocity.
     *
     * @param speed the speed of the velocity
     */
    public void setSpeed(double speed) {
        double angle = this.getAngle();
        this.dx = Math.cos(Math.toRadians(angle)) * speed;
        this.dy = Math.sin(Math.toRadians(angle)) * speed;
    }

    // set the angle of the velocity

    /**
     * Set the angle of the velocity.
     *
     * @param angle the angle of the velocity
     */
    public void setAngle(double angle) {
        double speed = this.getSpeed();
        this.dx = Math.cos(Math.toRadians(angle)) * speed;
        this.dy = Math.sin(Math.toRadians(angle)) * speed;
    }

    //swap velocity by case

    /**
     * Swap the horizontal direction of the velocity when hitting the left wall.
     */
    public void hitLeftWall() {
        if (this.dx < 0) {
            this.dx = -this.dx;
        }
    }

    /**
     * Swap the horizontal direction of the velocity when hitting the right wall.
     */
    public void hitRightWall() {
        if (this.dx > 0) {
            this.dx = -this.dx;
        }
    }

    /**
     * Swap the vertical direction of the velocity when hitting the up wall.
     */
    public void hitUpWall() {
        if (this.dy < 0) {
            this.dy = -this.dy;
        }
    }

    /**
     * Swap the vertical direction of the velocity when hitting the down wall.
     */
    public void hitDownWall() {
        if (this.dy > 0) {
            this.dy = -this.dy;
        }
    }
//hit a collisionable object


    //handle the next position may get out of the frame
    // delta is the part of the ball that is out of the frame int the next step

    /**
     * Adjust the velocity according to the collision with a wall.
     * Instead of a ball hitting through the wall, the ball will hit the wall and bounce back.
     *
     * @param p      the point of the collision
     * @param deltaX the part of the ball that is out of the frame in the x-axis
     * @param deltaY the part of the ball that is out of the frame in the y-axis
     * @return the new point after the adjustment
     */
    public Point applyToPoint(Point p, double deltaX, double deltaY) {
        return new Point(p.getX() + deltaX + this.dx, p.getY() + this.dy + deltaY); //dx is negative
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)

    /**
     * Apply the velocity to a point and return the new point.
     * This method is used to calculate the new position of an object after applying the velocity.
     * The new position is calculated by adding the velocity to the current position.
     * This is used to animate the movement of an object.
     *
     * @param p the point to apply the velocity to
     * @return the new point after applying the velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Apply the velocity to a point and return the new point, while adjusting the velocity according to the collision.
     * This method is used to calculate the new position of an object after applying the velocity.
     * The new position is calculated by adding the velocity to the current position, while adjusting the velocity
     * according to the collision with a wall.
     * This is used to animate the movement of an object.
     *
     * @param intersectionPoint the point of the collision (the point where the object hits the wall).
     * @param line              the line of the wall that the object hits.
     *                          The line is used to calculate the angle of the collision.
     * @param r                 the radius of the object.
     *                          The radius is used to calculate the new position of the object after the collision.
     * @param velocity          the velocity of the object. The velocity is adjusted according to the collision.
     * @return the new point after applying the velocity
     */
    public Point applyToPoint(Point intersectionPoint, Line line, double r, Velocity velocity) {
        double lineAngle = Math.toRadians(line.angle());
        double perpendicularAngle = lineAngle + Math.PI / 2;

        double centerX, centerY;

        if (Math.abs(Math.sin(lineAngle)) < 1e-6) { // Horizontal wall
            centerX = intersectionPoint.getX();
            centerY = intersectionPoint.getY() - r * Math.signum(velocity.getDy());
        } else if (Math.abs(Math.cos(lineAngle)) < 1e-6) { // Vertical wall
            centerX = intersectionPoint.getX() - r * Math.signum(velocity.getDx());
            centerY = intersectionPoint.getY();
        } else {
            centerX = intersectionPoint.getX() - r * Math.cos(perpendicularAngle);
            centerY = intersectionPoint.getY() - r * Math.sin(perpendicularAngle);
        }

        return new Point(centerX, centerY);
    } //adjust the velocity according to the collision

    /**
     * Adjust the velocity according to the collision with a wall.
     * Instead of a ball hitting through the wall, the ball will hit the wall and bounce back.
     *
     * @param collisionLine the line of the wall that the object hits
     * @return the new velocity after the adjustment
     */
    public Velocity adjustVelocity(Line collisionLine) {
        // Calculate the angle of the collision line
        double collisionLineAngle = collisionLine.angle();

        // Calculate the angle of incidence
        double incidenceAngle = this.getAngle() - collisionLineAngle;

        // Calculate the angle of reflection
        double reflectionAngle = collisionLineAngle - incidenceAngle;

        // Create a new velocity vector based on the angle of reflection and the magnitude of the original velocity
        return Velocity.fromAngleAndSpeed(reflectionAngle, this.getSpeed());

    }


}