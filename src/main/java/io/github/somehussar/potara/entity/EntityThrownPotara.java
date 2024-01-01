package io.github.somehussar.potara.entity;

import io.github.somehussar.potara.PotaraConfig;
import io.github.somehussar.potara.item.PotaraItemWrapper;
import io.github.somehussar.potara.player.DBCPlayerWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import noppes.npcs.entity.EntityProjectile;

public class EntityThrownPotara extends EntityProjectile {

    private EntityPlayerMP player;

    public EntityThrownPotara(World p_i1785_1_) {
        super(p_i1785_1_);
        this.summonDrop();
        this.setDead();
    }
    public EntityThrownPotara(World world, EntityLivingBase entity) {
        super(world, entity, PotaraItemWrapper.getItemStack(), false);
        this.setHasGravity(true);
        this.shoot(2);
        this.player = (EntityPlayerMP) entity;
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
                this.player.addChatComponentMessage(new ChatComponentText( "You missed!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));
            this.summonDrop();
            return;
        }

        DBCPlayerWrapper hitPlayer = DBCPlayerWrapper.getPlayer((EntityPlayer) object.entityHit);
        DBCPlayerWrapper throwerPlayer = DBCPlayerWrapper.getPlayer(this.player);

        if(throwerPlayer.compare(hitPlayer)){ //Return potara to player if they shot at themselves
            this.returnToPlayer();

            return;
        }

        if(hitPlayer.canUsePotara() && hitPlayer.willingToFuse() && hitPlayer.hasPotaraInHand() && throwerPlayer.canUsePotara()){
            throwerPlayer.fuseWith(hitPlayer, PotaraConfig.potaraFuseTime);

            ItemStack heldItem = hitPlayer.getPlayer().getHeldItem();
            if(heldItem.stackSize > 1)
               heldItem.stackSize--;
            else
                hitPlayer.getPlayer().setCurrentItemOrArmor(0, null);
            return;
        }

        this.player.addChatComponentMessage(new ChatComponentText("This player either can't or isn't willing to undergo Potara Fusion").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW)));

        this.returnToPlayer();
    }

    public Entity getDropEntity(){
        return new EntityItem(this.worldObj, this.posX, this.posY+0.5, this.posZ, PotaraItemWrapper.getItemStack());
    }

    private void returnToPlayer(){
        if(!this.player.capabilities.isCreativeMode)
            PotaraItemWrapper.give(this.player);
    }

    private void summonDrop(){
        this.worldObj.spawnEntityInWorld(getDropEntity());
    }



}
