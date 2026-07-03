package arkanoid.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * KeyPressStoppableAnimation class.
 * Wraps an animation and stops it when a key is pressed.
 */
public class KeyPressStoppableAnimation implements Animation {
    private final KeyboardSensor sensor;
    private final String key;
    private final Animation animation;
    private boolean stop;
    private boolean isAlreadyPressed;

    /**
     * Construct a KeyPressStoppableAnimation.
     *
     * @param sensor the keyboard sensor
     * @param key the key to wait for
     * @param animation the animation to wrap
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true; // Prevent immediate stop if key is already pressed
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        animation.doOneFrame(d);
        
        if (this.sensor.isPressed(key)) {
            if (!isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
