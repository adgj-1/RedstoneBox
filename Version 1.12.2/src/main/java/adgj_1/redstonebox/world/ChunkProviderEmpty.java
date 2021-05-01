package adgj_1.redstonebox.world;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;

public class ChunkProviderEmpty implements IChunkGenerator {

	World worldObj;
	
	public ChunkProviderEmpty(World world, long seed)
	{
		this.worldObj = world;
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		ChunkPrimer chunkprimer = new ChunkPrimer();
		
		//ChunkExtendedBiome
		Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);//new Chunk(this.worldObj, ablock, abyte, p_73154_1_, p_73154_2_);
		//TODO: convert back to int
		byte[] abyte1 = chunk.getBiomeArray();

		Arrays.fill(abyte1, (byte)Biome.getIdForBiome(Biome.getBiomeForId(1)));

		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {
		
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {
		
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

}
