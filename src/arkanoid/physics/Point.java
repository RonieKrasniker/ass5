//318828175 Aharon Krasniker
package arkanoid.physics;

import arkanoid.levels.Threshold;

/**
 * A class that handles a point in a 2D space.
 */
public class Point {
    private final double x, y; // x and y coordinates

    /**
     * Constructs a Point with the specified coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance between this point and another point.
     *
     * @param other the other point
     * @return the distance between this point and the other point
     */
    public double distance(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * gets array of points and returns the point with the maximum distance from the current point.
     *
     * @param points array of points
     * @return the point with the maximum distance from the current point
     */

    public Point closestPoint(Point[] points) {
        Point closest = points[0];
        double minDistance = this.distance(points[0]);
        for (Point point : points) {
            double distance = this.distance(point);
            if (distance < minDistance) {
                minDistance = distance;
                closest = point;
            }
        }
        return closest;
    }

    /**
     * gets two points and returns the point with the maximum distance from the current point.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the point with the maximum distance from the current point
     */
    public Point closetPoint(Point p1, Point p2) {
        Point[] points = {p1, p2};
        return this.closestPoint(points);
    }

    /**
     * Checks if this point is equal to another point, using a small threshold (epsilon) for comparison.
     *
     * @param other the other object
     * @return true if the points are considered equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Point)) {
            return false;
        }
        Point otherPoint = (Point) other;
        return Threshold.equals(this.x, otherPoint.x) && Threshold.equals(this.y, otherPoint.y);
    }
    
    /**
     * Returns a hash code for this point.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        long xBits = Double.doubleToLongBits(x);
        long yBits = Double.doubleToLongBits(y);
        return (int) (xBits ^ (xBits >>> 32) ^ yBits ^ (yBits >>> 32));
    }

    /**
     * Returns the x-coordinate of this point.
     *
     * @return the x-coordinate of this point
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this point.
     *
     * @return the y-coordinate of this point
     */
    public double getY() {
        return y;
    }
    //setters removed - Point is now immutable
}
