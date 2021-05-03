package adgj_1.redstonebox;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import adgj_1.redstonebox.init.DynamicDimensionHelper;
import adgj_1.redstonebox.items.blocks.BoxWorkbench;
import adgj_1.redstonebox.items.blocks.SignalBlock;
import adgj_1.redstonebox.items.items.RedstoneBox;
import adgj_1.redstonebox.networking.RBPacketHandler;
import adgj_1.redstonebox.util.Reference;
import adgj_1.redstonebox.util.handlers.GuiHandler;
import adgj_1.redstonebox.util.handlers.RBEventHandler;
import adgj_1.redstonebox.world.WorldTypeEmpty;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.WorldType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class Main
{
	
	@Instance
    public static Main instance;

    public static Logger logger;

    public static KeyBinding[] keyBindings;
    
    // items
    public static Item box_redstone;
    
    // blocks
    public static Block workbench_box;
    public static Block block_signal;
    
	public static WorldType emptyWorldType;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        box_redstone = new RedstoneBox();
        workbench_box = new BoxWorkbench();
        block_signal = new SignalBlock();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        
        emptyWorldType = new WorldTypeEmpty("Empty");
        
        // register gui handler
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
        
        // register message handler
        RBPacketHandler.regMessages();
        
        // register keybindings
        keyBindings = new KeyBinding[1];
        
        keyBindings[0] = new KeyBinding("key.teleport.desc", Keyboard.KEY_K, "key.rb.category");
        
        for (int i = 0; i < 1; i++) {
        	ClientRegistry.registerKeyBinding(keyBindings[0]);
        }
    }
    
    @EventHandler
    public void postinit(FMLInitializationEvent event)
    {
        RBEventHandler handler = new RBEventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
    }
    
    @EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
    	DynamicDimensionHelper.getInstance().loadDimensionData(DynamicDimensionHelper.workingPath);
    }
}
