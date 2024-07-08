//318828175 Aharon Krasniker

import biuoop.DrawSurface;

/**
 * A class that represents a rectangle.
 */
public class Rectangle {
    //fields
    private final Point upperLeft;
    private final double width;
    private final double height;
    private java.awt.Color color = java.awt.Color.BLACK;

    // constructors

    /**
     * Constructor that creates a rectangle from two points.
     *
     * @param upperLeft   the upper left point of the rectangle
     * @param bottomRight the bottom right point of the rectangle
     */
    public Rectangle(Point upperLeft, Point bottomRight) {
        this.upperLeft = upperLeft;
        this.width = bottomRight.getX() - upperLeft.getX();
        this.height = bottomRight.getY() - upperLeft.getY();
    }

    /**
     * Constructor that creates a rectangle from two points.
     *
     * @param upperLeftX   the x coordinate of the upper left point of the rectangle
     * @param upperLeftY   the y coordinate of the upper left point of the rectangle
     * @param bottomRightX the x coordinate of the bottom right point of the rectangle
     * @param bottomRightY the y coordinate of the bottom right point of the rectangle
     */
    public Rectangle(double upperLeftX, double upperLeftY, double bottomRightX, double bottomRightY) {
        this.upperLeft = new Point(upperLeftX, upperLeftY);
        this.width = bottomRightX - upperLeftX;
        this.height = bottomRightY - upperLeftY;
    }

    /**
     * Constructor that creates a rectangle from a point, width and height.
     *
     * @param upperLeft the upper left point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // accessors

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the upper left point of the rectangle
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return the upper right point of the rectangle
     */
    public Point getUpperRight() {
        return new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
    }

    /**
     * @return the bottom left point of the rectangle
     */
    public Point getBottomLeft() {
        return new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
    }

    /**
     * @return the bottom right point of the rectangle
     */
    public Point getBottomRight() {
        return new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY() + this.height);
    }

    /**
     * @return the top line of the rectangle
     */
    public Line getTop() {
        return new Line(this.getUpperLeft(), this.getUpperRight());
    }

    /**
     * @return the bottom line of the rectangle
     */
    public Line getBottom() {
        return new Line(this.getBottomLeft(), this.getBottomRight());
    }

    /**
     * @return the left line of the rectangle
     */
    public Line getLeft() {
        return new Line(this.getUpperLeft(), this.getBottomLeft());
    }

    /**
     * @return the right line of the rectangle
     */
    public Line getRight() {
        return new Line(this.getUpperRight(), this.getBottomRight());
    }

    //get array of lines

    /**
     * @return an array of the lines of the rectangle
     */
    public Line[] getLines() {
        Line[] lines = new Line[4];
        lines[0] = this.getTop();
        lines[1] = this.getBottom();
        lines[2] = this.getLeft();
        lines[3] = this.getRight();
        return lines;
    }


    // Return a (possibly empty) List of intersection points
    // with the specified line.

    /**
     * @param line the line to check for intersection
     * @return a list of intersection points with the specified line.
     * or null if there are no intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> intersectionPoints = new java.util.ArrayList<>();
        for (Line l : this.getLines()) {
            Point intersection = l.intersectionWith(line);
            if (intersection != null) {
                intersectionPoints.add(intersection);
            }
        }
        //if the list is empty, return null
        if (intersectionPoints.isEmpty()) {
            return null;
        }
        return intersectionPoints;
    }

    //setters

    /**
     * set the color of the rectangle.
     *
     * @param color the color to set
     */
    public void setColor(java.awt.Color color) {
        this.color = color;
    }

    //set the upper left point of the rectangle
    /**
     * set the upper left point of the rectangle.
     *
     * @param x the x coordinate of the upper left point
     * @param y the y coordinate of the upper left point
     */
    public void setUpperLeft(double x, double y) {
        this.upperLeft.setX(x);
        this.upperLeft.setY(y);
    }

    //boolean function that checks if a point is inside the rectangle

    /**
     * @param p the point to check
     * @return true if the point is inside the rectangle, false otherwise
     */
    public boolean isInside(Point p) {
        return p.getX() >= this.upperLeft.getX() && p.getX() <= this.upperLeft.getX() + this.width
                && p.getY() >= this.upperLeft.getY() && p.getY() <= this.upperLeft.getY() + this.height;
    }


    //draw the rectangle on the given DrawSurface

    /**
     * draw the rectangle on the given DrawSurface.
     *
     * @param surface the DrawSurface to draw on
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillRectangle((int) this.upperLeft.getX(),
                (int) this.upperLeft.getY(),
                (int) this.width, (int) this.height);
        //draw the rectangle's frame
        surface.setColor(java.awt.Color.BLACK);
        surface.drawRectangle((int) this.upperLeft.getX(),
                (int) this.upperLeft.getY(),
                (int) this.width, (int) this.height);
    }


}
