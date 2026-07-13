# LabyReplay LabyMod Port

Dieses Projekt enthielt urspruenglich ein Forge-Beispieltemplate. Die neue Replay-Struktur ist jetzt in drei
Module getrennt: `api`, `core` und `game-runner`.

## Zielarchitektur

- `api`: stabile Datentypen, Settings, Event-Typen und Rueckgabewerte.
- `core`: Minecraft-unabhaengige Replay-, Highlight-, Storage- und Share-Bild-Services.
- `game-runner`: duenne Spiel-, Loader- oder LabyMod-Adapter-Schicht.
- Versionsspezifische Zugriffe: nur in `game-runner`, wenn die LabyMod-API kein passendes Event anbietet.

## LabyMod-Anbindung

Ein echtes LabyMod-4-Addon sollte aus dem offiziellen Addon-Template aufgebaut werden:

- Root-Build mit `net.labymod.labygradle` und `net.labymod.labygradle.addon`.
- Module `api`, `core` und optional `game-runner`.
- Addon-Main im `game-runner` oder in einem LabyMod-spezifischen Runner mit `@AddonMain`.
- Listener im Runner registrieren und an `LabyReplayCore` weiterleiten.
- Einstellungen ueber `AddonConfig` und LabyMod-Setting-Widgets.

Der vorhandene Core braucht dafuer nur drei Adapter-Aufrufe:

```java
core.recordFrame(encodedImageBytes, "jpg");
core.onChatMessage(plainChatMessage);
core.saveStatsShareImage(playerName, serverName, true);
```

## Replay-Verhalten

`CircularClipBuffer` clippt dauerhaft lokal weiter. Es wird nicht permanent ein Video geschrieben, sondern ein
kurzer Ringbuffer aus bereits kodierten Frames gehalten. Bei Kill, Bettabbau, Rundengewinn oder manuellem Save
wird dieser Buffer in einen Highlight-Ordner geschrieben.

Das ist absichtlich FPS-schonend:

- niedrige Sampling-FPS, standardmaessig 12 FPS;
- maximale Frame-Groesse, standardmaessig 512 KiB;
- keine schweren Bildberechnungen im Renderloop;
- Share-Stats-Bild wird nur nach Bedarf erzeugt.

## Store- und Server-Regeln

- Keine Automatisierung von Klicks, Movement oder Aim.
- Keine Umgehung von Server-Feature-Sperren.
- Keine Uploads ohne explizite Nutzeraktion.
- Mixins nur fuer fehlende Capture-Hooks, nicht fuer Gameplay-Vorteile.
