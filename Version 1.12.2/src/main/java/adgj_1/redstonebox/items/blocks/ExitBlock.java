package adgj_1.redstonebox.items.blocks;

import java.util.UUID;

import javax.annotation.Nullable;
import javax.vecmath.Matrix3f;

import adgj_1.redstonebox.init.DimensionProperties;
import adgj_1.redstonebox.init.DynamicDimensionHelper;
import adgj_1.redstonebox.items.BlockBase;
import adgj_1.redstonebox.world.WorldProviderEmpty;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class ExitBlock extends BlockBase {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public ExitBlock() {
		super("block_exit", Material.ROCK);
		setCreativeTab(CreativeTabs.REDSTONE);
		this.setHardness(0.7f);
		this.setResistance(2.0f);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		// no gui yet
//		if (worldIn.isRemote)
//        {
//			
//            return true;
//        }
//        else
//        {
//        	TileEntity tileentity = worldIn.getTileEntity(pos);
//
//            if (tileentity instanceof TileEntitySignalBlock)
//            {
//            	playerIn.openGui(Main.instance, Reference.GUI_SIGNAL, worldIn, pos.getX(), pos.getY(), pos.getZ());
//            }
//            return true;
//        }
		
		return false;
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (!worldIn.isRemote) {
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing)state.getValue(FACING);
			
			if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.SOUTH;
			if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.NORTH;
			if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.EAST;
			if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.WEST;
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}
	
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()),2);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing facing = EnumFacing.getFront(meta);
		if (facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
		return this.getDefaultState().withProperty(FACING, facing);
	}
	
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
		if (!worldIn.isRemote) {
			if (worldIn.provider instanceof WorldProviderEmpty) {
				DimensionProperties properties = DynamicDimensionHelper.getInstance().getDimensionProperties(worldIn.provider.getDimension());
				if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss() && !(entityIn instanceof EntityPlayer) && properties.isKeyHeld)
		        {
					Vec3d exitPos = properties.getHeldPos();
					
					WorldServer worldserver = DimensionManager.getWorld(properties.getHeldDim());
		            
					Entity entity = EntityList.newEntity(entityIn.getClass(), DimensionManager.getWorld(properties.getHeldDim()));
					
					// Copy data from old entity and remove dimension flag
					NBTTagCompound nbttagcompound = entityIn.writeToNBT(new NBTTagCompound());
					nbttagcompound.removeTag("Dimension");
					entity.readFromNBT(nbttagcompound);
					
					// Remove old entity
					entityIn.getEntityWorld().removeEntity(entityIn);
					
					// Update location
					entity.setPosition(exitPos.x, exitPos.y, exitPos.z);
					
					Vec3d entityVel = new Vec3d(entity.motionX,entity.motionY,entity.motionZ);
					
					//TODO clean up the code a bit
					Matrix3f currentBase = getBlockFacingMatrix(state);
					
					Matrix3f mat = new Matrix3f();
					mat.setIdentity();

					
					Matrix3f targetBase = new Matrix3f((float)properties.getHeldRot().x,(float)properties.getHeldRotDown().x, (float)properties.getHeldRotLeft().x,
							(float)properties.getHeldRot().y,(float)properties.getHeldRotDown().y,(float)properties.getHeldRotLeft().y,
							(float)properties.getHeldRot().z, (float)properties.getHeldRotDown().z, (float)properties.getHeldRotLeft().z);
					mat.mul(targetBase);
					mat.mul(currentBase);
					entityVel = mulMatVec(mat,entityVel);
					
					entity.setVelocity(entityVel.x, entityVel.y, entityVel.z);
					entity.setUniqueId(UUID.randomUUID());
					worldserver.spawnEntity(entity);
		        }
			}
		}
        
    }
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }
	
	public Vec3d mulMatVec(Matrix3f mat, Vec3d vec) {
		Vec3d res = new Vec3d(mat.m00 * vec.x + mat.m01 * vec.y + mat.m02 * vec.z,
				mat.m10 * vec.x + mat.m11 * vec.y + mat.m12 * vec.z,
				mat.m20 * vec.x + mat.m21 * vec.y + mat.m22 * vec.z);
		return res;
	}
	
	public Matrix3f getBlockFacingMatrix(IBlockState state) {
		Matrix3f mat = new Matrix3f();
		mat.setIdentity();
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		
		if (face == EnumFacing.EAST) {
			mat.m00 = -1;
		}
		if (face == EnumFacing.NORTH) {
			mat.m00 = 0;
			mat.m20 = 1;
			mat.m02 = 1;
			mat.m22 = 0;
		}
		
		if (face == EnumFacing.SOUTH) {
			mat.m00 = 0;
			mat.m20 = -1;
			mat.m02 = -1;
			mat.m22 = 0;
		}
		
		return mat;
	}
	
	
}
