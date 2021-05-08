package adgj_1.redstonebox.util.compat;

import adgj_1.redstonebox.Main;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCompat {

	public static void registerOres() {
		OreDictionary.registerOre("oreSapphire", Main.ore_sapphire);
		OreDictionary.registerOre("gemSapphire", Main.sapphire);
	}
}
