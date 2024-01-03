package io.github.somehussar.potara.player;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatMessageHelper {
    public static void sendMessage(ICommandSender target, String message, EnumChatFormatting color){
        if(message == null){
            return;
        }
        ChatComponentText chatComponent = new ChatComponentText(message);
        if(color != null)
            chatComponent.getChatStyle().setColor(color);
        target.addChatMessage(chatComponent);
    }
}
