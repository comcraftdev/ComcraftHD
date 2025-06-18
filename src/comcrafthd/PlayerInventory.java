package comcrafthd;

public class PlayerInventory {
    
    private static final byte[] AVAILABLE_BLOCKS = {
        1, // stone
        2  // grass
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
        switch (id) {
            case 1: return "Stone";
            case 2: return "Grass";
            default: return "Block " + id;
        }
    }
}