package adgj_1.redstonebox.items.tileentity;

import adgj_1.redstonebox.gui.ContainerBoxWorkbench;
import adgj_1.redstonebox.gui.RecipeBoxWorkbench;
import adgj_1.redstonebox.init.DimensionProperties;
import adgj_1.redstonebox.init.DynamicDimensionHelper;
import adgj_1.redstonebox.networking.PacketTeleporter;
import adgj_1.redstonebox.networking.RBPacketHandler;
import adgj_1.redstonebox.util.Reference;
import adgj_1.redstonebox.world.WorldProviderEmpty;

import adgj_1.redstonebox.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityBoxWorkbench extends TileEntity implements IInventory, ITickable {
	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	private String customName;

	private int flag = 0;
	
	private EntityPlayer user;
	private ContainerBoxWorkbench container;
@Override
public String getName() {

	return this.hasCustomName() ? this.customName : "container.workbench_box";
}

@Override
public boolean hasCustomName() {
	return this.customName != null && !this.customName.isEmpty();
}

@Override
public ITextComponent getDisplayName() {
	return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
}

public String getGuiID() {
	return Reference.MODID + ":workbench_box";
}

@Override
public void update() {
	if (flag == 1) {
		flag = 0;
		this.markDirty();
	}
}

@Override
public void clear() {
	// TODO Auto-generated method stub
	
}

@Override
public void closeInventory(EntityPlayer arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public ItemStack decrStackSize(int arg0, int arg1) {
	return ItemStackHelper.getAndSplit(this.inventory, arg0, arg1);
}

@Override
public int getField(int arg0) {
		return 0;
}

@Override
public int getFieldCount() {
	return 0;
}

@Override
public int getInventoryStackLimit() {
	return 1;
}

@Override
public int getSizeInventory() {
	return this.inventory.size();
}

@Override
public ItemStack getStackInSlot(int arg0) {
	return (ItemStack)this.inventory.get(arg0);
}

@Override
public boolean isEmpty() {
	for (ItemStack stack : this.inventory) {
		if (!stack.isEmpty()) {
			return false;
		}
	}
	return true;
}

@Override
public boolean isItemValidForSlot(int arg0, ItemStack arg1) {
	return false;
}

@Override
public boolean isUsableByPlayer(EntityPlayer arg0) {
	return this.world.getTileEntity(this.pos) != this ? false : arg0.getDistanceSq((double)this.pos.getX()+0.5D,(double)this.pos.getY()+0.5D,(double)this.pos.getZ()+0.5D) <= 64;
}

@Override
public void openInventory(EntityPlayer arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public ItemStack removeStackFromSlot(int arg0) {
	// TODO Auto-generated method stub
	return ItemStackHelper.getAndRemove(this.inventory, arg0);
}

@Override
public void setField(int arg0, int arg1) {
	// TODO Auto-generated method stub
}

@Override
public void setInventorySlotContents(int arg0, ItemStack arg1) {
	ItemStack itemstack = (ItemStack)this.inventory.get(arg0);
	boolean flag = arg1.isEmpty() && arg1.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(itemstack, arg1);
	this.inventory.set(arg0, arg1);
	if (arg1.getCount() > this.getInventoryStackLimit()) arg1.setCount(this.getInventoryStackLimit());
	if (arg0 == 0 && arg0 + 1 == 1 && !flag) {
		this.markDirty();
	}
}

@Override
public NBTTagCompound writeToNBT(NBTTagCompound compound) {
	super.writeToNBT(compound);
	ItemStackHelper.saveAllItems(compound, this.inventory);
	
	if (this.hasCustomName()) compound.setString("CustomName", this.customName);
	return compound;
}

@Override
public void readFromNBT(NBTTagCompound compound) {
	super.readFromNBT(compound);
	ItemStackHelper.loadAllItems(compound, this.inventory);
}

public void buttonCommand(int id) {
	switch (id) {
	
	// Craft Button
	case 1: {
		ItemStack[] res = RecipeBoxWorkbench.INSTANCE.getResult(getStackInSlot(0), getStackInSlot(1), this.getWorld());
		if (res != null) {
			this.inventory.set(0, res[0]);
			this.inventory.set(1, res[1]);
			if (!world.isRemote) {
				flag = 1;
			}
		}
		
		break;
	}
	
	case 2: {
		//TODO make abstraction for world teleport
		if (world.isRemote) {
			if (!(world.provider instanceof WorldProviderEmpty)) {
			if (getStackInSlot(1).hasTagCompound() && getStackInSlot(1).getTagCompound().hasKey("boxid")) {
				int boxid = getStackInSlot(1).getTagCompound().getInteger("boxid");
				Main.logger.info("Teleporting to world #" + boxid);
				DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(boxid);
				
				if (properties != null) {
					NBTTagCompound nbt = new NBTTagCompound();
					properties.writeToNBT(nbt);
					Main.logger.info("Target Dimension" + boxid + " Properties: " + nbt); // Print out properties
				} else {
					properties = new DimensionProperties(boxid);
					Main.logger.info("Something is wrong, dimension property is null");
				}
				properties.setExitDim(world.provider.getDimension());
				properties.setExitPos(new BlockPos(user.posX,user.posY,user.posZ));
				
				RBPacketHandler.INSTANCE.sendToServer(new PacketTeleporter(7,1,7,boxid,user.getEntityId()));
			}
			} else {
				Main.logger.info("Teleporting from " + world.provider.getDimension() + " back to exit dimension");
				DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(world.provider.getDimension());
				
				if (properties == null) {
					
					RBPacketHandler.INSTANCE.sendToServer(new PacketTeleporter(0,0,0,0,user.getEntityId()));
					Main.logger.info("Something is wrong, dimension property is null");
				} else {
					BlockPos exitPos = properties.getExitPos();
					RBPacketHandler.INSTANCE.sendToServer(new PacketTeleporter(exitPos.getX(),exitPos.getY(),exitPos.getZ(),properties.getExitDim(),user.getEntityId()));
				}

			}
		} else {
			this.getContainer().transferStackInSlot(user, 1);
		}
		break;
	}
	
	default: {
		Main.logger.info("Unknown Command: " + id);
		break;
	}
	}
	
}

public void setUser(EntityPlayer player) {
	user = player;
}

public void setContainer(ContainerBoxWorkbench containerBoxWorkbench) {
	this.container = containerBoxWorkbench;
	
}

public ContainerBoxWorkbench getContainer() {
	return container;
}
}
