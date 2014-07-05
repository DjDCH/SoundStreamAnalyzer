package com.djdch.dev.soundstreamanalyzer.swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;

import org.apache.log4j.Logger;

import com.djdch.dev.soundstreamanalyzer.controller.ApplicationController;
import com.djdch.dev.soundstreamanalyzer.entity.SoundMetadata;

public class ApplicationFrame extends JFrame implements Observer {

    public static final String APPLICATION_NAME = "SoundStreamAnalyzer";

    private final Logger logger = Logger.getLogger(getClass());
    private final ApplicationController controller;
    private final VisualizerComponent visualizer;

    private SoundMetadata metadata;

    public ApplicationFrame(ApplicationController controller_) {
        super(APPLICATION_NAME);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }
            @Override
            public void windowClosing(WindowEvent e) {
                controller.stop();
//                System.exit(0); // Trigger shutdown hook
            }
            @Override
            public void windowClosed(WindowEvent e) {
            }
            @Override
            public void windowIconified(WindowEvent e) {
            }
            @Override
            public void windowDeiconified(WindowEvent e) {
            }
            @Override
            public void windowActivated(WindowEvent e) {
            }
            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        this.controller = controller_;

        visualizer = new VisualizerComponent("Visualizer Kick+Beat");

        setLayout(new BorderLayout());

        Container content = getContentPane();
        content.add(visualizer, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Centered window
    }

    public void rebuild() {
        visualizer.setH(metadata.getH());
        visualizer.setS(metadata.getS());
        visualizer.setV(metadata.getV());
        visualizer.setLength(metadata.getLength());
        visualizer.rebuild();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof SoundMetadata) {
            logger.debug("Received metadata");
            this.metadata = (SoundMetadata) arg;
        }
    }
}
