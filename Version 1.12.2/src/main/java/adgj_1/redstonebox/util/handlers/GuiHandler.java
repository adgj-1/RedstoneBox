package adgj_1.redstonebox.util.handlers;

import adgj_1.redstonebox.gui.ContainerBoxWorkbench;
import adgj_1.redstonebox.gui.ContainerSignalBlock;
import adgj_1.redstonebox.gui.GuiBoxWorkbench;
import adgj_1.redstonebox.gui.GuiSignalBlock;
import adgj_1.redstonebox.items.tileentity.TileEntityBoxWorkbench;
import adgj_1.redstonebox.items.tileentity.TileEntitySignalBlock;
import adgj_1.redstonebox.util.Reference;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GUI_WORKBENCH) return new ContainerBoxWorkbench(player.inventory, (TileEntityBoxWorkbench)world.getTileEntity(new BlockPos(x,y,z)));
		if (ID == Reference.GUI_SIGNAL) return new ContainerSignalBlock(player.inventory, (TileEntitySignalBlock)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == Reference.GUI_WORKBENCH) return new GuiBoxWorkbench(player.inventory, (TileEntityBoxWorkbench)world.getTileEntity(new BlockPos(x,y,z)));
		
		if (ID == Reference.GUI_SIGNAL) return new GuiSignalBlock(player.inventory, (TileEntitySignalBlock)world.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

}
