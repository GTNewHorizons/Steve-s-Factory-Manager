package vswe.stevesfactory.blocks;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import vswe.stevesfactory.StevesFactoryManager;

public class BlockCableBUD extends BlockContainer {
    public BlockCableBUD() {
        super(Material.iron);
        setCreativeTab(ModBlocks.creativeTab);
        setStepSound(soundTypeMetal);
        setBlockName(StevesFactoryManager.UNLOCALIZED_START + ModBlocks.CABLE_BUD_UNLOCALIZED_NAME);
        setHardness(1.2F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBUD();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        blockIcon = register.registerIcon(StevesFactoryManager.RESOURCE_LOCATION + ":cable_bud");
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntityBUD bud = TileEntityCluster.getTileEntity(TileEntityBUD.class, world, x, y, z);
        if (bud != null) {
            bud.onTrigger();
        }
    }


}
