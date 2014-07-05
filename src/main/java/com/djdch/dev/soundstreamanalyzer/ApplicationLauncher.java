package com.djdch.dev.soundstreamanalyzer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.djdch.dev.serialtoarduinoled.serial.SerialUtil;
import com.djdch.dev.soundstreamanalyzer.controller.ApplicationController;

import ddf.minim.Minim;

public class ApplicationLauncher {

    private final ApplicationController controller;
    private final Options options;

    private int inputType;
    private boolean monitor;
    private boolean gui;
    private String serial;

    public ApplicationLauncher() {
        controller = new ApplicationController(this);

        options = new Options();
        options.addOption(new Option(null, "mono", false, "use a MONO chanel input"));
        options.addOption(new Option(null, "stereo", false, "use a STEREO chanel input (default)"));
        options.addOption(new Option(null, "lists-serials", false, "lists available serial links"));
        options.addOption(new Option(null, "auto-serial", false, "automatically select serial link"));
        options.addOption(Option.builder()
                .longOpt("serial")
                .hasArg()
                .argName("/dev/tty")
                .valueSeparator(' ')
                .desc("use specified serial link")
                .build());
        options.addOption(new Option(null, "monitor", false, "monitor audio input"));
        options.addOption(new Option(null, "gui", false, "display the visualizer gui"));

        inputType = Minim.STEREO;
        monitor = false;
        gui = false;
    }

    public void launch(String args[]) {
        if (args.length == 0) {
            help();
        } else {
            CommandLineParser parser = new DefaultParser();

            try {
                CommandLine cmd = parser.parse(options, args);

                if (cmd.hasOption("lists-serials")) {
                    if (cmd.getOptions().length > 1) {
                        throw new ParseException("Too many arguments.");
                    }

                    listSerials();
                } else {
                    if (cmd.hasOption("serial") && cmd.hasOption("auto-serial")) {
                        throw new ParseException("Conflicting options: serial and auto-serial");
                    }
                    if (cmd.hasOption("serial")) {
                        serial = cmd.getOptionValue("serial");
                    }
                    if (cmd.hasOption("auto-serial")) {
                        serial = SerialUtil.getPorts()[0];
                    }
                    if (cmd.hasOption("mono") && cmd.hasOption("stereo")) {
                        throw new ParseException("Conflicting options: mono and stereo");
                    }
                    if (cmd.hasOption("mono")) {
                        inputType = Minim.MONO;
                    }
                    if (cmd.hasOption("monitor")) {
                        monitor = true;
                    }
                    if (cmd.hasOption("gui")) {
                        gui = true;
                    }

                    controller.start();
                }
            } catch(ParseException e) {
//                e.printStackTrace();
                System.err.println(e.getMessage());
            }
        }
    }

    public void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(120, "soundstreamanalyzer --lists-serials\n       soundstreamanalyzer (--mono | --stereo) [--auto-serial | --serial </dev/tty>] [--monitor] [--gui]", null, options, null);
    }

    private void listSerials() {
        String[] ports = SerialUtil.getPorts();

        if (ports.length == 0) {
            System.out.println("No serial link available.");
        } else {
            System.out.println("Available serial link:");

            for (String port : ports) {
                System.out.println("    " + port);
            }
        }
    }

    public int getInputType() {
        return inputType;
    }

    public boolean isMonitored() {
        return monitor;
    }

    public boolean isGUI() {
        return gui;
    }

    public String getSerial() {
        return serial;
    }
}
