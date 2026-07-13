package de.lukaspellny.labyreplay.api;

import java.time.Duration;

public record ReplaySettings(
    boolean enabled,
    boolean autoSaveOnKill,
    boolean autoSaveOnBedBreak,
    boolean autoSaveOnRoundWin,
    Duration clipLength,
    int targetFramesPerSecond,
    int maxEncodedFrameBytes
) {

    public ReplaySettings {
        if (clipLength.isNegative() || clipLength.isZero()) {
            throw new IllegalArgumentException("clipLength must be positive");
        }
        if (targetFramesPerSecond <= 0) {
            throw new IllegalArgumentException("targetFramesPerSecond must be positive");
        }
        if (maxEncodedFrameBytes <= 0) {
            throw new IllegalArgumentException("maxEncodedFrameBytes must be positive");
        }
    }

    public static ReplaySettings defaultSettings() {
        return new ReplaySettings(
            true,
            true,
            true,
            true,
            Duration.ofSeconds(20),
            12,
            512 * 1024
        );
    }

    public int maxBufferedFrames() {
        long frames = this.clipLength.toSeconds() * (long) this.targetFramesPerSecond;
        return Math.toIntExact(Math.max(1L, frames));
    }
}
