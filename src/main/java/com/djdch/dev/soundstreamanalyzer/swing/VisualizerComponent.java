package com.djdch.dev.soundstreamanalyzer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class VisualizerComponent extends JComponent {

    private static final int PREFERRED_WIDTH = 900;
    private static final int PREFERRED_HEIGHT = 500;

    private String name;
    private float h;
    private float s;
    private float v;
    private long length;

    public VisualizerComponent(String name) {
        this.name = name;
        h = 0.0f;
        s = 0.0f;
        v = 0.0f;
        length = 0L;

        setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));
        setFocusable(false);

        rebuild();
    }

    public void rebuild() {
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.getHSBColor(h, s, v));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new Font("default", Font.BOLD, 15));

        g.setColor(Color.WHITE);
        g.drawString(name, 5 + 1, 20 + 1);
        g.drawString(String.format("L: %s", length), 5 + 1, getHeight() - 70 + 1);
        g.drawString(String.format("H: %s", h), 5 + 1, getHeight() - 50 + 1);
        g.drawString(String.format("S: %s", s), 5 + 1, getHeight() - 30 + 1);
        g.drawString(String.format("V: %s", v), 5 + 1, getHeight() - 10 + 1);

        g.setColor(Color.BLACK);
        g.drawString(name, 5, 20);
        g.drawString(String.format("L: %s", length), 5, getHeight() - 70);
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
