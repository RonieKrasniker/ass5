package arkanoid.animation;

import biuoop.DrawSurface;

/**
 * Animation interface.
 * Represents an animation that can be displayed frame by frame.
 */
public interface Animation {
    /**
     * Perform one frame of the animation.
     *
     * @param d the DrawSurface to draw on
     */
    void doOneFrame(DrawSurface d);

    /**
     * Check if the animation should stop.
     *
     * @return true if the animation should stop, false otherwise
     */
    boolean shouldStop();
}
