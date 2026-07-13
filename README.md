# LabyReplay

LabyReplay is a local highlight recorder concept for LabyMod. It continuously keeps a short replay buffer and saves the last moments when important in-game events happen, such as kills, bed breaks, round wins, or manual saves.

## Features

- Continuous local clip buffer
- Auto-highlight detection from chat messages
- Highlight export into a local `Highlights` folder
- Round stats tracking for kills, beds, hits, and deaths
- Share-ready stats image generation
- Modular structure for LabyMod-style development

## Project Structure

- `api`  
  Shared contracts and data types.

- `core`  
  Platform-independent replay, highlight, storage, and stats logic.

- `game-runner`  
  Thin integration layer for the game, loader, or future LabyMod adapter.
