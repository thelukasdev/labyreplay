package de.lukaspellny.labyreplay.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class HighlightStorage {

    private static final DateTimeFormatter FOLDER_TIME = DateTimeFormatter
        .ofPattern("yyyy-MM-dd_HH-mm-ss")
        .withZone(ZoneId.systemDefault());

    private final Path highlightDirectory;

    public HighlightStorage(Path highlightDirectory) {
        this.highlightDirectory = highlightDirectory;
    }

    public StoredHighlight saveClip(DetectedHighlight highlight, List<ReplayFrame> frames) throws IOException {
        Files.createDirectories(this.highlightDirectory);

        String folderName = FOLDER_TIME.format(highlight.detectedAt()) + "_" + highlight.type().fileNamePart();
        Path clipDirectory = uniquePath(this.highlightDirectory.resolve(folderName));
        Files.createDirectories(clipDirectory);

        Path posterFrame = null;
        for (int i = 0; i < frames.size(); i++) {
            ReplayFrame frame = frames.get(i);
            Path framePath = clipDirectory.resolve(String.format("frame_%05d.%s", i + 1, frame.format()));
            Files.write(framePath, frame.encodedImage());
            posterFrame = framePath;
        }

        Files.writeString(
            clipDirectory.resolve("highlight.txt"),
            manifest(highlight, frames.size()),
            StandardCharsets.UTF_8
        );

        return new StoredHighlight(highlight.type(), clipDirectory, posterFrame, frames.size());
    }

    public Path saveShareImage(HighlightEventType type, byte[] pngBytes) throws IOException {
        Files.createDirectories(this.highlightDirectory);
        Path imagePath = uniquePath(this.highlightDirectory.resolve(
            FOLDER_TIME.format(Instant.now()) + "_" + type.fileNamePart() + "_stats.png"
        ));
        Files.write(imagePath, pngBytes);
        return imagePath;
    }

    private static String manifest(DetectedHighlight highlight, int frameCount) {
        return "type=" + highlight.type() + System.lineSeparator()
            + "detectedAt=" + highlight.detectedAt() + System.lineSeparator()
            + "frameCount=" + frameCount + System.lineSeparator()
            + "sourceMessage=" + highlight.sourceMessage() + System.lineSeparator();
    }

    private static Path uniquePath(Path path) {
        if (!Files.exists(path)) {
            return path;
        }

        String fileName = path.getFileName().toString();
        Path parent = path.getParent();
        for (int i = 2; ; i++) {
            Path candidate = parent.resolve(fileName + "_" + i);
            if (!Files.exists(candidate)) {
                return candidate;
            }
        }
    }
}
