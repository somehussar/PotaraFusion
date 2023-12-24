package io.github.somehussar.potara.proxy;

import cpw.mods.fml.common.event.*;
import io.github.somehussar.potara.PotaraMain;
import io.github.somehussar.potara.command.TestCommand;

public class CommonProxy {

    public void onPreInit(FMLPreInitializationEvent e){

    }

    public void onInit(FMLInitializationEvent e){
        PotaraMain.getLogger().info("Initializing!");
    }

    public void onPostInit(FMLPostInitializationEvent e) {
        PotaraMain.getLogger().info("INITIALIZED!!!");
    }

    public void onAboutToStart(FMLServerAboutToStartEvent e) {

    }

    public void onStarting(FMLServerStartingEvent e) {

    }

    public void onStarted(FMLServerStartedEvent e) {

    }

    public void onStopping(FMLServerStoppingEvent e) {

    }

    public void onStopped(FMLServerStoppedEvent e) {

    }



}
