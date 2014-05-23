package com.djdch.dev.soundstreamanalyzer.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class Visualizer2Component extends JComponent {

    private static final int PREFERRED_WIDTH = 120;
    private static final int PREFERRED_HEIGHT = 300;

    private String name;
    private float data;
    private float rawData;
    private float min;
    private float max;
    private float h;
    private float s;
    private float v;
    private int total;
    private double last;
    private float number;

    public Visualizer2Component(String name) {
        this(name, 0.0f, 1.0f);
    }

    public Visualizer2Component(String name, float min, float max) {
        this.name = name;
        this.min = min;
        this.max = max;
        data = 0.0f;
        h = 0.0f;
        s = 0.0f;
        v = 0.0f;
        total = 0;
        last = 0;
        number = 0;

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

//        g.setColor(new Color(1.0f, 1.0f, 1.0f, data));
        g.setColor(Color.getHSBColor(h, s, v));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new Font("default", Font.BOLD, 15));

        g.setColor(Color.WHITE);
        g.drawString(name, 5 + 1, 20 + 1);
        g.drawString(String.format("N: %s", number), 5 + 1, getHeight() - 110 + 1);
        g.drawString(String.format("L: %s", last), 5 + 1, getHeight() - 90 + 1);
        g.drawString(String.format("T: %s", total), 5 + 1, getHeight() - 70 + 1);
        g.drawString(String.format("H: %s", h), 5 + 1, getHeight() - 50 + 1);
        g.drawString(String.format("S: %s", s), 5 + 1, getHeight() - 30 + 1);
        g.drawString(String.format("V: %s", v), 5 + 1, getHeight() - 10 + 1);

        g.setColor(Color.BLACK);
        g.drawString(name, 5, 20);
        g.drawString(String.format("N: %s", number), 5, getHeight() - 110);
        g.drawString(String.format("L: %s", last), 5, getHeight() - 90);
        g.drawString(String.format("T: %s", total), 5, getHeight() - 70);
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

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public double getLast() {
        return last;
    }

    public void setLast(double last) {
        this.last = last;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
}
