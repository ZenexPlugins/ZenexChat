package com.zenex.chat.manager;

import com.zenex.chat.ZenexChat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    
    private final ZenexChat plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    
    public CooldownManager(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    public boolean isOnCooldown(Player player) {
        if (player.hasPermission("zenexchat.bypass")) return false;
        
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) return false;
        
        int cooldown = plugin.getConfig().getInt("chat.cooldown", 3);
        long timeLeft = (cooldowns.get(uuid) + cooldown * 1000L) - System.currentTimeMillis();
        return timeLeft > 0;
    }
    
    public int getRemainingSeconds(Player player) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) return 0;
        
        int cooldown = plugin.getConfig().getInt("chat.cooldown", 3);
        long timeLeft = (cooldowns.get(uuid) + cooldown * 1000L) - System.currentTimeMillis();
        return (int) Math.ceil(timeLeft / 1000.0);
    }
    
    public void setCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }
}
