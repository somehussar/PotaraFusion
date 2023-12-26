package io.github.somehussar.potara;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class PotaraConfig {
    public static Configuration configuration;

    private static final String CATEGORY_ITEM = "POTARA_ITEM";
    private static final String CATEGORY_FUSION = "FUSION_TIMERS";

    public static String potaraName = EnumChatFormatting.GREEN.toString() + EnumChatFormatting.BOLD + "Potara Earring";
    public static String[] potaraLore = {
            EnumChatFormatting.DARK_PURPLE+"An earring used to create powerful, cross/race fusions."
    };

    public static int potaraFuseTime = 30;

    public static void init(String configDir){
        if(configuration == null){
            File path = new File(configDir+ "/" + PotaraMain.MODID + ".cfg");
            configuration = new Configuration(path);
            loadConfig();
        }
    }

    private static void loadConfig(){
        potaraName = configuration.getString("Potara item name: ", CATEGORY_ITEM, potaraName, "");
        potaraLore = configuration.getStringList("Potara item lore: ", CATEGORY_ITEM, potaraLore, "Use \u00a7 instead of & for color codes.");

        potaraFuseTime = configuration.getInt("Potara fuse time: ", CATEGORY_FUSION, potaraFuseTime, 10, 120, "Fusion time in minutes.");

        if(configuration.hasChanged()){
            configuration.save();
        }
    }

}
