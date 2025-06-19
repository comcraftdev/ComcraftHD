# Minecraft Blocks Reference

This document serves as a reference for implementing Minecraft blocks in ComcraftHD. It includes block IDs, descriptions, and metadata (data value) usage.

## Block ID System

In classic Minecraft (pre-1.13), blocks use:
- **Block ID**: Main identifier (0-255 in early versions)
- **Metadata/Data Value**: 4-bit value (0-15) for block variants

## Complete Block List

### Basic Blocks (0-31)

| ID | Name | Description | Metadata Usage |
|----|------|-------------|----------------|
| 0 | Air | Empty space | Not used |
| 1 | Stone | Basic building material | 0: Stone, 1: Granite, 2: Polished Granite, 3: Diorite, 4: Polished Diorite, 5: Andesite, 6: Polished Andesite |
| 2 | Grass Block | Dirt with grass on top | Not used (snowy variant uses block state) |
| 3 | Dirt | Basic terrain block | 0: Dirt, 1: Coarse Dirt, 2: Podzol |
| 4 | Cobblestone | Mined stone result | Not used |
| 5 | Planks | Wooden planks | 0: Oak, 1: Spruce, 2: Birch, 3: Jungle, 4: Acacia, 5: Dark Oak |
| 6 | Sapling | Tree saplings | 0: Oak, 1: Spruce, 2: Birch, 3: Jungle, 4: Acacia, 5: Dark Oak (bits 3-4 for growth stage) |
| 7 | Bedrock | Unbreakable base layer | Not used |
| 8 | Water | Flowing water | 0-7: Water level (0 = source), 8-15: Falling water |
| 9 | Stationary Water | Still water source | Same as ID 8 |
| 10 | Lava | Flowing lava | 0-7: Lava level (0 = source), 8-15: Falling lava |
| 11 | Stationary Lava | Still lava source | Same as ID 10 |
| 12 | Sand | Falls when unsupported | 0: Sand, 1: Red Sand |
| 13 | Gravel | Falls when unsupported | Not used |
| 14 | Gold Ore | Mineable ore | Not used |
| 15 | Iron Ore | Mineable ore | Not used |
| 16 | Coal Ore | Mineable ore | Not used |
| 17 | Log | Tree logs | 0-3: Oak/Spruce/Birch/Jungle, bits 2-3 for orientation |
| 18 | Leaves | Tree leaves | 0-3: Oak/Spruce/Birch/Jungle, bit 2: decay check, bit 3: persistent |
| 19 | Sponge | Absorbs water | 0: Dry, 1: Wet |
| 20 | Glass | Transparent block | Not used |
| 21 | Lapis Lazuli Ore | Mineable ore | Not used |
| 22 | Lapis Lazuli Block | Storage block | Not used |
| 23 | Dispenser | Redstone device | 0-5: Facing direction |
| 24 | Sandstone | Crafted from sand | 0: Normal, 1: Chiseled, 2: Smooth |
| 25 | Note Block | Musical block | Not used (pitch stored in tile entity) |
| 26 | Bed | Sleep and spawn point | 0-3: Direction, bit 2: occupied, bit 3: head/foot |
| 27 | Powered Rail | Accelerates minecarts | 0-5: Orientation, bit 3: powered |
| 28 | Detector Rail | Detects minecarts | 0-5: Orientation, bit 3: powered |
| 29 | Sticky Piston | Pulls blocks | 0-5: Direction, bit 3: extended |
| 30 | Cobweb | Slows entities | Not used |
| 31 | Tall Grass | Decorative plant | 0: Shrub, 1: Grass, 2: Fern |

### Plant and Decoration Blocks (32-63)

| ID | Name | Description | Metadata Usage |
|----|------|-------------|----------------|
| 32 | Dead Bush | Decorative plant | Not used |
| 33 | Piston | Pushes blocks | 0-5: Direction, bit 3: extended |
| 34 | Piston Head | Piston extension | 0-5: Direction, bit 3: sticky |
| 35 | Wool | Colored building block | 0-15: 16 colors (white, orange, magenta, etc.) |
| 36 | Piston Extension | Technical block | Not used |
| 37 | Dandelion | Yellow flower | Not used |
| 38 | Poppy/Flowers | Various flowers | 0: Poppy, 1: Blue Orchid, 2: Allium, 3: Azure Bluet, 4: Red Tulip, 5: Orange Tulip, 6: White Tulip, 7: Pink Tulip, 8: Oxeye Daisy |
| 39 | Brown Mushroom | Grows in dark areas | Not used |
| 40 | Red Mushroom | Grows in dark areas | Not used |
| 41 | Gold Block | Storage block | Not used |
| 42 | Iron Block | Storage block | Not used |
| 43 | Double Stone Slab | Full block slab | 0: Stone, 1: Sandstone, 2: Wood, 3: Cobblestone, 4: Brick, 5: Stone Brick, 6: Nether Brick, 7: Quartz |
| 44 | Stone Slab | Half block | Same as ID 43, bit 3: top half |
| 45 | Bricks | Clay bricks | Not used |
| 46 | TNT | Explosive block | Not used |
| 47 | Bookshelf | Decorative/enchanting | Not used |
| 48 | Mossy Cobblestone | Dungeon block | Not used |
| 49 | Obsidian | Blast resistant | Not used |
| 50 | Torch | Light source | 0: Invalid, 1: East, 2: West, 3: South, 4: North, 5: Up |
| 51 | Fire | Spreading fire | 0-15: Age |
| 52 | Monster Spawner | Spawns mobs | Not used (mob type in tile entity) |
| 53 | Oak Wood Stairs | Climbable block | 0-3: Direction, bit 2: upside-down |
| 54 | Chest | Storage container | 2-5: Facing direction |
| 55 | Redstone Wire | Power transmission | 0-15: Power level |
| 56 | Diamond Ore | Rare ore | Not used |
| 57 | Diamond Block | Storage block | Not used |
| 58 | Crafting Table | 3x3 crafting grid | Not used |
| 59 | Wheat Crops | Farmland crop | 0-7: Growth stage |
| 60 | Farmland | Tilled dirt | 0-7: Moisture level |
| 61 | Furnace | Smelting block | 2-5: Facing direction |
| 62 | Burning Furnace | Active furnace | 2-5: Facing direction |
| 63 | Standing Sign | Text display | 0-15: Rotation |

### Utility and Redstone Blocks (64-95)

| ID | Name | Description | Metadata Usage |
|----|------|-------------|----------------|
| 64 | Oak Door | Openable barrier | 0-3: Direction, bit 2: top half, bit 3: hinge side/powered |
| 65 | Ladder | Climbable block | 2-5: Facing direction |
| 66 | Rail | Minecart track | 0-9: Shape/direction |
| 67 | Cobblestone Stairs | Climbable block | 0-3: Direction, bit 2: upside-down |
| 68 | Wall Sign | Wall-mounted sign | 2-5: Facing direction |
| 69 | Lever | Redstone switch | 0-7: Orientation, bit 3: powered |
| 70 | Stone Pressure Plate | Detects entities | Bit 0: powered |
| 71 | Iron Door | Stronger door | Same as ID 64 |
| 72 | Wooden Pressure Plate | Detects entities | Bit 0: powered |
| 73 | Redstone Ore | Glows when touched | Not used |
| 74 | Glowing Redstone Ore | Active redstone ore | Not used |
| 75 | Redstone Torch (off) | Inverted power source | 0-5: Same as torch |
| 76 | Redstone Torch (on) | Power source | 0-5: Same as torch |
| 77 | Stone Button | Momentary switch | 0-5: Facing, bit 3: powered |
| 78 | Snow Layer | Thin snow cover | 0-7: Layers (0-7 = 1-8 layers) |
| 79 | Ice | Slippery block | Not used |
| 80 | Snow Block | Full snow block | Not used |
| 81 | Cactus | Damages on contact | 0-15: Age |
| 82 | Clay Block | Found near water | Not used |
| 83 | Sugar Canes | Grows near water | 0-15: Age |
| 84 | Jukebox | Plays music discs | Not used (disc in tile entity) |
| 85 | Fence | Barrier block | Not used |
| 86 | Pumpkin | Decorative gourd | 0-3: Direction |
| 87 | Netherrack | Nether stone | Not used |
| 88 | Soul Sand | Slows movement | Not used |
| 89 | Glowstone | Light source | Not used |
| 90 | Nether Portal | Dimension transport | 0-1: Axis (X/Z) |
| 91 | Jack o'Lantern | Light source | 0-3: Direction |
| 92 | Cake | Food block | 0-6: Bites taken |
| 93 | Repeater (off) | Redstone delay | 0-3: Direction, bits 2-3: delay |
| 94 | Repeater (on) | Active repeater | Same as ID 93 |
| 95 | Stained Glass | Colored glass | 0-15: 16 colors |

### Special and Nether Blocks (96-127)

| ID | Name | Description | Metadata Usage |
|----|------|-------------|----------------|
| 96 | Trapdoor | Horizontal door | 0-3: Direction, bit 2: open, bit 3: top half |
| 97 | Monster Egg | Silverfish block | 0: Stone, 1: Cobblestone, 2: Stone Brick, 3: Mossy Stone Brick, 4: Cracked Stone Brick, 5: Chiseled Stone Brick |
| 98 | Stone Bricks | Decorative stone | 0: Normal, 1: Mossy, 2: Cracked, 3: Chiseled |
| 99 | Brown Mushroom Block | Giant mushroom part | 0-10: Texture sides, 14: All sides, 15: All stem |
| 100 | Red Mushroom Block | Giant mushroom part | Same as ID 99 |
| 101 | Iron Bars | Thin barrier | Not used (connections automatic) |
| 102 | Glass Pane | Thin glass | Not used (connections automatic) |
| 103 | Melon | Food source | Not used |
| 104 | Pumpkin Stem | Growing pumpkin | 0-7: Growth stage |
| 105 | Melon Stem | Growing melon | 0-7: Growth stage |
| 106 | Vines | Climbable plant | Bits 0-3: South/West/North/East |
| 107 | Fence Gate | Openable fence | 0-3: Direction, bit 2: open |
| 108 | Brick Stairs | Climbable block | 0-3: Direction, bit 2: upside-down |
| 109 | Stone Brick Stairs | Climbable block | 0-3: Direction, bit 2: upside-down |
| 110 | Mycelium | Mushroom grass | Not used |
| 111 | Lily Pad | Water surface plant | Not used |
| 112 | Nether Brick | Nether fortress block | Not used |
| 113 | Nether Brick Fence | Nether barrier | Not used |
| 114 | Nether Brick Stairs | Nether stairs | 0-3: Direction, bit 2: upside-down |
| 115 | Nether Wart | Brewing ingredient | 0-3: Growth stage |
| 116 | Enchantment Table | Enchanting items | Not used |
| 117 | Brewing Stand | Potion brewing | Bits 0-2: Bottle slots filled |
| 118 | Cauldron | Water storage | 0-3: Water level |
| 119 | End Portal | End dimension access | Not used |
| 120 | End Portal Frame | Portal structure | 0-3: Direction, bit 2: eye inserted |
| 121 | End Stone | End dimension block | Not used |
| 122 | Dragon Egg | Victory trophy | Not used |
| 123 | Redstone Lamp (off) | Powered light | Not used |
| 124 | Redstone Lamp (on) | Active lamp | Not used |
| 125 | Double Wood Slab | Full wood slab | 0-5: Wood types |
| 126 | Wood Slab | Half wood block | 0-5: Wood types, bit 3: top half |
| 127 | Cocoa | Cocoa beans | 0-3: Direction, bits 2-3: growth stage |

### Extended Blocks (128-255)

| ID | Name | Description | Metadata Usage |
|----|------|-------------|----------------|
| 128 | Sandstone Stairs | Desert stairs | 0-3: Direction, bit 2: upside-down |
| 129 | Emerald Ore | Rare mountain ore | Not used |
| 130 | Ender Chest | Shared storage | 2-5: Facing direction |
| 131 | Tripwire Hook | Trap component | 0-3: Direction, bit 2: connected, bit 3: powered |
| 132 | Tripwire | Trap wire | Bit 0: powered, bit 2: attached, bit 3: disarmed |
| 133 | Emerald Block | Storage block | Not used |
| 134 | Spruce Wood Stairs | Wood stairs variant | 0-3: Direction, bit 2: upside-down |
| 135 | Birch Wood Stairs | Wood stairs variant | 0-3: Direction, bit 2: upside-down |
| 136 | Jungle Wood Stairs | Wood stairs variant | 0-3: Direction, bit 2: upside-down |
| 137 | Command Block | Server commands | Not used |
| 138 | Beacon | Buff provider | Not used |
| 139 | Cobblestone Wall | Decorative wall | 0: Cobblestone, 1: Mossy |
| 140 | Flower Pot | Plant container | 0-12: Content type |
| 141 | Carrots | Farmland crop | 0-7: Growth stage |
| 142 | Potatoes | Farmland crop | 0-7: Growth stage |
| 143 | Wooden Button | Momentary switch | 0-5: Facing, bit 3: powered |
| 144 | Mob Head | Decorative head | 0-5: Rotation (only floor) |
| 145 | Anvil | Item repair/rename | 0-3: Direction, bits 2-3: damage |
| 146 | Trapped Chest | Redstone chest | 2-5: Facing direction |
| 147 | Light Weighted Pressure Plate | Gold plate | 0-15: Power level |
| 148 | Heavy Weighted Pressure Plate | Iron plate | 0-15: Power level |
| 149 | Redstone Comparator (off) | Redstone device | 0-3: Direction, bit 2: mode, bit 3: powered |
| 150 | Redstone Comparator (on) | Active comparator | Same as ID 149 |
| 151 | Daylight Sensor | Light detector | 0-15: Power level |
| 152 | Redstone Block | Power source | Not used |
| 153 | Nether Quartz Ore | Nether ore | Not used |
| 154 | Hopper | Item transport | 0-5: Direction |
| 155 | Quartz Block | Decorative block | 0: Normal, 1: Chiseled, 2: Pillar, 3-4: Pillar rotated |
| 156 | Quartz Stairs | White stairs | 0-3: Direction, bit 2: upside-down |
| 157 | Activator Rail | Minecart activator | 0-5: Orientation, bit 3: powered |
| 158 | Dropper | Item dispenser | 0-5: Facing direction |
| 159 | Stained Clay | Colored terracotta | 0-15: 16 colors |
| 160 | Stained Glass Pane | Colored thin glass | 0-15: 16 colors |
| 161 | Leaves2 | New tree leaves | 0-1: Acacia/Dark Oak, bit 2: decay check, bit 3: persistent |
| 162 | Log2 | New tree logs | 0-1: Acacia/Dark Oak, bits 2-3 for orientation |
| 163 | Acacia Wood Stairs | Wood stairs variant | 0-3: Direction, bit 2: upside-down |
| 164 | Dark Oak Wood Stairs | Wood stairs variant | 0-3: Direction, bit 2: upside-down |
| 165 | Slime Block | Bouncy block | Not used |
| 166 | Barrier | Invisible wall | Not used |
| 167 | Iron Trapdoor | Strong trapdoor | 0-3: Direction, bit 2: open, bit 3: top half |
| 168 | Prismarine | Ocean monument | 0: Prismarine, 1: Prismarine Bricks, 2: Dark Prismarine |
| 169 | Sea Lantern | Underwater light | Not used |
| 170 | Hay Bale | Decorative/feed | 0-2: Axis (Y/Z/X) |
| 171 | Carpet | Thin decoration | 0-15: 16 colors |
| 172 | Hardened Clay | Terracotta | Not used |
| 173 | Coal Block | Fuel storage | Not used |
| 174 | Packed Ice | No-melt ice | Not used |
| 175 | Double Plant | Tall plants | 0-5: Type, bit 3: top half |

### Technical and New Blocks (176-255)

| ID | Name | Description | Metadata Usage |
|----|------|-------------|----------------|
| 176 | Standing Banner | Decorative flag | 0-15: Rotation |
| 177 | Wall Banner | Wall-mounted flag | 2-5: Facing direction |
| 178 | Inverted Daylight Sensor | Night detector | 0-15: Power level |
| 179 | Red Sandstone | Desert variant | 0: Normal, 1: Chiseled, 2: Smooth |
| 180 | Red Sandstone Stairs | Desert stairs | 0-3: Direction, bit 2: upside-down |
| 181 | Double Stone Slab2 | New slab types | Bit 3: Red sandstone |
| 182 | Stone Slab2 | New half slabs | Bit 3: top half |
| 183 | Spruce Fence Gate | Wood gate variant | 0-3: Direction, bit 2: open |
| 184 | Birch Fence Gate | Wood gate variant | 0-3: Direction, bit 2: open |
| 185 | Jungle Fence Gate | Wood gate variant | 0-3: Direction, bit 2: open |
| 186 | Dark Oak Fence Gate | Wood gate variant | 0-3: Direction, bit 2: open |
| 187 | Acacia Fence Gate | Wood gate variant | 0-3: Direction, bit 2: open |
| 188 | Spruce Fence | Wood fence variant | Not used |
| 189 | Birch Fence | Wood fence variant | Not used |
| 190 | Jungle Fence | Wood fence variant | Not used |
| 191 | Dark Oak Fence | Wood fence variant | Not used |
| 192 | Acacia Fence | Wood fence variant | Not used |
| 193 | Spruce Door | Wood door variant | Same as oak door |
| 194 | Birch Door | Wood door variant | Same as oak door |
| 195 | Jungle Door | Wood door variant | Same as oak door |
| 196 | Acacia Door | Wood door variant | Same as oak door |
| 197 | Dark Oak Door | Wood door variant | Same as oak door |

## Metadata Usage Patterns

### Common Patterns:
- **Direction/Facing**: Many blocks use 0-5 for 6 directions (down, up, north, south, west, east)
- **Rotation**: Signs and similar use 0-15 for 16 rotation positions
- **Growth Stages**: Crops typically use 0-7 for 8 growth stages
- **Wood Types**: 0-5 typically represents Oak, Spruce, Birch, Jungle, Acacia, Dark Oak
- **Colors**: Wool, glass, clay use 0-15 for 16 dye colors
- **Power Levels**: Redstone components often use 0-15 for signal strength

### Bit Usage:
- **Bit 0-1**: Often direction or type
- **Bit 2**: Common for binary states (open/closed, top/bottom half)
- **Bit 3**: Secondary states (powered, connected, persistent)

## Implementation Notes

1. **Block States**: Modern Minecraft (1.13+) replaced the ID/metadata system with block states. For J2ME implementation, the classic system is more suitable.

2. **Tile Entities**: Some blocks (chests, furnaces, signs) store additional data in tile entities rather than metadata.

3. **Block Updates**: Many blocks require update ticks (water flow, plant growth, redstone).

4. **Rendering Considerations**: Different blocks need different rendering approaches:
   - Full cubes (stone, dirt)
   - Transparent (glass, leaves)
   - Non-cube models (stairs, slabs, fences)
   - Fluids (water, lava)
   - Plants (crossed billboards)

5. **Performance**: For J2ME, consider:
   - Limiting complex blocks
   - Simplified physics
   - Reduced metadata usage
   - Efficient rendering for common blocks