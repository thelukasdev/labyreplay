package de.lukaspellny.labyreplay.core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public final class CircularClipBuffer {

    private final int maxFrames;
    private final int maxEncodedFrameBytes;
    private final ArrayDeque<ReplayFrame> frames;

    public CircularClipBuffer(int maxFrames, int maxEncodedFrameBytes) {
        if (maxFrames <= 0) {
            throw new IllegalArgumentException("maxFrames must be positive");
        }
        this.maxFrames = maxFrames;
        this.maxEncodedFrameBytes = maxEncodedFrameBytes;
        this.frames = new ArrayDeque<>(maxFrames);
    }

    public synchronized void add(ReplayFrame frame) {
        if (frame.encodedSize() > this.maxEncodedFrameBytes) {
            return;
        }

        if (this.frames.size() == this.maxFrames) {
            this.frames.removeFirst();
        }

        this.frames.addLast(frame);
    }

    public synchronized List<ReplayFrame> snapshot() {
        return new ArrayList<>(this.frames);
    }

    public synchronized int size() {
        return this.frames.size();
    }

    public synchronized void clear() {
        this.frames.clear();
    }
}
