package adgj_1.redstonebox.gui;

import java.io.IOException;

import adgj_1.redstonebox.items.tileentity.TileEntityBoxWorkbench;
import adgj_1.redstonebox.networking.PacketBoxWorkbench;
import adgj_1.redstonebox.networking.RBPacketHandler;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiBoxWorkbench extends GuiContainer
{
    private static final ResourceLocation WORKBENCH_GUI_TEXTURE = new ResourceLocation("rb:textures/gui/box_workbench.png");
    private final InventoryPlayer playerInventory;
    @SuppressWarnings("unused")
	private final ContainerBoxWorkbench container;
    private final TileEntity te;
    public int ticks;
    
    public GuiBoxWorkbench(InventoryPlayer inventory, TileEntityBoxWorkbench tileentity)
    {
        super(new ContainerBoxWorkbench(inventory, tileentity));
        this.playerInventory = inventory;
        this.container = (ContainerBoxWorkbench)this.inventorySlots;
        this.te = tileentity;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //this.fontRenderer.drawString(this.nameable.getDisplayName().getUnformattedText(), 12, 5, 4210752);
        this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    }

    public void updateScreen()
    {
        super.updateScreen();
    }

    public void initGui() {
    	super.initGui();
    	this.buttonList.add(new GuiButton(1, 30, 50, 100, 20, "Craft"));
    	this.buttonList.add(new GuiButton(2, 30, 100, 100, 20, "Teleport"));
    }
    
    @Override
    protected void actionPerformed(GuiButton b) {
    	
		RBPacketHandler.INSTANCE.sendToServer(new PacketBoxWorkbench(b.id, te.getPos()));
		((TileEntityBoxWorkbench)te).buttonCommand(b.id);
    	
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
//        int i = (this.width - this.xSize) / 2;
//        int j = (this.height - this.ySize) / 2;
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(WORKBENCH_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        partialTicks = this.mc.getTickLength();
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
//        boolean flag = this.mc.player.capabilities.isCreativeMode;
//        int i = (this.width - this.xSize) / 2;
//        int j = (this.height - this.ySize) / 2;
        //this.mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
        //this.drawTexturedModalRect(i+x, i+y, 0, 185, w, h);
        
    }

}

