# LabyReplay

LabyReplay is a local highlight recorder for LabyMod. It keeps a short replay buffer and saves the last moments when important in-game events happen, such as kills, bed breaks, round wins, or manual saves.

## Features

- Continuous local clip buffer
- Auto-highlight detection from chat messages
- Local highlight export
- Round stats tracking
- Share-ready stats image generation
- LabyMod-style `api` and `core` module structure

## Modules

- `api`: shared contracts and data types
- `core`: replay, highlight, storage, stats, and LabyMod addon entrypoint
- `game-runner`: LabyMod template runner files

## Build

```bash
./gradlew build
```

On Windows:

```bat
gradlew.bat build
```
