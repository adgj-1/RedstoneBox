package adgj_1.redstonebox.items.blocks;

import adgj_1.redstonebox.items.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockSapphire extends BlockBase {
	
	public BlockSapphire() {
		super("block_sapphire", Material.ROCK);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(3f);
		this.setResistance(3f);
		this.setHarvestLevel("pickaxe", 2);
	}

}
