package io.github.somehussar.potara.event;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import io.github.somehussar.potara.entity.EntityThrownPotara;
import io.github.somehussar.potara.item.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PlayerListener {

    @SubscribeEvent
    public void interactEvent(PlayerInteractEvent event){
        if(event.entityPlayer.getEntityWorld().isRemote){
            return;
        }

        if(event.action == PlayerInteractEvent.Action.LEFT_CLICK_BLOCK){
            return;
        }

        EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
        ItemStack item = player.getHeldItem();

        if(ItemRegistry.POTARA_CUSTOM_ITEM.compare(item)){
            event.setCanceled(true);

            //Disappearing item fix
            player.sendContainerToPlayer(player.inventoryContainer);
            event.setResult(Event.Result.DENY);

            Entity potara = new EntityThrownPotara(player.getEntityWorld(), (EntityLivingBase) player, item.copy());
            player.getEntityWorld().spawnEntityInWorld(potara);

        }
    }
}
