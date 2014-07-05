SoundStreamAnalyzer
===================

Java application design to analyse the sound stream from the default input sound
card and send the resulting byte stream over a serial link. Intended to be
used with [ArduinoLEDFromSerial](https://github.com/DjDCH/ArduinoLEDFromSerial)
to control a RGB LED strip with music.

Requirements
------------

* Java SE Development Kit 7 (JDK7) installed
* Maven 3 installed

Compilation
-----------

First, install the Minim jar files into your local Maven repository:

    mvn install:install-file -Dfile=lib/minim.jar -DgroupId=ddf.minim -DartifactId=minim -Dversion=2.2.0 -Dpackaging=jar
    mvn install:install-file -Dfile=lib/jsminim.jar -DgroupId=ddf.minim.javasound -DartifactId=jsminim -Dversion=2.2.0 -Dpackaging=jar

Then, use Maven to build the runnable jar file:

    mvn clean package

Usage
-----

This software can be used in various way. Here's few examples:

* List the available serial interfaces:

        java -jar soundstreamanalyzer-1.0.jar --lists-serials

* Start the analyzer in stereo with the first available serial interface:

        java -jar soundstreamanalyzer-1.0.jar --stereo --auto-serial

* Same as above, with the sound monitoring and GUI:

        java -jar soundstreamanalyzer-1.0.jar --stereo --auto-serial --monitor --gui

* Display the command references:

        java -jar soundstreamanalyzer-1.0.jar
