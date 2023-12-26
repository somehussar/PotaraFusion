package io.github.somehussar.potara.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import io.github.somehussar.potara.PotaraConfig;
import io.github.somehussar.potara.PotaraMain;
import io.github.somehussar.potara.command.GivePotara;
import io.github.somehussar.potara.entity.CustomEntityRegistry;
import io.github.somehussar.potara.entity.EntityThrownPotara;
import io.github.somehussar.potara.event.PlayerListener;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void onPreInit(FMLPreInitializationEvent e){
        PotaraConfig.init(e.getModConfigurationDirectory().getAbsolutePath());
        CustomEntityRegistry.registerEntity(EntityThrownPotara.class, "ThrownPotara");
    }

    public void onInit(FMLInitializationEvent e){
        PotaraMain.getLogger().info("Initializing!");

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
    }

    public void onStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new GivePotara());
    }

}
