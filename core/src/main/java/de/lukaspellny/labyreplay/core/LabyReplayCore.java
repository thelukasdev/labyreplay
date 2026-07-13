package de.lukaspellny.labyreplay.core;

import de.lukaspellny.labyreplay.api.DetectedHighlight;
import de.lukaspellny.labyreplay.api.HighlightEventType;
import de.lukaspellny.labyreplay.api.ReplayFrame;
import de.lukaspellny.labyreplay.api.ReplaySettings;
import de.lukaspellny.labyreplay.api.RoundStats;
import de.lukaspellny.labyreplay.api.StoredHighlight;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public final class LabyReplayCore {

    private final ReplaySettings settings;
    private final CircularClipBuffer clipBuffer;
    private final RoundStats roundStats;
    private final ChatHighlightDetector chatDetector;
    private final HighlightStorage storage;
    private final StatsShareImageRenderer statsRenderer;

    public LabyReplayCore(ReplaySettings settings, Path highlightDirectory) {
        this.settings = settings;
        this.clipBuffer = new CircularClipBuffer(settings.maxBufferedFrames(), settings.maxEncodedFrameBytes());
        this.roundStats = new RoundStats();
        this.chatDetector = new ChatHighlightDetector(Clock.systemDefaultZone());
        this.storage = new HighlightStorage(highlightDirectory);
        this.statsRenderer = new StatsShareImageRenderer();
    }

    public void recordFrame(byte[] encodedImage, String format) {
        if (!this.settings.enabled()) {
            return;
        }

        this.clipBuffer.add(new ReplayFrame(Instant.now(), encodedImage, format));
    }

    public Optional<StoredHighlight> onChatMessage(String message) throws IOException {
        if (!this.settings.enabled()) {
            return Optional.empty();
        }

        Optional<DetectedHighlight> detected = this.chatDetector.detect(message, this.roundStats);
        if (detected.isEmpty() || !this.shouldAutoSave(detected.get().type())) {
            return Optional.empty();
        }

        return Optional.of(this.saveHighlight(detected.get()));
    }

    public StoredHighlight saveManualHighlight() throws IOException {
        return this.saveHighlight(new DetectedHighlight(
            HighlightEventType.MANUAL,
            "manual",
            Instant.now()
        ));
    }

    public Path saveStatsShareImage(String playerName, String serverName, boolean copyToClipboard) throws IOException {
        BufferedImage image = this.statsRenderer.render(this.roundStats, playerName, serverName);
        if (copyToClipboard) {
            this.statsRenderer.copyToClipboard(image);
        }

        return this.storage.saveShareImage(HighlightEventType.ROUND_WIN, this.statsRenderer.toPng(image));
    }

    public void recordHit() {
        this.roundStats.recordHit();
    }

    public void resetRoundStats() {
        this.roundStats.reset();
    }

    public RoundStats roundStats() {
        return this.roundStats;
    }

    public int bufferedFrameCount() {
        return this.clipBuffer.size();
    }

    private StoredHighlight saveHighlight(DetectedHighlight highlight) throws IOException {
        List<ReplayFrame> frames = this.clipBuffer.snapshot();
        return this.storage.saveClip(highlight, frames);
    }

    private boolean shouldAutoSave(HighlightEventType type) {
        return switch (type) {
            case KILL -> this.settings.autoSaveOnKill();
            case BED_BREAK -> this.settings.autoSaveOnBedBreak();
            case ROUND_WIN -> this.settings.autoSaveOnRoundWin();
            case MANUAL -> true;
        };
    }
}
