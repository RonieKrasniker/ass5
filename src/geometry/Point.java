//318828175 Aharon Krasniker

/**
 * A class that handles a point in a 2D space.
 */
public class Point {
    private double x, y; // x and y coordinates

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
     * @param other the other point
     * @return true if the points are considered equal, false otherwise
     */
    public boolean equals(Point other) {
        double epsilon = 1e-10; // A small threshold value for comparison
        return Math.abs(this.x - other.x) < epsilon && Math.abs(this.y - other.y) < epsilon;
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
    //setters

    /**
     * set the x-coordinate of this point.
     *
     * @param x the x-coordinate of this point
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * set the y-coordinate of this point.
     *
     * @param y the y-coordinate of this point
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Moves the point by a given amount.
     *
     * @param v the amount to move the point on the x-axis
     * @param i the amount to move the point on the y-axis
     */
    public void move(double v, int i) {
        this.x += v;
        this.y += i;
    }
}
