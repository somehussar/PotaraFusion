package somehussar.potara.command;

import somehussar.potara.item.PotaraItemWrapper;
import somehussar.potara.player.ChatMessageHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class GivePotara extends CommandBase {
    @Override
    public String getCommandName() {
        return "givepotara";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/givepotara [name]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(!(sender instanceof EntityPlayer)){
            ChatMessageHelper.sendMessage(sender, "Command can only be ran by a player.", EnumChatFormatting.RED);
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        PotaraItemWrapper.give(player);
        ChatMessageHelper.sendMessage(sender, "Successfully given a Potara item!", EnumChatFormatting.GREEN);
    }
}
