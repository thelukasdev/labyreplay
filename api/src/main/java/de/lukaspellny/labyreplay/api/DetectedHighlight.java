package de.lukaspellny.labyreplay.api;

import java.time.Instant;

public record DetectedHighlight(
    HighlightEventType type,
    String sourceMessage,
    Instant detectedAt
) {
}
