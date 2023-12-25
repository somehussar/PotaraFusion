package io.github.somehussar.potara.player;

import JinRyuu.JRMCore.JRMCoreConfig;
import JinRyuu.JRMCore.JRMCoreH;
import io.github.somehussar.potara.item.ItemRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class DBCPlayerWrapper {

    EntityPlayer player;
    NBTTagCompound tag;

    private DBCPlayerWrapper(EntityPlayer p){
        this.player = p;
        this.tag = p.getEntityData().getCompoundTag("PlayerPersisted");
    }
    
    public static DBCPlayerWrapper getPlayer(EntityPlayer p){
        return new DBCPlayerWrapper(p);
    }

    public boolean canFuse(){
        return !JRMCoreH.isFused(player) && !hasNoFuse();
    }

    public boolean willingToFuse(){
        return JRMCoreH.PlyrSettingsB(this.player, 4);
    }

    public void fuseWith(EntityPlayer spectator){
        fuseWith(spectator, 30);
    }

    public void fuseWith(EntityPlayer spectator, int time){
        fuseWith(DBCPlayerWrapper.getPlayer(spectator), time);
    }
    public void fuseWith(DBCPlayerWrapper spectator, int time){

    }

    public boolean canUsePotara(){
        return canFuse() && getFusionLevel() == 10 && player.inventory.hasItemStack(ItemRegistry.POTARA_CUSTOM_ITEM.getItemStack());
    }

    public boolean hasNoFuse(){
        String fusion = tag.getString("jrmcFuzion");
        if (fusion == null){
            return false;
        }
        if(fusion.isEmpty() || fusion.equals(" ") || fusion.equals("0")){
            return false;
        }
        return true;
    }

    public int getFusionLevel(){
        return JRMCoreH.SklLvl(0, player);
    }
}
