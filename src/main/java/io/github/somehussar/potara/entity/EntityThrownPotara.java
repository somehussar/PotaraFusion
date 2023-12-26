package io.github.somehussar.potara.entity;

import io.github.somehussar.potara.item.ItemRegistry;
import io.github.somehussar.potara.player.DBCPlayerWrapper;
import io.github.somehussar.potara.util.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityThrownPotara extends EntityExpBottle {

    private EntityPlayerMP player;

    public EntityThrownPotara(World p_i1785_1_)
    {
        super(p_i1785_1_);
    }
    public EntityThrownPotara(World world, EntityLivingBase entity){
        super(world, entity);

        this.player = (EntityPlayerMP) entity;

        this.motionX *= 2.5;
        this.motionZ *= 2.5;
    }

    public EntityThrownPotara(World world, double x, double y, double z){
        super(world, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition object){
        if(this.worldObj.isRemote){ //Make sure it runs on the server's side
            return;
        }

        this.setDead();


        // this.player == null - used for potaras thrown during a server restart
        //
        // checks if potara still has a thrower player and if the hit object is a player
        if( this.player == null || !(object.entityHit instanceof EntityPlayerMP)){
            if(this.player != null)
                this.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "You missed!"));
            this.summonDrop();
            return;
        }

        DBCPlayerWrapper hitPlayer = DBCPlayerWrapper.getPlayer((EntityPlayer) object.entityHit);
        DBCPlayerWrapper throwerPlayer = DBCPlayerWrapper.getPlayer(this.player);

        if(throwerPlayer.compare(hitPlayer)){ //Return potara to player if they shot at themselves
            this.returnToPlayer();

            return;
        }

        if(hitPlayer.canUsePotara() && hitPlayer.willingToFuse()){
            throwerPlayer.fuseWith(hitPlayer, 30);
            InventoryUtil.removeItem(hitPlayer.getPlayer(), ItemRegistry.POTARA_CUSTOM_ITEM, 1);

            return;
        }

        this.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "This player either can't or isn't willing to undergo Potara Fusion"));

        this.returnToPlayer();
    }

    public Entity getDropEntity(){
        return new EntityItem(this.worldObj, this.posX, this.posY+0.5, this.posZ, ItemRegistry.POTARA_CUSTOM_ITEM.getItemStack());
    }

    private void returnToPlayer(){
        if(!this.player.capabilities.isCreativeMode)
            ItemRegistry.POTARA_CUSTOM_ITEM.give(this.player);
    }

    private void summonDrop(){
        this.worldObj.spawnEntityInWorld(getDropEntity());
    }



}
