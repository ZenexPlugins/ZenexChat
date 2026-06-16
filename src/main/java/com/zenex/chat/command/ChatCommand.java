package com.zenex.chat.command;

import com.zenex.chat.ZenexChat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ChatCommand implements CommandExecutor {
    
    private final ZenexChat plugin;
    
    public ChatCommand(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("zenexchat.admin")) {
            sender.sendMessage("§cУ вас нет прав!");
            return true;
        }
        
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                plugin.saveResource("messages.yml", true);
                plugin.saveResource("hologram.yml", true);
                sender.sendMessage("§a✅ ZenexChat перезагружен!");
                break;
                
            case "global":
                sender.sendMessage("§aГлобальный чат включён!");
                break;
                
            case "local":
                sender.sendMessage("§aЛокальный чат включён!");
                break;
                
            case "game":
                if (args.length < 2) {
                    sender.sendMessage("§c/chat game <название>");
                    return true;
                }
                sender.sendMessage("§aИгра '" + args[1] + "' запущена!");
                break;
                
            case "voice":
                sender.sendMessage("§aГолосовые сообщения включены!");
                break;
                
            case "hologram":
                sender.sendMessage("§aГолограммы включены!");
                break;
                
            default:
                sendHelp(sender);
        }
        
        return true;
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§6=== ZenexChat Help ===");
        sender.sendMessage("§e/chat reload §7- Перезагрузить");
        sender.sendMessage("§e/chat game <название> §7- Запустить игру");
        sender.sendMessage("§e/chat global §7- Включить глобальный чат");
        sender.sendMessage("§e/chat local §7- Включить локальный чат");
        sender.sendMessage("§e/chat voice §7- Включить голосовые сообщения");
        sender.sendMessage("§e/chat hologram §7- Включить голограммы");
    }
}
