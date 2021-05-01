package adgj_1.redstonebox.items;

import adgj_1.redstonebox.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public abstract class BlockBase extends Block {
	private ItemBlock itemBlock;
	
	public BlockBase(String name,Material mat) {
		super(mat);
		this.setRegistryName(Reference.MODID, name);
		this.setUnlocalizedName(Reference.MODID + "." + name);
		itemBlock = new ItemBlock(this);
		itemBlock.setRegistryName(Reference.MODID, name);
		itemBlock.setUnlocalizedName(Reference.MODID + "." + name);
	}
	
	public ItemBlock getItemBlock() {
		return itemBlock;
	}

}
