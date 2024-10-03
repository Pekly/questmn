package com.pekly;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class QuestMN extends JavaPlugin {
    private static Economy economy = null; // Declare the Economy variable

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("QuestMN has been enabled!");

        // Setup Vault economy
        setupEconomy();

        // Register commands, listeners, etc.
        if (economy != null) {
            getLogger().info("Vault economy successfully set up!");
        } else {
            getLogger().warning("Vault economy setup failed. Make sure Vault is installed.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("QuestMN has been disabled!");
    }

    // Method to setup the economy
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        }
        return (economy != null);
    }

    // Getter for the economy instance
    public static Economy getEconomy() {
        return economy;
    }
}