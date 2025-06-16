# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ComcraftHD is a J2ME (Java ME) voxel-based mobile game similar to Minecraft, targeting MIDP 2.0 devices. It uses NetBeans as the IDE and Apache Ant as the build system.

## Build Commands

```bash
# Build the JAR file
ant jar

# Clean and build
ant clean jar

# Run in emulator
ant run

# Debug in emulator
ant debug

# Build release version (optimized, obfuscated)
ant -Dconfig.active=Release jar

# Clean all configurations
ant clean-all
```

## Architecture

### Core Components

- **MIDlet Entry Point**: `src/comcrafthd/client/midlets/` - J2ME application entry points
- **Game Core**: `ComcraftGame.java` - Main game loop and state management
- **Rendering**: `ComcraftRenderer.java` - 3D rendering using JSR-184 (Mobile 3D Graphics API)
- **World System**: Chunk-based world similar to Minecraft
  - `Chunk.java` - 16x16x16 voxel chunks
  - `ChunkWorld.java` - World management
  - `ChunkGenerator.java` - Terrain generation
- **Block System**: 
  - `BlockList.java` - Block registry
  - `BlockCreator.java` - Block creation logic
  - `blocks/` - Individual block implementations

### Threading Model

The game uses multiple threads:
- Game thread - Game logic updates
- Renderer thread - 3D rendering
- Proper thread synchronization is critical for J2ME performance

### J2ME Constraints

- **Java Version**: 1.3 (CLDC 1.1)
- **No generics, enums, or modern Java features**
- **Limited memory** - Be mindful of object allocation
- **No traditional unit testing frameworks** - Use `TestHelper.java` for assertions
- **Fixed-point math** - Often used instead of floating-point for performance

## Development Notes

- The project uses preprocessing directives for conditional compilation
- Obfuscation is enabled for release builds (level 9)
- Resources are in `res/` directory (textures, icons)
- Build outputs go to `build/` and `dist/` (both gitignored)
- Current branch: `develop`