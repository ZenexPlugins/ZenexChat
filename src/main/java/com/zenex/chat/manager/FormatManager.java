package com.zenex.chat.manager;

import com.zenex.chat.ZenexChat;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FormatManager {
    
    private final ZenexChat plugin;
    private final Map<String, String> formats = new HashMap<>();
    
    public FormatManager(ZenexChat plugin) {
        this.plugin = plugin;
        loadFormats();
    }
    
    public void loadFormats() {
        File formatFolder = new File(plugin.getDataFolder(), "formats");
        if (!formatFolder.exists()) {
            formatFolder.mkdirs();
        }
        
        // Загружаем форматы из папки
        // TODO: Реализовать загрузку из файлов
        formats.put("default", "&7[&a{prefix}&7] {player} &7: &f{message}");
        formats.put("global", "&7[&6Глобал&7] {player} &7: &f{message}");
        formats.put("local", "&7[&aЛокальный&7] {player} &7: &f{message}");
        formats.put("voice", "&7[&b🎤&7] {player} &6: &7{message}");
    }
    
    public String getFormat(String name) {
        return formats.getOrDefault(name, formats.get("default"));
    }
    
    public String applyFormat(String format, Player player, String message) {
        return format
            .replace("{player}", player.getName())
            .replace("{displayname}", player.getDisplayName())
            .replace("{message}", message)
            .replace("&", "§");
    }
}
