package engine;
// a engine.BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.

import sprites.Ball;
import sprites.Block;

import java.util.HashMap;
import java.util.Map;


/**
 * sprites.Block remover class.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;
    private Map<Integer, Integer> blocksPerRow; // Tracks blocks per row

    // constructor
    // create a new engine.BlockRemover with the game and the counter of remaining blocks
    /**
     * Construct a block remover.
     * @param game
     * @param remainingBlocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
        this.blocksPerRow = new HashMap<>();
    }
    // Blocks that are hit should be removed
// from the game. Remember to remove this listener from the block
// that is being removed from the game.
    /**
     * Remove the block from the game and decrease the counter of remaining blocks.
     * @param beingHit the block that is being hit
     * @param hitter the ball that is hitting the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
    }


}
