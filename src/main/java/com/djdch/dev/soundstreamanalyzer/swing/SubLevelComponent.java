package com.djdch.dev.soundstreamanalyzer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

public class SubLevelComponent extends JComponent {

    private static final int PREFERRED_WIDTH = 15;
    private static final int PREFERRED_HEIGHT = 300;

    private boolean even;
    private float data;
    private float rawData;
    private float min;
    private float max;

    public SubLevelComponent(boolean even) {
        this(even, 0.0f, 200.0f);
    }

    public SubLevelComponent(boolean even, float min, float max) {
        this.even = even;
        this.min = min;
        this.max = max;
        data = 0.0f;

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setFocusable(false);

        rebuild();
    }

    private void rebuild() {
        data = (rawData - min) / (max - min);

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int height = (int) (data * getHeight());

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (even) {
            g.setColor(Color.MAGENTA);
        } else {
            g.setColor(Color.ORANGE);
        }
        g.fillRect(0, getHeight() - height, getWidth(), height);
    }

    public float getData() {
        return data;
    }

    public void setData(float data) {
        this.data = data;
        rebuild();
    }

    public float getRawData() {
        return rawData;
    }

    public void setRawData(float rawData) {
        this.rawData = rawData;
        rebuild();
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
        rebuild();
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
        rebuild();
    }
}
