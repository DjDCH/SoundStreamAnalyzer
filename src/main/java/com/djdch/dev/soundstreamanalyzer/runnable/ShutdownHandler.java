package com.djdch.dev.soundstreamanalyzer.runnable;

import com.djdch.dev.soundstreamanalyzer.controller.ApplicationController;

public class ShutdownHandler implements Runnable {

    private final ApplicationController controller;

    public ShutdownHandler(ApplicationController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        controller.stop();
    }
}
