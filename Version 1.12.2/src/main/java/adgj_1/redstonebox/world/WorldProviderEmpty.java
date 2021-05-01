package adgj_1.redstonebox.world;

import adgj_1.redstonebox.init.DimensionProperties;
import adgj_1.redstonebox.init.DynamicDimensionHelper;

import adgj_1.redstonebox.Main;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderEmpty extends WorldProvider {
//	private IRenderHandler skyRender;
	
	@Override
	public double getHorizon() {
		return 0;
	}
	
	
	
	public int getAverageGroundLevel() {
		return 0;
	}
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderEmpty(this.world, this.world.getSeed());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer() {
		
		return super.getSkyRenderer();
	}
	
	public float getAtmosphereDensity(BlockPos pos) {
		DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(world.provider.getDimension());
		//TODO change brightness based on properties
		if (properties == null) {
			return 0;
		}
		return properties.getAtmosphereDensity();
	}
	

	@Override
	public float getSunBrightness(float partialTicks) {
		
		DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(world.provider.getDimension());
		//TODO change brightness based on properties
		if (properties == null) {
			return 50;
		}
		
		return 50;
	}

	@Override
	protected void init() {
		this.hasSkyLight=true;
		world.getWorldInfo().setTerrainType(Main.emptyWorldType);
		
		this.biomeProvider = new BiomeProviderSingle(Biome.getBiome(1));//new ChunkManagerPlanet(worldObj, worldObj.getWorldInfo().getGeneratorOptions(), DimensionManager.getInstance().getDimensionProperties(worldObj.provider.getDimension()).getBiomes());
		
	}
	
	@Override
	public boolean canRespawnHere() {
		return false;
	}
	
	public DimensionProperties getDimensionProperties(BlockPos pos) {
		return DynamicDimensionHelper.defaultDimensionProperties;
	}



	@Override
	public DimensionType getDimensionType() {
		// TODO Auto-generated method stub
		return DynamicDimensionHelper.EmptyDimensionType;
	}
}
