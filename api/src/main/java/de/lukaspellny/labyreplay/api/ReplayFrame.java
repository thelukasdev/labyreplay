package de.lukaspellny.labyreplay.api;

import java.time.Instant;
import java.util.Arrays;

public final class ReplayFrame {

    private final Instant capturedAt;
    private final byte[] encodedImage;
    private final String format;

    public ReplayFrame(Instant capturedAt, byte[] encodedImage, String format) {
        this.capturedAt = capturedAt;
        this.encodedImage = Arrays.copyOf(encodedImage, encodedImage.length);
        this.format = format;
    }

    public Instant capturedAt() {
        return this.capturedAt;
    }

    public byte[] encodedImage() {
        return Arrays.copyOf(this.encodedImage, this.encodedImage.length);
    }

    public String format() {
        return this.format;
    }

    public int encodedSize() {
        return this.encodedImage.length;
    }
}
