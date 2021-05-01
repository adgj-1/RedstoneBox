package adgj_1.redstonebox.init;

import net.minecraft.nbt.NBTTagCompound;

public interface IDimensionProperties {
	/**
	 * @return the DIMID of the planet
	 */
	public int getId();
	
	/**
	 * @return the color of the sun as an array of floats represented as  {r,g,b}
	 */
	public float[] getSunColor();
	
	
	/**
	 * @return the name of the planet
	 */
	public String getName();
	
	/**
	 * @return the {@link DimensionProperties} of the parent planet
	 */
	public IDimensionProperties getParentProperties();
	
	/**
	 * @return float[3] array containing sky color
	 */
	public float[] getSkyColor();
	
	/**
	 * sets the gravity multiplier of the object
	 */
	public void setGravitationalMultiplier(float mult);
	
	/**
	 * gets the gravity multiplier of the object
	 */
	public float getGravitationalMultiplier();
	
	

	public void writeToNBT(NBTTagCompound nbt);

	public void readFromNBT(NBTTagCompound nbt);

	/**
	 * @return density of the atmosphere in the range 0 to 200
	 */
	public int getAtmosphereDensity();

	

	/**
	 * @return temperature of the planet in Kelvin
	 */
	public int getAverageTemp();
	

	/**
	 * Set the atmosphere like the terraformer does, can trigger terraform event
	 * @param i new atmosphere density
	 */
	public void setAtmosphereDensity(int i);

	
}
