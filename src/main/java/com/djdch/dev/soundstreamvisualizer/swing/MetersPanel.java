package com.djdch.dev.soundstreamvisualizer.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemTray;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JPanel;

import com.djdch.dev.serialtoarduinoled.serial.SerialLink;
import com.djdch.dev.soundstreamvisualizer.entity.SoundMetadata;

public class MetersPanel extends JPanel implements Observer {

    private final Random random;

    private final LevelComponent left;
    private final LevelComponent right;
    private final LevelComponent mix;

    private final BeatComponent kick;
    private final BeatComponent snare;
    private final BeatComponent hat;

    private final BeatComponent beat;

    private final PitchComponent pitch;

//    private final VisualizerComponent visualizer;
    private final Visualizer2Component visualizer2;
//    private final VisualizerBeatComponent visualizer3;
    private final VisualizerBeat2Component visualizer4;
    private final VisualizerBeat3Component visualizer5;

    private final MultiLevelComponent fft;

    private final SerialLink serial;

    private float currentH;
    private int count;
    private long last;

//    private final MultiLevelComponent fft2;

//    private final LevelComponent instantRMS;
//    private final LevelComponent smoothRMS;
//    private final LevelComponent count;
//    private final BeatComponent beat2;
//    private final BeatComponent beat3;

    public SoundMetadata metadata; // FIXME: Currently public because of the keyEvent; switch back to private

    public MetersPanel(SerialLink serial) {
        this.serial = serial;

        random = new Random(System.currentTimeMillis());

        metadata = new SoundMetadata();

        left = new LevelComponent("Left", 0.0f, 1.0f);
        right = new LevelComponent("Right", 0.0f, 1.0f);
        mix = new LevelComponent("Mix", 0.0f, 1.0f);

        kick = new BeatComponent("Kick");
        snare = new BeatComponent("Snare");
        hat = new BeatComponent("Hat");

        beat = new BeatComponent("Beat");

        pitch = new PitchComponent("Pitch", 0.0f, 29.0f);

//        visualizer = new VisualizerComponent("Visualizer");
        visualizer2 = new Visualizer2Component("Visualizer2");
//        visualizer3 = new VisualizerBeatComponent("VisualizerBeat");
        visualizer5 = new VisualizerBeat3Component("VisualizerBeat3");
        visualizer4 = new VisualizerBeat2Component("VisualizerBeat2");

        currentH = 0.0f;
        count = 0;
        last = System.currentTimeMillis();

        fft = new MultiLevelComponent("FFT Fixed", 30);
//        fft2 = new MultiLevelComponent("FFT Original", 30);

//        instantRMS = new LevelComponent("Instant RMS", 0.0f, 164.0f);
//        smoothRMS = new LevelComponent("Smooth RMS", 0.0f, 164.0f);
//        beat2 = new BeatComponent("Beat");
//        beat3 = new BeatComponent("Beat");
//        count = new LevelComponent("Count", 0.0f, 60000.0f);

//        setLayout(new GridLayout(1, 8));
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(1, 8));

        gridPanel.add(left);
        gridPanel.add(mix);
        gridPanel.add(right);
        gridPanel.add(kick);
        gridPanel.add(snare);
        gridPanel.add(hat);
//        add(instantRMS);
//        add(smoothRMS);
        gridPanel.add(beat);
//        add(beat2);
//        add(beat3);
        gridPanel.add(pitch);
//        gridPanel.add(visualizer);
        gridPanel.add(visualizer2);
//        gridPanel.add(visualizer3);
        gridPanel.add(visualizer5);
        gridPanel.add(visualizer4);
//        add(count);
        add(gridPanel, BorderLayout.NORTH);
        add(fft, BorderLayout.CENTER);
//        add(fft2, BorderLayout.SOUTH);
    }

    public void rebuild() {
        left.setRawData(metadata.getLeft());
        right.setRawData(metadata.getRight());
        mix.setRawData(metadata.getMix());
        kick.setEnabled(metadata.isKick());
        snare.setEnabled(metadata.isSnare());
        hat.setEnabled(metadata.isHat());
//        instantRMS.setRawData(metadata.getInstantRMS());
//        smoothRMS.setRawData(metadata.getSmoothRMS());
        beat.setEnabled(metadata.isBeat());
//        beat2.setEnabled(metadata.isBeat2());
//        beat3.setEnabled(metadata.isBeat3());
//        pitch.setRawData(metadata.getSmoothRMS());
//        count.setRawData(metadata.getCount());

        double size = 0;
        double total = 0;

        for (int i = 0; i < fft.getCount(); i++) {
            float value = metadata.getFFT().getAvg(i);

//            fft2.setRawData(i, value);
            if (i >= 5) {
//                value = ((float) Math.sqrt((i - 4) * 0.5)) * value;
                value = ((float) Math.pow(2, (i - 4) / 4)) * value;
            } else {
                value *= 0.75;
            }

            fft.setRawData(i, value);

            if (i > 5) {
                if (value > 100) {
                    value = 100;
                }

                total += i * value;
                size += value;
            }
        }
        pitch.setRawData((float) (total / size));

        int c = (int) ((kick.getData() + hat.getData() + snare.getData())/3 * 0xFFFFFF);

//        visualizer.setR((c >> 4) & 0xFF);
//        visualizer.setG((c >> 2) & 0xFF);
//        visualizer.setB((c) & 0xFF);
//        visualizer.setA((int) (beat.getData() * 255));
//        visualizer.rebuild();

        if (metadata.isKick()) {
            count +=1;
        }
        if (metadata.isSnare()) {
            count +=1;
        }
        if (metadata.isHat()) {
            count +=1;
        }
        if (metadata.isBeat()) {
            long current = System.currentTimeMillis();

//            currentH += random.nextFloat() * 0.1f;
//            currentH %= 1.0f;
//            visualizer2.setH(currentH);

            visualizer2.setTotal(count);
            visualizer2.setLast(current - last);
//            visualizer3.setLast(current - last);
            visualizer5.setLast(current - last);
            visualizer4.setLast(current - last);
            float number = ((float) count) / ((float) (current - last)) * 20;

            if (number > 1.0f) {
                number = 1.0f;
            }

            number += 0.3f;
            number %= 1.0f;

            visualizer2.setNumber(number);
            visualizer2.setH(number);
            count = 0;
            last = current;
        }
        visualizer2.setS(1.0f - beat.getData());
        visualizer2.setV(0.8f + (beat.getData() * 0.2f));
        visualizer2.rebuild();

//        visualizer3.setEnabled(metadata.isBeat());
//        visualizer3.rebuild();

        visualizer5.setEnabled(metadata.isBeat());
        visualizer5.rebuild();

        visualizer4.setEnabled(metadata.isBeat());
        visualizer4.rebuild();

        if (serial.isConnected()) {
            Color color = Color.getHSBColor(visualizer4.getH(), visualizer4.getS(), visualizer4.getV());
            serial.write(color.getRed());
            serial.write(color.getGreen());
            serial.write(color.getBlue());
            serial.flush();
        }

        fft.rebuild();
//        fft2.rebuild();

        metadata.softReset();
    }

    public void reset() {
        metadata.reset();

        rebuild();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof SoundMetadata) {
            this.metadata = (SoundMetadata) arg;
        }
    }
}
