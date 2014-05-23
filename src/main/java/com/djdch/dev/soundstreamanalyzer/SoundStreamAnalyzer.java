package com.djdch.dev.soundstreamanalyzer;

import com.djdch.dev.soundstreamanalyzer.swing.ApplicationFrame;

public class SoundStreamAnalyzer {

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
