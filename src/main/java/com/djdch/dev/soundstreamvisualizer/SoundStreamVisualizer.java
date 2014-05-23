package com.djdch.dev.soundstreamvisualizer;

import javax.swing.UIManager;

import com.djdch.dev.soundstreamvisualizer.swing.ApplicationFrame;

public class SoundStreamVisualizer {

    /**
     * @param args Command line arguments.
     */
    public static void main(String args[]) {
//        try {
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//        } catch (Exception e) {
//            // TODO: handle exception
//        }

        ApplicationFrame application = new ApplicationFrame();
        application.setVisible(true);
//        Mixers.getMixers();
    }
}
