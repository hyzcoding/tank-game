package com.hyzcoding.tankgame.component.wave;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author huyouzhi
 * @version 1.0
 * @since 1.0
 **/
public class AePlayWave extends Thread {
    private final String fileName;
    CountDownLatch latch;
    public AePlayWave(String fileName, CountDownLatch latch  ) {
        this.fileName = fileName;
        this.latch = latch;
    }

    public void run() {
        File voiceFile = new File(fileName);
        AudioInputStream ais;
        try {
            ais = AudioSystem.getAudioInputStream(voiceFile);
            assert ais != null;
            AudioFormat afm = ais.getFormat();
            SourceDataLine auline;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, afm);

            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(afm);
            auline.start();
            int r = 0;
            byte[] b = new byte[1024];
            while (r != -1) {
                r = ais.read(b, 0, b.length);
                if (r >= 0) {
                    auline.write(b, 0, r);
                }
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        latch.countDown();
    }

}
