package arkanoid.levels;

/**
 * Threshold class.
 * This class provides utility methods for comparing floating-point numbers with epsilon threshold.
 */
public final class Threshold {
    // Epsilon value for floating-point comparisons
    public static final double EPSILON = 1e-10;
    
    /**
     * Check if two double values are equal within epsilon threshold.
     *
     * @param a the first value
     * @param b the second value
     * @return true if the values are equal within epsilon, false otherwise
     */
    public static boolean equals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
    
    /**
     * Check if the first value is less than the second value, considering epsilon.
     *
     * @param a the first value
     * @param b the second value
     * @return true if a < b (beyond epsilon threshold), false otherwise
     */
    public static boolean lessThan(double a, double b) {
        return (b - a) > EPSILON;
    }
    
    /**
     * Check if the first value is greater than the second value, considering epsilon.
     *
     * @param a the first value
     * @param b the second value
     * @return true if a > b (beyond epsilon threshold), false otherwise
     */
    public static boolean greaterThan(double a, double b) {
        return (a - b) > EPSILON;
    }
    
    /**
     * Check if the first value is less than or equal to the second value, considering epsilon.
     *
     * @param a the first value
     * @param b the second value
     * @return true if a <= b (within epsilon), false otherwise
     */
    public static boolean lessOrEquals(double a, double b) {
        return equals(a, b) || lessThan(a, b);
    }
    
    /**
     * Check if the first value is greater than or equal to the second value, considering epsilon.
     *
     * @param a the first value
     * @param b the second value
     * @return true if a >= b (within epsilon), false otherwise
     */
    public static boolean greaterOrEquals(double a, double b) {
        return equals(a, b) || greaterThan(a, b);
    }
    
    // Private constructor to prevent instantiation
    private Threshold() {
        throw new AssertionError("Cannot instantiate Threshold class");
    }
}
