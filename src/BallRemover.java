/**
 * BallRemover class. This class is in charge of removing balls from the game, as well as keeping count
 * of the number of balls that remain in the game.
 */
public class BallRemover implements HitListener {
    // fields
    private Game game;
    private Counter remainingBalls;

    // constructor
    /**
     * Construct a BallRemover object.
     *
     * @param removedBalls the counter of removed balls
     */
    public BallRemover(Game game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    // methods
    /**
     * This method is called whenever a ball is being hit.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that is hitting the block
     */
    // remove the ball from the game and decrease the counter of remaining balls
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
