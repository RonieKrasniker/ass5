package arkanoid.animation;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import arkanoid.levels.Constants;

/**
 * AnimationRunner class.
 * Handles running animations at a specified frame rate.
 */
public class AnimationRunner {
    private final GUI gui;
    private final int framesPerSecond;
    private final Sleeper sleeper;

    /**
     * Construct an AnimationRunner.
     *
     * @param gui the GUI to draw on
     * @param framesPerSecond the target frames per second
     */
    public AnimationRunner(GUI gui, int framesPerSecond) {
        this.gui = gui;
        this.framesPerSecond = framesPerSecond;
        this.sleeper = new Sleeper();
    }

    /**
     * Construct an AnimationRunner with default FPS.
     *
     * @param gui the GUI to draw on
     */
    public AnimationRunner(GUI gui) {
        this(gui, Constants.FRAMES_PER_SECOND);
    }

    /**
     * Run the given animation.
     *
     * @param animation the animation to run
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d);

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * Get the GUI.
     *
     * @return the GUI
     */
    public GUI getGui() {
        return this.gui;
    }
}
