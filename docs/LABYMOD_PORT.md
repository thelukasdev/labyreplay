# LabyReplay LabyMod Port

The replay logic is split into LabyMod-style modules:

- `api`: stable data types, settings, event types, and return values.
- `core`: platform-independent replay, highlight, storage, and share-image services.
- `game-runner`: template runner files for local development.

Loader-specific or version-specific code should stay outside the replay core. The core only needs adapter calls like:

```java
core.recordFrame(encodedImageBytes, "jpg");
core.onChatMessage(plainChatMessage);
core.saveStatsShareImage(playerName, serverName, true);
```

Server rules matter: no automated clicks, movement, aim, feature-lock bypassing, or hidden uploads.

Official LabyMod runtime wiring should use public LabyMod events first:

- `ChatReceiveEvent` for chat-based highlight detection.
- `GameTickEvent` only for lightweight scheduling.
- Screenshot/render capture events for frame sampling where available.

Avoid mixins unless there is no public event or API for the capture hook.
