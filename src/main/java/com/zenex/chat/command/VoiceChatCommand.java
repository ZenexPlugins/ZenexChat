package com.zenex.chat.command;

import com.zenex.chat.ZenexChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VoiceChatCommand implements CommandExecutor {
    
    private final ZenexChat plugin;
    
    public VoiceChatCommand(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cТолько игроки!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("zenexchat.vc")) {
            player.sendMessage("§cУ вас нет прав на голосовой чат!");
            return true;
        }
        
        if (args.length == 0) {
            sendHelp(player);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "включить":
            case "on":
            case "enable":
                plugin.getVoiceChatManager().enableVoice(player);
                break;
                
            case "выключить":
            case "off":
            case "disable":
                plugin.getVoiceChatManager().disableVoice(player);
                break;
                
            case "присоединиться":
            case "join":
                if (args.length < 2) {
                    player.sendMessage("§c/vc присоединиться <канал>");
                    return true;
                }
                plugin.getVoiceChatManager().joinVoiceChannel(player, args[1]);
                break;
                
            case "выйти":
            case "leave":
                plugin.getVoiceChatManager().leaveVoiceChannel(player);
                break;
                
            default:
                sendHelp(player);
        }
        
        return true;
    }
    
    private void sendHelp(Player player) {
        player.sendMessage("§6=== ZenexChat Voice Help ===");
        player.sendMessage("§e/vc включить §7- Включить голосовой чат");
        player.sendMessage("§e/vc выключить §7- Выключить голосовой чат");
        player.sendMessage("§e/vc присоединиться <канал> §7- Присоединиться к каналу");
        player.sendMessage("§e/vc выйти §7- Выйти из канала");
    }
}
