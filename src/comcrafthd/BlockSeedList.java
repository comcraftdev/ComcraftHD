/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comcrafthd;

import comcrafthd.blocks.StandardBlockBuilder;

/**
 *
 * @author quead
 */
public final class BlockSeedList {

    public final Block stone = StandardBlockBuilder.create(1, 0)
            .setAllTexture(1, 0)
            .build();

    public final Block grass = StandardBlockBuilder.create(2, 0)
            .setAllTexture(2, 0)
            .setSidesTexture(3, 0)
            .setTexture(Block.SIDE_TOP, 0, 0)
//            .setColor(Block.SIDE_TOP, 0x79C05A)
//            .setColor(Block.SIDE_TOP, 0x90814D)
            .setAllColor(0x90814D)
            .build();
}
