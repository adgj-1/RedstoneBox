package adgj_1.redstonebox.init;

import java.util.List;

import net.minecraft.world.DimensionType;

public interface IInfusingMaterial {

	/**
	 * preferably a subtype of DimensionTypeEmpty, otherwise teleportation back
	 * will not work as it will think this is not a pocket dimension and attempt
	 * to teleport into another
	 * @return DimensionType
	 */
	public DimensionType getInfusedDimensionType();
	
	public boolean hasCustomLore();
	
	public List<String> getLore();
}
