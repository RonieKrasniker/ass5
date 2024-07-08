/**
 * The HitListener interface will be used to notify objects that implement it that a hit occurred.
 */
public interface HitListener {
    // This method is called whenever the beingHit object is hit.
// The hitter parameter is the Ball that's doing the hitting.
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that is hitting the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
