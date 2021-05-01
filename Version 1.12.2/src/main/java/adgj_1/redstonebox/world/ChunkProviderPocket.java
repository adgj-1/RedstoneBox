package adgj_1.redstonebox.world;

import java.util.List;

import adgj_1.redstonebox.init.DimensionProperties;
import adgj_1.redstonebox.init.DynamicDimensionHelper;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkProviderPocket extends ChunkProviderEmpty implements IChunkGenerator {

	public ChunkProviderPocket(World world, long seed) {
		super(world, seed);
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		
		Chunk chunk = super.generateChunk(x, z);
		
		DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(chunk.getWorld().provider.getDimension());
		int size = properties.getSize();
		if (x == 0 && z == 0) {
			generateBox(chunk,size);
		}
		
		return chunk;
	}

	public void generateBox(Chunk chunk, int size) {
		chunk.setBlockState(new BlockPos(0,0,0), Block.getBlockById(7).getDefaultState());
		for (int bx = 0; bx < size; bx++) {
			for (int by = 0; by < size; by++) {
				for (int bz = 0; bz < size; bz++) {
					boolean flagX = bx == 0 || bx == size-1;
					boolean flagY = by == 0 || by == size-1;
					boolean flagZ = bz == 0 || bz == size-1;
					if (!(flagX || flagY || flagZ) || flagX && (flagY || flagZ) || flagY && flagZ) {
						continue;
					}
					
					chunk.setBlockState(new BlockPos(bx,by,bz), Block.getBlockById(7).getDefaultState());
				}
			}
		}
	}
	
	@Override
	public boolean generateStructures(Chunk arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BlockPos getNearestStructurePos(World arg0, String arg1, BlockPos arg2, boolean arg3) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType arg0, BlockPos arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInsideStructure(World arg0, String arg1, BlockPos arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void populate(int x, int z) {
		// TODO Change Size To Depend On DimensionProperties
//		
	}

	@Override
	public void recreateStructures(Chunk arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
