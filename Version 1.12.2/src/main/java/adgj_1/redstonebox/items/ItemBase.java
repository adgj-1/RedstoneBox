package adgj_1.redstonebox.items;

import adgj_1.redstonebox.util.Reference;

import net.minecraft.item.Item;

public class ItemBase extends Item {
	public ItemBase(String name) {
		setUnlocalizedName(Reference.MODID+ "." +name);
		setRegistryName(Reference.MODID, name);
	}
}
