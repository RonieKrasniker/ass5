package arkanoid.levels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import arkanoid.physics.Velocity;
import arkanoid.physics.Point;
import arkanoid.game.Sprite;
import arkanoid.sprites.Block;

/**
 * ClassicLevel replicates the original Arkanoid level layout.
 */
public class ClassicLevel implements LevelInformation {
    private final List<Block> templateBlocks;

    /**
     * Construct the classic level definition.
     */
    public ClassicLevel() {
        this.templateBlocks = createTemplateBlocks();
    }

    @Override
    public int numberOfBalls() {
        return Constants.INITIAL_BALL_COUNT;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        double[] angles = {280, 50, 40};
        for (int i = 0; i < numberOfBalls(); i++) {

            double angle = angles[i % angles.length] * (5 + Math.random() * 5); 
            velocities.add(Velocity.fromAngleAndSpeed(angle, Constants.BALL_INITIAL_SPEED));
        }
        return velocities;
    }

    @Override
    public int paddleSpeed() {
        return Constants.PADDLE_SPEED;
    }

    @Override
    public int paddleWidth() {
        return Constants.PADDLE_WIDTH;
    }

    @Override
    public String levelName() {
        return "Classic Arkanoid";
    }

    @Override
    public Sprite getBackground() {
        return new BackgroundSprite(Color.BLUE);
    }

    @Override
    public List<Block> blocks() {
        List<Block> copies = new ArrayList<>();
        for (Block block : this.templateBlocks) {
            Point upperLeft = block.getCollisionRectangle().getUpperLeft();
            double width = block.getCollisionRectangle().getWidth();
            double height = block.getCollisionRectangle().getHeight();
            copies.add(new Block(new Point(upperLeft.getX(), upperLeft.getY()), width, height, block.getColor()));
        }
        return copies;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return templateBlocks.size();
    }

    private List<Block> createTemplateBlocks() {
        List<Block> blocks = new ArrayList<>();
        Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.GRAY, Color.YELLOW, Color.PINK};

        for (int row = 0; row < Constants.MAX_ROWS; row++) {
            int blocksInRow = Constants.MAX_BLOCKS_PER_ROW - row;
            for (int col = 0; col < blocksInRow; col++) {
                double x = Constants.SCREEN_WIDTH - Constants.BLOCK_WIDTH - col * Constants.BLOCK_WIDTH;
                double y = Constants.FIRST_ROW_Y + row * Constants.BLOCK_HEIGHT;
                blocks.add(new Block(new Point(x, y), Constants.BLOCK_WIDTH, Constants.BLOCK_HEIGHT, colors[row]));
            }
        }
        return blocks;
    }
}
