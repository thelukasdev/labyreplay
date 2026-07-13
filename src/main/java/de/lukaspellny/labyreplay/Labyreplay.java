package de.lukaspellny.labyreplay;

import de.lukaspellny.labyreplay.core.LabyReplayCore;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

@Mod(Labyreplay.MODID)
public class Labyreplay {

    public static final String MODID = "labyreplay";

    private static final Logger LOGGER = LoggerFactory.getLogger(Labyreplay.class);

    private final LabyReplayCore core;

    public Labyreplay() {
        Path highlightDirectory = Path.of(System.getProperty("user.home"), "LabyReplay", "Highlights");

        this.core = new LabyReplayCore(
            Config.defaultReplaySettings(),
            highlightDirectory
        );

        LOGGER.info("LabyReplay core loaded. Highlights will be written to {}", highlightDirectory);
    }

    public LabyReplayCore core() {
        return this.core;
    }
}
