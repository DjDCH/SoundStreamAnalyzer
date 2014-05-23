package com.djdch.dev.soundstreamanalyzer.util;

import java.util.concurrent.ConcurrentLinkedQueue;

public class SampleQueue {

    private final ConcurrentLinkedQueue<byte[]> queue; // XXX: Or use ConcurrentLinkedDeque

    public SampleQueue() {
        queue = new ConcurrentLinkedQueue<byte[]>();
    }

    public boolean addSamples(byte[] bytes) {
        return queue.add(bytes);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public byte[] poolSamples() {
        return queue.poll();
    }
}
