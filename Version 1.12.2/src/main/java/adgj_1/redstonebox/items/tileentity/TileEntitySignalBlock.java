package adgj_1.redstonebox.items.tileentity;

import adgj_1.redstonebox.Main;
import adgj_1.redstonebox.init.DimensionProperties;
import adgj_1.redstonebox.init.DynamicDimensionHelper;
import adgj_1.redstonebox.items.blocks.SignalBlock;
import adgj_1.redstonebox.world.WorldProviderEmpty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntitySignalBlock extends TileEntity implements ITickable{

	private int selectedKey;
	
	public boolean isOn;
	
	public TileEntitySignalBlock() {
		
		//selectedKey = Keyboard.KEY_G;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (!world.isRemote) {
			if (world.provider instanceof WorldProviderEmpty) {
				DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(world.provider.getDimension());
				IBlockState block = world.getBlockState(pos);
				if (properties.getActiveKey() == selectedKey) {
					// Sends redstone signal
					//Main.logger.info("Signal Triggered at " + pos.getX() + "," + pos.getY() + "," + pos.getZ());
					if (!isOn) {
						isOn = true;
						SignalBlock.setState(isOn, getWorld(), pos);
						syncClient();
					}
				} else {
					// stops sending signal
					if (isOn) {
						isOn = false;
						SignalBlock.setState(isOn, getWorld(), pos);
						syncClient();
					}
				}
			}
		}
	}

	public boolean isUsableByPlayer(EntityPlayer playerIn) {
		return this.world.getTileEntity(this.pos) != this ? false : playerIn.getDistanceSq((double)this.pos.getX()+0.5D,(double)this.pos.getY()+0.5D,(double)this.pos.getZ()+0.5D) <= 64;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		
		//System.out.println("saving " + selectedKey + " to data");
		nbt.setInteger("keyCode", selectedKey);
		super.writeToNBT(nbt);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
		selectedKey = nbt.getInteger("keyCode");
		//System.out.println("reading " + selectedKey + " from data");
		super.readFromNBT(nbt);
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
	    NBTTagCompound nbtTag = new NBTTagCompound();
	    nbtTag.setInteger("keyCode", selectedKey);
	    return new SPacketUpdateTileEntity(getPos(), getBlockMetadata(), nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
	    NBTTagCompound tag = pkt.getNbtCompound();
	    selectedKey = tag.getInteger("keyCode");
	    //System.out.println("read key from packet " + selectedKey);
	}
	
	
	public int getSelectedKey() {
		return selectedKey;
	}

	public void setKey(int id) {
		selectedKey = id;
		//System.out.println("key set to " + selectedKey + ", isClient: " + world.isRemote);
		markDirty();
		syncClient();
	}

	public void syncClient() {
		//System.out.println("client sync");
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
	}
}
