package adgj_1.redstonebox.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCenter extends Slot {

	public SlotCenter(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		// TODO Auto-generated constructor stub
	}

	public boolean isItemValid(ItemStack stack) {
		return true;
	}
	
	public int getItemStackLimit(ItemStack stack) {
		return super.getItemStackLimit(stack);
	}
}
