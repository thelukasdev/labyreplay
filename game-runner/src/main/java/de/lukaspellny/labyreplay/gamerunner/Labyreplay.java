package de.lukaspellny.labyreplay.gamerunner;

import de.lukaspellny.labyreplay.core.Config;
import de.lukaspellny.labyreplay.core.LabyReplayCore;

import java.nio.file.Path;

public class Labyreplay {

    public static final String MODID = "labyreplay";

    private static final System.Logger LOGGER = System.getLogger(Labyreplay.class.getName());

    private final LabyReplayCore core;

    public Labyreplay() {
        Path highlightDirectory = Path.of(System.getProperty("user.home"), "LabyReplay", "Highlights");

        this.core = new LabyReplayCore(
            Config.defaultReplaySettings(),
            highlightDirectory
        );

        LOGGER.log(System.Logger.Level.INFO, "LabyReplay core loaded. Highlights will be written to {0}", highlightDirectory);
    }

    public LabyReplayCore core() {
        return this.core;
    }
}
