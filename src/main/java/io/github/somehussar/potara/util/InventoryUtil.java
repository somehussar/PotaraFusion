package io.github.somehussar.potara.util;

import io.github.somehussar.potara.item.ItemStackWrapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryUtil {
    private InventoryUtil(){}

    public static void removeItem(EntityPlayer player, ItemStackWrapper item, int amount){
        ItemStack[] mainInv = player.inventory.mainInventory;
        for(int i = 0; i < mainInv.length; i++){
            ItemStack is = player.inventory.mainInventory[i];
            if(item.compare(is)){
                if(amount > is.stackSize){
                    amount -= is.stackSize;
                    mainInv[i] = null;
                }else{
                    is.splitStack(amount);
                    break;
                }
            }
        }
        player.inventoryContainer.detectAndSendChanges();
    }

    public static void removeItem(EntityPlayer player, ItemStack item, int amount){
        removeItem(player, item, amount, true, true);
    }

    public static void removeItem(EntityPlayer player, ItemStack item, int amount, boolean ignoreNBT, boolean ignoreDamage) {
        int count = inventoryItemCount(player, item);
        if (amount > count)
            return;
        if (count == amount) {
            removeAllItems(player, item);
        } else {
            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                ItemStack is = player.inventory.mainInventory[i];
                if (is != null && compareItems(is, item, ignoreDamage, ignoreNBT))
                    if (amount > is.stackSize) {
                        player.inventory.mainInventory[i] = null;
                        amount -= is.stackSize;
                    } else {
                        is.splitStack(amount);
                        break;
                    }
            }
        }
        player.inventoryContainer.detectAndSendChanges();
    }

    public static void removeAllItems(EntityPlayer player, ItemStack item){
        removeAllItems(player, item, true, true);
    }

    public static void removeAllItems(EntityPlayer player, ItemStack item, boolean ignoreNBT, boolean ignoreDamage){
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack is = player.inventory.mainInventory[i];
            if (is != null && compareItems(is, item, ignoreDamage, ignoreNBT)) {
                player.inventory.mainInventory[i] = null;
            }
        }
    }

    public static int inventoryItemCount(EntityPlayer player, ItemStack item){
        return inventoryItemCount(player, item, true, true);
    }

    public static int inventoryItemCount(EntityPlayer player, ItemStack item, boolean ignoreNBT, boolean ignoreDamage){
        int i = 0;
        for(ItemStack is : player.inventory.mainInventory){
            if(is != null && compareItems(is, item, ignoreNBT, ignoreDamage))
                i += is.stackSize;
        }
        return i;
    }

    @SuppressWarnings({"deprecation"})
    public static boolean compareItems(ItemStack item, ItemStack item2, boolean ignoreNBT, boolean ignoreDamage){
        if (item2 == null || item == null)
            return false;
        OreDictionary.itemMatches(item, item2, false);
        int[] ids = OreDictionary.getOreIDs(item);
        for (int id : ids) {
            boolean match1 = false, match2 = false;
            for (ItemStack is : OreDictionary.getOres(id)) {
                if (compareItemDetails(item, is, ignoreDamage, ignoreNBT))
                    match1 = true;
                if (compareItemDetails(item2, is, ignoreDamage, ignoreNBT))
                    match2 = true;
            }
            if (match1 && match2)
                return true;
        }
        return compareItemDetails(item, item2, ignoreDamage, ignoreNBT);
    }

    public static boolean compareItemDetails(ItemStack item, ItemStack item2, boolean ignoreNBT, boolean ignoreDamage){
        if(item.getItem() != item2.getItem())
            return false;
        if(!ignoreDamage && item.getItemDamage() != -1 && item.getItemDamage() == item2.getItemDamage())
            return false;
        if (!ignoreNBT && item.stackTagCompound != null && (item2.stackTagCompound == null || !item.stackTagCompound.equals(item2.stackTagCompound)))
            return false;
        if(!ignoreNBT && item2.stackTagCompound != null && item.stackTagCompound == null)
            return false;
        return true;
    }
}
