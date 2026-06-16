package com.zenex.chat.manager;

import com.zenex.chat.ZenexChat;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {
    
    private final ZenexChat plugin;
    private final Map<String, ChatGame> games = new HashMap<>();
    private boolean gameActive = false;
    private String currentGame = null;
    
    public GameManager(ZenexChat plugin) {
        this.plugin = plugin;
        registerGames();
    }
    
    private void registerGames() {
        games.put("trivia", new TriviaGame());
        games.put("hangman", new HangmanGame());
    }
    
    public void startGame(Player player, String gameName) {
        if (gameActive) {
            player.sendMessage("§cИгра уже активна!");
            return;
        }
        
        ChatGame game = games.get(gameName.toLowerCase());
        if (game == null) {
            player.sendMessage("§cИгра не найдена! Доступны: " + String.join(", ", games.keySet()));
            return;
        }
        
        gameActive = true;
        currentGame = gameName;
        game.start(player);
        plugin.getServer().broadcastMessage("§a🎮 Игра '" + gameName + "' началась!");
    }
    
    public void endGame() {
        gameActive = false;
        currentGame = null;
        plugin.getServer().broadcastMessage("§c🎮 Игра закончена!");
    }
    
    public boolean isGameActive() {
        return gameActive;
    }
    
    public interface ChatGame {
        void start(Player host);
        void handleAnswer(Player player, String answer);
        void end();
    }
    
    private class TriviaGame implements ChatGame {
        @Override
        public void start(Player host) {
            plugin.getServer().broadcastMessage("§a❓ Викторина началась! Ответьте на вопрос:");
            plugin.getServer().broadcastMessage("§eСколько будет 2+2?");
        }
        
        @Override
        public void handleAnswer(Player player, String answer) {
            if (answer.equals("4")) {
                plugin.getServer().broadcastMessage("§a🎉 " + player.getName() + " победил в викторине!");
                endGame();
            }
        }
        
        @Override
        public void end() {
            gameActive = false;
        }
    }
    
    private class HangmanGame implements ChatGame {
        @Override
        public void start(Player host) {
            plugin.getServer().broadcastMessage("§a🔤 Виселица началась! Угадайте слово:");
            plugin.getServer().broadcastMessage("§e_ _ _ _ _ (5 букв)");
        }
        
        @Override
        public void handleAnswer(Player player, String answer) {
            if (answer.equalsIgnoreCase("apple")) {
                plugin.getServer().broadcastMessage("§a🎉 " + player.getName() + " угадал слово 'apple'!");
                endGame();
            }
        }
        
        @Override
        public void end() {
            gameActive = false;
        }
    }
}
