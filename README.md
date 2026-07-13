# LabyReplay

LabyReplay is a local highlight recorder for LabyMod. It keeps a short replay buffer and can save the last moments around important in-game events such as kills, bed breaks, round wins, or manual saves.

## Features

- Continuous local clip buffer core
- Chat-message highlight detection core
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

The official GitHub Actions workflow also creates a release jar with:

```bash
./gradlew createReleaseJar
```

## Privacy

LabyReplay is local-only. It does not upload highlights, collect analytics, or read account credentials. See [PRIVACY.md](PRIVACY.md).

## Store Status

The repository is prepared for LabyMod Store review structure-wise, but final event wiring must be compiled and tested in the LabyMod development client before submission. See [STORE_SUBMISSION.md](STORE_SUBMISSION.md).
