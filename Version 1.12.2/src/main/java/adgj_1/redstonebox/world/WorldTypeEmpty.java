package adgj_1.redstonebox.world;

import net.minecraft.world.WorldType;

public class WorldTypeEmpty extends WorldType {

	public WorldTypeEmpty(String string) {
		super(string);
	}

	
	@Override
	public boolean canBeCreated() {
		return false;
	}
}
