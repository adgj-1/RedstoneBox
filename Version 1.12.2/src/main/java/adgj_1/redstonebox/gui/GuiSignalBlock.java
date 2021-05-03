package adgj_1.redstonebox.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import adgj_1.redstonebox.items.tileentity.TileEntitySignalBlock;
import adgj_1.redstonebox.networking.PacketSignalBlock;
import adgj_1.redstonebox.networking.RBPacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSignalBlock extends GuiScreen
{
    private static final ResourceLocation WORKBENCH_GUI_TEXTURE = new ResourceLocation("rb:textures/gui/box_workbench.png");
    private final InventoryPlayer playerInventory;
    private final TileEntitySignalBlock te;
    private GuiButton selectButton;
    public static boolean selecting = false;
    public int ticks;
	private int ySize = 250;
	private int xSize = 250;
    private int displayKey = 0;
    
    public GuiSignalBlock(InventoryPlayer inventory, TileEntitySignalBlock tileentity)
    {
        super();
        this.playerInventory = inventory;
        this.te = tileentity;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        //this.fontRenderer.drawString(this.nameable.getDisplayName().getUnformattedText(), 12, 5, 4210752);
    	this.fontRenderer.drawString(this.playerInventory.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);
    	this.fontRenderer.drawString("Select Key: ", 100, 140, 4210752);
    }

    public void updateScreen()
    {
        super.updateScreen();
        int key = te.getSelectedKey();
        if (!selecting && displayKey != key) {
        	selectKey(key);
        	displayKey = key;
        }
    }

    public void initGui() {
    	super.initGui();
    	RBPacketHandler.INSTANCE.sendToServer(new PacketSignalBlock(-1, te.getPos()));
    	selectButton = new GuiButton(1, 170, 140, 100, 20, Keyboard.getKeyName(te.getSelectedKey()));
    	this.buttonList.add(selectButton);
    }
    
    @Override
    protected void actionPerformed(GuiButton b) {
    	selecting = true;
    	b.displayString = "Select Key";
    	
    }
    
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
//        int i = (this.width - this.xSize) / 2;
//        int j = (this.height - this.ySize) / 2;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    	super.keyTyped(typedChar, keyCode);
    	if (keyCode == 1) {
    		GuiSignalBlock.selecting = false;
    		return;
    	}
    	if (GuiSignalBlock.selecting) {
			BlockPos pos = te.getPos();
			RBPacketHandler.INSTANCE.sendToServer(new PacketSignalBlock(keyCode, pos));
			GuiSignalBlock.selecting = false;
		}
    	
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
        
    }
    
    /**
     * This function is purely for client side render, it doesn't actually update the key
     * @param key
     */
    public void selectKey(int key) {
    	selectButton.displayString = Keyboard.getKeyName(key);
    }

    @Override
	public boolean doesGuiPauseGame() {
		return false;
	}
    
    
}

