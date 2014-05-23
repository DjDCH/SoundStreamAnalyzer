package com.djdch.dev.soundstreamvisualizer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class VisualizerBeat3Component extends JComponent {

    private static final int PREFERRED_WIDTH = 120;
    private static final int PREFERRED_HEIGHT = 300;

    private String name;
    private float data;
    private float rawData;
    private float min;
    private float max;
    private boolean enabled;
    private float h;
    private float s;
    private float v;
    private long last;
    private float fast;
    private float slow;
    private boolean reversed;

    public VisualizerBeat3Component(String name) {
        this(name, 0.0f, 1.0f);
    }

    public VisualizerBeat3Component(String name, float min, float max) {
        this.name = name;
        this.min = min;
        this.max = max;
        data = 0.0f;
        h = 0.0f;
        s = 0.0f;
        v = 0.0f;
        last = 0L;
        fast = 0.0f;
        slow = 0.0f;

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setFocusable(false);

        rebuild();
    }

    public void rebuild() {
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

        if (enabled) {
//            last -= 75;
//            last -= 100;

            if (last > 2000) {
                last = 2000;
            }

            if (last < 0) {
                last = 0;
            }

            fast = max;
//            slow = max;

//            slow = ((1.0f - ((float) last / 1000.0f)) * 0.5f) + 0.5f; // Should give something between 0.5f and 1.0f
//            slow = ((float) last / 800.0f) * 0.35f; // Should give something between 0.0f and 0.35f
            slow = ((float) (last * 0.4f) / 800.0f) * 0.4f; // Should give something between 0.0f and 0.35f

            enabled = false;
            reversed = false;
        }

        s = max - fast;
        v = 0.8f + (fast * 0.2f);
        h = slow;
//        h = (0.9f + slow) % 1.0f;
//        h = 0.15f - slow;
//        if (h < 0.0f) {
//            h = 1.0f - (h * -1.0f);
//        }

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

//        g.setColor(new Color(1.0f, 1.0f, 1.0f, data));
        g.setColor(Color.getHSBColor(h, s, v));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new Font("default", Font.BOLD, 15));

        g.setColor(Color.WHITE);
        g.drawString(name, 5 + 1, 20 + 1);
        g.drawString(String.format("L: %s", last), 5 + 1, getHeight() - 70 + 1);
        g.drawString(String.format("H: %s", h), 5 + 1, getHeight() - 50 + 1);
        g.drawString(String.format("S: %s", s), 5 + 1, getHeight() - 30 + 1);
        g.drawString(String.format("V: %s", v), 5 + 1, getHeight() - 10 + 1);

        g.setColor(Color.BLACK);
        g.drawString(name, 5, 20);
        g.drawString(String.format("L: %s", last), 5, getHeight() - 70);
        g.drawString(String.format("H: %s", h), 5, getHeight() - 50);
        g.drawString(String.format("S: %s", s), 5, getHeight() - 30);
        g.drawString(String.format("V: %s", v), 5, getHeight() - 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
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

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
    }

    public float getRawData() {
        return rawData;
    }

    public void setRawData(float rawData) {
        this.rawData = rawData;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
