package arkanoid.listeners;

import arkanoid.game.GameContext;
import arkanoid.sprites.Block;
import arkanoid.sprites.Ball;
import arkanoid.util.Counter;
import arkanoid.resources.PlaySound;

// a BlockRemover is in charge of removing blocks from the game, as well as keeping count
// of the number of blocks that remain.

/**
 * Block remover class.
 */
public class BlockRemover implements HitListener {
    private GameContext gameLevel;
    private Counter remainingBlocks;
    private PlaySound blockPopSFX;

    // constructor
    // create a new BlockRemover with the game and the counter of remaining blocks
    /**
     * Construct a block remover.
    * @param gameLevel the game level
     * @param remainingBlocks the counter of remaining blocks
     */
    public BlockRemover(GameContext gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
        blockPopSFX = new PlaySound(false, "BlockPopSFX.wav");
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
        beingHit.getRow().decrease(1);
        beingHit.removeFromGame(this.gameLevel);
        beingHit.removeHitListener(this);
        this.remainingBlocks.decrease(1);
        blockPopSFX.play();
    }


}
