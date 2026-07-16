package de.lukaspellny.labyreplay.core.addon;

import de.lukaspellny.labyreplay.core.LabyReplayCore;
import de.lukaspellny.labyreplay.core.addon.command.LabyReplayCommand;
import de.lukaspellny.labyreplay.core.addon.listener.LabyReplayChatListener;
import java.nio.file.Path;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class LabyReplayAddon extends LabyAddon<LabyReplayConfiguration> {

  private LabyReplayCore replayCore;

  @Override
  protected void enable() {
    Path highlightDirectory = Path.of(System.getProperty("user.home"), "LabyReplay", "Highlights");

    this.replayCore = new LabyReplayCore(
        this.configuration().replaySettings(),
        highlightDirectory
    );

    this.registerListener(new LabyReplayChatListener(this, this.replayCore));
    this.registerCommand(new LabyReplayCommand(this, this.replayCore));
    this.registerSettingCategory();
    this.logger().info("LabyReplay enabled. Highlights will be written to {}", highlightDirectory);
  }

  @Override
  protected Class<LabyReplayConfiguration> configurationClass() {
    return LabyReplayConfiguration.class;
  }

  public LabyReplayCore replayCore() {
    return this.replayCore;
  }
}
