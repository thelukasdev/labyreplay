package de.lukaspellny.labyreplay.api;

import java.nio.file.Path;

public record StoredHighlight(
    HighlightEventType type,
    Path directory,
    Path posterFrame,
    int frameCount
) {
}
