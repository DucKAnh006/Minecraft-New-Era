# New Era

## Overview

New Era is a Minecraft mod built for the Fabric ecosystem that refreshes the vanilla experience with upgraded visuals, more lifelike world interactions, and overhauled gameplay elements.

### Key Features

- **Refreshed Animations & Visuals**: Smoother, more expressive animations and visual polish across vanilla entities and environments.
- **Living World & Behaviors**: Enhanced creature behaviors, ambient reactions, and biome-based variations that make the world feel more alive.
- **Overhauled Encounters**: Reworked major gameplay encounters, progression moments, and atmospheric audio-visual details.

## Technical Requirements

- Minecraft: 26.3
- Fabric Loader: 0.19.5 or newer
- Fabric API: 0.161.0+26.3
- Java Development Kit (JDK): 25 or newer

## Setup and Running Guide

### 1. Prerequisites

Make sure **Java JDK 25** (or newer) is installed and available in your system `PATH`.

Verify your current Java version with:
```bash
java -version
```

If Java 25 is not installed yet, you can download and install a compatible JDK 25 build (Eclipse Temurin 25) using one of the commands below:

- **Windows (WinGet)**:
  ```bash
  winget install EclipseAdoptium.Temurin.25.JDK
  ```
- **macOS (Homebrew)**:
  ```bash
  brew install --cask temurin@25
  ```
- **Linux (APT — Debian/Ubuntu with Adoptium repository)**:
  ```bash
  sudo apt update && sudo apt install temurin-25-jdk
  ```
- **SDKMAN! (macOS / Linux / WSL)**:
  ```bash
  sdk install java 25.0.5-tem
  ```

You can also download installers manually from [Adoptium Temurin 25 Releases](https://adoptium.net/temurin/releases/?version=25).

### 2. Run and Build

From the project root directory, use `runClient` to launch the development client or `build` to compile the mod JAR into `build/libs/`:

- **Windows (Command Prompt / PowerShell)**:
  ```bash
  gradlew.bat runClient
  gradlew.bat build
  ```

- **Linux / macOS**:
  ```bash
  ./gradlew runClient
  ./gradlew build
  ```

Once the build finishes successfully, the packaged `.jar` file will be located inside the `build/libs/` directory.

### 3. Project Structure

- `src/main`: Core mod logic, registries, world features, sound events, and common/server mixins.
- `src/client`: Client-side rendering, custom models, animations, and HUD/UI elements.

## Author

- Nguyen Tran Duc Anh (GitHub: DucKAnh006)

## License

This project is licensed under the CC0 1.0 Universal License. See the `LICENSE` file for details.
