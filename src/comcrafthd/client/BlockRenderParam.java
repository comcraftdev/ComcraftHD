package comcrafthd.client;

import comcrafthd.*;

/**
 * Reusable parameter object allocated once per chunk to avoid excessive
 * method parameters and stack usage on J2ME platforms.
 */
public final class BlockRenderParam {

    public int blockX;
    public int blockY;
    public int blockZ;
    public int localBlockX;
    public int localBlockY;
    public int localBlockZ;
    public int id;
    public int meta;
    public Block block;

}
