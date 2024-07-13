package vswe.stevesfactory.blocks;

import java.util.EnumSet;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityClusterElement extends TileEntity {

    private ClusterRegistry registryElement;
    private boolean isPartOfCluster;
    public int meta;

    protected TileEntityClusterElement() {
        registryElement = ClusterRegistry.get(this);
    }

    public ItemStack getItemStackFromBlock() {
        return registryElement.getItemStack(getBlockMetadata());
    }

    public boolean isPartOfCluster() {
        return isPartOfCluster;
    }

    public void setPartOfCluster(boolean partOfCluster) {
        isPartOfCluster = partOfCluster;
    }

    @Override
    public int getBlockMetadata() {
        if (isPartOfCluster) {
            return meta;
        } else {
            return super.getBlockMetadata();
        }
    }

    public void setMetaData(int meta) {
        if (isPartOfCluster) {
            this.meta = meta;
        } else {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta, 2);
        }
    }

    @Override
    public final void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("MetaData", this.meta);
        writeContentToNBT(tagCompound);
    }

    @Override
    public final void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        this.meta = tagCompound.getInteger("MetaData");
        readContentFromNBT(tagCompound);
    }

    protected void readContentFromNBT(NBTTagCompound tagCompound) {}

    protected void writeContentToNBT(NBTTagCompound tagCompound) {}

    protected abstract EnumSet<ClusterMethodRegistration> getRegistrations();
}
