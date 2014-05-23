package com.djdch.dev.soundstreamvisualizer.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.djdch.dev.serialtoarduinoled.serial.SerialLink;
import com.djdch.dev.serialtoarduinoled.serial.SerialUtil;
import com.djdch.dev.soundstreamvisualizer.controller.ApplicationController;

public class ApplicationFrame extends JFrame implements KeyEventDispatcher {

    public static final String APPLICATION_NAME = "SoundStreamVisualizer";

    private final ApplicationController controller;
    private final MetersPanel metersPanel;

    public final SerialLink serial;

    private JCheckBoxMenuItem currentItemPort, monitoringItem;
    private JMenuItem startItem, stopItem;

    public boolean monitoring = false;

//    private final JButton start = new JButton("Start");
//    private final JButton stop = new JButton("Stop");

    public ApplicationFrame() {
        super(APPLICATION_NAME);
//        setSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        serial = new SerialLink();

        controller = new ApplicationController(this);

//        JPanel gridPanel = new JPanel(new GridLayout(1, 2));
        metersPanel = new MetersPanel(serial);

//        start.setEnabled(true);
//        start.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                start.setEnabled(false);
//                stop.setEnabled(true);
//                controller.start();
//            }
//        });
//        gridPanel.add(start);

//        stop.setEnabled(false);
//        stop.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                start.setEnabled(true);
//                stop.setEnabled(false);
//                controller.stop();
//            }
//        });
//        gridPanel.add(stop);

        initMenu();

        setLayout(new BorderLayout());

        Container content = getContentPane();
//        content.add(gridPanel, BorderLayout.NORTH);
        content.add(metersPanel, BorderLayout.CENTER);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);

        pack();
        setLocationRelativeTo(null); // Centered window
    }

    public void failure(String message) {
//        start.setEnabled(false);
//        stop.setEnabled(true);

        if (message == null) {
            message = "Something bad happened.";
        }

        JOptionPane.showMessageDialog(this, message, "Internal error", JOptionPane.ERROR_MESSAGE);
    }

    private void initMenu() {
        startItem = new JMenuItem("Start");
        startItem.setEnabled(true);
        startItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                for (JCheckBoxMenuItem portItem : portItems) {
//                    portItem.setEnabled(false);
//                }
//                for (JComponent component : components) {
//                    component.setEnabled(true);
//                }

                startItem.setEnabled(false);
                stopItem.setEnabled(true);

                if (currentItemPort != null)  {
                    serial.setPortName(currentItemPort.getText());
                    serial.connect();
                }

                controller.start();
            }
        });

        stopItem = new JMenuItem("Stop");
        stopItem.setEnabled(false);
        stopItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startItem.setEnabled(true);
                stopItem.setEnabled(false);

//                for (JComponent component : components) {
//                    component.setEnabled(false);
//                }
//                for (JCheckBoxMenuItem portItem : portItems) {
//                    portItem.setEnabled(true);
//                }

//                rSlider.setValue(0);
//                gSlider.setValue(0);
//                bSlider.setValue(0);

                if (serial.isConnected()) {
                    serial.disconnect();
                }

                controller.stop();
            }
        });

        JMenu actionMenu = new JMenu("Action");
        actionMenu.add(startItem);
        actionMenu.add(stopItem);

        JMenu serialPortMenu = new JMenu("Serial Port");

        for (String port : SerialUtil.getPorts()) {
            JCheckBoxMenuItem portItem = new JCheckBoxMenuItem(port);
            portItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBoxMenuItem portItem = (JCheckBoxMenuItem) e.getSource();

                    if (currentItemPort == null) {
                        currentItemPort = portItem;
                        currentItemPort.setState(true);

//                        startItem.setEnabled(true);
                    } else {
                        if (portItem == currentItemPort) {
                            currentItemPort.setState(false);
                            currentItemPort = null;

//                            startItem.setEnabled(false);
                        } else {
                            currentItemPort.setState(false);
                            currentItemPort = portItem;
                            currentItemPort.setState(true);
                        }
                    }
                }
            });

            serialPortMenu.add(portItem);
//            portItems.add(portItem);
        }

        monitoringItem = new JCheckBoxMenuItem("Monitoring");
        monitoringItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monitoring = !monitoring;
                monitoringItem.setState(monitoring);
            }
        });

        JMenu settingMenu = new JMenu("Setting");
        settingMenu.add(monitoringItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(actionMenu);
        menuBar.add(serialPortMenu);
        menuBar.add(settingMenu);

        setJMenuBar(menuBar);
    }

    public MetersPanel getMetersPanel() {
        return metersPanel;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
//            System.out.println(String.format("%s %s %s", e.getKeyCode(), e.getKeyChar(), e.getKeyLocation()));

            if (e.getKeyCode() == KeyEvent.VK_A) {
                metersPanel.metadata.setBeat(true);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                metersPanel.metadata.setBeat2(true);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                metersPanel.metadata.setBeat3(true);
            }
        }

        return false;
    }
}
