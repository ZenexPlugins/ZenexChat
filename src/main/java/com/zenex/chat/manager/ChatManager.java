package com.zenex.chat.manager;

import com.zenex.chat.ZenexChat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatManager {
    
    private final ZenexChat plugin;
    private final Map<UUID, String> chatMode = new HashMap<>();
    
    public ChatManager(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    public void sendGlobalMessage(Player player, String message) {
        String format = plugin.getConfig().getString("messages.global-format", "&7[&6Глобал&7] &f{player} &7: &f{message}")
            .replace("{player}", player.getName())
            .replace("{message}", message)
            .replace("&", "§");
        
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            p.sendMessage(format);
        }
    }
    
    public void sendLocalMessage(Player player, String message) {
        int radius = plugin.getConfig().getInt("chat.local-radius", 50);
        String format = plugin.getConfig().getString("messages.local-format", "&7[&aЛокальный&7] &f{player} &7: &f{message}")
            .replace("{player}", player.getName())
            .replace("{message}", message)
            .replace("&", "§");
        
        for (Player p : plugin.getServer().getOnlinePlayers()) {
            if (p.getLocation().distance(player.getLocation()) <= radius) {
                p.sendMessage(format);
            }
        }
    }
    
    public void setChatMode(Player player, String mode) {
        chatMode.put(player.getUniqueId(), mode);
    }
    
    public String getChatMode(Player player) {
        return chatMode.getOrDefault(player.getUniqueId(), "LOCAL");
    }
}
