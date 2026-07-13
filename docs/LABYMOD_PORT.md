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
