package com.pekly.quests;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Quest {
    private String name;
    private String description;
    private int targetAmount;
    private int currentAmount;
    private double rewardAmount;
    private Economy economy;

    public Quest(String name, String description, int targetAmount, double rewardAmount) {
        this.name = name;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentAmount = 0;
        this.rewardAmount = rewardAmount;
        setupEconomy();
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        economy = rsp != null ? rsp.getProvider() : null;
        if (economy == null) {
            Bukkit.getLogger().warning("Vault economy could not be initialized. Please ensure Vault and an economy plugin are installed.");
        }
    }

    public void addItem(Player player, int amount) {
        currentAmount += amount;
        if (currentAmount >= targetAmount) {
            completeQuest(player);
        }
    }

    private void completeQuest(Player player) {
        if (economy == null) {
            player.sendMessage("Economy system is not available. Cannot complete quest.");
            return; // Early exit if economy is not set up
        }

        EconomyResponse response = economy.depositPlayer(player, rewardAmount);
        if (response.transactionSuccess()) {
            player.sendMessage("Quest " + name + " completed! You received: " + rewardAmount + " currency.");
        } else {
            player.sendMessage("An error occurred while processing your reward.");
        }
    }
}