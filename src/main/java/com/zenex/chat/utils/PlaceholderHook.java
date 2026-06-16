package com.zenex.chat.utils;

import com.zenex.chat.ZenexChat;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class PlaceholderHook extends PlaceholderExpansion {
    
    private final ZenexChat plugin;
    
    public PlaceholderHook(ZenexChat plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public String getIdentifier() {
        return "chat";
    }
    
    @Override
    public String getAuthor() {
        return "ZenexPlugins";
    }
    
    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }
    
    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (player == null) return "";
        
        switch (params.toLowerCase()) {
            case "prefix":
                return "§7[§aЧат§7]";
            case "suffix":
                return "";
            case "global":
                return plugin.getConfig().getString("chat.global-symbol", "!");
            default:
                return "";
        }
    }
}
