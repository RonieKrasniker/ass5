//318828175 Aharon Krasniker
package arkanoid.physics;


// Velocity specifies the change in position on the `x` and the `y` axes.

/**
 * This class represents the velocity of an object in the game.
 * The velocity is represented by the change in position on the `x` and the `y` axes.
 */

public class Velocity {
    // fields

    private final double dx;
    private final double dy;

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

    //setters removed - Velocity is now immutable
    //To modify velocity, create a new Velocity object

    //swap velocity by case - now returns new Velocity objects

    /**
     * Return new velocity with horizontal direction swapped when hitting the left wall.
     * @return new velocity with swapped dx
     */
    public Velocity hitLeftWall() {
        if (this.dx < 0) {
            return new Velocity(-this.dx, this.dy);
        }
        return this;
    }

    /**
     * Return new velocity with horizontal direction swapped when hitting the right wall.
     * @return new velocity with swapped dx
     */
    public Velocity hitRightWall() {
        if (this.dx > 0) {
            return new Velocity(-this.dx, this.dy);
        }
        return this;
    }

    /**
     * Return new velocity with vertical direction swapped when hitting the up wall.
     * @return new velocity with swapped dy
     */
    public Velocity hitUpWall() {
        if (this.dy < 0) {
            return new Velocity(this.dx, -this.dy);
        }
        return this;
    }

    /**
     * Return new velocity with vertical direction swapped when hitting the down wall.
     * @return new velocity with swapped dy
     */
    public Velocity hitDownWall() {
        if (this.dy > 0) {
            return new Velocity(this.dx, -this.dy);
        }
        return this;
    }

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