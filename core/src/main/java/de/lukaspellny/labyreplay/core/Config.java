package de.lukaspellny.labyreplay.core;

import de.lukaspellny.labyreplay.api.ReplaySettings;

import java.time.Duration;

public final class Config {

    private Config() {
    }

    public static ReplaySettings defaultReplaySettings() {
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
}
