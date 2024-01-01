package io.github.somehussar.potara.command;

import io.github.somehussar.potara.item.PotaraItemWrapper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

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
            sendMessage(sender, "Command can only be ran by a player.", EnumChatFormatting.RED);
            return;
        }
        EntityPlayer player = (EntityPlayer) sender;
        PotaraItemWrapper.give(player);
        sendMessage(sender, "Successfully given a Potara item!", EnumChatFormatting.GREEN);
    }

    private void sendMessage(ICommandSender target, String message, EnumChatFormatting color){
        if(message == null){
            return;
        }
        IChatComponent chatComponent = new ChatComponentText(message);
        if(color != null)
            chatComponent.getChatStyle().setColor(color);
        target.addChatMessage(chatComponent);
    }
}
