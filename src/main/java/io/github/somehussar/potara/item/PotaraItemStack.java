package io.github.somehussar.potara.item;

import io.github.somehussar.potara.PotaraConfig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class PotaraItemStack extends ItemStackWrapper {

    public PotaraItemStack(){
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

    //Custom comparison check for custom/outdated potara earrings
    @Override
    public boolean compare(ItemStack stack){
        return stack != null && stack.stackTagCompound != null && stack.stackTagCompound.getBoolean("isPotara");
    }

}
