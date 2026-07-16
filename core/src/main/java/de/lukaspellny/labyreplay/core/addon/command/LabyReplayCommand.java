package de.lukaspellny.labyreplay.core.addon.command;

import de.lukaspellny.labyreplay.core.LabyReplayCore;
import de.lukaspellny.labyreplay.core.addon.LabyReplayAddon;
import java.io.IOException;
import net.labymod.api.client.chat.command.Command;

public final class LabyReplayCommand extends Command {

  private final LabyReplayAddon addon;
  private final LabyReplayCore replayCore;

  public LabyReplayCommand(LabyReplayAddon addon, LabyReplayCore replayCore) {
    super("labyreplay", "lr");
    this.addon = addon;
    this.replayCore = replayCore;
  }

  @Override
  public boolean execute(String prefix, String[] arguments) {
    if (!this.addon.configuration().enabled().get()) {
      this.addon.logger().info("LabyReplay is disabled.");
      return true;
    }

    String action = arguments.length == 0 ? "save" : arguments[0].toLowerCase();
    try {
      return switch (action) {
        case "save", "clip" -> {
          this.replayCore.saveManualHighlight();
          this.addon.logger().info("Saved manual LabyReplay highlight.");
          yield true;
        }
        case "stats", "share" -> {
          this.replayCore.saveStatsShareImage("Player", "LabyMod", true);
          this.addon.logger().info("Saved LabyReplay stats image.");
          yield true;
        }
        case "reset" -> {
          this.replayCore.resetRoundStats();
          this.addon.logger().info("Reset LabyReplay round stats.");
          yield true;
        }
        default -> false;
      };
    } catch (IOException exception) {
      this.addon.logger().warn("Failed to execute LabyReplay command", exception);
      return true;
    }
  }
}
