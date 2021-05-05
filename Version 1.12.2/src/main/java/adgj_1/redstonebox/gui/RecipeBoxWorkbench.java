package adgj_1.redstonebox.gui;

import adgj_1.redstonebox.init.DynamicDimensionHelper;
import adgj_1.redstonebox.init.IInfusingMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class RecipeBoxWorkbench {
	
	public static final RecipeBoxWorkbench INSTANCE = new RecipeBoxWorkbench();
	
	
	public ItemStack[] getResult(ItemStack in0, ItemStack in1, World worldIn) {
		if (in0 == ItemStack.EMPTY) {
			return null;
		} 
		if (in0.getItem() instanceof IInfusingMaterial) {
			IInfusingMaterial mat = (IInfusingMaterial)in0.getItem();
			
			if (in1.hasTagCompound()) {
				if (in1.getTagCompound().hasKey("boxid")) {
					return null;
				}
			} else {
				in1.setTagCompound(new NBTTagCompound());
			}
			if (!worldIn.isRemote) {
				in1.getTagCompound().setInteger("boxid", DynamicDimensionHelper.getInstance().getRegBoxId(mat.getInfusedDimensionType()));
			} else {
				in1.getTagCompound().setInteger("boxid", DynamicDimensionHelper.nextBoxId);
			}
			if (!in1.getTagCompound().hasKey("display", 10)) {
				in1.getTagCompound().setTag("display", new NBTTagCompound());
			}
			if (!in1.getTagCompound().getCompoundTag("display").hasKey("Lore", 8)) {
				in1.getTagCompound().getCompoundTag("display").setTag("Lore", new NBTTagList());
			}
			NBTTagList loreList = in1.getTagCompound().getCompoundTag("display").getTagList("Lore", 8);
			
			if (!mat.hasCustomLore()) {
				loreList.appendTag(new NBTTagString(TextFormatting.RESET + "" + TextFormatting.AQUA + "Dimensional Container"));
			} else {
				for (String str : mat.getLore()) {
					loreList.appendTag(new NBTTagString(str));
				}
			}
		}
		//in1.setStackDisplayName("Dimensional Container");
		return new ItemStack[] {ItemStack.EMPTY,in1};
		
	}
	
}
