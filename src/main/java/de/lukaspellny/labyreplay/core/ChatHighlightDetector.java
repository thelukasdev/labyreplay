package de.lukaspellny.labyreplay.core;

import java.time.Clock;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

public final class ChatHighlightDetector {

    private static final Pattern COLOR_CODES = Pattern.compile("(?i)§[0-9A-FK-OR]");
    private static final Pattern KILL = Pattern.compile(
        "\\b(killed|eliminated|final kill|finalkill|getötet|besiegt|eliminiert)\\b",
        Pattern.CASE_INSENSITIVE
    );
    private static final Pattern BED_BREAK = Pattern.compile(
        "\\b(bed destroyed|bed broken|bett zerstört|bett abgebaut|bett wurde zerstört|bett von)\\b",
        Pattern.CASE_INSENSITIVE
    );
    private static final Pattern ROUND_WIN = Pattern.compile(
        "\\b(victory|winner|you won|gewonnen|sieg|runde gewonnen)\\b",
        Pattern.CASE_INSENSITIVE
    );
    private static final Pattern DEATH = Pattern.compile(
        "\\b(you died|died|gestorben|du bist gestorben)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private final Clock clock;

    public ChatHighlightDetector(Clock clock) {
        this.clock = clock;
    }

    public Optional<DetectedHighlight> detect(String rawMessage, RoundStats stats) {
        String message = normalize(rawMessage);

        if (BED_BREAK.matcher(message).find()) {
            stats.recordBedBreak();
            return Optional.of(new DetectedHighlight(HighlightEventType.BED_BREAK, rawMessage, this.clock.instant()));
        }

        if (ROUND_WIN.matcher(message).find()) {
            return Optional.of(new DetectedHighlight(HighlightEventType.ROUND_WIN, rawMessage, this.clock.instant()));
        }

        if (KILL.matcher(message).find()) {
            stats.recordKill();
            return Optional.of(new DetectedHighlight(HighlightEventType.KILL, rawMessage, this.clock.instant()));
        }

        if (DEATH.matcher(message).find()) {
            stats.recordDeath();
        }

        return Optional.empty();
    }

    private static String normalize(String message) {
        return COLOR_CODES.matcher(message)
            .replaceAll("")
            .toLowerCase(Locale.ROOT)
            .trim();
    }
}
