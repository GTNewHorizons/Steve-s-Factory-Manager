package vswe.stevesfactory.waila;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import vswe.stevesfactory.Localization;
import vswe.stevesfactory.blocks.BlockCableCamouflages;
import vswe.stevesfactory.blocks.BlockCableCluster;
import vswe.stevesfactory.blocks.BlockCableOutput;
import vswe.stevesfactory.blocks.ClusterRegistry;
import vswe.stevesfactory.blocks.TileEntityCamouflage;
import vswe.stevesfactory.blocks.TileEntityCluster;
import vswe.stevesfactory.blocks.TileEntityOutput;

public class Provider implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te != null && !isShiftDown()) {
            TileEntityCamouflage camouflage = TileEntityCluster
                    .getTileEntity(TileEntityCamouflage.class, te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord);
            if (camouflage != null) {
                int id = camouflage.getId(accessor.getPosition().sideHit);
                int meta = camouflage.getMeta(accessor.getPosition().sideHit);

                if (id != 0) {
                    Block block = Block.getBlockById(id);
                    if (block != null) {
                        return new ItemStack(block, 1, block.damageDropped(meta));
                    }
                }
            }
        }

        return new ItemStack(accessor.getBlock(), 1, accessor.getBlock().damageDropped(accessor.getMetadata()));
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        if (itemStack != null && itemStack.getItem() == accessor.getStack().getItem()) {
            TileEntity te = accessor.getTileEntity();
            if (te != null) {
                if (te instanceof TileEntityCluster) {
                    TileEntityCluster cluster = (TileEntityCluster) te;

                    for (byte type : cluster.getTypes()) {
                        currenttip.add(ClusterRegistry.getRegistryList().get(type).getItemStack().getDisplayName());
                    }
                } else if (te instanceof TileEntityOutput) {
                    TileEntityOutput emitter = (TileEntityOutput) te;

                    if (isShiftDown()) {
                        for (int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
                            currenttip.add(getEmitterSide(emitter, i, true));
                        }
                    } else {
                        currenttip.add(getEmitterSide(emitter, accessor.getPosition().sideHit, false));
                    }
                }
            }
        }
        return currenttip;
    }

    @SideOnly(Side.CLIENT)
    private boolean isShiftDown() {
        return GuiScreen.isShiftKeyDown();
    }

    private String getEmitterSide(TileEntityOutput emitter, int side, boolean full) {
        String str = (emitter.hasStrongSignalAtSide(side) ? Localization.STRONG_POWER.toString()
                : Localization.WEAK_POWER.toString()) + ": " + emitter.getStrengthFromSide(side) + " ";

        if (full) {
            str = Localization.getForgeDirectionLocalization(side) + " " + str;
        }

        return str;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, TileEntity tileEntity,
            NBTTagCompound nbtTagCompound, World world, int i, int i1, int i2) {
        if (tileEntity != null) tileEntity.writeToNBT(nbtTagCompound);
        return nbtTagCompound;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {
        Provider instance = new Provider();
        registrar.registerBodyProvider(instance, BlockCableCluster.class);
        registrar.registerBodyProvider(instance, BlockCableOutput.class);

        registrar.registerStackProvider(instance, BlockCableCluster.class);
        registrar.registerStackProvider(instance, BlockCableCamouflages.class);
    }

}
