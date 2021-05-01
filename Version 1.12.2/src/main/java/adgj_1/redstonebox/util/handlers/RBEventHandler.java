package adgj_1.redstonebox.util.handlers;

import adgj_1.redstonebox.init.DimensionProperties;
import adgj_1.redstonebox.init.DynamicDimensionHelper;
import adgj_1.redstonebox.networking.PacketDimensionProperties;
import adgj_1.redstonebox.networking.PacketTeleporter;
import adgj_1.redstonebox.networking.RBPacketHandler;
import adgj_1.redstonebox.world.WorldProviderEmpty;

import adgj_1.redstonebox.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class RBEventHandler {

	@SubscribeEvent
	public void worldSaveEvent(WorldEvent.Save event) {
		Main.logger.info("Saving dimension data");
		try {
			DynamicDimensionHelper.getInstance().saveDimensions(DynamicDimensionHelper.workingPath);
		} catch (Exception e) {
			Main.logger.fatal("An error has occured saving dimension data, this can happen if another mod causes the game to crash during game load.  If the game has fully loaded, then this is a serious error, Advanced Rocketry data has not been saved.");
			e.printStackTrace();
		}
			
	}
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent e) {
		// Test code to see if key is working
		/*	Main.logger.info(Keyboard.getEventCharacter());
			Main.logger.info("Player holding " + Minecraft.getMinecraft().player.getHeldItemMainhand().getDisplayName()); */
		
		if (Main.keyBindings[0].isPressed()) {
			EntityPlayerSP player = Minecraft.getMinecraft().player;
			ItemStack heldItem = player.getHeldItemMainhand();
			if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("boxid")) {
				if (!(player.world.provider instanceof WorldProviderEmpty)) {
					int boxid = heldItem.getTagCompound().getInteger("boxid");
					Main.logger.info("Teleporting to world #" + boxid);
					DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(boxid);
					
					if (properties != null) {
						NBTTagCompound nbt = new NBTTagCompound();
						properties.writeToNBT(nbt);
						Main.logger.info("Target Dimension" + boxid + " Properties: " + nbt); // Print out properties
						RBPacketHandler.INSTANCE.sendToServer(new PacketDimensionProperties(boxid, 1, player.world.provider.getDimension()));
						RBPacketHandler.INSTANCE.sendToServer(new PacketDimensionProperties(boxid, 2, (int) player.posX));
						RBPacketHandler.INSTANCE.sendToServer(new PacketDimensionProperties(boxid, 3, (int) player.posY));
						RBPacketHandler.INSTANCE.sendToServer(new PacketDimensionProperties(boxid, 4, (int) player.posZ));
						
					} else {
						Main.logger.info("Something is wrong, dimension property is null");
					}
					
					
					RBPacketHandler.INSTANCE.sendToServer(new PacketTeleporter(7,1,7,boxid,player.getEntityId()));
				} else {
					Main.logger.info("Teleporting from " + player.world.provider.getDimension() + " back to exit dimension");
					DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(player.world.provider.getDimension());

					if (properties == null) {
						RBPacketHandler.INSTANCE.sendToServer(new PacketTeleporter(0,0,0,0,player.getEntityId()));
						Main.logger.info("Something is wrong, dimension property is null");
					} else {
						BlockPos exitPos = properties.getExitPos();
						RBPacketHandler.INSTANCE.sendToServer(new PacketTeleporter(exitPos.getX(),exitPos.getY(),exitPos.getZ(),properties.getExitDim(),player.getEntityId()));
					}
				}
			}
		}
	}
}
