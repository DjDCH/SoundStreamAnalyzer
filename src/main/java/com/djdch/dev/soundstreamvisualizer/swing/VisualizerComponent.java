package com.djdch.dev.soundstreamvisualizer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class VisualizerComponent extends JComponent {

    private static final int PREFERRED_WIDTH = 120;
    private static final int PREFERRED_HEIGHT = 300;

    private String name;
    private float data;
    private float rawData;
    private float min;
    private float max;
    private int r;
    private int g;
    private int b;
    private int a;

    public VisualizerComponent(String name) {
        this(name, 0.0f, 1.0f);
    }

    public VisualizerComponent(String name, float min, float max) {
        this.name = name;
        this.min = min;
        this.max = max;
        data = 0.0f;
        r = 0;
        g = 0;
        b = 0;
        a = 0;

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

        g.setColor(new Color(r, this.g, b, a));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 15));
        g.drawString(name, 5, 20);
        g.drawString(String.format("R: %s", r), 5, getHeight()-70);
        g.drawString(String.format("G: %s", this.g), 5, getHeight()-50);
        g.drawString(String.format("B: %s", b), 5, getHeight()-30);
        g.drawString(String.format("A: %s", a), 5, getHeight()-10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
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
}
