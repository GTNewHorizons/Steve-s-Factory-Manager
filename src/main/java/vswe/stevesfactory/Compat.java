package vswe.stevesfactory;

import vswe.stevesfactory.blocks.TileEntityCluster;
import vswe.stevesfactory.blocks.TileEntityRFCluster;

public class Compat {

    public static TileEntityCluster getTERFC() {
        return new TileEntityRFCluster();
    }
}
