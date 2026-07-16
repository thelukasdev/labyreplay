package de.lukaspellny.labyreplay.core.addon;

import de.lukaspellny.labyreplay.api.ReplaySettings;
import java.time.Duration;
import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget.SliderSetting;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class LabyReplayConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> autoSaveOnKill = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> autoSaveOnBedBreak = new ConfigProperty<>(true);

  @SwitchSetting
  private final ConfigProperty<Boolean> autoSaveOnRoundWin = new ConfigProperty<>(true);

  @SliderSetting(min = 5, max = 45)
  private final ConfigProperty<Integer> clipLengthSeconds = new ConfigProperty<>(20);

  @SliderSetting(min = 4, max = 20)
  private final ConfigProperty<Integer> targetFramesPerSecond = new ConfigProperty<>(12);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> autoSaveOnKill() {
    return this.autoSaveOnKill;
  }

  public ConfigProperty<Boolean> autoSaveOnBedBreak() {
    return this.autoSaveOnBedBreak;
  }

  public ConfigProperty<Boolean> autoSaveOnRoundWin() {
    return this.autoSaveOnRoundWin;
  }

  public ConfigProperty<Integer> clipLengthSeconds() {
    return this.clipLengthSeconds;
  }

  public ConfigProperty<Integer> targetFramesPerSecond() {
    return this.targetFramesPerSecond;
  }

  public ReplaySettings replaySettings() {
    return new ReplaySettings(
        this.enabled.get(),
        this.autoSaveOnKill.get(),
        this.autoSaveOnBedBreak.get(),
        this.autoSaveOnRoundWin.get(),
        Duration.ofSeconds(this.clipLengthSeconds.get()),
        this.targetFramesPerSecond.get(),
        512 * 1024
    );
  }
}
