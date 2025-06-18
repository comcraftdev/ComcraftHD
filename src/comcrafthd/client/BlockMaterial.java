package comcrafthd.client;

import javax.microedition.m3g.*;

public class BlockMaterial {

    public final byte id;

    public final Appearance appearance;

    public BlockMaterial(byte id, Appearance appearance) {
        this.id = id;
        this.appearance = appearance;
    }

}
