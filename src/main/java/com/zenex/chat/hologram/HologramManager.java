package com.zenex.chat.hologram;

import com.zenex.chat.ZenexChat;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HologramManager {
    
    private final ZenexChat plugin;
    private final Map<UUID, HologramData> holograms = new HashMap<>();
    
    public HologramManager(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    public void showHologram(Player player, String message) {
        // Удаляем старую голограмму
        removeHologram(player);
        
        // Проверяем разрешение
        if (!player.hasPermission("zenexchat.hologram")) {
            return;
        }
        
        // Проверяем включена ли функция
        if (!plugin.getConfig().getBoolean("hologram.enabled", true)) {
            return;
        }
        
        // Загружаем настройки из hologram.yml
        double height = plugin.getConfig().getDouble("hologram.height", 1.5);
        int duration = plugin.getConfig().getInt("hologram.duration", 5);
        int radius = plugin.getConfig().getInt("hologram.radius", 50);
        boolean showToAll = plugin.getConfig().getBoolean("hologram.show-to-all", true);
        
        // Создаём голограмму
        Location loc = player.getLocation().add(0, height, 0);
        ArmorStand stand = player.getWorld().spawn(loc, ArmorStand.class);
        
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setMarker(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(plugin.getConfig().getString("hologram.format", "&f{player}\n&7{message}")
            .replace("{player}", player.getName())
            .replace("{message}", message)
            .replace("&", "§"));
        
        // Показываем голограмму игрокам в радиусе
        if (showToAll) {
            for (Player p : plugin.getServer().getOnlinePlayers()) {
                if (p.getLocation().distance(player.getLocation()) <= radius) {
                    p.sendMessage("§a💬 Голограмма появилась!");
                }
            }
        }
        
        // Сохраняем данные
        HologramData data = new HologramData(stand, duration);
        holograms.put(player.getUniqueId(), data);
        
        // Запускаем таймер удаления
        data.task = new BukkitRunnable() {
            @Override
            public void run() {
                removeHologram(player);
            }
        }.runTaskLater(plugin, duration * 20L);
    }
    
    public void removeHologram(Player player) {
        HologramData data = holograms.remove(player.getUniqueId());
        if (data != null) {
            if (data.task != null) {
                data.task.cancel();
            }
            if (data.stand != null) {
                data.stand.remove();
            }
        }
    }
    
    public void removeAll() {
        for (HologramData data : holograms.values()) {
            if (data.task != null) {
                data.task.cancel();
            }
            if (data.stand != null) {
                data.stand.remove();
            }
        }
        holograms.clear();
    }
    
    private class HologramData {
        ArmorStand stand;
        int duration;
        BukkitTask task;
        
        HologramData(ArmorStand stand, int duration) {
            this.stand = stand;
            this.duration = duration;
        }
    }
}
