package adgj_1.redstonebox.items.blocks;

import adgj_1.redstonebox.Main;
import adgj_1.redstonebox.items.BlockBase;
import adgj_1.redstonebox.items.tileentity.TileEntitySignalBlock;
import adgj_1.redstonebox.util.Reference;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SignalBlock extends BlockBase implements ITileEntityProvider {

	public static final PropertyBool isOn = PropertyBool.create("is_on");
	
	public SignalBlock() {
		super("block_signal", Material.ROCK);
		setCreativeTab(CreativeTabs.REDSTONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(isOn, false));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote)
        {
			
            return true;
        }
        else
        {
        	TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntitySignalBlock)
            {
            	playerIn.openGui(Main.instance, Reference.GUI_SIGNAL, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
	}
	
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntitySignalBlock();
    }
	
	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}
	
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		//System.out.println("check power state: " + state.getProperties().get(isOn));
		return (state.getProperties().get(isOn).equals(true)) ? 15 : 0;
		//return 15;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {isOn});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState();
	}
	
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos) 
	{
		
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active) worldIn.setBlockState(pos, Main.block_signal.getDefaultState().withProperty(isOn, true), 3);
		else worldIn.setBlockState(pos, Main.block_signal.getDefaultState().withProperty(isOn, false), 3);
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
		IBlockState state = worldIn.getBlockState(pos);
	}
	
}
