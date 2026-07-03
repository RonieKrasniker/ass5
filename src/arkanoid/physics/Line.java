//318828175 Aharon Krasniker
package arkanoid.physics;


/**
 * A class that handle a line.
 */
public class Line {
    private final Point start, end; // start and end point of the line
    private static final double EPSILON = 1e-10; // Epsilon for floating-point comparisons


    // constructors

    /**
     * Constructs a new line from two points.
     *
     * @param start The start point of the line.
     * @param end   The end point of the line.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Constructs a new line from four coordinates.
     *
     * @param x1 The x-coordinate of the start point.
     * @param y1 The y-coordinate of the start point.
     * @param x2 The x-coordinate of the end point.
     * @param y2 The y-coordinate of the end point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }


    /**
     * Calculates the length of the line segment.
     *
     * @return The length of the line segment.
     */
    public double length() {
        return start.distance(end);
    }

    // Return the slope of the line

    /**
     * Calculates the slope of the line.
     *
     * @return The slope of the line.
     */
    public double getSlope() {
        return (end.getY() - start.getY()) / (end.getX() - start.getX());
    }

    // Return the angle of the line

    /**
     * Calculates the angle of the line.
     *
     * @return The angle of the line.
     */
    public double angle() {
        return Math.toDegrees(Math.atan2(end.getY() - start.getY(), end.getX() - start.getX()));
    }

    // Returns the distance from a point to the line
    /**
     * Returns the distance from a point to the line.
     *
     * @param point The point to calculate the distance from.
     * @return The distance from the point to the line.
     */
    public double distanceFromLine(Point point) {
        double x0 = point.getX();
        double y0 = point.getY();
        double x1 = start.getX();
        double y1 = start.getY();
        double x2 = end.getX();
        double y2 = end.getY();

        double numerator = Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1);
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        return numerator / denominator;
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.

    /**
     * Calculates the closest intersection point to the start of the line with a rectangle.
     *
     * @param rect The rectangle to check for intersection with.
     * @return The closest intersection point to the start of the line, or null if there is no intersection.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        java.util.List<Point> intersectionPoints = rect.intersectionPoints(this);
        //if the list is null, return null
        if (intersectionPoints == null) {
            return null;
        }

        Point closest = intersectionPoints.get(0);
        double minDistance = start.distance(closest);

        for (Point point : intersectionPoints) {
            double distance = start.distance(point);
            if (distance < minDistance) {
                minDistance = distance;
                closest = point;
            }
        }

        return closest;
    }


    /**
     * Calculates the middle point of the line segment.
     *
     * @return The middle point of the line segment.
     */
    public Point middle() {
        double x = (start.getX() + end.getX()) / 2;
        double y = (start.getY() + end.getY()) / 2;
        return new Point(x, y);
    }

    // Returns the start point of the line

    /**
     * Returns the start point of the line.
     *
     * @return The start point of the line.
     */
    public Point start() {
        return start;
    }

    // Returns the end point of the line

    /**
     * Returns the end point of the line.
     *
     * @return The end point of the line.
     */
    public Point end() {
        return end;
    }


    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other The other line to check for intersection.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        // Calculate the intersection point using intersectionWith method
        Point intersection = this.intersectionWith(other);

        // If intersection point is not null and within bounds, lines intersect
        if (intersection != null) {
            return true;
        }

        // Check for collinear and overlapping lines
        double x1 = start.getX(), y1 = start.getY(), x2 = end.getX(), y2 = end.getY();
        double x3 = other.start().getX(), y3 = other.start().getY(), x4 = other.end().getX(), y4 = other.end().getY();

        double m = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (Math.abs(m) < EPSILON) {
            // Check if the lines are collinear
            if (Math.abs((y2 - y1) * (x3 - x1) - (y3 - y1) * (x2 - x1)) < EPSILON) {
                // Check if they overlap using isWithinBounds for segment points
                return this.isWithinBounds(x3, y3) || this.isWithinBounds(x4, y4)
                        || other.isWithinBounds(x1, y1) || other.isWithinBounds(x2, y2);
            }
        }

        return false;
    }


    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other1 The first other line to check for intersection.
     * @param other2 The second other line to check for intersection.
     * @return true if the lines intersect, false otherwise.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return intersectionWith(other1) != null && intersectionWith(other2) != null;
    }


    /**
     * Calculates the intersection point of this line with another line.
     *
     * @param other The other line to find the intersection with.
     * @return The intersection point, or null if there is no intersection.
     */
    public Point intersectionWith(Line other) {
        double x1 = start.getX(), y1 = start.getY(), x2 = end.getX(), y2 = end.getY();
        double x3 = other.start().getX(), y3 = other.start().getY(), x4 = other.end().getX(), y4 = other.end().getY();

        // Check if the segments share exactly one endpoint consider we have 4 points a b c d
        boolean samePointAC = Math.abs(x1 - x3) < EPSILON && Math.abs(y1 - y3) < EPSILON;
        boolean samePointAD = Math.abs(x1 - x4) < EPSILON && Math.abs(y1 - y4) < EPSILON;
        boolean samePointBC = Math.abs(x2 - x3) < EPSILON && Math.abs(y2 - y3) < EPSILON;
        boolean samePointBD = Math.abs(x2 - x4) < EPSILON && Math.abs(y2 - y4) < EPSILON;
        // If the segments share exactly one endpoint, return the common point
        if ((samePointAC && !samePointBD)
                || (samePointAD && !samePointBC)
                || (samePointBC && !samePointAD)
                || (samePointBD && !samePointAC)) {
            if (samePointAD || samePointAC) {
                return new Point(x1, y1);
            } else {
                return new Point(x2, y2);
            }
        }

        // Check if either line segment is a point
        boolean thisIsPoint = Math.abs(x1 - x2) < EPSILON && Math.abs(y1 - y2) < EPSILON;
        boolean otherIsPoint = Math.abs(x3 - x4) < EPSILON && Math.abs(y3 - y4) < EPSILON;

        if (thisIsPoint && otherIsPoint) {
            // Both are points
            if (Math.abs(x1 - x3) < EPSILON && Math.abs(y1 - y3) < EPSILON) {
                return new Point(x1, y1); // Same point
            } else {
                return null; // Different points
            }
        } else if (thisIsPoint) {
            // This line is a point
            if (other.isWithinBounds(x1, y1)) {
                return new Point(x1, y1);
            } else {
                return null;
            }
        } else if (otherIsPoint) {
            // Other line is a point
            if (isWithinBounds(x3, y3)) {
                return new Point(x3, y3);
            } else {
                return null;
            }
        }

        // Check if either line is vertical
        boolean thisVertical = Math.abs(x1 - x2) < EPSILON;
        boolean otherVertical = Math.abs(x3 - x4) < EPSILON;

        // Check if either line is horizontal
        boolean thisHorizontal = Math.abs(y1 - y2) < EPSILON;
        boolean otherHorizontal = Math.abs(y3 - y4) < EPSILON;

        if (thisVertical) {
            // This line is vertical, other line is general
            double y = ((x1 - x3) * (y4 - y3) / (x4 - x3)) + y3;
            if (isWithinBounds(x1, y) && other.isWithinBounds(x1, y)) {
                return new Point(x1, y);
            }
        } else if (otherVertical) {
            // This line is general, other line is vertical
            double y = ((x3 - x1) * (y2 - y1) / (x2 - x1)) + y1;
            if (isWithinBounds(x3, y) && other.isWithinBounds(x3, y)) {
                return new Point(x3, y);
            }
        } else if (thisHorizontal) {
            // This line is horizontal, other line is general
            double x = ((y1 - y3) * (x4 - x3) / (y4 - y3)) + x3;
            if (isWithinBounds(x, y1) && other.isWithinBounds(x, y1)) {
                return new Point(x, y1);
            }
        } else if (otherHorizontal) {
            // This line is general, other line is horizontal
            double x = ((y3 - y1) * (x2 - x1) / (y2 - y1)) + x1;
            if (isWithinBounds(x, y3) && other.isWithinBounds(x, y3)) {
                return new Point(x, y3);
            }
        } else {
            // General case: neither line is purely vertical or horizontal
            double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
            if (Math.abs(denom) < EPSILON) {
                return null; // Lines are parallel or coincident
            }

            double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
            double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom;

            if (ua >= 0 && ua <= 1 && ub >= 0 && ub <= 1) {
                double x = x1 + ua * (x2 - x1);
                double y = y1 + ua * (y2 - y1);
                if (isWithinBounds(x, y) && other.isWithinBounds(x, y)) {
                    return new Point(x, y);
                }
            }
        }

        return null; // No intersection
    }

    /**
     * Returns true if the point is within the bounds of the line, false otherwise.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     * @return true if the point is within the bounds of the line, false otherwise.
     */
    private boolean isWithinBounds(double x, double y) {
        return (Math.min(start.getX(), end.getX()) - EPSILON <= x
                && x <= Math.max(start.getX(), end.getX()) + EPSILON)
                && (Math.min(start.getY(), end.getY()) - EPSILON <= y
                && y <= Math.max(start.getY(), end.getY()) + EPSILON);
    }


    /**
     * Returns true if the point is on the line, false otherwise.
     *
     * @param point The point to check.
     * @return true if the point is on the line, false otherwise.
     */
    public boolean isOnLine(Point point) {
        // Check if the sum of the distances
        // from the point to the start and end points is approximately equal to the line's length
        return Math.abs(point.distance(start) + point.distance(end) - length()) < EPSILON;
    }

    /**
     * Returns true if the lines are equal, false otherwise.
     *
     * @param other The other object to compare with.
     * @return true if the lines are equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Line)) {
            return false;
        }
        Line otherLine = (Line) other;
        return (start.equals(otherLine.start) && end.equals(otherLine.end))
                || (start.equals(otherLine.end) && end.equals(otherLine.start));
    }
    
    /**
     * Returns a hash code for this line.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        // Use XOR to make hash symmetric (since line direction doesn't matter for equality)
        return start.hashCode() ^ end.hashCode();
    }





}
