package arkanoid.resources;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * ResourceManager class (Singleton pattern).
 * Centralizes sound and image loading with caching to avoid repeated file I/O.
 */
public class ResourceManager {
    private static ResourceManager instance;
    private Map<String, Clip> soundCache;
    
    /**
     * Private constructor for singleton pattern.
     */
    private ResourceManager() {
        this.soundCache = new HashMap<>();
    }
    
    /**
     * Get the singleton instance of ResourceManager.
     *
     * @return the ResourceManager instance
     */
    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }
    
    /**
     * Load and cache a sound file.
     *
     * @param filename the name of the sound file
     * @return the loaded Clip, or null if loading failed
     */
    public Clip loadSound(String filename) {
        // Check cache first
        if (soundCache.containsKey(filename)) {
            return soundCache.get(filename);
        }
        
        try {
            File soundFile = new File(filename);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filename);
                return null;
            }
            
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            
            // Cache the loaded clip
            soundCache.put(filename, clip);
            return clip;
        } catch (Exception e) {
            System.err.println("Error loading sound file " + filename + ": " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Clear all cached resources.
     */
    public void clearCache() {
        // Close all clips before clearing
        for (Clip clip : soundCache.values()) {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }
        }
        soundCache.clear();
    }
    
    /**
     * Get a cached clip without loading.
     *
     * @param filename the name of the sound file
     * @return the cached Clip, or null if not in cache
     */
    public Clip getCachedSound(String filename) {
        return soundCache.get(filename);
    }
}
