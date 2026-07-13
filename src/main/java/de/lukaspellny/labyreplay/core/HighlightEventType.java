package de.lukaspellny.labyreplay.core;

public enum HighlightEventType {
    KILL("kill"),
    BED_BREAK("bed_break"),
    ROUND_WIN("round_win"),
    MANUAL("manual");

    private final String fileNamePart;

    HighlightEventType(String fileNamePart) {
        this.fileNamePart = fileNamePart;
    }

    public String fileNamePart() {
        return this.fileNamePart;
    }
}
