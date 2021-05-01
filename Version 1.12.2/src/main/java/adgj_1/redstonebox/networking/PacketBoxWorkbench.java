package adgj_1.redstonebox.networking;


import adgj_1.redstonebox.items.tileentity.TileEntityBoxWorkbench;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketBoxWorkbench implements IMessage {
	private int x, y, z, id;
	public PacketBoxWorkbench() {
		
	}
	public PacketBoxWorkbench(int id, BlockPos pos) {
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		this.id = id;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		x = buf.getInt(0);
		y = buf.getInt(4);
		z = buf.getInt(8);
		id = buf.getInt(12);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(id);
	}

	public static class PacketBoxWorkbenchHandler implements IMessageHandler<PacketBoxWorkbench, IMessage> {

		@Override
		public IMessage onMessage(PacketBoxWorkbench message, MessageContext ctx) {
			World world = ctx.getServerHandler().player.getEntityWorld();
			TileEntity te = world.getTileEntity(new BlockPos(message.x, message.y, message.z));
			if (te instanceof TileEntityBoxWorkbench) {
				TileEntityBoxWorkbench workbench = (TileEntityBoxWorkbench) te;
				workbench.buttonCommand(message.id);
				workbench.markDirty();
			}
			return null;
		}

	}
}
