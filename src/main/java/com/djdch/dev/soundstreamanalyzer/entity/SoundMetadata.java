package com.djdch.dev.soundstreamanalyzer.entity;

public class SoundMetadata {

    private volatile float left;
    private volatile float right;
    private volatile float mix;

    private volatile boolean kick;
    private volatile boolean snare;
    private volatile boolean hat;

    private volatile boolean beat;

    private volatile long last;
    private volatile long length;

    private float h;
    private float s;
    private float v;

    public SoundMetadata() {
        reset();
    }

    public void reset() {
        left = 0.0f;
        right = 0.0f;
        mix = 0.0f;

        last = System.currentTimeMillis();
        length = 0L;

        h = 0.0f;
        s = 0.0f;
        v = 0.0f;

        softReset();
    }

    public void softReset() {
        kick = false;
        snare = false;
        hat = false;

        beat = false;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getMix() {
        return mix;
    }

    public void setMix(float mix) {
        this.mix = mix;
    }

    public boolean isKick() {
        return kick;
    }

    public void setKick(boolean kick) {
        this.kick = kick;
    }

    public boolean isSnare() {
        return snare;
    }

    public void setSnare(boolean snare) {
        this.snare = snare;
    }

    public boolean isHat() {
        return hat;
    }

    public void setHat(boolean hat) {
        this.hat = hat;
    }

    public boolean isBeat() {
        return beat;
    }

    public void setBeat(boolean beat) {
        this.beat = beat;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public float getS() {
        return s;
    }

    public void setS(float s) {
        this.s = s;
    }

    public float getV() {
        return v;
    }

    public void setV(float v) {
        this.v = v;
    }
}
