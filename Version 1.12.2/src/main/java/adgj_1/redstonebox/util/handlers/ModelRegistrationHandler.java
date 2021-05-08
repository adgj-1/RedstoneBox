package adgj_1.redstonebox.util.handlers;

import adgj_1.redstonebox.items.BlockBase;
import adgj_1.redstonebox.util.Reference;

import adgj_1.redstonebox.Main;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = Reference.MODID)
public class ModelRegistrationHandler {
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		registerModel(Main.box_redstone, 0);
		registerModel(Main.sapphire, 0);
		registerModel(((BlockBase)Main.workbench_box).getItemBlock(), 0);
		registerModel(((BlockBase)Main.block_signal).getItemBlock(), 0);
		registerModel(((BlockBase)Main.block_exit).getItemBlock(), 0);
		registerModel(((BlockBase)Main.ore_sapphire).getItemBlock(), 0);
		registerModel(((BlockBase)Main.block_sapphire).getItemBlock(), 0);
	}
	
	private static void registerModel(Item item, int meta) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(),"inventory"));
	}
}
