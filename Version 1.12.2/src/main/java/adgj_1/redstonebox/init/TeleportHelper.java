package adgj_1.redstonebox.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleportHelper extends Teleporter {
	
	private final WorldServer world;
	private double x, y, z;
	public TeleportHelper(WorldServer worldIn, double x, double y, double z) {
		super(worldIn);
		this.world = worldIn;
		this.x = x;
		this.y = y;
		this.z = z;
		// TODO Auto-generated constructor stub
	}

	public void placeInPortal(Entity entityIn, float rotationYaw) {
		this.world.getBlockState(new BlockPos(this.x,this.y,this.z));
		entityIn.setPosition(x, y, z);
		entityIn.motionX = 0f;
		entityIn.motionY = 0f;
		entityIn.motionZ = 0f;
	}
	
	
}
