package io.github.somehussar.potara.entity;

import io.github.somehussar.potara.PotaraMain;
import io.github.somehussar.potara.item.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityThrownPotara extends EntityExpBottle {

    private EntityPlayerMP player;
    private ItemStack stack;
    public EntityThrownPotara(World p_i1779_1_) {
        super(p_i1779_1_);
    }

    public EntityThrownPotara(World world, EntityLivingBase entity, ItemStack stack){
        super(world, entity);
        this.player = (EntityPlayerMP) entity;
        this.stack = stack;
        this.motionX *= 2;
        this.motionY *= 2;
        this.motionZ *= 2;
    }
    public EntityThrownPotara(World world, double x, double y, double z){
        super(world, x, y, z);
    }


    @Override
    protected void onImpact(MovingObjectPosition object){
        if(this.worldObj.isRemote){
            return;
        }

        MovingObjectPosition.MovingObjectType hitType = object.typeOfHit;

        player.addChatComponentMessage(new ChatComponentText("HIT! Impact type: "+hitType));

        Entity hitEntity = object.entityHit;

        if(!(hitEntity instanceof EntityPlayerMP)){ //Summon drop and return if it didn't hit any player
            summonDrop();
            return;
        }

        this.setDead();
    }


    private void summonDrop(){
        Entity drop = new EntityItem(this.worldObj, this.posX, this.posY+0.5, this.posZ, this.stack);
        this.worldObj.spawnEntityInWorld(drop);
        this.setDead();
    }



}
