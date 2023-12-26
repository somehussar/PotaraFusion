package io.github.somehussar.potara.event;

import JinRyuu.JRMCore.JRMCoreConfig;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import io.github.somehussar.potara.entity.EntityThrownPotara;
import io.github.somehussar.potara.item.ItemRegistry;
import io.github.somehussar.potara.player.DBCPlayerWrapper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class PlayerListener {

    @SubscribeEvent
    public void interactEvent(PlayerInteractEvent event){
        if(event.entityPlayer.getEntityWorld().isRemote){
            return;
        }

        if(event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR){
            return;
        }



        EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
        ItemStack item = player.getHeldItem();

        if(!ItemRegistry.POTARA_CUSTOM_ITEM.compare(item)){
            return;
        }

        event.setCanceled(true);

        //Disappearing item fix
        player.sendContainerToPlayer(player.inventoryContainer);
        event.setResult(Event.Result.DENY);

        if(!JRMCoreConfig.fuzn){
            player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Fusion is disabled on the server."));
            return;
        }

        if(!DBCPlayerWrapper.getPlayer(player).canUsePotara()){
            player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "You are unable to undergo Potara Fusion (Requirements: no NoFuse, lvl 10 fusion and one Potara Earring for each player)"));
            return;
        }

        if(!player.capabilities.isCreativeMode)
            if(--item.stackSize <= 0)
                player.setCurrentItemOrArmor(0, null);


        player.getEntityWorld().playSoundAtEntity(player, "random.bow", 0.5F, (float) (0.4F / Math.random() * 0.4F + 0.8F));
        EntityThrownPotara potara = new EntityThrownPotara(player.getEntityWorld(), player);
        player.getEntityWorld().spawnEntityInWorld(potara);

    }

}
