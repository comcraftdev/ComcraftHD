# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ComcraftHD is a J2ME (Java ME) voxel-based mobile game similar to Minecraft, targeting MIDP 2.0 devices. It uses NetBeans as the IDE and Apache Ant as the build system. It is designed to run on devices with limited resources, such as older mobile phones. For this reason, it requires careful management of memory and performance, avoiding unnecessary overhead and sticking to the constraints of old Java dialect.

## Build Commands

You currently don't have access to build commands, but here are the common command that user can run to build and run the project. If you need to check if the project builds successfully, ask the user to build the project for you.

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
  - `Block.java` - Base class for all blocks
  - `BlockDefinitions.java` - Block registry
  - `blocks/` - Individual block implementations
  - `client/renderers/` - Block renderers

### Threading Model

The game uses multiple threads:
- Game thread - Game logic updates
- Renderer thread - 3D rendering
- World loading/generation thread - Asynchronous world loading
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
- Prefer `import comcrafthd.*;`, `import comcrafthd.client.*;` etc. for comcraft related imports for simplicity
- Math.round() doesn't work in J2ME, use MathHelper.roundToInt() instead