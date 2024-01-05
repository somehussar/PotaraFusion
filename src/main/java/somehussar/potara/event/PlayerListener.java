package somehussar.potara.event;

import JinRyuu.JRMCore.JRMCoreConfig;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import somehussar.potara.entity.EntityThrownPotara;
import somehussar.potara.item.PotaraItemWrapper;
import somehussar.potara.player.ChatMessageHelper;
import somehussar.potara.player.DBCPlayerWrapper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
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

        if(!PotaraItemWrapper.compare(item)){ //If the used item isn't a potara, return.
            return;
        }

        event.setCanceled(true);

        //Fixes the disappearing item bug on clientside
        player.sendContainerToPlayer(player.inventoryContainer);
        event.setResult(Event.Result.DENY);

        if(!JRMCoreConfig.fuzn){ //If fusion is disabled, return
            ChatMessageHelper.sendMessage(player, "Fusion is disabled on the server", EnumChatFormatting.RED);
            return;
        }
        DBCPlayerWrapper plr = DBCPlayerWrapper.getPlayer(player);

        if(!plr.canUsePotara() || !plr.willingToFuse()){
            ChatMessageHelper.sendMessage(player, "You are unable to undergo Potara Fusion", EnumChatFormatting.RED);
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
