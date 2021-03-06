/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import comcrafthd.client.IBlockRenderer;
import comcrafthd.client.blocks.StandardBlockRenderer;
import java.util.Vector;

/**
 *
 * @author quead
 */
public final class BlockCreator {

    public final Vector[] blocks = new Vector[Block.BLOCK_MAX_ID];

    private void add(Block block) {
        byte id = block.getId();
        if (blocks[id] == null) {
            blocks[id] = new Vector();
        }
        blocks[id].addElement(block);
    }

    public void registerVariants(BlockList blockList) {
        for (int n = blocks.length - 1; n >= 0; --n) {
            Vector list = blocks[n];
            if (list == null) {
                continue;
            }

            byte id = ((Block) list.elementAt(0)).getId();
            int count = list.size();

            BlockVariants variants = new BlockVariants(id, (byte) count);

            for (int k = count - 1; k >= 0; --k) {
                variants.set((byte) k, (Block) list.elementAt(k));
            }

            blockList.registerVariant(variants);

            list.removeAllElements();
            blocks[n] = null;
        }
    }

    public Block createStandard(int id, int metaId, int texX, int texY) {
        throw new UnsupportedOperationException();
        
//        IBlockRenderer standardRenderer = new StandardBlockRenderer(ComcraftGame.instance.blockMaterials.standardMat, (byte) texX, (byte) texY);
//
//        Block block = new Block((byte) id, (byte) metaId, standardRenderer);
//
//        add(block);
//        return block;
    }

}
