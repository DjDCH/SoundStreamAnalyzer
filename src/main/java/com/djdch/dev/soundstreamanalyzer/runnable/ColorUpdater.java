package com.djdch.dev.soundstreamanalyzer.runnable;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;

import com.djdch.dev.soundstreamanalyzer.controller.ApplicationController;
import com.djdch.dev.soundstreamanalyzer.entity.SoundMetadata;

import jssc.SerialPortException;

public class ColorUpdater implements Runnable, Observer {

    private final Logger logger = Logger.getLogger(getClass());

    private final ApplicationController controller;

    private SoundMetadata metadata;

    private float min;
    private float max;
    private float fast;
    private float slow;
    private long length;
    private boolean reversed;
    private boolean running;

    public ColorUpdater(ApplicationController controller) {
        this.controller = controller;

        min = 0.0f;
        max = 1.0f;
        fast = 0.0f;
        slow = 0.0f;
        length = 0L;
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            if (metadata.isBeat()) {
                long current = System.currentTimeMillis();

                length = current - metadata.getLast();

                metadata.setLast(current);
            }

            refresh();

            if (controller.getLauncher().isGUI()) {
                controller.getFrame().rebuild();
            }

            if (controller.getSerial().isConnected()) {
                Color color = Color.getHSBColor(metadata.getH(), metadata.getS(), metadata.getV());

                try {
                    controller.getSerial().write(color.getRed());
                    controller.getSerial().write(color.getGreen());
                    controller.getSerial().write(color.getBlue());
                    controller.getSerial().flush();
                } catch (SerialPortException e) {
                    logger.error("Exception occurred while writing to serial link", e);
                }
            }

            metadata.softReset();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                logger.error("Exception occurred while sleeping.", e);
            }
        }
    }

    public void refresh() {
        if (fast > min) {
            fast -= 0.05f;
        }

        if (fast < min) {
            fast = min;
        }

        if (reversed) {
            slow -= 0.0015f;

            if (slow < 0.40f) {
                reversed = false;
            }
        } else {
            slow += 0.0015f;
        }

        if (slow > 0.8f) {
            slow = 0.8f;
            reversed = true;
        }

        if (metadata.isBeat() || metadata.isSnare()) {
//            length -= 75;
//            length -= 100;

            if (length > 2000) {
                length = 2000;
            }

            if (length < 0) {
                length = 0;
            }

            fast = max;
//            slow = max;

//            slow = ((1.0f - ((float) length / 1000.0f)) * 0.5f) + 0.5f; // Should give something between 0.5f and 1.0f
//            slow = ((float) length / 800.0f) * 0.35f; // Should give something between 0.0f and 0.35f
            slow = ((float) (length * 0.4f) / 800.0f) * 0.4f; // Should give something between 0.0f and 0.35f

//            enabled = false; // metadata.softReset();
            reversed = false;
        }

        metadata.setH(slow);
        metadata.setS(max - fast);
        metadata.setV(0.8f + (fast * 0.2f));
        metadata.setLength(length);
    }

    public void stop() {
        running = false;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof SoundMetadata) {
            logger.debug("Received metadata");
            this.metadata = (SoundMetadata) arg;
        }
    }
}
