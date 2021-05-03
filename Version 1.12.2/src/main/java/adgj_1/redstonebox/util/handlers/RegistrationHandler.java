package adgj_1.redstonebox.util.handlers;

import adgj_1.redstonebox.items.BlockBase;
import adgj_1.redstonebox.items.tileentity.TileEntityBoxWorkbench;
import adgj_1.redstonebox.items.tileentity.TileEntitySignalBlock;
import adgj_1.redstonebox.util.Reference;

import adgj_1.redstonebox.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber(modid = Reference.MODID)
public class RegistrationHandler {
	
	@SubscribeEvent
	public static void registerItems(Register<Item> event) {
		final Item[] items = {
				Main.box_redstone
		};
		
		final Item[] blocks = {
				((BlockBase)Main.workbench_box).getItemBlock(),
				((BlockBase)Main.block_signal).getItemBlock()
		};
		event.getRegistry().registerAll(items);
		event.getRegistry().registerAll(blocks);
		
		GameRegistry.registerTileEntity(TileEntityBoxWorkbench.class, "workbench_box");
		GameRegistry.registerTileEntity(TileEntitySignalBlock.class, "block_signal");
	}
	
	@SubscribeEvent
	public static void registerBlocks(Register<Block> event) {
		final Block[] blocks = {
				Main.workbench_box,
				Main.block_signal
		};
		event.getRegistry().registerAll(blocks);
	}
	
	
	
}
