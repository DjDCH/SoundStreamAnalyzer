package com.djdch.dev.soundstreamanalyzer.util;

import javax.sound.sampled.AudioFormat;

public class SSVAudioFormat {

    private static final float SAMPLE_RATE = 16000; // Was 44100
    private static final int SAMPLE_SIZE_IN_BITS = 8;
    private static final int CHANNELS = 2;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = true;
    private static final boolean LITTLE_ENDIAN = false;

    private static final AudioFormat format;

    static {
        format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
    }

    public static AudioFormat getFormat() {
        return format;
    }
}
