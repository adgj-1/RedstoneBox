package adgj_1.redstonebox.networking;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class RBPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("rb");
	
	public static void regMessages() {
		INSTANCE.registerMessage(PacketBoxWorkbench.PacketBoxWorkbenchHandler.class, PacketBoxWorkbench.class, 0,
				Side.SERVER);
		INSTANCE.registerMessage(PacketTeleporter.TeleportHandler.class, PacketTeleporter.class, 1,
				Side.SERVER);
		INSTANCE.registerMessage(PacketDimensionProperties.DimensionPropertiesHandler.class, PacketDimensionProperties.class, 2,
				Side.SERVER);
	}
}
