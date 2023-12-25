package io.github.somehussar.potara.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class ItemStackWrapper {

    protected ItemStack itemStack;


    public void give(EntityPlayer player){
        give(player, 1);
    }

    public void give(EntityPlayer player, int amount){
        if(amount <= 0){
            return;
        }

        ItemStack stack = getItemStack();
        stack.stackSize = amount;

        EntityItem entityitem = player.dropPlayerItemWithRandomChoice(stack, false);
        entityitem.delayBeforeCanPickup = 0;
        entityitem.func_145797_a(player.getCommandSenderName());
    }

    public ItemStack getItemStack(){
        return itemStack.copy();
    }

    public boolean compare(ItemStack itemStack){
        return ItemStack.areItemStacksEqual(this.itemStack, itemStack);
    }
}
