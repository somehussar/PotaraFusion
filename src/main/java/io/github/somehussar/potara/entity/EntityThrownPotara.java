package io.github.somehussar.potara.entity;

import io.github.somehussar.potara.item.ItemRegistry;
import io.github.somehussar.potara.player.DBCPlayerWrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
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
        if(this.worldObj.isRemote){
            return;
        }

        if(player == null){
            summonDrop();
            return;
        }

        MovingObjectPosition.MovingObjectType hitType = object.typeOfHit;

        player.addChatComponentMessage(new ChatComponentText("HIT! Impact type: "+hitType));

        if(hitType != MovingObjectPosition.MovingObjectType.ENTITY){
            summonDrop();
            return;
        }

        Entity hitEntity = object.entityHit;

        if(!(hitEntity instanceof EntityPlayerMP)){ //Summon drop and return if it didn't hit any player
            summonDrop();
            return;
        }

        if(hitEntity == (Entity) player){
            player.addChatComponentMessage(new ChatComponentText("Bruh... Really trying to fuse with yourself?"));
            if(!player.capabilities.isCreativeMode)
                ItemRegistry.POTARA_CUSTOM_ITEM.give(player);
            player.addChatComponentMessage(new ChatComponentText(""+ DBCPlayerWrapper.getPlayer(player).hasNoFuse()+" "+DBCPlayerWrapper.getPlayer(player).willingToFuse()));
            this.setDead();
            return;
        }
        this.setDead();
    }

    public Entity getDropEntity(){
        return new EntityItem(this.worldObj, this.posX, this.posY+0.5, this.posZ, ItemRegistry.POTARA_CUSTOM_ITEM.getItemStack());
    }
    private void summonDrop(){
        this.worldObj.spawnEntityInWorld(getDropEntity());
        this.setDead();
    }



}
