package de.lukaspellny.labyreplay.core.addon.listener;

import de.lukaspellny.labyreplay.core.LabyReplayCore;
import de.lukaspellny.labyreplay.core.addon.LabyReplayAddon;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;

public final class LabyReplayChatListener {

  private static final String[] MESSAGE_METHODS = {
      "message",
      "component",
      "chatMessage",
      "text"
  };

  private static final String[] TEXT_METHODS = {
      "plainText",
      "getPlainText",
      "getString",
      "getText"
  };

  private final LabyReplayAddon addon;
  private final LabyReplayCore replayCore;

  public LabyReplayChatListener(LabyReplayAddon addon, LabyReplayCore replayCore) {
    this.addon = addon;
    this.replayCore = replayCore;
  }

  @Subscribe
  public void onChatReceive(ChatReceiveEvent event) {
    if (!this.addon.configuration().enabled().get()) {
      return;
    }

    Optional<String> plainMessage = this.extractPlainMessage(event);
    if (plainMessage.isEmpty() || plainMessage.get().isBlank()) {
      return;
    }

    try {
      this.replayCore.onChatMessage(plainMessage.get()).ifPresent(highlight ->
          this.addon.logger().info(
              "Saved {} highlight with {} buffered frames to {}",
              highlight.type(),
              highlight.frameCount(),
              highlight.directory()
          )
      );
    } catch (IOException exception) {
      this.addon.logger().warn("Failed to save LabyReplay highlight", exception);
    }
  }

  private Optional<String> extractPlainMessage(ChatReceiveEvent event) {
    for (String methodName : MESSAGE_METHODS) {
      Optional<Object> message = invokeNoArg(event, methodName);
      if (message.isPresent()) {
        return Optional.of(this.asPlainText(message.get()));
      }
    }

    return Optional.of(event.toString());
  }

  private String asPlainText(Object message) {
    if (message instanceof CharSequence sequence) {
      return sequence.toString();
    }

    for (String methodName : TEXT_METHODS) {
      Optional<Object> text = invokeNoArg(message, methodName);
      if (text.isPresent()) {
        return String.valueOf(text.get());
      }
    }

    return String.valueOf(message);
  }

  private static Optional<Object> invokeNoArg(Object target, String methodName) {
    try {
      Method method = target.getClass().getMethod(methodName);
      return Optional.ofNullable(method.invoke(target));
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
      return Optional.empty();
    }
  }
}
