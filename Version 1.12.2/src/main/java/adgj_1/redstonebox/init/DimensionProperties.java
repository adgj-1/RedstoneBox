package adgj_1.redstonebox.init;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;

public class DimensionProperties {
	public static final int KEY_ACTIVE_DURATION = 8;
	
	private float[] fogColor;
	private float[] skyColor;
	private float[] sunriseSunsetColors;
	private float gravitationalMultiplier;
	private int atmosphereDensity;
	private int id;
	private int averageTemperature;
	private BlockPos exitPos;
	private int exitDim;
	private int size;
	
	// temporary variables does not save to disk
	private int activeKey;
	private int countBeforeKeyReset;
	
	public DimensionProperties(int id) {
		this.id = id;
		resetProperties();
	}

	public void resetProperties() {
		fogColor = new float[] {1f,1f,1f};
		skyColor = new float[] {1f,1f,1f};
		sunriseSunsetColors = new float[] {.7f,.2f,.2f,1};
		gravitationalMultiplier = 1;
		atmosphereDensity = 100;
		averageTemperature = 100;
		exitPos = new BlockPos(0,0,0);
		exitDim = 0;
		size = 16;
	}
	
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float[] getSunColor() {
		// TODO Auto-generated method stub
		return sunriseSunsetColors;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public float[] getSkyColor() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setGravitationalMultiplier(float mult) {
		// TODO Auto-generated method stub
		
	}

	public float getGravitationalMultiplier() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagList list;

		if(skyColor != null) {
			list = new NBTTagList();
			for(float f : skyColor) {
				list.appendTag(new NBTTagFloat(f));
			}
			nbt.setTag("skyColor", list);
		}

		if(sunriseSunsetColors != null) {
			list = new NBTTagList();
			for(float f : sunriseSunsetColors) {
				list.appendTag(new NBTTagFloat(f));
			}
			nbt.setTag("sunriseSunsetColors", list);
		}

		list = new NBTTagList();
		for(float f : fogColor) {
			list.appendTag(new NBTTagFloat(f));
		}
		nbt.setTag("fogColor", list);


//		if(!allowedBiomes.isEmpty()) {
//			int biomeId[] = new int[allowedBiomes.size()];
//			for(int i = 0; i < allowedBiomes.size(); i++) {
//				biomeId[i] = Biome.getIdForBiome(allowedBiomes.get(i).biome);
//			}
//			nbt.setIntArray("biomes", biomeId);
//		}

		nbt.setInteger("size", size);
		nbt.setInteger("id", id);
		nbt.setFloat("gravitationalMultiplier", gravitationalMultiplier);
		nbt.setInteger("atmosphereDensity", atmosphereDensity);
		nbt.setInteger("avgTemperature", averageTemperature);
		nbt.setInteger("exitdim", exitDim);
		nbt.setInteger("exitX", exitPos.getX());
		nbt.setInteger("exitY", exitPos.getY());
		nbt.setInteger("exitZ", exitPos.getZ());
	}

	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagList list;

		if(nbt.hasKey("skyColor")) {
			list = nbt.getTagList("skyColor", NBT.TAG_FLOAT);
			skyColor = new float[list.tagCount()];
			for(int f = 0 ; f < list.tagCount(); f++) {
				skyColor[f] = list.getFloatAt(f);
			}
		}


		if(nbt.hasKey("sunriseSunsetColors")) {
			list = nbt.getTagList("sunriseSunsetColors", NBT.TAG_FLOAT);
			sunriseSunsetColors = new float[list.tagCount()];
			for(int f = 0 ; f < list.tagCount(); f++) {
				sunriseSunsetColors[f] = list.getFloatAt(f);
			}
		}

		if(nbt.hasKey("fogColor")) {
			list = nbt.getTagList("fogColor", NBT.TAG_FLOAT);
			fogColor = new float[list.tagCount()];
			for(int f = 0 ; f < list.tagCount(); f++) {
				fogColor[f] = list.getFloatAt(f);
			}
		}

		
		//Load biomes
//		if(nbt.hasKey("biomes")) {
//
//			allowedBiomes.clear();
//			int biomeIds[] = nbt.getIntArray("biomes");
//			List<Biome> biomesList = new ArrayList<Biome>();
//
//
//			for(int i = 0; i < biomeIds.length; i++) {
//				biomesList.add(AdvancedRocketryBiomes.instance.getBiomeById(biomeIds[i]));
//			}
//
//			allowedBiomes.addAll(getBiomesEntries(biomesList));
//		}

		

		gravitationalMultiplier = nbt.getFloat("gravitationalMultiplier");
		atmosphereDensity = nbt.getInteger("atmosphereDensity");
		averageTemperature = nbt.getInteger("avgTemperature");
		id = nbt.getInteger("id");
		
		if (nbt.hasKey("exitdim")) {
			exitDim = nbt.getInteger("exitdim");
			exitPos = new BlockPos(nbt.getInteger("exitX"), nbt.getInteger("exitY"), nbt.getInteger("exitZ"));
		} else {
			exitDim = 0;
		}

		if (nbt.hasKey("size")) {
			size = nbt.getInteger("size");
		} else {
			size = 16;
		}
	}

	public int getAtmosphereDensity() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getAverageTemp() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setAtmosphereDensity(int i) {
		// TODO Auto-generated method stub
		
	}
	
	public static DimensionProperties createFromNBT(int id, NBTTagCompound nbt) {
		DimensionProperties properties = new DimensionProperties(id);
		properties.readFromNBT(nbt);
		properties.id = id;

		return properties;
	}
	
	public BlockPos getExitPos() {
		if (exitPos == null) {
			exitPos = new BlockPos(0,0,0);
		}
		return exitPos;
	}
	
	public int getExitDim() {
		return exitDim;
	}

	public void setExitDim(int dimension) {
		this.exitDim = dimension;
		
	}

	public void setExitPos(BlockPos blockPos) {
		this.exitPos = blockPos;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void setProperty(int property, int value) {
		switch (property) {
		case 0: {
			size = value;
			break;
		}
		
		case 1: {
			exitDim = value;
			break;
		}
		
		case 2: {
			exitPos = new BlockPos(value,exitPos.getY(),exitPos.getZ());
			break;
		}
		
		case 3: {
			exitPos = new BlockPos(exitPos.getX(),value,exitPos.getZ());
			break;
		}
		
		case 4: {
			exitPos = new BlockPos(exitPos.getX(),exitPos.getY(),value);
			break;
		}
		}
	}

	public void setActiveKey(int key) {
		activeKey = key;
	}
	
	public int getActiveKey() {
		return activeKey;
	}

	public int getCountBeforeKeyReset() {
		return countBeforeKeyReset;
	}

	public void setCountBeforeKeyReset(int countBeforeKeyReset) {
		this.countBeforeKeyReset = countBeforeKeyReset;
	}
}
