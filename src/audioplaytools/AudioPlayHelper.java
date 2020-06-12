package audioplaytools;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * 音频播放工具类
 *
 * @author LeoHao
 */
public class AudioPlayHelper {
    private static AudioClip audioPlayer;

    /**
     * 背景音乐播放
     *
     * @param url 音频路径
     */
    public static void open(URL url) {
        audioPlayer = Applet.newAudioClip(url);
        audioPlayer.loop();
    }

    public static void play() {
        if (audioPlayer != null) {
            audioPlayer.play();
        }
    }

    public static void stop() {
        if (audioPlayer != null) {
            audioPlayer.stop();
        }
    }
}
