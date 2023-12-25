package io.github.somehussar.potara.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import io.github.somehussar.potara.PotaraMain;
import io.github.somehussar.potara.command.GivePotara;
import io.github.somehussar.potara.entity.EntityThrownPotara;
import io.github.somehussar.potara.event.PlayerListener;
import io.github.somehussar.potara.event.WorldListener;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void onPreInit(FMLPreInitializationEvent e){
    }

    public void onInit(FMLInitializationEvent e){
        PotaraMain.getLogger().info("Initializing!");

        MinecraftForge.EVENT_BUS.register(new PlayerListener());
        //MinecraftForge.EVENT_BUS.register(new WorldListener());
        //FMLCommonHandler.instance().bus().register(new PlayerListener());
    }

    public void onPostInit(FMLPostInitializationEvent e) {
        PotaraMain.getLogger().info("INITIALIZED!!!");
    }

    public void onAboutToStart(FMLServerAboutToStartEvent e) {

    }

    public void onStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new GivePotara());
    }

    public void onStarted(FMLServerStartedEvent e) {

    }

    public void onStopping(FMLServerStoppingEvent e) {

    }

    public void onStopped(FMLServerStoppedEvent e) {

    }



}
