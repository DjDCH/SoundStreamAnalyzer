package com.djdch.dev.soundstreamanalyzer.util;

public class SoundTools {

    /*
     * Source: http://stackoverflow.com/questions/15870666/calculating-microphone-volume-trying-to-find-max
     */
    public static float calculateRMSLevel(byte[] samples) { // samples might be buffered data read from a data line
        long lSum = 0;
        for (int i = 0; i < samples.length; i++) {
            lSum = lSum + samples[i];
        }

        double dAvg = lSum / samples.length;

        double sumMeanSquare = 0d;
        for (int j = 0; j < samples.length; j++) {
            sumMeanSquare = sumMeanSquare + Math.pow(samples[j] - dAvg, 2d);
        }

        double averageMeanSquare = sumMeanSquare / samples.length;
//        return (int) (Math.pow(averageMeanSquare, 0.5d) + 0.5);
        return (float) (Math.pow(averageMeanSquare, 0.5d) + 0.5);
//        return (float) sumMeanSquare / samples.length;
    }

    /*
     * Source: http://stackoverflow.com/questions/5800649/detect-silence-when-recording
     */
    public static float volumeRMS(byte[] raw) {
        double sum = 0d;

        for (int ii = 0; ii < raw.length; ii++) {
            sum += raw[ii];
        }

        double average = sum / raw.length;

        double sumMeanSquare = 0d;
        for (int ii = 0; ii < raw.length; ii++) {
            sumMeanSquare += Math.pow(raw[ii] - average, 2d);
        }
        double averageMeanSquare = sumMeanSquare / raw.length;
        double rootMeanSquare = Math.sqrt(averageMeanSquare);

        return (float) rootMeanSquare;
    }

    public static float volumeRMSByte(Byte[] raw) {
        double sum = 0d;

        for (int ii = 0; ii < raw.length; ii++) {
            sum += raw[ii];
        }

        double average = sum / raw.length;

        double sumMeanSquare = 0d;
        for (int ii = 0; ii < raw.length; ii++) {
            sumMeanSquare += Math.pow(raw[ii] - average, 2d);
        }
        double averageMeanSquare = sumMeanSquare / raw.length;
        double rootMeanSquare = Math.sqrt(averageMeanSquare);

        return (float) rootMeanSquare;
    }
}
