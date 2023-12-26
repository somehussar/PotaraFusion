package io.github.somehussar.potara.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import io.github.somehussar.potara.PotaraMain;
import net.minecraft.entity.Entity;

public class CustomEntityRegistry {

    public static void registerEntity(Class<? extends Entity> clazz, String name){
        PotaraMain.getLogger().info(String.format("Registering entity `%s`, class: %s", name, clazz.getName()));
        int randomInt = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(clazz, name, randomInt);
    }
}
