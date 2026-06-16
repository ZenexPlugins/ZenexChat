package com.zenex.chat.manager;

import com.zenex.chat.ZenexChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VoiceManager {
    
    private final ZenexChat plugin;
    private final Map<UUID, Long> voiceCooldown = new HashMap<>();
    
    public VoiceManager(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    public void sendVoiceMessage(Player player, String message) {
        UUID uuid = player.getUniqueId();
        
        // Проверка кулдауна
        if (voiceCooldown.containsKey(uuid)) {
            long last = voiceCooldown.get(uuid);
            if (System.currentTimeMillis() - last < 5000) {
                player.sendMessage("§cПодождите 5 секунд между голосовыми сообщениями!");
                return;
            }
        }
        
        voiceCooldown.put(uuid, System.currentTimeMillis());
        
        String format = plugin.getConfig().getString("voice.format", "&7[&b🎤&7] {player} &6: &7{message}")
            .replace("{player}", player.getName())
            .replace("{message}", message)
            .replace("&", "§");
        
        // Отправляем всем в радиусе
        int radius = plugin.getConfig().getInt("chat.local-radius", 50);
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getLocation().distance(player.getLocation()) <= radius) {
                p.sendMessage(format);
            }
        }
        
        // Воспроизводим звук
        if (plugin.getConfig().getBoolean("voice.sound-enabled", true)) {
            String sound = plugin.getConfig().getString("voice.sound", "ENTITY_PLAYER_LEVELUP");
            try {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getLocation().distance(player.getLocation()) <= radius) {
                        p.playSound(p.getLocation(), org.bukkit.Sound.valueOf(sound), 1.0f, 1.0f);
                    }
                }
            } catch (IllegalArgumentException ignored) {}
        }
    }
}
