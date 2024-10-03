package com.pekly.questmn;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class QuestManager {
    private Map<Player, Quest> playerQuests;
    private Quest sampleQuest;
    private Economy economy;

    public QuestManager(Main plugin) {
        playerQuests = new HashMap<>();
        sampleQuest = new Quest("The First Quest", "Collect 5 wood.", 5, 100.0);
        setupEconomy(plugin);
    }

    private void setupEconomy(Main plugin) {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        } else {
            plugin.getLogger().warning("Vault economy could not be initialized. Please ensure Vault and an economy plugin are installed.");
        }
    }

    public Quest getQuestForPlayer(Player player) {
        return playerQuests.get(player);
    }

    public void assignQuest(Player player) {
        playerQuests.put(player, sampleQuest);
        player.sendMessage("You have been assigned a quest: " + sampleQuest.getName());
        player.sendMessage("Description: " + sampleQuest.getDescription());
    }

    public void addItemToQuest(Player player, int amount) {
        Quest quest = playerQuests.get(player);
        if (quest != null) {
            quest.addItem(player, amount); // Update quest progress
        } else {
            player.sendMessage("You have not been assigned a quest.");
        }
    }

    public void completeQuest(Player player) {
        Quest quest = playerQuests.get(player);
        if (quest != null && quest.getCurrentAmount() >= quest.getTargetAmount()) {
            quest.completeQuest(player);
            playerQuests.remove(player); // Remove completed quest
        } else {
            player.sendMessage("You have not completed the quest or it has not been assigned to you.");
        }
    }
}