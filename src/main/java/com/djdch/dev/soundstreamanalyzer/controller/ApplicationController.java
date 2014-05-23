package com.djdch.dev.soundstreamanalyzer.controller;

import com.djdch.dev.soundstreamanalyzer.runnable.AudioInputListener;
import com.djdch.dev.soundstreamanalyzer.runnable.MetersRefresher;
import com.djdch.dev.soundstreamanalyzer.runnable.SoundAnalyzer;
import com.djdch.dev.soundstreamanalyzer.runnable.SoundListener;
import com.djdch.dev.soundstreamanalyzer.swing.ApplicationFrame;
import com.djdch.dev.soundstreamanalyzer.util.SampleQueue;

import ddf.minim.AudioInput;
import ddf.minim.Minim;
import ddf.minim.javasound.JSMinim;

public class ApplicationController {

//    private static final String SERIAL_PORT = "/dev/tty.usbmodemfa1311";
//    private static final String SERIAL_PORT = "/dev/tty.usbmodemfd121";

    private final ApplicationFrame frame;
    private final SampleQueue queue;

    private SoundListener listener;
    private SoundAnalyzer analyzer;
    private MetersRefresher refresher;

    public ApplicationController(ApplicationFrame frame) {
        this.frame = frame;

        queue = new SampleQueue();
    }

    public void start() {
//        frame.serial.setPortName(SERIAL_PORT);
//        frame.serial.connect();

        AudioInputListener listener = new AudioInputListener(this);
        listener.addObserver(frame.getMetersPanel());

//        listener = new SoundListener(this);
//        Thread listenerThread = new Thread(listener);
//        listenerThread.start();

//        analyzer = new SoundAnalyzer(this);
//        analyzer.addObserver(frame.getMetersPanel());
//        Thread analyzerThread = new Thread(analyzer);
//        analyzerThread.start();

        refresher = new MetersRefresher(this, this.frame);
        Thread refresherThread = new Thread(refresher);
        refresherThread.start();

//        Executors.newSingleThreadExecutor().submit(listener);

        // FIXME
        JSMinim jsMinim = new JSMinim(this);
        Minim minim = new Minim(jsMinim);

        AudioInput in = minim.getLineIn();

        if (frame.monitoring) {
            in.enableMonitoring();
        }

        listener.start(in);

        in.addListener(listener);

    }

    public void stop() {
//        frame.serial.disconnect();

//        minim.stop(); // TODO

//        listener.stop();
//        analyzer.stop();
        refresher.stop();

//        analyzer.deleteObserver(frame.getMetersPanel());

        frame.getMetersPanel().reset();
    }

    public void failure(Exception e) {
        frame.failure(e.getMessage());
        stop();
        e.printStackTrace();
    }

    public SampleQueue getQueue() {
        return queue;
    }
}
