package com.djdch.dev.soundstreamanalyzer.listener;

import java.util.Observable;

import com.djdch.dev.soundstreamanalyzer.entity.SoundMetadata;

import ddf.minim.AudioInput;
import ddf.minim.AudioListener;
import ddf.minim.analysis.BeatDetect;

public class AudioInputListener extends Observable implements AudioListener {

    private final SoundMetadata metadata;
    private final BeatDetect fdetect;
    private final BeatDetect bdetect;

    private AudioInput in;

    public AudioInputListener() {
        metadata = new SoundMetadata();

        fdetect = new BeatDetect();
        fdetect.detectMode(BeatDetect.FREQ_ENERGY);
//        fdetect.setSensitivity(100);

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
        update();
    }

    @Override
    public void samples(float[] floats, float[] floats2) {
        update();
    }

    public void update() {
        fdetect.detect(in.mix);
        bdetect.detect(in.mix);

        metadata.setKick(fdetect.isKick());
        metadata.setSnare(fdetect.isSnare());
        metadata.setHat(fdetect.isHat());

        metadata.setBeat(bdetect.isOnset());

//        metadata.setLeft(in.left.level());
//        metadata.setRight(in.right.level());
//        metadata.setMix(in.mix.level());
    }
}
