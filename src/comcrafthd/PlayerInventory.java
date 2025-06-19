package comcrafthd;

public class PlayerInventory {
    
    private static final byte[] AVAILABLE_BLOCKS = {
        1,  // stone
        2,  // grass
        3,  // dirt
        4,  // cobblestone
        5,  // planks
        12, // sand
        13, // gravel
        17, // log
        20, // glass
        45, // bricks
        89  // glowstone
    };
    
    private int selectedIndex = 0;
    
    public byte getSelectedBlockId() {
        return AVAILABLE_BLOCKS[selectedIndex];
    }
    
    public void nextBlock() {
        selectedIndex = (selectedIndex + 1) % AVAILABLE_BLOCKS.length;
    }
    
    public void previousBlock() {
        selectedIndex--;
        if (selectedIndex < 0) {
            selectedIndex = AVAILABLE_BLOCKS.length - 1;
        }
    }
    
    public String getSelectedBlockName() {
        byte id = getSelectedBlockId();
        Block block = BlockDefinitions.blocks[id];
        if (block != null && block.name != null) {
            return block.name;
        }
        return "Block " + id;
    }
}