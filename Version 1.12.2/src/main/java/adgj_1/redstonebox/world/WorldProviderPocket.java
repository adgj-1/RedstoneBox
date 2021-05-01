package adgj_1.redstonebox.world;

import adgj_1.redstonebox.init.DynamicDimensionHelper;

import net.minecraft.world.DimensionType;
import net.minecraft.world.gen.IChunkGenerator;

public class WorldProviderPocket extends WorldProviderEmpty {

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderPocket(this.world, this.world.getSeed());
	}
	
	@Override
	public DimensionType getDimensionType() {
		// TODO Auto-generated method stub
		return DynamicDimensionHelper.PocketDimensionType;
	}
	
}
