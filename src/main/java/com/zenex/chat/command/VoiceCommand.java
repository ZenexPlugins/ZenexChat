package com.zenex.chat.command;

import com.zenex.chat.ZenexChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoiceCommand implements CommandExecutor {
    
    private final ZenexChat plugin;
    
    public VoiceCommand(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cТолько игроки!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§c/voice <сообщение>");
            return true;
        }
        
        if (!player.hasPermission("zenexchat.voice")) {
            player.sendMessage("§cУ вас нет прав на голосовые сообщения!");
            return true;
        }
        
        String message = String.join(" ", args);
        plugin.getVoiceManager().sendVoiceMessage(player, message);
        
        return true;
    }
}
