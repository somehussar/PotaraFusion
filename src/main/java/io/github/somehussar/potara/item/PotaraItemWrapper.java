package io.github.somehussar.potara.item;

import io.github.somehussar.potara.PotaraConfig;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class PotaraItemWrapper {

    protected static ItemStack itemStack;

    public static ItemStack getItemStack(){
        return itemStack.copy();
    }

    public static void give(EntityPlayer player){
        give(player, 1);
    }

    public static void give(EntityPlayer player, int amount){
        if(amount <= 0){
            return;
        }

        ItemStack stack = getItemStack();
        stack.stackSize = amount;

        if(player.inventory.getFirstEmptyStack() == -1 && !player.inventory.hasItemStack(stack)) {
            EntityItem entityitem = player.dropPlayerItemWithRandomChoice(stack, false);
            entityitem.delayBeforeCanPickup = 0;
            entityitem.func_145797_a(player.getCommandSenderName());
        }else{
            player.inventory.addItemStackToInventory(stack);
        }
    }

    /**
     * Creates a new itemstack that has correct item info loaded from configs
     */
    public static void init(){
        itemStack = new ItemStack(Items.experience_bottle, 1);
        itemStack.setStackDisplayName(PotaraConfig.potaraName);

        NBTTagCompound item = itemStack.getTagCompound();
        if(item == null){
            item = new NBTTagCompound();
            itemStack.setTagCompound(item);
        }

        item.getCompoundTag("PotaraEarrings");
        item.setBoolean("isPotara", true);
        NBTTagCompound display = item.getCompoundTag("display");

        NBTTagList lore = new NBTTagList();

        for(String s : PotaraConfig.potaraLore){
            lore.appendTag(new NBTTagString(s));
        }

        display.setTag("Lore", lore);
    }

    //Custom comparison check for custom/outdated/corrupted potara earrings
    public static boolean compare(ItemStack stack){
        return stack != null && stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("isPotara");
    }

}
