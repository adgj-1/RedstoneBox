package adgj_1.redstonebox.gui;

import adgj_1.redstonebox.items.tileentity.TileEntityBoxWorkbench;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBoxWorkbench extends Container
{
    private final TileEntityBoxWorkbench tileentity;
    
    public ContainerBoxWorkbench(InventoryPlayer player, TileEntityBoxWorkbench tileentity) {
    	this.tileentity = tileentity;
    	tileentity.setUser(player.player);
    	tileentity.setContainer(this);
    	this.addSlotToContainer(new SlotBox(tileentity, 0, 56, 17));
    	this.addSlotToContainer(new SlotBox(tileentity, 1, 84, 35));
    	
    	for (int y = 0; y < 3; y++) {
    		for (int x = 0; x < 9; x++) {
    			this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x * 18, 84 + y * 18));
    		}
    	}
    	
    	for (int x = 0; x < 9; x++) {
    		this.addSlotToContainer(new Slot(player, x, 8+x*18, 142));
    	}
    }
    
    @Override
    public void addListener(IContainerListener listener) {
    	super.addListener(listener);
    	listener.sendAllWindowProperties(this, this.tileentity);
    }
    
    @Override
    public void detectAndSendChanges() {
    	super.detectAndSendChanges();
    	
//    	for (int i = 0; i < this.listeners.size(); ++i) {
//    		IContainerListener listener = (IContainerListener)this.listeners.get(i);
    		//listener.sendAllWindowProperties(this, this.tileentity);
//    		if (this.tileentity.getStackInSlot(1) != this.getSlot(1).getStack()) {
//    			System.out.println("detect and send change");
//    			inventorySlots.get(1).putStack(this.tileentity.getStackInSlot(1));
//    			listener.sendAllWindowProperties(this, this.tileentity);
//    		}
//    	}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
    	super.updateProgressBar(id, data);
    	this.tileentity.setField(id, data);
    }
    
    public boolean canInteractWith(EntityPlayer playerIn) {
    	return this.tileentity.isUsableByPlayer(playerIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    	//TODO needs further investigation
    	ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)this.inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 1) 
			{
				if(!this.mergeItemStack(stack1, 2, 37, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			} 
			else if(!this.mergeItemStack(stack1, 2, 37, false)) 
			{
				return ItemStack.EMPTY;
			}
			if(stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();

			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
    }
    @Override
    public void onCraftMatrixChanged(IInventory parInventory)
    {
//    	if (!getSlot(0).getStack().isEmpty() && !getSlot(1).getStack().isEmpty()) {
//    		getSlot(1).putStack(ItemStack.EMPTY);
//    	}
    }
}
