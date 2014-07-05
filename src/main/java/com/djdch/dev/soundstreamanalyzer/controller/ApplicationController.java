package com.djdch.dev.soundstreamanalyzer.controller;

import org.apache.log4j.Logger;

import com.djdch.dev.serialtoarduinoled.serial.SerialLink;
import com.djdch.dev.soundstreamanalyzer.ApplicationLauncher;
import com.djdch.dev.soundstreamanalyzer.listener.AudioInputListener;
import com.djdch.dev.soundstreamanalyzer.runnable.ColorUpdater;
import com.djdch.dev.soundstreamanalyzer.runnable.ShutdownHandler;
import com.djdch.dev.soundstreamanalyzer.swing.ApplicationFrame;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.javasound.JSMinim;

import jssc.SerialPortException;

public class ApplicationController {

    private final Logger logger = Logger.getLogger(getClass());

    private final ApplicationLauncher launcher;
    private final SerialLink serial;

    private ApplicationFrame frame;
    private ColorUpdater updater;
    private Minim minim;

    public ApplicationController(ApplicationLauncher launcher) {
        this.launcher = launcher;

        frame = null;
        serial = new SerialLink();
    }

    public void start() {
        logger.debug("Starting SoundStreamAnalyzer");

        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHandler(this)));

        logger.debug("Creating listener");
        AudioInputListener listener = new AudioInputListener();

        logger.debug("Creating updater");
        updater = new ColorUpdater(this);
        listener.addObserver(updater);

        if (launcher.getSerial() != null) {
            try {
                logger.debug("Connecting serial link");
                serial.setPortName(launcher.getSerial());
                serial.connect();
            } catch (SerialPortException e) {
                logger.error("Exception occurred while connecting serial link", e);
            }
        }

        if (launcher.isGUI()) {
            logger.debug("Opening GUI");
            frame = new ApplicationFrame(this);
            frame.setVisible(true);
            listener.addObserver(frame);
        }

        logger.debug("Starting Minim");
        JSMinim jsMinim = new JSMinim(this);
        jsMinim.debugOff();
        minim = new Minim(jsMinim);

        AudioInput in = minim.getLineIn(launcher.getInputType());
//        AudioInput in = minim.getLineIn(launcher.getInputType(), 1024, 11025, 16);

        if (launcher.isMonitored()) {
            logger.debug("Enabling monitoring");
            in.enableMonitoring();
        }

        logger.debug("Starting listener");
        listener.start(in);
        in.addListener(listener);

        logger.debug("Starting updater");
        Thread refresherThread = new Thread(updater);
        refresherThread.start();

        logger.info("SoundStreamAnalyzer started");
    }

    public void stop() {
        logger.debug("Stopping SoundStreamAnalyzer");

        logger.debug("Stopping updater");
        updater.stop();

        logger.debug("Stopping Minim");
        minim.stop(); // TODO

        if (launcher.isGUI()) {
            if (frame.isVisible()) {
                // FIXME
//                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

                logger.debug("Closing GUI");
                frame.setVisible(false);
                logger.debug("Disposing GUI");
                frame.dispose();
            }
        }

        if (serial.isConnected()) {
            try {
                logger.debug("Disconnecting serial link");
                serial.disconnect();
            } catch (SerialPortException e) {
                logger.error("Exception occurred while disconnecting serial link", e);
            }
        }

        logger.info("SoundStreamAnalyzer stopped");
    }

    public ApplicationLauncher getLauncher() {
        return launcher;
    }

    public ApplicationFrame getFrame() {
        return frame;
    }

    public SerialLink getSerial() {
        return serial;
    }
}
