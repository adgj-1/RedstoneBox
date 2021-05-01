package adgj_1.redstonebox.items.blocks;

import adgj_1.redstonebox.items.BlockBase;
import adgj_1.redstonebox.items.tileentity.TileEntityBoxWorkbench;
import adgj_1.redstonebox.util.Reference;

import adgj_1.redstonebox.Main;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BoxWorkbench extends BlockBase implements ITileEntityProvider {

	public BoxWorkbench() {
		super("workbench_box", Material.ROCK);
		setCreativeTab(CreativeTabs.REDSTONE);
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

            if (tileentity instanceof TileEntityBoxWorkbench)
            {
            	playerIn.openGui(Main.instance, Reference.GUI_WORKBENCH, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityBoxWorkbench();
    }
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityBoxWorkbench tileentity = (TileEntityBoxWorkbench)worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, tileentity);
		super.breakBlock(worldIn, pos, state);
	}
	
}
