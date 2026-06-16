package com.zenex.chat;

import com.zenex.chat.command.*;
import com.zenex.chat.listener.ChatListener;
import com.zenex.chat.manager.*;
import com.zenex.chat.hologram.HologramManager;
import com.zenex.chat.voice.VoiceChatManager;
import com.zenex.chat.utils.PlaceholderHook;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ZenexChat extends JavaPlugin {
    
    private static ZenexChat instance;
    private ChatManager chatManager;
    private HologramManager hologramManager;
    private VoiceManager voiceManager;
    private VoiceChatManager voiceChatManager;
    private GameManager gameManager;
    private FormatManager formatManager;
    private CooldownManager cooldownManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        printStartupArt();
        
        saveDefaultConfig();
        saveResource("messages.yml", false);
        saveResource("hologram.yml", false);
        
        // Инициализация менеджеров
        chatManager = new ChatManager(this);
        hologramManager = new HologramManager(this);
        voiceManager = new VoiceManager(this);
        voiceChatManager = new VoiceChatManager(this);
        gameManager = new GameManager(this);
        formatManager = new FormatManager(this);
        cooldownManager = new CooldownManager(this);
        
        // Регистрация команд
        getCommand("chat").setExecutor(new ChatCommand(this));
        getCommand("g").setExecutor(new GlobalCommand(this));
        getCommand("l").setExecutor(new LocalCommand(this));
        getCommand("voice").setExecutor(new VoiceCommand(this));
        getCommand("vc").setExecutor(new VoiceChatCommand(this));
        
        // Регистрация слушателей
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        
        // PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderHook(this).register();
            getLogger().info("✅ PlaceholderAPI hook enabled!");
        }
        
        // SimpleVoiceChat
        if (Bukkit.getPluginManager().getPlugin("SimpleVoiceChat") != null) {
            getLogger().info("✅ SimpleVoiceChat integration enabled!");
        }
        
        getLogger().info("✅ ZenexChat v" + getDescription().getVersion() + " enabled!");
    }
    
    @Override
    public void onDisable() {
        if (hologramManager != null) {
            hologramManager.removeAll();
        }
        getLogger().info("ZenexChat disabled!");
    }
    
    private void printStartupArt() {
        String[] art = {
            "§b╔══════════════════════════════════════════════════════════════════╗",
            "§b║                                                                  ║",
            "§b║   §6██████╗  ██████╗                                           §b║",
            "§b║   §6██╔══██╗██╔═══██╗                                          §b║",
            "§b║   §6██████╔╝██║   ██║                                          §b║",
            "§b║   §6██╔═══╝ ██║   ██║                                          §b║",
            "§b║   §6██║     ╚██████╔╝                                          §b║",
            "§b║   §6╚═╝      ╚═════╝                                           §b║",
            "§b║                                                                  ║",
            "§b║   §6§lZP §f§lZenexChat §ev" + getDescription().getVersion() + "           §fBy §eZenexPlugins   §b║",
            "§b║   §7💬 Система чата с голосовыми сообщениями                   §b║",
            "§b║                                                                  ║",
            "§b╚══════════════════════════════════════════════════════════════════╝"
        };
        
        for (String line : art) {
            getLogger().info(line.replace("§", ""));
        }
    }
    
    public static ZenexChat getInstance() {
        return instance;
    }
    
    public ChatManager getChatManager() {
        return chatManager;
    }
    
    public HologramManager getHologramManager() {
        return hologramManager;
    }
    
    public VoiceManager getVoiceManager() {
        return voiceManager;
    }
    
    public VoiceChatManager getVoiceChatManager() {
        return voiceChatManager;
    }
    
    public GameManager getGameManager() {
        return gameManager;
    }
    
    public FormatManager getFormatManager() {
        return formatManager;
    }
    
    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}
