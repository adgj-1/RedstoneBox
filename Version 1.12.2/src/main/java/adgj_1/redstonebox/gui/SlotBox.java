package adgj_1.redstonebox.gui;

import adgj_1.redstonebox.init.IInfusingMaterial;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBox extends Slot {

	public SlotBox(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		// TODO Auto-generated constructor stub
	}

	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof IInfusingMaterial;
	}
	
	public int getItemStackLimit(ItemStack stack) {
		return super.getItemStackLimit(stack);
	}
}
