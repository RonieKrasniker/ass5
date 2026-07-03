package arkanoid.app;

import biuoop.GUI;
import java.util.ArrayList;
import java.util.List;
import arkanoid.animation.AnimationRunner;
import arkanoid.game.GameFlow;
import arkanoid.levels.Constants;
import arkanoid.levels.LevelInformation;
import arkanoid.levels.ClassicLevel;
import arkanoid.listeners.ScoreManager;

/**
 * The main entry point for the Arkanoid game.
 */
public class Ass5Game {
    /**
     * Run the game.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        GUI gui = new GUI(Constants.WINDOW_TITLE, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        AnimationRunner runner = new AnimationRunner(gui);
        ScoreManager scoreManager = new ScoreManager();
        GameFlow gameFlow = new GameFlow(runner, gui.getKeyboardSensor(), gui, scoreManager);

        List<LevelInformation> levels = new ArrayList<>();
        levels.add(new ClassicLevel());

        boolean playerWon = gameFlow.runLevels(levels);
        gameFlow.showEndScreen(playerWon);
    }
}

