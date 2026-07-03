package arkanoid.physics;

import arkanoid.levels.Threshold;

/**
 * PhysicsEngine centralizes movement and collision math.
 */
public final class PhysicsEngine {
    private PhysicsEngine() {
    }

    /**
     * Calculate the next point after applying the given velocity.
     *
     * @param point the starting point
     * @param velocity the velocity to apply
     * @return the resulting point
     */
    public static Point applyVelocity(Point point, Velocity velocity) {
        return new Point(point.getX() + velocity.getDx(), point.getY() + velocity.getDy());
    }

    /**
     * Create the trajectory line for a moving object.
     *
     * @param start the starting point
     * @param velocity the velocity
     * @return the trajectory line
     */
    public static Line createTrajectory(Point start, Velocity velocity) {
        return new Line(start, applyVelocity(start, velocity));
    }

    /**
     * Position the object slightly before the collision point to avoid sticking to walls.
     *
     * @param collisionPoint the collision point
     * @param velocity the current velocity
     * @param epsilon the offset distance
     * @return the adjusted point
     */
    public static Point offsetBeforeCollision(Point collisionPoint, Velocity velocity, double epsilon) {
        double speed = velocity.getSpeed();
        if (Threshold.equals(speed, 0)) {
            return collisionPoint;
        }
        double normalizedDx = velocity.getDx() / speed;
        double normalizedDy = velocity.getDy() / speed;
        return new Point(
                collisionPoint.getX() - epsilon * normalizedDx,
                collisionPoint.getY() - epsilon * normalizedDy
        );
    }
}
