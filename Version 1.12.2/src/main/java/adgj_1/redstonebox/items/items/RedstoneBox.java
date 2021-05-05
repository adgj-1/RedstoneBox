package adgj_1.redstonebox.items.items;

import java.util.List;

import adgj_1.redstonebox.init.DynamicDimensionHelper;
import adgj_1.redstonebox.init.IInfusingMaterial;
import adgj_1.redstonebox.items.ItemBase;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class RedstoneBox extends ItemBase implements IInfusingMaterial {

	public RedstoneBox() {
		super("box_redstone");
		setCreativeTab(CreativeTabs.REDSTONE);
	}

	@Override
	public DimensionType getInfusedDimensionType() {
		return DynamicDimensionHelper.PocketDimensionType;
	}

	@Override
	public boolean hasCustomLore() {
		return false;
	}

	@Override
	public List<String> getLore() {
		return null;
	}

	
	
}
