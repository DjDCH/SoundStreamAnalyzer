package com.djdch.dev.soundstreamanalyzer.runnable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import com.djdch.dev.soundstreamanalyzer.controller.ApplicationController;
import com.djdch.dev.soundstreamanalyzer.util.SampleQueue;
import com.djdch.dev.soundstreamanalyzer.util.SSVAudioFormat;

public class SoundListener implements Runnable {

    private final ApplicationController controller;
    private final SampleQueue queue;

    private SourceDataLine lineOut = null;
    private AudioFormat format = null;
    private TargetDataLine lineIn = null;

    private boolean running;

    public SoundListener(ApplicationController controller) {
        this.controller = controller;
        this.queue = controller.getQueue();

        try {
            format = SSVAudioFormat.getFormat();

            DataLine.Info infoIn = new DataLine.Info(TargetDataLine.class, format);
            lineIn = (TargetDataLine) AudioSystem.getLine(infoIn);

            DataLine.Info infoOut = new DataLine.Info(SourceDataLine.class, format);
            lineOut = (SourceDataLine) AudioSystem.getLine(infoOut);

            lineIn.open(format);
            lineOut.open(format);

            lineIn.start();
            lineOut.start();
        } catch (LineUnavailableException e) {
            controller.failure(e);
        }
    }

    @Override
    public void run() {
//        int bufferSize = (int) format.getSampleRate() * format.getFrameSize();
        int bufferSize = format.getFrameSize();
        byte buffer[] = new byte[bufferSize];

        running = true;

        while (running) {
            int count = lineIn.read(buffer, 0, buffer.length);
            if (count > 0) {
                lineOut.write(buffer, 0, count);
                queue.addSamples(buffer);
            }
        }

        lineIn.drain();
        lineIn.close();

        lineOut.drain();
        lineOut.close();
    }

    public void stop() {
        running = false;
    }
}
