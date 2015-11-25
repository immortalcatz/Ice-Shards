package de.ljfa.iceshards.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import de.ljfa.iceshards.Config;
import de.ljfa.iceshards.Reference;

public class IsConfigGui extends GuiConfig {
    public IsConfigGui(GuiScreen parent) {
        super(parent, getConfigElements(), Reference.MODID, false, false, "Ice Shards configuration");
    }
    
    /** Compiles a list of config elements
     * Borrowed from EnderIO's implementation
     */
    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        
        for(String name: Config.conf.getCategoryNames())
            list.add(new ConfigElement<ConfigCategory>(Config.conf.getCategory(name)));
        
        return list;
    }
}
