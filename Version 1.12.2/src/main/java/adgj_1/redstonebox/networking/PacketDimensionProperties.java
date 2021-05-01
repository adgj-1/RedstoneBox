package adgj_1.redstonebox.networking;

import adgj_1.redstonebox.init.DimensionProperties;
import adgj_1.redstonebox.init.DynamicDimensionHelper;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketDimensionProperties implements IMessage {

	int value;
	int property;
	int id;
	
	public PacketDimensionProperties() {
		
	}
	
	public PacketDimensionProperties(int id, int property, int value) {
		this.id = id;
		this.property = property;
		this.value = value;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		id = buf.getInt(0);
		property = buf.getInt(4);
		value = buf.getInt(8);
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		buf.writeInt(id);
		buf.writeInt(property);
		buf.writeInt(value);
	}

	public static class DimensionPropertiesHandler implements IMessageHandler<PacketDimensionProperties, IMessage> {

		@Override
		public IMessage onMessage(PacketDimensionProperties message, MessageContext ctx) {
			// TODO Auto-generated method stub
			DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(message.id);
			properties.setProperty(message.property, message.value);
			return message;
		}
		
	}
	
}
