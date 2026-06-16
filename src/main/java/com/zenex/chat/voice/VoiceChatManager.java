package com.zenex.chat.voice;

import com.zenex.chat.ZenexChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VoiceChatManager {
    
    private final ZenexChat plugin;
    private final Map<UUID, Boolean> voiceEnabled = new HashMap<>();
    private boolean voiceChatInstalled = false;
    
    public VoiceChatManager(ZenexChat plugin) {
        this.plugin = plugin;
        this.voiceChatInstalled = Bukkit.getPluginManager().getPlugin("SimpleVoiceChat") != null;
        if (voiceChatInstalled) {
            plugin.getLogger().info("✅ SimpleVoiceChat integration enabled!");
        }
    }
    
    public boolean isVoiceChatInstalled() {
        return voiceChatInstalled;
    }
    
    public void enableVoice(Player player) {
        voiceEnabled.put(player.getUniqueId(), true);
        player.sendMessage("§a✅ Голосовой чат включён!");
    }
    
    public void disableVoice(Player player) {
        voiceEnabled.remove(player.getUniqueId());
        player.sendMessage("§c❌ Голосовой чат выключен!");
    }
    
    public boolean isVoiceEnabled(Player player) {
        return voiceEnabled.getOrDefault(player.getUniqueId(), false);
    }
    
    public void joinVoiceChannel(Player player, String channel) {
        if (!voiceChatInstalled) {
            player.sendMessage("§c❌ SimpleVoiceChat не установлен!");
            return;
        }
        player.sendMessage("§a✅ Вы присоединились к голосовому каналу: §6" + channel);
    }
    
    public void leaveVoiceChannel(Player player) {
        if (!voiceChatInstalled) {
            player.sendMessage("§c❌ SimpleVoiceChat не установлен!");
            return;
        }
        player.sendMessage("§c❌ Вы покинули голосовой канал!");
    }
}
