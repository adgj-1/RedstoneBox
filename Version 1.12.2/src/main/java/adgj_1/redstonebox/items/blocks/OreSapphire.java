package adgj_1.redstonebox.items.blocks;

import java.util.Random;

import adgj_1.redstonebox.Main;
import adgj_1.redstonebox.items.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class OreSapphire extends BlockBase {
	
	public OreSapphire() {
		super("ore_sapphire", Material.ROCK);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.setHardness(3f);
		this.setResistance(3f);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Main.sapphire;
    }
	
	public int quantityDropped(Random random)
    {
        return 1;
    }
	
	public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
    }
	
	@Override
    public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        int i = MathHelper.getInt(rand, 3, 7);
        return i;
    }

}
