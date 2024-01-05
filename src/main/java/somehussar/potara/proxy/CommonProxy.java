package somehussar.potara.proxy;

import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.EntityRegistry;
import somehussar.potara.PotaraConfig;
import somehussar.potara.PotaraMain;
import somehussar.potara.command.GivePotara;
import somehussar.potara.entity.EntityThrownPotara;
import somehussar.potara.event.PlayerListener;
import somehussar.potara.item.PotaraItemWrapper;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {

    public void onPreInit(FMLPreInitializationEvent e){
        PotaraConfig.init(e.getModConfigurationDirectory().getAbsolutePath());
        registerEntity(EntityThrownPotara.class, "ThrownPotara");
        PotaraItemWrapper.init();
    }

    public void onInit(FMLInitializationEvent e){
        PotaraMain.getLogger().info("Initializing!");
        MinecraftForge.EVENT_BUS.register(new PlayerListener());
    }

    public void onStarting(FMLServerStartingEvent e) {
        e.registerServerCommand(new GivePotara());
    }


    public static void registerEntity(Class<? extends Entity> clazz, String name){
        PotaraMain.getLogger().info(String.format("Registering entity `%s`, class: %s", name, clazz.getName()));
        int randomInt = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(clazz, name, randomInt);
    }

}
