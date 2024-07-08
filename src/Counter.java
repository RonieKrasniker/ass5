/**
 * Counter Class.
 */
public class Counter {
    //fields
    private int count;


    //constructor
    // create a new counter with a given count

    /**
     * Construct a counter.
     *
     * @param count the count of the counter
     */
    public Counter(int count) {
        this.count = count;
    }

    // add number to current count.

    /**
     * Increase the count of blocks by number.
     *
     * @param number the number to increase the count by
     */
    void increase(int number) {
        this.count += number;
    }

    // subtract number from current count of blocks
    void decrease(int number) {
        this.count -= number;
    }
    // get current count.

    /**
     * Get the value of the counter.
     *
     * @return the value of the counter
     */
    int getValue() {
        return this.count;
    }
    String getStrValue() {
        return Integer.toString(this.count);
    }
}
