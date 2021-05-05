package adgj_1.redstonebox.init;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPOutputStream;

import adgj_1.redstonebox.world.WorldProviderEmpty;
import adgj_1.redstonebox.world.WorldProviderPocket;
import com.google.common.io.Files;

import adgj_1.redstonebox.Main;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.DimensionType;

public class DynamicDimensionHelper {
	public static final String tempFile = "/temp.dat";
	public static final String workingPath = "rboxdim";
	public static final int BASE_BOXID = 15;
	private static DynamicDimensionHelper instance = new DynamicDimensionHelper();
	private Map<Integer, DimensionProperties> dimensionList;
	
	public static int nextBoxId;
	public static DimensionProperties defaultDimensionProperties;
	public static final DimensionType EmptyDimensionType = DimensionType.register("Empty", "Empty", 2, WorldProviderEmpty.class, false);
	public static final DimensionType PocketDimensionType = DimensionType.register("Pocket", "Pocket", 3, WorldProviderPocket.class, false);
	
	public DynamicDimensionHelper() {
		defaultDimensionProperties = new DimensionProperties(0);
	}
	
	/**
	 * Saves all dimension data to disk, SHOULD NOT BE CALLED OUTSIDE OF WORLDSAVEEVENT
	 * @param filePath file path to which to save the data
	 */
	public void saveDimensions(String filePath) throws Exception {

		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound dimListnbt = new NBTTagCompound();

		//Save next id
		nbt.setInteger("nextBoxId", nextBoxId);

		//Save Dimension Data
		NBTTagCompound dimNbt = new NBTTagCompound();

		for(Entry<Integer, DimensionProperties> dimSet : dimensionList.entrySet()) {

			dimNbt = new NBTTagCompound();
			try {
				dimSet.getValue().writeToNBT(dimNbt);
				
			} catch (Exception e) {
				Main.logger.error("Unable to write nbt data: ");
				e.printStackTrace();
			}

			dimListnbt.setTag(dimSet.getKey().toString(), dimNbt);
		}

		nbt.setTag("dimList", dimListnbt);

		try {
			File file = new File(net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory(), filePath + tempFile);

			if(!file.exists())
				file.createNewFile();

			File tmpFile = File.createTempFile("dimprops", ".DAT", net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory());
			FileOutputStream tmpFileOut = new FileOutputStream(tmpFile);
			DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(tmpFileOut)));
			try {
				//Closes output stream internally without flush... why tho...
				CompressedStreamTools.write(nbt, outStream);

				//Open in append mode to make sure the file syncs, hacky AF
				outStream.flush();
				tmpFileOut.getFD().sync();
				outStream.close();

				Files.copy(tmpFile, file);
				tmpFile.delete();

			} catch(Exception e) {
				Main.logger.error("Cannot save dimension file, you may be able to find backups in " + net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory());
				e.printStackTrace();
			}



		} catch (IOException e) {
			Main.logger.error("Cannot save dimension files, you may be able to find backups in " + net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory());
			e.printStackTrace();
		}
	}
	
	public void loadDimensionData(String filePath) {
		dimensionList = loadDimensions(filePath);
	}
	
	/**
	 * Loads all dimension information from disk into the current instance of DimensionManager
	 * @param filePath file path from which to load the information
	 */
	public Map<Integer,DimensionProperties> loadDimensions(String filePath) {
		//hasBeenInitiallized = true;
		Map<Integer,DimensionProperties> loadedDimProps = new HashMap<Integer,DimensionProperties>();

		FileInputStream inStream;
		NBTTagCompound nbt;
		try {
			File file = new File(net.minecraftforge.common.DimensionManager.getCurrentSaveRootDirectory(), filePath + tempFile);

			if(!file.exists()) {
				new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - file.getName().length())).mkdirs();


				file.createNewFile();
				nextBoxId = BASE_BOXID;
				return loadedDimProps;
			}

			inStream = new FileInputStream(file);
			nbt = CompressedStreamTools.readCompressed(inStream);
			inStream.close();
		} catch(Exception e) {
			e.printStackTrace();
			return loadedDimProps;
		}

		nextBoxId = nbt.getInteger("nextBoxId");

		NBTTagCompound dimListNbt = nbt.getCompoundTag("dimList");
		
		Main.logger.info("this is what's in the save file");
		Main.logger.info(nbt.toString());

		for(Object key : dimListNbt.getKeySet()) {
			String keyString = (String)key;
			DimensionProperties properties = DimensionProperties.createFromNBT(Integer.parseInt(keyString) ,dimListNbt.getCompoundTag(keyString));

			if(properties != null) {
				int keyInt = Integer.parseInt(keyString);
				if(!net.minecraftforge.common.DimensionManager.isDimensionRegistered(keyInt)) {
					net.minecraftforge.common.DimensionManager.registerDimension(keyInt, properties.getDimType());
					Main.logger.info("Registered dimension #" + keyInt + " as type " + properties.getDimType());
				}

				loadedDimProps.put(new Integer(keyInt), properties);
			}
			else{
				Main.logger.warn("Null Dimension Properties Recieved");
			}
			//TODO: print unable to register world
		}


		return loadedDimProps;
	}
	
	/**
	 * Iterates though the list of existing dimIds, and returns the closest free id greater than two
	 * @return next free id
	 */
	public int getNextFreeDim(int startingValue) {
		for(int i = startingValue; i < 10000; i++) {
			if(!net.minecraftforge.common.DimensionManager.isDimensionRegistered(i) && !dimensionList.containsKey(i))
				return i;
		}
		return -1;
	}
	
	public static DynamicDimensionHelper getInstance() {
		return instance;
	}
	public static void setInstance(DynamicDimensionHelper instance) {
		DynamicDimensionHelper.instance = instance;
	}
	
	/**
	 * register and return the next available dimension id
	 * @return registered dimension id
	 */
	public int getRegBoxId() {
		int res = nextBoxId;
		nextBoxId = getNextFreeDim(nextBoxId);
		registerNewDimension(nextBoxId, EmptyDimensionType);
		return res;
	}

	/**
	 * register and return the next available dimension id with the given type
	 * @return registered dimension id
	 */
	public int getRegBoxId(DimensionType type) {
		int res = nextBoxId;
		nextBoxId = getNextFreeDim(nextBoxId);
		registerNewDimension(nextBoxId, type);
		return res;
	}
	
	/**
	 * registers the dimension with forge and DynamicDimensionHelper given the id
	 * @param dimId the dimension id to register
	 */
	public void registerNewDimension(int dimId, DimensionType type) {
		DimensionProperties properties = new DimensionProperties(dimId);
		properties.setDimType(type);
		dimensionList.put(dimId, properties);
		net.minecraftforge.common.DimensionManager.registerDimension(nextBoxId, type);
		Main.logger.info("Registered dimension #" + nextBoxId + " as type " + type);
//		Main.logger.info("Registered dimension properties #" + nextBoxId + " as type " + properties.getDimType());
//		Main.logger.info(DynamicDimensionHelper.getInstance().getDimensionProperties(nextBoxId).getDimType());
	}
	
	/**
	 * 
	 * @param dimension the dimension id
	 * @return the properties of the dimension with given id
	 */
	public DimensionProperties getDimensionProperties(int dimension) {
		return (DimensionProperties)dimensionList.get(dimension);
	}
}
