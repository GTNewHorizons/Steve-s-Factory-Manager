package vswe.stevesfactory.compat;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import vswe.stevesfactory.blocks.ConnectionBlock;
import vswe.stevesfactory.blocks.TileEntityCluster;
import vswe.stevesfactory.blocks.TileEntityManager;
import vswe.stevesfactory.components.ComponentMenuItem;
import vswe.stevesfactory.components.ConnectionOption;
import vswe.stevesfactory.components.ConnectionSet;
import vswe.stevesfactory.components.FlowComponent;

public interface Hooks {

    String fixToolTip(String str, TileEntity tileEntity);

    TileEntityCluster getTERFC();

    boolean instanceOf(Class clazz, TileEntity tileEntity);

    void addCopyButton(TileEntityManager tileEntityManager);

    void tickTriggers(TileEntityManager tileEntityManager);

    boolean containerAdvancedSearch(ConnectionBlock block, String search);

    List updateItemSearch(ComponentMenuItem componentMenuItem, String search, boolean showAll);

    ItemStack fixLoadingStack(ItemStack item);

    void executeTriggerCommand(TileEntityManager thiz, FlowComponent component,
            EnumSet<ConnectionOption> validTriggerOutputs);

    GuiScreen getGui(TileEntity te, InventoryPlayer inv);

    boolean isNotDelayed(ConnectionSet s);
}
