package de.lukaspellny.labyreplay.core;

import java.nio.file.Path;

public record StoredHighlight(
    HighlightEventType type,
    Path directory,
    Path posterFrame,
    int frameCount
) {
}
