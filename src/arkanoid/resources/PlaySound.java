package arkanoid.resources;

import javax.sound.sampled.Clip;

/**
 * PlaySound class.
 * Manages sound playback using the ResourceManager for efficient caching.
 */
public class PlaySound {
    // fields
    private boolean repeat;
    private String fileName;
    private Clip clip;

    // constructor will get play in repeat or not and the file name
    public PlaySound(boolean repeat, String fileName) {
        this.repeat = repeat;
        this.fileName = fileName;
        this.clip = null;
    }

    // play the sound
    public void play() {
        // Use ResourceManager to load (or get cached) clip
        this.clip = ResourceManager.getInstance().loadSound(this.fileName);
        
        if (this.clip != null) {
            // Reset clip to beginning if it was already played
            this.clip.setFramePosition(0);
            
            if (this.repeat) {
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                this.clip.start();
            }
        }
    }

    // stop the sound
    public void stop() {
        if (this.clip != null) {
            if (this.clip.isRunning()) {
                this.clip.stop();
            }
            this.clip.flush();
            this.clip.close();
        }
    }

    // ensure clip resources are released
    public void close() {
        if (this.clip != null) {
            if (this.clip.isRunning()) {
                this.clip.stop();
            }
            this.clip.flush();
            this.clip.close();
            this.clip = null;
        }
    }
}
