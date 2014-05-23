package com.djdch.dev.soundstreamvisualizer.runnable;

import java.util.Observable;

import com.djdch.dev.soundstreamvisualizer.controller.ApplicationController;
import com.djdch.dev.soundstreamvisualizer.entity.SoundMetadata;

import ddf.minim.AudioInput;
import ddf.minim.AudioListener;
import ddf.minim.analysis.BeatDetect;

public class AudioInputListener extends Observable implements AudioListener {

    private final ApplicationController controller;
    private final SoundMetadata metadata;
    private final BeatDetect fdetect;
    private final BeatDetect bdetect;
    private AudioInput in;

    public AudioInputListener(ApplicationController controller) {
        this.controller = controller;

        metadata = new SoundMetadata();

        fdetect = new BeatDetect();
        fdetect.detectMode(BeatDetect.FREQ_ENERGY);
        fdetect.setSensitivity(100);

        bdetect = new BeatDetect();
        bdetect.detectMode(BeatDetect.SOUND_ENERGY);
//        bdetect.setSensitivity(100);
    }

    public void start(AudioInput in) {
        this.in = in;

        setChanged();
        notifyObservers(metadata);
    }

    @Override
    public void samples(float[] floats) {
        System.out.println("Shouldn't be called.");
    }

    @Override
    public void samples(float[] floats, float[] floats2) {
        fdetect.detect(in.mix);
        bdetect.detect(in.mix);

        metadata.getFFT().forward(in.mix);
//        metadata.getFFT2().forward(in.mix);

        metadata.setKick(fdetect.isKick());
        metadata.setSnare(fdetect.isSnare());
        metadata.setHat(fdetect.isHat());

        metadata.setBeat(bdetect.isOnset());

        metadata.setLeft(in.left.level());
        metadata.setRight(in.right.level());
        metadata.setMix(in.mix.level());
    }
}
