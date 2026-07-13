package de.lukaspellny.labyreplay.core;

import java.time.Instant;

public record DetectedHighlight(
    HighlightEventType type,
    String sourceMessage,
    Instant detectedAt
) {
}
