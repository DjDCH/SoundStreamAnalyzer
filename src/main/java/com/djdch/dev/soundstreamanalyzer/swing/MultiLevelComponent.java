package com.djdch.dev.soundstreamanalyzer.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

public class MultiLevelComponent extends JPanel {

    private ArrayList<SubLevelComponent> components;

    private String name;
    private int count;

    public MultiLevelComponent(String name, int count) {
        this.name = name;
        this.count = count;

        components = new ArrayList<SubLevelComponent>();

        setLayout(new GridLayout(1, count));
        setFocusable(false);
//        setOpaque(false);

        for (int i = 0; i < count; i++) {
            SubLevelComponent component = new SubLevelComponent((i & 1) == 0);

            components.add(component);
            add(component);
        }

        rebuild();
    }

    public void rebuild() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("default", Font.BOLD, 15));
        g.drawString(name, 5, 20);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setRawData(int subcomponent, float rawData) {
        components.get(subcomponent).setRawData(rawData);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
