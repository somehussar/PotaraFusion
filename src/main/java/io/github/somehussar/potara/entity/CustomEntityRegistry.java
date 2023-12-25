package io.github.somehussar.potara.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

public class CustomEntityRegistry {

    public static void registerEntity(Class<? extends Entity> clazz, String name){
        int randomInt = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(clazz, name, randomInt);
    }
}
