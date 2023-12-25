package io.github.somehussar.potara.item;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import static net.minecraft.util.EnumChatFormatting.*;

public class PotaraCustomItem extends CustomItem {

    public PotaraCustomItem(){
        itemStack = new ItemStack(Items.experience_bottle, 1);
        itemStack.setStackDisplayName(""+ GREEN + BOLD + "Potara Earring!");

        NBTTagCompound item = itemStack.getTagCompound();
        if(item == null){
            item = new NBTTagCompound();
            itemStack.setTagCompound(item);
        }

        item.getCompoundTag("PotaraEarrings");
        item.setBoolean("isPotara", true);
        NBTTagCompound display = item.getCompoundTag("display");

        NBTTagList lore = new NBTTagList();
        lore.appendTag(new NBTTagString(DARK_PURPLE + "Test"));

        display.setTag("Lore", lore);
    }

    @Override
    public boolean compare(ItemStack stack){
        return stack != null && stack.getTagCompound().getBoolean("isPotara");
    }
}
