package adgj_1.redstonebox.networking;

import adgj_1.redstonebox.init.TeleportHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTeleporter implements IMessage {

	int x;
	int y;
	int z;
	int dimid;
	int id;
	
	public PacketTeleporter() {
		
	}
	
	public PacketTeleporter(int x, int y, int z, int dimid, int id) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimid = dimid;
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		x = buf.getInt(0);
		y = buf.getInt(4);
		z = buf.getInt(8);
		dimid = buf.getInt(12);
		id = buf.getInt(16);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(dimid);
		buf.writeInt(id);
	}

	public static class TeleportHandler implements IMessageHandler<PacketTeleporter, IMessage> {

		@Override
		public IMessage onMessage(PacketTeleporter message, MessageContext ctx) {
			Entity entity = ctx.getServerHandler().player.getEntityWorld().getEntityByID(
					message.id);
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
			.addScheduledTask(() -> teleportToDimension(player,message.dimid,message.x,message.y,message.z));
			}
			return message;
		}
		
	}
	
	public static void teleportToDimension(EntityPlayer player, int dimension, double x, double y, double z) {
		//int oldDimension = player.getEntityWorld().provider.getDimension();
		EntityPlayerMP entityPlayerMP = (EntityPlayerMP)player;
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		WorldServer worldServer = server.getWorld(dimension);
		
		if (worldServer == null || server == null) {
			throw new IllegalArgumentException("Dimension: " + dimension + "doesn't exist!");
		}
		worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new TeleportHelper(worldServer,x,y,z));
		player.setPositionAndUpdate(x,y,z);
	}
	
	
}
