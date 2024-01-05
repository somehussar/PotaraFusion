package somehussar.potara;

import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class PotaraConfig {
    public static Configuration configuration;

    private static final String CATEGORY_ITEM = "POTARA_ITEM";
    private static final String CATEGORY_THROWN_ENTITY = "POTARA_ENTITY";
    private static final String CATEGORY_FUSION_SETTINGS = "FUSION_SETTINGS";

    public static String potaraName = EnumChatFormatting.GREEN.toString() + EnumChatFormatting.BOLD + "Potara Earring";
    public static String[] potaraLore = {
            EnumChatFormatting.LIGHT_PURPLE+"An earring used to create powerful, cross/race fusions.",
            "",
            EnumChatFormatting.AQUA+"Simply throw to use",
            "",
            EnumChatFormatting.GREEN + "Requirements: ",
            EnumChatFormatting.DARK_PURPLE+"-Level 10 fusion",
            EnumChatFormatting.DARK_PURPLE+"-Fusion enabled",
            EnumChatFormatting.DARK_PURPLE+"-Potara held by each player."
    };

    public static int potaraItemId = Item.getIdFromItem((Item) Item.itemRegistry.getObject("experience_bottle"));

    public static int potaraThrowSpeed = 15;


    public static int potaraFuseTime = 30;

    public static int potaraFusionLevelRequirement = 10;

    public static void init(String configDir){
        if(configuration == null){
            File path = new File(configDir+ "/" + PotaraMain.MODID + ".cfg");
            configuration = new Configuration(path);
            loadConfig();
        }
    }

    private static void loadConfig(){
        potaraLore = configuration.getStringList("Potara item lore: ", CATEGORY_ITEM, potaraLore, "Use \u00a7 instead of & for color codes.");
        potaraName = configuration.getString("Potara item name: ", CATEGORY_ITEM, potaraName, "");
        potaraItemId = configuration.get(CATEGORY_ITEM, "Potara item ID: ", potaraItemId, "").getInt();

        potaraFuseTime = configuration.getInt("Potara fuse time: ", CATEGORY_FUSION_SETTINGS, potaraFuseTime, 10, 120, "Fusion time in minutes.");
        potaraFusionLevelRequirement = configuration.getInt("Potara fusion lvl requirement: ", CATEGORY_FUSION_SETTINGS, potaraFusionLevelRequirement, 1, 10, "Fusion skill level requirement to be viable to attempt Potara Fusion");

        potaraThrowSpeed = configuration.getInt("Thrown potara speed", CATEGORY_THROWN_ENTITY, potaraThrowSpeed, 1, 30, "Speed at which the earring travels when thrown.");

        if(configuration.hasChanged()){
            configuration.save();
        }
    }

}
