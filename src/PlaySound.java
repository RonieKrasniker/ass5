import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.File;

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
        try {
            // Open an audio input stream.
            File soundFile = new File(this.fileName); // your sound file here
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            // Get a sound clip resource.
            this.clip = AudioSystem.getClip();
            // Open audio clip and load samples from the audio input stream.
            this.clip.open(audioIn);
            if (this.repeat) {
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                this.clip.start();
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
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
