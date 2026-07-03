package arkanoid.game;

import biuoop.GUI;
import biuoop.KeyboardSensor;
import java.util.List;
import arkanoid.animation.AnimationRunner;
import arkanoid.animation.Animation;
import arkanoid.animation.KeyPressStoppableAnimation;
import arkanoid.animation.EndScreen;
import arkanoid.levels.LevelInformation;
import arkanoid.listeners.ScoreManager;

/**
 * GameFlow manages running multiple levels in sequence.
 */
public class GameFlow {
    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final GUI gui;
    private final ScoreManager scoreManager;

    /**
     * Construct a GameFlow controller.
     *
     * @param runner the animation runner
     * @param keyboard the keyboard sensor
     * @param gui the GUI instance
     * @param scoreManager the score manager shared across levels
     */
    public GameFlow(AnimationRunner runner, KeyboardSensor keyboard, GUI gui, ScoreManager scoreManager) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.gui = gui;
        this.scoreManager = scoreManager;
    }

    /**
     * Run the given list of levels sequentially.
     *
     * @param levels the levels to run
     * @return true if the player cleared all levels, false otherwise
     */
    public boolean runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            GameLevel level = new GameLevel(levelInfo, runner, keyboard, scoreManager);
            level.initialize();
            level.run();
            if (level.isPlayerFailed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Show the end screen and wait for user input.
     *
     * @param playerWon true if player cleared all levels
     */
    public void showEndScreen(boolean playerWon) {
        Animation end = new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                new EndScreen(playerWon, scoreManager.getScoreValue()));
        runner.run(end);
        gui.close();
    }
}
