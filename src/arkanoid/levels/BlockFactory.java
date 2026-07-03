package arkanoid.levels;

import java.awt.Color;
import arkanoid.physics.Point;
import arkanoid.game.GameContext;
import arkanoid.sprites.Block;
import arkanoid.listeners.HitListener;
import arkanoid.listeners.BlockRemover;
import arkanoid.util.Counter;

/**
 * BlockFactory class.
 * Factory for creating blocks with consistent patterns.
 */
public class BlockFactory {
    /**
     * Create a horizontal row of blocks.
     *
     * @param startX the starting x position
     * @param y the y position
     * @param count the number of blocks
     * @param blockWidth the width of each block
     * @param blockHeight the height of each block
     * @param color the color of the blocks
    * @param gameLevel the game level to add blocks to
     * @param listeners the hit listeners to add to each block
     * @return an array of created blocks
     */
    public static Block[] createRow(double startX, double y, int count, 
                                   double blockWidth, double blockHeight, 
                                   Color color, GameContext gameLevel, HitListener... listeners) {
        Block[] blocks = new Block[count];
        for (int i = 0; i < count; i++) {
            double x = startX - i * blockWidth;
            Block block = new Block(new Point(x, y), blockWidth, blockHeight, color);
            
            // Add all listeners
            for (HitListener listener : listeners) {
                block.addHitListener(listener);
            }
            
            block.addToGame(gameLevel);
            blocks[i] = block;
        }
        return blocks;
    }

    /**
     * Create descending rows of blocks (pyramid pattern).
     *
    * @param gameLevel the game level to add blocks to
     * @param blockRemover the block remover listener
     * @param scoreListener the score tracking listener
     * @param rowCounters the list to store row counters
     * @return the total number of blocks created
     */
    public static int createDescendingRows(GameContext gameLevel, BlockRemover blockRemover, 
                                          HitListener scoreListener, 
                                          java.util.List<Counter> rowCounters) {
        Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.GRAY, Color.YELLOW, Color.PINK};
        int totalBlocks = 0;
        
        for (int i = 0; i < Constants.MAX_ROWS; i++) {
            int blocksInRow = Constants.MAX_BLOCKS_PER_ROW - i;
            rowCounters.add(new Counter(blocksInRow));
            
            double startX = Constants.SCREEN_WIDTH - Constants.BLOCK_WIDTH;
            double y = Constants.FIRST_ROW_Y + i * Constants.BLOCK_HEIGHT;
            
            Block[] rowBlocks = createRow(startX, y, blocksInRow, 
                Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT, 
                colors[i], gameLevel, blockRemover, scoreListener);
            
            // Set row counter for each block
            for (Block block : rowBlocks) {
                block.setRow(rowCounters.get(i));
            }
            
            totalBlocks += blocksInRow;
        }
        
        return totalBlocks;
    }
}
