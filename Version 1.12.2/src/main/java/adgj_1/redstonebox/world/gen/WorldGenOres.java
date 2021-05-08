package adgj_1.redstonebox.world.gen;

import java.util.Random;

import adgj_1.redstonebox.Main;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenOres implements IWorldGenerator {
	private WorldGenerator ore_sapphire;
	
	public WorldGenOres() {
		ore_sapphire = new WorldGenMinable(Main.ore_sapphire.getDefaultState(), 8);
	}

	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight) {
		if (minHeight > maxHeight || minHeight < 0 || maxHeight > 256) {
			throw new IllegalArgumentException("Ore generated out of bounds");
		}
		
		int heightDiff = maxHeight - minHeight + 1;
		for (int i = 0; i < chance; i++) {
			int x = chunkX * 16 + rand.nextInt(16);
			int z = chunkZ * 16 + rand.nextInt(16);
			int y = minHeight + rand.nextInt(heightDiff);
			
			gen.generate(world, rand, new BlockPos(x,y,z));
		}
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
		
		case -1: {
			break;
		}
		
		case 1: {
			break;
		}
		
		default: {
			runGenerator(ore_sapphire, world, random, chunkX, chunkZ, 5, 5, 30);
		}
		}
		
	}
}
