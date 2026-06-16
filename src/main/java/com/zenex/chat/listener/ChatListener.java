package com.zenex.chat.listener;

import com.zenex.chat.ZenexChat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    
    private final ZenexChat plugin;
    
    public ChatListener(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        
        // Отменяем стандартную отправку
        event.setCancelled(true);
        
        // Проверка кулдауна
        if (plugin.getCooldownManager().isOnCooldown(player)) {
            int remaining = plugin.getCooldownManager().getRemainingSeconds(player);
            String msg = plugin.getConfig().getString("messages.cooldown", "&c❌ Подождите {time} секунд!")
                .replace("{time}", String.valueOf(remaining))
                .replace("&", "§");
            player.sendMessage(msg);
            return;
        }
        
        // Проверка на глобальный чат
        String globalSymbol = plugin.getConfig().getString("chat.global-symbol", "!");
        if (message.startsWith(globalSymbol)) {
            String actualMessage = message.substring(1).trim();
            if (!actualMessage.isEmpty() && player.hasPermission("zenexchat.global")) {
                plugin.getChatManager().sendGlobalMessage(player, actualMessage);
                plugin.getCooldownManager().setCooldown(player);
                plugin.getHologramManager().showHologram(player, actualMessage);
                return;
            }
        }
        
        // Локальный чат
        if (player.hasPermission("zenexchat.local")) {
            plugin.getChatManager().sendLocalMessage(player, message);
            plugin.getCooldownManager().setCooldown(player);
            plugin.getHologramManager().showHologram(player, message);
        }
    }
}
