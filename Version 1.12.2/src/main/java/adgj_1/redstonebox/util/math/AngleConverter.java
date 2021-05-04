package adgj_1.redstonebox.util.math;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class AngleConverter {

	public static Vec3d getDownVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F - (float)Math.PI/2);
        float f3 = MathHelper.sin(-pitch * 0.017453292F - (float)Math.PI/2);
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
	
	public static Vec3d getLeftVectorForRotation(float pitch, float yaw)
    {
		pitch = 0;
        float f = MathHelper.cos(-yaw * 0.017453292F + (float)Math.PI/2);
        float f1 = MathHelper.sin(-yaw * 0.017453292F + (float)Math.PI/2);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
	
}
