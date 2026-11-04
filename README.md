# New Era

## Overview

New Era is a Minecraft mod developed for the Fabric ecosystem that revitalizes the vanilla world by overhauling creature visuals, introducing natural animal behaviors, expanding climate-based variants, and redesigning the Ender Dragon encounter into a challenging, cinematic boss fight.

### Key Features

- Lifelike Entity Animations: Custom skeletal animation systems and natural movement sets for vanilla fauna, including Cows, Chickens, Pigs, Sheep, Camels, Horses, Donkeys, Mules, Wolves, Cats, Ocelots, Parrots, and Armadillos.
- Dynamic Animal Behaviors: Enhanced AI routines allowing creatures to sit, sleep, stalk prey, forage, and react realistically to environment stimuli.
- Biome and Climate Variants: Appearance and physiological adaptations across different climates (temperate, cold, and warm biomes).
- Ender Dragon Overhaul: Completely redesigned Ender Dragon combat phases, custom bite and breath attacks, custom dragon sounds, a redesigned boss health bar HUD, and revamped End structures including central podium and obsidian spikes.

## Technical Requirements

- Minecraft: 26.3
- Fabric Loader: 0.19.5 or newer
- Fabric API: 0.161.0+26.3
- Java Development Kit (JDK): 25 or newer

## Setup and Running Guide

### 1. Prerequisites

Make sure the following tools are installed and available in your environment path:
- Java JDK 25 or newer
- Git

Verify your Java version with:
```bash
java -version
```

### 2. Clone the Repository

Clone the project to your local machine:
```bash
git clone https://github.com/DucKAnh006/Minecraft-New-Era-Mod.git
cd Minecraft-New-Era-Mod
```

### 3. Launch the Development Client

To launch the Minecraft client in the development environment, execute:

On Windows (Command Prompt / PowerShell):
```cmd
gradlew.bat runClient
```

On Linux / macOS:
```bash
./gradlew runClient
```

Alternatively, on Windows, you can execute the helper script located in the dev directory:
```cmd
dev\run_game.bat
```

### 4. Build the Mod File

To compile and package the mod jar for distribution, run:

On Windows:
```cmd
gradlew.bat build
```

On Linux / macOS:
```bash
./gradlew build
```

Once the build finishes successfully, the packaged jar will be located inside the `build/libs/` directory.

### 5. Development Workspace Structure

- `src/main`: Mod initialization logic, game registries, worldgen features, sound handlers, and server-side mixins.
- `src/client`: Client-side rendering pipeline, custom entity renderers, models, animations, and HUD overlays.
- `dev/`: Development workspace containing raw Blockbench models, animation backups, and development scripts.

## Author

- Nguyen Tran Duc Anh (GitHub: DucKAnh006)

## License

This project is licensed under the CC0 1.0 Universal License. See the LICENSE file for details.
