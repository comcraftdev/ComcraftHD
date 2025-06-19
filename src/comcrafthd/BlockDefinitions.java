package comcrafthd;

import comcrafthd.blocks.*;
import comcrafthd.blocks.behaviors.*;

public final class BlockDefinitions {

    public static final int MAX_BLOCKS = 256;

    public static final Block[] blocks = new Block[MAX_BLOCKS];
    
    /*
     * Block renderer IDs
     */
    public static final int RENDERER_STANDARD = 0;

    public static final Block stone = create(1)
            .setAllTexture(1, 0)
            .setName("Stone")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(1.5f)
            .build();

    public static final Block grass = create(2)
            .setAllTexture(2, 0)
            .setSidesTexture(3, 0)
            .setTexture(Block.SIDE_TOP, 0, 0)
            //            .setColor(Block.SIDE_TOP, 0x79C05A)
            //            .setColor(Block.SIDE_TOP, 0x90814D)
            .setAllColor(0x79C05A)
            //            .setAllColor(0x90814D)
            .setName("Grass Block")
            .setMaterial(Block.MATERIAL_SOLID)
            .build();

    public static final Block dirt = create(3)
            .setAllTexture(2, 0)
            .setName("Dirt")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(0.5f)
            .build();

    public static final Block cobblestone = create(4)
            .setAllTexture(0, 1)
            .setName("Cobblestone")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .build();

    public static final Block planks = create(5)
            .setAllTexture(4, 0)
            .setName("Oak Planks")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.0f)
            .build();

    public static final Block sapling = create(6)
            .setAllTexture(15, 0)
            .setName("Oak Sapling")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block bedrock = create(7)
            .setAllTexture(1, 1)
            .setName("Bedrock")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(-1.0f) // Unbreakable
            .build();

    // Water and lava will need special renderers
    public static final Block water = create(8)
            .setAllTexture(15, 13) // Water texture
            .setName("Water")
            .setMaterial(Block.MATERIAL_LIQUID)
            .setTransparent(true)
            .setHardness(-1.0f)
            .build();

    public static final Block stationaryWater = create(9)
            .setAllTexture(15, 13)
            .setName("Stationary Water")
            .setMaterial(Block.MATERIAL_LIQUID)
            .setTransparent(true)
            .setHardness(-1.0f)
            .build();

    public static final Block lava = create(10)
            .setAllTexture(15, 15) // Lava texture
            .setName("Lava")
            .setMaterial(Block.MATERIAL_LIQUID)
            .setTransparent(true)
            .setLightLevel(15)
            .setHardness(-1.0f)
            .build();

    public static final Block stationaryLava = create(11)
            .setAllTexture(15, 15)
            .setName("Stationary Lava")
            .setMaterial(Block.MATERIAL_LIQUID)
            .setTransparent(true)
            .setLightLevel(15)
            .setHardness(-1.0f)
            .build();

    public static final Block sand = create(12)
            .setAllTexture(2, 1)
            .setName("Sand")
            .setMaterial(Block.MATERIAL_SAND)
            .setHardness(0.5f)
            .build();

    public static final Block gravel = create(13)
            .setAllTexture(3, 1)
            .setName("Gravel")
            .setMaterial(Block.MATERIAL_SAND)
            .setHardness(0.6f)
            .build();

    public static final Block goldOre = create(14)
            .setAllTexture(0, 2)
            .setName("Gold Ore")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block ironOre = create(15)
            .setAllTexture(1, 2)
            .setName("Iron Ore")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block coalOre = create(16)
            .setAllTexture(2, 2)
            .setName("Coal Ore")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block log = create(17)
            .setAllTexture(4, 1)
            .setTexture(Block.SIDE_TOP, 5, 1)
            .setTexture(Block.SIDE_BOTTOM, 5, 1)
            .setName("Oak Log")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.0f)
            .build();

    public static final Block leaves = create(18)
            .setAllTexture(5, 3)
            .setName("Oak Leaves")
            .setMaterial(Block.MATERIAL_LEAVES)
            .setTransparent(true)
            .setHardness(0.2f)
            .setAllColor(0x79C05A)
            .build();

    public static final Block sponge = create(19)
            .setAllTexture(0, 3)
            .setName("Sponge")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(0.6f)
            .build();

    public static final Block glass = create(20)
            .setAllTexture(1, 3)
            .setName("Glass")
            .setMaterial(Block.MATERIAL_GLASS)
            .setTransparent(true)
            .setHardness(0.3f)
            .build();

    public static final Block lapisOre = create(21)
            .setAllTexture(0, 10)
            .setName("Lapis Lazuli Ore")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block lapisBlock = create(22)
            .setAllTexture(0, 9)
            .setName("Lapis Lazuli Block")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block dispenser = create(23)
            .setAllTexture(14, 2)
            .setTexture(Block.SIDE_FRONT, 14, 3)
            .setTexture(Block.SIDE_TOP, 13, 2)
            .setTexture(Block.SIDE_BOTTOM, 13, 2)
            .setName("Dispenser")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.5f)
            .build();

    public static final Block sandstone = create(24)
            .setAllTexture(0, 12)
            .setTexture(Block.SIDE_TOP, 0, 11)
            .setTexture(Block.SIDE_BOTTOM, 0, 13)
            .setName("Sandstone")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(0.8f)
            .build();

    public static final Block noteBlock = create(25)
            .setAllTexture(10, 4)
            .setName("Note Block")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(0.8f)
            .build();

    // Bed will need special renderer
    public static final Block bed = create(26)
            .setAllTexture(5, 9)
            .setName("Bed")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(0.2f)
            .setOccludesNeighbourFace(false)
            .build();

    // Rails will need special renderer
    public static final Block poweredRail = create(27)
            .setAllTexture(3, 10)
            .setName("Powered Rail")
            .setMaterial(Block.MATERIAL_METAL)
            .setTransparent(true)
            .setHardness(0.7f)
            .build();

    public static final Block detectorRail = create(28)
            .setAllTexture(3, 12)
            .setName("Detector Rail")
            .setMaterial(Block.MATERIAL_METAL)
            .setTransparent(true)
            .setHardness(0.7f)
            .build();

    public static final Block stickyPiston = create(29)
            .setAllTexture(11, 6)
            .setTexture(Block.SIDE_TOP, 10, 6)
            .setTexture(Block.SIDE_BOTTOM, 13, 6)
            .setName("Sticky Piston")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(0.5f)
            .build();

    public static final Block cobweb = create(30)
            .setAllTexture(11, 0)
            .setName("Cobweb")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(4.0f)
            .build();

    public static final Block tallGrass = create(31)
            .setAllTexture(7, 2)
            .setName("Tall Grass")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .setAllColor(0x79C05A)
            .build();

    // Plant and Decoration Blocks (32-63)
    public static final Block deadBush = create(32)
            .setAllTexture(7, 3)
            .setName("Dead Bush")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block piston = create(33)
            .setAllTexture(11, 6)
            .setTexture(Block.SIDE_TOP, 11, 6)
            .setTexture(Block.SIDE_BOTTOM, 13, 6)
            .setName("Piston")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(0.5f)
            .build();

    // Piston head - technical block
    public static final Block pistonHead = create(34)
            .setAllTexture(11, 6)
            .setName("Piston Head")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(0.5f)
            .build();

    public static final Block wool = create(35)
            .setAllTexture(0, 4) // White wool
            .setName("White Wool")
            .setMaterial(Block.MATERIAL_WOOL)
            .setHardness(0.8f)
            .build();

    // Block 36 is piston extension (technical)
    
    public static final Block dandelion = create(37)
            .setAllTexture(13, 0)
            .setName("Dandelion")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block rose = create(38)
            .setAllTexture(12, 0)
            .setName("Rose")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block brownMushroom = create(39)
            .setAllTexture(13, 1)
            .setName("Brown Mushroom")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .setLightLevel(1)
            .build();

    public static final Block redMushroom = create(40)
            .setAllTexture(12, 1)
            .setName("Red Mushroom")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block goldBlock = create(41)
            .setAllTexture(7, 1)
            .setName("Block of Gold")
            .setMaterial(Block.MATERIAL_METAL)
            .setHardness(3.0f)
            .build();

    public static final Block ironBlock = create(42)
            .setAllTexture(6, 1)
            .setName("Block of Iron")
            .setMaterial(Block.MATERIAL_METAL)
            .setHardness(5.0f)
            .build();

    // Double stone slab
    public static final Block doubleStoneSlab = create(43)
            .setAllTexture(5, 0)
            .setSidesTexture(5, 0)
            .setTexture(Block.SIDE_TOP, 6, 0)
            .setTexture(Block.SIDE_BOTTOM, 6, 0)
            .setName("Double Stone Slab")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .build();

    // Stone slab - will need special renderer
    public static final Block stoneSlab = create(44)
            .setAllTexture(5, 0)
            .setSidesTexture(5, 0)
            .setTexture(Block.SIDE_TOP, 6, 0)
            .setTexture(Block.SIDE_BOTTOM, 6, 0)
            .setName("Stone Slab")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block bricks = create(45)
            .setAllTexture(7, 0)
            .setName("Bricks")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .build();

    public static final Block tnt = create(46)
            .setAllTexture(8, 0)
            .setTexture(Block.SIDE_TOP, 9, 0)
            .setTexture(Block.SIDE_BOTTOM, 10, 0)
            .setName("TNT")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(0.0f)
            .build();

    public static final Block bookshelf = create(47)
            .setAllTexture(3, 2)
            .setTexture(Block.SIDE_TOP, 4, 0)
            .setTexture(Block.SIDE_BOTTOM, 4, 0)
            .setName("Bookshelf")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(1.5f)
            .build();

    public static final Block mossyCobblestone = create(48)
            .setAllTexture(4, 2)
            .setName("Mossy Cobblestone")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .build();

    public static final Block obsidian = create(49)
            .setAllTexture(5, 2)
            .setName("Obsidian")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(50.0f)
            .build();

    public static final Block torch = create(50)
            .setAllTexture(0, 5)
            .setName("Torch")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setLightLevel(14)
            .setHardness(0.0f)
            .build();

    public static final Block fire = create(51)
            .setAllTexture(15, 1) // Fire texture (animated in real MC)
            .setName("Fire")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setLightLevel(15)
            .setHardness(0.0f)
            .build();

    public static final Block mobSpawner = create(52)
            .setAllTexture(1, 4)
            .setName("Monster Spawner")
            .setMaterial(Block.MATERIAL_STONE)
            .setTransparent(true)
            .setHardness(5.0f)
            .build();

    // Stairs will need special renderer
    public static final Block oakStairs = create(53)
            .setAllTexture(4, 0)
            .setName("Oak Wood Stairs")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block chest = create(54)
            .setAllTexture(11, 1) // Placeholder - needs special renderer
            .setName("Chest")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.5f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block redstoneWire = create(55)
            .setAllTexture(4, 10) // Redstone dust
            .setName("Redstone Wire")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block diamondOre = create(56)
            .setAllTexture(2, 3)
            .setName("Diamond Ore")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block diamondBlock = create(57)
            .setAllTexture(8, 1)
            .setName("Block of Diamond")
            .setMaterial(Block.MATERIAL_METAL)
            .setHardness(5.0f)
            .build();

    public static final Block craftingTable = create(58)
            .setAllTexture(11, 3)
            .setTexture(Block.SIDE_TOP, 11, 2)
            .setTexture(Block.SIDE_BOTTOM, 4, 0)
            .setName("Crafting Table")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.5f)
            .build();

    public static final Block wheat = create(59)
            .setAllTexture(15, 5) // Fully grown wheat
            .setName("Wheat Crops")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block farmland = create(60)
            .setAllTexture(2, 0) // Dry farmland (texture 6,5)
            .setTexture(Block.SIDE_TOP, 6, 5)
            .setName("Farmland")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(0.6f)
            .setOccludesNeighbourFace(false) // Slightly lower than full block
            .build();

    public static final Block furnace = create(61)
            .setAllTexture(13, 2)
            .setTexture(Block.SIDE_FRONT, 12, 2)
            .setTexture(Block.SIDE_TOP, 14, 3)
            .setTexture(Block.SIDE_BOTTOM, 14, 3)
            .setName("Furnace")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.5f)
            .build();

    public static final Block burningFurnace = create(62)
            .setAllTexture(13, 2)
            .setTexture(Block.SIDE_FRONT, 13, 3)
            .setTexture(Block.SIDE_TOP, 14, 3)
            .setTexture(Block.SIDE_BOTTOM, 14, 3)
            .setName("Burning Furnace")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.5f)
            .setLightLevel(13)
            .build();

    // Sign - will need special renderer
    public static final Block standingSign = create(63)
            .setAllTexture(4, 0)
            .setName("Sign")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(1.0f)
            .build();

    // Utility and Redstone Blocks (64-95)
    public static final Block oakDoor = create(64)
            .setAllTexture(1, 5) // Door texture - needs special renderer
            .setName("Oak Door")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(3.0f)
            .build();

    public static final Block ladder = create(65)
            .setAllTexture(3, 5)
            .setName("Ladder")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(0.4f)
            .build();

    public static final Block rail = create(66)
            .setAllTexture(0, 8) // Rail texture
            .setName("Rail")
            .setMaterial(Block.MATERIAL_METAL)
            .setTransparent(true)
            .setHardness(0.7f)
            .build();

    public static final Block cobblestoneStairs = create(67)
            .setAllTexture(0, 1)
            .setName("Cobblestone Stairs")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block wallSign = create(68)
            .setAllTexture(4, 0)
            .setName("Wall Sign")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(1.0f)
            .build();

    public static final Block lever = create(69)
            .setAllTexture(0, 6)
            .setName("Lever")
            .setMaterial(Block.MATERIAL_STONE)
            .setTransparent(true)
            .setHardness(0.5f)
            .build();

    public static final Block stonePressurePlate = create(70)
            .setAllTexture(1, 0)
            .setName("Stone Pressure Plate")
            .setMaterial(Block.MATERIAL_STONE)
            .setTransparent(true)
            .setHardness(0.5f)
            .build();

    public static final Block ironDoor = create(71)
            .setAllTexture(2, 5) // Iron door texture
            .setName("Iron Door")
            .setMaterial(Block.MATERIAL_METAL)
            .setTransparent(true)
            .setHardness(5.0f)
            .build();

    public static final Block woodenPressurePlate = create(72)
            .setAllTexture(4, 0)
            .setName("Wooden Pressure Plate")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(0.5f)
            .build();

    public static final Block redstoneOre = create(73)
            .setAllTexture(3, 3)
            .setName("Redstone Ore")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block glowingRedstoneOre = create(74)
            .setAllTexture(3, 3)
            .setName("Glowing Redstone Ore")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .setLightLevel(9)
            .build();

    public static final Block redstoneTorchOff = create(75)
            .setAllTexture(3, 7)
            .setName("Redstone Torch (off)")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block redstoneTorchOn = create(76)
            .setAllTexture(3, 6)
            .setName("Redstone Torch (on)")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setLightLevel(7)
            .setHardness(0.0f)
            .build();

    public static final Block stoneButton = create(77)
            .setAllTexture(1, 0)
            .setName("Stone Button")
            .setMaterial(Block.MATERIAL_STONE)
            .setTransparent(true)
            .setHardness(0.5f)
            .build();

    public static final Block snowLayer = create(78)
            .setAllTexture(2, 4)
            .setName("Snow")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setHardness(0.1f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block ice = create(79)
            .setAllTexture(3, 4)
            .setName("Ice")
            .setMaterial(Block.MATERIAL_GLASS)
            .setTransparent(true)
            .setHardness(0.5f)
            .build();

    public static final Block snowBlock = create(80)
            .setAllTexture(2, 4)
            .setName("Snow Block")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(0.2f)
            .build();

    public static final Block cactus = create(81)
            .setAllTexture(6, 4)
            .setTexture(Block.SIDE_TOP, 5, 4)
            .setTexture(Block.SIDE_BOTTOM, 7, 4)
            .setName("Cactus")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.4f)
            .build();

    public static final Block clayBlock = create(82)
            .setAllTexture(8, 4)
            .setName("Clay")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(0.6f)
            .build();

    public static final Block sugarCane = create(83)
            .setAllTexture(9, 4)
            .setName("Sugar Canes")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block jukebox = create(84)
            .setAllTexture(10, 4)
            .setTexture(Block.SIDE_TOP, 11, 4)
            .setName("Jukebox")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.0f)
            .build();

    public static final Block fence = create(85)
            .setAllTexture(4, 0)
            .setName("Oak Fence")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block pumpkin = create(86)
            .setAllTexture(6, 6)
            .setTexture(Block.SIDE_FRONT, 7, 7)
            .setTexture(Block.SIDE_TOP, 6, 6)
            .setTexture(Block.SIDE_BOTTOM, 6, 6)
            .setName("Pumpkin")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(1.0f)
            .build();

    public static final Block netherrack = create(87)
            .setAllTexture(7, 6)
            .setName("Netherrack")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(0.4f)
            .build();

    public static final Block soulSand = create(88)
            .setAllTexture(8, 6)
            .setName("Soul Sand")
            .setMaterial(Block.MATERIAL_SAND)
            .setHardness(0.5f)
            .build();

    public static final Block glowstone = create(89)
            .setAllTexture(9, 6)
            .setName("Glowstone")
            .setMaterial(Block.MATERIAL_GLASS)
            .setLightLevel(15)
            .setHardness(0.3f)
            .build();

    public static final Block portal = create(90)
            .setAllTexture(14, 0) // Portal texture
            .setName("Nether Portal")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setLightLevel(11)
            .setHardness(-1.0f)
            .build();

    public static final Block jackOLantern = create(91)
            .setAllTexture(6, 6)
            .setTexture(Block.SIDE_FRONT, 8, 7)
            .setTexture(Block.SIDE_TOP, 6, 6)
            .setTexture(Block.SIDE_BOTTOM, 6, 6)
            .setName("Jack o'Lantern")
            .setMaterial(Block.MATERIAL_SOLID)
            .setLightLevel(15)
            .setHardness(1.0f)
            .build();

    public static final Block cake = create(92)
            .setAllTexture(9, 7) // Cake texture
            .setTexture(Block.SIDE_TOP, 10, 7)
            .setTexture(Block.SIDE_BOTTOM, 11, 7)
            .setName("Cake")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setHardness(0.5f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block repeaterOff = create(93)
            .setAllTexture(3, 8)
            .setName("Redstone Repeater (off)")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block repeaterOn = create(94)
            .setAllTexture(3, 9)
            .setName("Redstone Repeater (on)")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setLightLevel(9)
            .setHardness(0.0f)
            .build();

    // Stained glass would use different colors
    public static final Block stainedGlass = create(95)
            .setAllTexture(1, 3) // White stained glass
            .setName("White Stained Glass")
            .setMaterial(Block.MATERIAL_GLASS)
            .setTransparent(true)
            .setHardness(0.3f)
            .build();

    // Special and Nether Blocks (96-127)
    public static final Block trapdoor = create(96)
            .setAllTexture(4, 5)
            .setName("Oak Trapdoor")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(3.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block monsterEgg = create(97)
            .setAllTexture(1, 0) // Stone texture - silverfish block
            .setName("Monster Egg")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(0.75f)
            .build();

    public static final Block stoneBricks = create(98)
            .setAllTexture(6, 3)
            .setName("Stone Bricks")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(1.5f)
            .build();

    public static final Block brownMushroomBlock = create(99)
            .setAllTexture(14, 7) // Mushroom block texture
            .setName("Brown Mushroom Block")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(0.2f)
            .build();

    public static final Block redMushroomBlock = create(100)
            .setAllTexture(13, 7) // Red mushroom block texture
            .setName("Red Mushroom Block")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(0.2f)
            .build();

    public static final Block ironBars = create(101)
            .setAllTexture(5, 5)
            .setName("Iron Bars")
            .setMaterial(Block.MATERIAL_METAL)
            .setTransparent(true)
            .setHardness(5.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block glassPane = create(102)
            .setAllTexture(1, 3)
            .setName("Glass Pane")
            .setMaterial(Block.MATERIAL_GLASS)
            .setTransparent(true)
            .setHardness(0.3f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block melon = create(103)
            .setAllTexture(8, 8)
            .setTexture(Block.SIDE_TOP, 9, 8)
            .setName("Melon")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(1.0f)
            .build();

    public static final Block pumpkinStem = create(104)
            .setAllTexture(15, 6) // Stem texture
            .setName("Pumpkin Stem")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block melonStem = create(105)
            .setAllTexture(15, 6) // Stem texture (same as pumpkin)
            .setName("Melon Stem")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block vines = create(106)
            .setAllTexture(15, 8)
            .setName("Vines")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.2f)
            .setAllColor(0x79C05A)
            .build();

    public static final Block fenceGate = create(107)
            .setAllTexture(4, 0)
            .setName("Oak Fence Gate")
            .setMaterial(Block.MATERIAL_WOOD)
            .setTransparent(true)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block brickStairs = create(108)
            .setAllTexture(7, 0)
            .setName("Brick Stairs")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block stoneBrickStairs = create(109)
            .setAllTexture(6, 3)
            .setName("Stone Brick Stairs")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(1.5f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block mycelium = create(110)
            .setAllTexture(2, 0)
            .setSidesTexture(13, 4)
            .setTexture(Block.SIDE_TOP, 14, 4)
            .setName("Mycelium")
            .setMaterial(Block.MATERIAL_SOLID)
            .setHardness(0.6f)
            .build();

    public static final Block lilyPad = create(111)
            .setAllTexture(12, 4)
            .setName("Lily Pad")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block netherBrick = create(112)
            .setAllTexture(0, 14)
            .setName("Nether Brick")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .build();

    public static final Block netherBrickFence = create(113)
            .setAllTexture(0, 14)
            .setName("Nether Brick Fence")
            .setMaterial(Block.MATERIAL_STONE)
            .setTransparent(true)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block netherBrickStairs = create(114)
            .setAllTexture(0, 14)
            .setName("Nether Brick Stairs")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block netherWart = create(115)
            .setAllTexture(4, 14) // Nether wart texture
            .setName("Nether Wart")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.0f)
            .build();

    public static final Block enchantmentTable = create(116)
            .setAllTexture(6, 10)
            .setTexture(Block.SIDE_TOP, 6, 11)
            .setTexture(Block.SIDE_BOTTOM, 7, 11)
            .setName("Enchantment Table")
            .setMaterial(Block.MATERIAL_STONE)
            .setTransparent(true)
            .setHardness(5.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block brewingStand = create(117)
            .setAllTexture(13, 9) // Brewing stand texture
            .setName("Brewing Stand")
            .setMaterial(Block.MATERIAL_METAL)
            .setTransparent(true)
            .setHardness(0.5f)
            .setLightLevel(1)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block cauldron = create(118)
            .setAllTexture(10, 8)
            .setTexture(Block.SIDE_TOP, 10, 9)
            .setTexture(Block.SIDE_BOTTOM, 11, 8)
            .setName("Cauldron")
            .setMaterial(Block.MATERIAL_METAL)
            .setTransparent(true)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block endPortal = create(119)
            .setAllTexture(14, 0) // Portal texture
            .setName("End Portal")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setLightLevel(15)
            .setHardness(-1.0f)
            .build();

    public static final Block endPortalFrame = create(120)
            .setAllTexture(15, 9)
            .setTexture(Block.SIDE_TOP, 15, 10)
            .setName("End Portal Frame")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(-1.0f)
            .build();

    public static final Block endStone = create(121)
            .setAllTexture(15, 10)
            .setName("End Stone")
            .setMaterial(Block.MATERIAL_STONE)
            .setHardness(3.0f)
            .build();

    public static final Block dragonEgg = create(122)
            .setAllTexture(7, 10)
            .setName("Dragon Egg")
            .setMaterial(Block.MATERIAL_SOLID)
            .setTransparent(true)
            .setLightLevel(1)
            .setHardness(3.0f)
            .build();

    public static final Block redstoneLampOff = create(123)
            .setAllTexture(3, 13)
            .setName("Redstone Lamp")
            .setMaterial(Block.MATERIAL_GLASS)
            .setHardness(0.3f)
            .build();

    public static final Block redstoneLampOn = create(124)
            .setAllTexture(4, 13)
            .setName("Redstone Lamp (Active)")
            .setMaterial(Block.MATERIAL_GLASS)
            .setLightLevel(15)
            .setHardness(0.3f)
            .build();

    public static final Block doubleWoodSlab = create(125)
            .setAllTexture(4, 0)
            .setName("Double Wood Slab")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.0f)
            .build();

    public static final Block woodSlab = create(126)
            .setAllTexture(4, 0)
            .setName("Wood Slab")
            .setMaterial(Block.MATERIAL_WOOD)
            .setHardness(2.0f)
            .setOccludesNeighbourFace(false)
            .build();

    public static final Block cocoa = create(127)
            .setAllTexture(8, 10) // Cocoa pod texture
            .setName("Cocoa")
            .setMaterial(Block.MATERIAL_PLANT)
            .setTransparent(true)
            .setHardness(0.2f)
            .build();

    private static BlockBuilder create(int id) {
        return BlockBuilder.create(id);
    }

    public static void register(Block block) {
        if (blocks[block.id] != null) {
            throw new IllegalStateException("block " + block.id + " already exists");
        }
        blocks[block.id] = block;
    }
    
    /**
     * Initialize block behaviors
     */
    public static void initializeBehaviors() {
        // Register gravity behavior for sand and gravel
        GravityBlockBehavior gravityBehavior = new GravityBlockBehavior();
        BehaviorRegistry.register(sand.id, gravityBehavior);
        BehaviorRegistry.register(gravel.id, gravityBehavior);
    }
}
