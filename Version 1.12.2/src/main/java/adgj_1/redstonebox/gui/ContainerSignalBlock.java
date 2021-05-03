package adgj_1.redstonebox.gui;

import adgj_1.redstonebox.items.tileentity.TileEntitySignalBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerSignalBlock extends Container {

	public final TileEntitySignalBlock tileentity;
	
	public ContainerSignalBlock(InventoryPlayer player, TileEntitySignalBlock te) {
		super();
		tileentity = te;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return this.tileentity.isUsableByPlayer(playerIn);
	}
	

}
