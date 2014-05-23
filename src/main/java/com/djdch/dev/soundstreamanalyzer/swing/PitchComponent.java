package com.djdch.dev.soundstreamanalyzer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class PitchComponent extends JComponent {

    private static final int PREFERRED_WIDTH = 120;
    private static final int PREFERRED_HEIGHT = 300;

    private String name;
    private float data;
    private float rawData;
    private float min;
    private float max;

    public PitchComponent(String name) {
        this(name, 0.0f, 1.0f);
    }

    public PitchComponent(String name, float min, float max) {
        this.name = name;
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

        g.setColor(Color.BLUE);
        g.fillRect(0, getHeight() / 8, getWidth(), 1);
        g.fillRect(0, getHeight() / 4, getWidth(), 1);
        g.fillRect(0, (getHeight() / 8) * 3, getWidth(), 1);
        g.fillRect(0, getHeight() / 2, getWidth(), 1);
        g.fillRect(0, (getHeight() / 8) * 5, getWidth(), 1);
        g.fillRect(0, (getHeight() / 4) * 3, getWidth(), 1);
        g.fillRect(0, (getHeight() / 8) * 7, getWidth(), 1);

        g.setColor(Color.GREEN);
        g.fillRect(0, getHeight() - height - 1, getWidth(), 3);

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 15));
        g.drawString(name, 5, 20);
        g.drawString(String.format("%s", rawData), 5, getHeight()-10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
